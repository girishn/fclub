package com.example.foodclub.repository;

import com.example.foodclub.model.Usermap;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "usermap", path = "user")
public interface UsermapRepository extends CrudRepository<Usermap, Long> {

//    List<Usermap> findByUserKeysPgrIdOrUserKeysWcsIdOrUserKeysPalsId(String pgrId);
    List<Usermap> findByUserKeysPgrId(String pgrId);
    List<Usermap> findByUserKeysWcsId(long wcsId);
    List<Usermap> findByUserKeysPalsId(String palsId);
}
