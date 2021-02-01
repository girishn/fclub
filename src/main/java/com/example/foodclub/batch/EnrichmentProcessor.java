package com.example.foodclub.batch;

import com.example.foodclub.model.Purchase;
import com.example.foodclub.model.Usermap;
import com.example.foodclub.service.FoodClubService;
import com.example.foodclub.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Recover;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class EnrichmentProcessor implements ItemProcessor<PurchaseRecord, List<Purchase>> {

    @Autowired
    private FoodClubService foodClubService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Recover
    public Purchase fallback(Purchase purchase) {
//		purchase.setMessage("error");
        return purchase;
    }

    @CircuitBreaker
    @Override
    public List<Purchase> process(PurchaseRecord purchaseRecord) throws Exception {
        log.info("purchaseRecord: {}", purchaseRecord);
        Usermap usermap = userService.findUserByIds(purchaseRecord.getPalsId(), purchaseRecord.getPgrId(), purchaseRecord.getWcsId());
        List<Purchase> purchases = IntStream.range(0, purchaseRecord.getQuantity()).mapToObj(index -> {
            Purchase purchase = new Purchase();
            purchase.setUsermap(usermap);
            modelMapper.map(purchaseRecord, purchase);
            return purchase;
        }).collect(Collectors.toList());

        log.info("purchases: {}", purchases);
        return purchases;
    }
}