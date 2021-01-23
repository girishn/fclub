package com.example.foodclub.repository;

import com.example.foodclub.model.Counter;
import org.springframework.data.repository.CrudRepository;

public interface CounterRepository extends CrudRepository<Counter, Long> {
}
