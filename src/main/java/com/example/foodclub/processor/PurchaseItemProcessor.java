package com.example.foodclub.processor;

import com.example.foodclub.model.Purchase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class PurchaseItemProcessor implements ItemProcessor<Purchase, Purchase> {
    @Override
    public Purchase process(Purchase purchase) throws Exception {
        return null;
    }
}
