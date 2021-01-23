package com.example.foodclub.repository;

import com.example.foodclub.model.Purchase;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    List<Purchase> findByUsermapId(Long userId);

    List<Purchase> findByPromoId(Long promoId);
}
