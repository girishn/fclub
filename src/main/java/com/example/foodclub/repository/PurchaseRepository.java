package com.example.foodclub.repository;

import com.example.foodclub.model.Purchase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    List<Purchase> findByUsermapId(Long userId);

    @Query("SELECT promo.purchases FROM Promo promo WHERE promo.id = :promoId")
    List<Purchase> findAllByPromos(Long promoId);
}
