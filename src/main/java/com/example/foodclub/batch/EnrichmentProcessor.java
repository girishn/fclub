package com.example.foodclub.batch;

import com.example.foodclub.model.Purchase;
import com.example.foodclub.model.Usermap;
import com.example.foodclub.service.FoodClubService;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Recover;

public class EnrichmentProcessor implements ItemProcessor<PurchaseRecord, Purchase> {

    @Autowired
    private FoodClubService foodClubService;

    @Autowired
    private ModelMapper modelMapper;

    @Recover
    public Purchase fallback(Purchase purchase) {
//		purchase.setMessage("error");
        return purchase;
    }

    @CircuitBreaker
    @Override
    public Purchase process(PurchaseRecord purchaseRecord) throws Exception {
        Usermap usermap = foodClubService.findUserByIds(purchaseRecord.getPalsId(), null, 0L);
        Purchase purchase = new Purchase();
        purchase.setUsermap(usermap);
        modelMapper.map(purchaseRecord, purchase);
        return purchase;
    }
}