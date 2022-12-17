package com.wwi21sebgroup5.cinema.repositories;

import com.wwi21sebgroup5.cinema.entities.Director;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DirectorRepository extends CrudRepository<Director, UUID> {

    Optional<Director> findByNameAndFirstName(String name, String firstName);

    @Override
    List<Director> findAll();


}
