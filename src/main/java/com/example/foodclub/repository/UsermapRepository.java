package com.example.foodclub.repository;

import com.example.foodclub.model.Usermap;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsermapRepository extends CrudRepository<Usermap, Long> {
    Optional<Usermap> findByPalsId(String palsId);
}
