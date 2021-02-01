package com.example.foodclub.service;

import com.example.foodclub.error.ResourceNotFoundException;
import com.example.foodclub.model.*;
import com.example.foodclub.repository.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class FoodClubService {

    private final PurchaseRepository purchaseRepository;
    private final UsermapRepository usermapRepository;
    private final UserKeyRepository userKeyRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CounterRepository counterRepository;
    private final PromoRepository promoRepository;

    public void initData() {

        Usermap usermap = new Usermap("111", "1111", 11111L);

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
//        purchaseList1.forEach(purchase -> purchase.addPromo(savedPromo1));
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
//        purchaseList2.forEach(purchase -> purchase.addPromo(savedPromo2));

//        Enrollment enrolled = enrollmentRepository.save(new Enrollment("enrolled", LocalDate.now()));
//        Counter counter = counterRepository.save(new Counter(1, 0));

        FoodClubEnrollment foodClubEnrollment = new FoodClubEnrollment("enrolled", LocalDate.now(), usermap);
        enrollmentRepository.save(foodClubEnrollment);
        FoodClubCounter foodClubCounter = new FoodClubCounter(1, 0, usermap);
        counterRepository.save(foodClubCounter);

        usermapRepository.save(usermap);

        Usermap usermap1 = new Usermap("222", "2222", 22222L);

        FoodClubEnrollment foodClubEnrollment1 = new FoodClubEnrollment("enrolled", LocalDate.now(), usermap1);
        enrollmentRepository.save(foodClubEnrollment1);

        usermap1.addUserKey(new UserKey("222-2", "", 22222L));

        usermapRepository.save(usermap1);
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

    @SneakyThrows
    public Purchase createPurchase(Long userId, Purchase purchase) {
        return usermapRepository.findById(userId).map(usermap -> {

            boolean createPromo = incrementPunchCount(usermap);

            if (createPromo && isUserEnrolled(usermap)) {
                createPromo(usermap, purchase);
            }

            purchase.setUsermap(usermap);
            return purchaseRepository.save(purchase);
        }).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    @SneakyThrows
    public void createPurchases(List<Purchase> purchases) throws ResourceNotFoundException {
        purchases.forEach(purchase -> createPurchase(purchase.getUsermap().getId(), purchase));
    }

    private boolean incrementPunchCount(Usermap usermap) {

        boolean counterReset = false;

        FoodClubCounter foodClubCounter = counterRepository.findById(Objects.requireNonNull(usermap.getId())).orElse(new FoodClubCounter());

        if (foodClubCounter.getPunchCount() + 1 == 6) {
            foodClubCounter.setPunchCount(0);
            foodClubCounter.setResetCount(foodClubCounter.getResetCount() + 1);
            counterReset = true;
        } else {
            foodClubCounter.setPunchCount(foodClubCounter.getPunchCount() + 1);
        }

        foodClubCounter.setUsermap(usermap);
        counterRepository.save(foodClubCounter);
        return counterReset;
    }

    private boolean isUserEnrolled(Usermap usermap) {
        FoodClubEnrollment foodClubEnrollment = enrollmentRepository.findById(usermap.getId()).orElseThrow();
        return foodClubEnrollment != null && "enrolled".equalsIgnoreCase(foodClubEnrollment.getStatus());
    }

    private void createPromo(Usermap usermap, Purchase purchase) {
        String palsId = fetchPrimaryPalsId(usermap);
        Promo promo = new Promo(palsId, "$off voucher", LocalDate.now(), LocalDate.now(), "890000888234234333", "valid", usermap);
        purchase.addPromo(promo);
        promoRepository.save(promo);
    }

    private String fetchPrimaryPalsId(Usermap usermap) {
        return usermap.getUserKeys().stream()
                .findFirst()
                .orElseThrow()
                .getPalsId();
    }

    public FoodClubEnrollment getEnrollmentByUser(Long userId) throws ResourceNotFoundException {
        return enrollmentRepository.findById(userId).orElseThrow();
    }

    public FoodClubEnrollment createEnrollment(Long userId, FoodClubEnrollment foodClubEnrollment) throws ResourceNotFoundException {
        return usermapRepository.findById(userId).map(usermap -> {
            FoodClubEnrollment savedFoodClubEnrollment = enrollmentRepository.save(foodClubEnrollment);
            savedFoodClubEnrollment.setUsermap(usermap);
            usermapRepository.save(usermap);
            return savedFoodClubEnrollment;
        }).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    public List<Promo> getPromosByUser(Long userId) {
        return promoRepository.findByUsermapId(userId);
    }

    public List<Purchase> findPurchasesByPromoId(long promoId) {
        return purchaseRepository.findAllByPromos(promoId);
    }
}
