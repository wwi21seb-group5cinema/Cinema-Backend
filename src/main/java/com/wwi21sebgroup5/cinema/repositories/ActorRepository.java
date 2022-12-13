package com.wwi21sebgroup5.cinema.repositories;

import com.wwi21sebgroup5.cinema.entities.Actor;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActorRepository extends CrudRepository<Actor, UUID> {
    List<Actor> findByName(String name);

    List<Actor> findByFirstName(String firstName);

    List<Actor> findByBirthdate(Date birthdate);

    Optional<Actor> findByNameAndFirstNameAndBirthdate(String name, String firstName, Date birthdate);

    @Override
    List<Actor> findAll();


}
