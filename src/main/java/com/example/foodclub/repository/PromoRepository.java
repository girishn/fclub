package com.example.foodclub.repository;

import com.example.foodclub.model.Counter;
import com.example.foodclub.model.Promo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PromoRepository extends CrudRepository<Promo, Long> {
    List<Promo> findByUsermapId(Long userId);
}
