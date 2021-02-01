package com.example.foodclub.repository;

import com.example.foodclub.model.UserKey;
import com.example.foodclub.model.Usermap;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserKeyRepository extends CrudRepository<UserKey, Long> {

//    @Transactional(readOnly = true)
//    @Query("SELECT u FROM Usermap u WHERE u.userKeys.palsId = :palsId")
//    Optional<Usermap> findUsermapByPalsId(String palsId);
}
