package com.example.foodclub.repository;

import com.example.foodclub.model.FoodClubCounter;
import org.springframework.data.repository.CrudRepository;

public interface CounterRepository extends CrudRepository<FoodClubCounter, Long> {
}
