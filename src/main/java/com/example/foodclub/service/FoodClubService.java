package com.example.foodclub.service;

import com.example.foodclub.error.ResourceNotFoundException;
import com.example.foodclub.model.*;
import com.example.foodclub.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class FoodClubService {

    private final PurchaseRepository purchaseRepository;
    private final UsermapRepository usermapRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CounterRepository counterRepository;
    private final PromoRepository promoRepository;

    public void initData() {

        Usermap usermap = new Usermap("213213", "123241243", 1244524L);

//        Random random = new Random();
//
//        List<Purchase> purchaseList1 = IntStream.range(0, 5)
//                .mapToObj(i -> new Purchase(LocalDate.now(), i, "WH sku", random.nextDouble(), usermap))
//                .collect(Collectors.toList());
//
//        Promo promo1 = new Promo("$off voucher", LocalDate.now(), LocalDate.now(), "89000088823423433333", "valid", usermap);
////        purchaseList1.forEach(promo1::addPurchase);
//        purchaseList1.forEach(purchaseRepository::save);
//        Promo savedPromo1 = promoRepository.save(promo1);
//
//        purchaseList1.forEach(purchase -> purchase.setPromo(savedPromo1));
//
//        List<Purchase> purchaseList2 = IntStream.range(0, 5)
//                .mapToObj(i -> new Purchase(LocalDate.now(), i, "WH sku", random.nextDouble(), usermap))
//                .collect(Collectors.toList());
//
//        Promo promo2 = new Promo("$off voucher", LocalDate.now(), LocalDate.now(), "89000088823425555555", "valid", usermap);
////        purchaseList2.forEach(promo2::addPurchase);
//        purchaseList2.forEach(purchaseRepository::save);
//        Promo savedPromo2 = promoRepository.save(promo2);
//
//        purchaseList2.forEach(purchase -> purchase.setPromo(savedPromo2));
//
        Enrollment enrolled = enrollmentRepository.save(new Enrollment("enrolled", LocalDate.now()));
//        Counter counter = counterRepository.save(new Counter(1, 0));
//
        usermap.setEnrollment(enrolled);
//        usermap.setCounter(counter);

        usermapRepository.save(usermap);
    }

    public Usermap saveUserData(Usermap newUsermap) {

        Usermap savedUsermap = usermapRepository.save(newUsermap);

        newUsermap.getPurchases().stream().map(purchase -> {
            purchase.setUsermap(savedUsermap);
            return purchase;
        }).forEach(purchaseRepository::save);

        return savedUsermap;
    }

    public List<Purchase> getPurchasesByUser(Long userId) {
        return purchaseRepository.findByUsermapId(userId);
    }

    public Purchase createPurchase(Long userId, Purchase purchase) throws ResourceNotFoundException {
        return usermapRepository.findById(userId).map(usermap -> {

            boolean createPromo = incrementPunchCount(usermap, purchase);

            if (createPromo && isUserEnrolled(usermap)) {
                createPromo(usermap, purchase);
            }

            purchase.setUsermap(usermap);
            return purchaseRepository.save(purchase);
        }).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    private boolean incrementPunchCount(Usermap usermap, Purchase purchase) {

        boolean counterReset = false;

        Counter counter = usermap.getCounter();

        if (counter == null) {
            counter = new Counter();
            usermap.setCounter(counter);
        }

        if (counter.getPunchCount() + 1 == 6) {
            counter.setPunchCount(0);
            counterReset = true;
        }

        counter.setPunchCount(counter.getPunchCount() + 1);
        counterRepository.save(counter);
        return counterReset;
    }

    private boolean isUserEnrolled(Usermap usermap) {
        return usermap.getEnrollment() != null && "enrolled".equalsIgnoreCase(usermap.getEnrollment().getStatus());
    }

    private void createPromo(Usermap usermap, Purchase purchase) {
        Promo promo = new Promo("$off voucher", LocalDate.now(), LocalDate.now(), "890000888234234333", "valid", usermap);
//        promo.getPurchases().add(purchase);
        promoRepository.save(promo);
    }

    public Enrollment getEnrollmentByUser(Long userId) throws ResourceNotFoundException {
        return usermapRepository.findById(userId).map(Usermap::getEnrollment).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    public Enrollment createEnrollment(Long userId, Enrollment enrollment) throws ResourceNotFoundException {
        return usermapRepository.findById(userId).map(usermap -> {
            Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
            usermap.setEnrollment(savedEnrollment);
            usermapRepository.save(usermap);
            return savedEnrollment;
        }).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    public List<Promo> getPromosByUser(Long userId) {
        return promoRepository.findByUsermapId(userId);
    }

    public List<Purchase> findPurchasesByPromoId(long promoId) {
        return purchaseRepository.findByPromoId(promoId);
    }

    public Usermap findUserByIds(String palsId, String pgrId, long wcsId) throws ResourceNotFoundException {
        return usermapRepository.findByPalsId(palsId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }
}
