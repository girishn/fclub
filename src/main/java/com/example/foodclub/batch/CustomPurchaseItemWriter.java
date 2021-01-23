package com.example.foodclub.batch;

import com.example.foodclub.error.ResourceNotFoundException;
import com.example.foodclub.model.Purchase;
import com.example.foodclub.service.FoodClubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.support.transaction.TransactionAwareProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CustomPurchaseItemWriter implements ItemWriter<Purchase> {

    @Autowired
    private FoodClubService foodClubService;


    @Override
    public void write(List<? extends Purchase> list) throws Exception {
        list.forEach(purchase -> {
            try {
                foodClubService.createPurchase(purchase.getUsermap().getId(), purchase);
            } catch (ResourceNotFoundException e) {
                log.info("Exception: ", e);
            }
        });
    }
}
