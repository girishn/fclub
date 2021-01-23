package com.example.foodclub.repository;

import com.example.foodclub.dto.EnrollmentDto;
import com.example.foodclub.model.Enrollment;
import org.springframework.data.repository.CrudRepository;

public interface EnrollmentRepository extends CrudRepository<Enrollment, Long> {
}
