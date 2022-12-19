package com.wwi21sebgroup5.cinema.repositories;

import com.wwi21sebgroup5.cinema.entities.Actor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActorRepository extends CrudRepository<Actor, UUID> {

    Optional<Actor> findByNameAndFirstName(String name, String firstName);

    @Override
    List<Actor> findAll();

    Optional<Actor> findById(UUID id);


}
