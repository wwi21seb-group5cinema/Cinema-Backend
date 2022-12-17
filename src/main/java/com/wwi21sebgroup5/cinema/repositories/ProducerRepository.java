package com.wwi21sebgroup5.cinema.repositories;

import com.wwi21sebgroup5.cinema.entities.Producer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProducerRepository extends CrudRepository<Producer, UUID> {
    Optional<Producer> findByName(String name);

    @Override
    List<Producer> findAll();


}

