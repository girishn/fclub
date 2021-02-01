package com.example.foodclub.repository;

import com.example.foodclub.model.FoodClubEnrollment;
import org.springframework.data.repository.CrudRepository;

public interface EnrollmentRepository extends CrudRepository<FoodClubEnrollment, Long> {
}
