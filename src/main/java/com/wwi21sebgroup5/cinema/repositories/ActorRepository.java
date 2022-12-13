package com.wwi21sebgroup5.cinema.repositories;

import com.wwi21sebgroup5.cinema.entities.Actor;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

public interface ActorRepository extends CrudRepository<Actor, UUID> {
    Optional<Actor> findByName(String name);

    Optional<Actor> findByFirstName(String firstName);

    Optional<Actor> findByBirthdate(Date birthdate);

    Optional<Actor> findByNameAndFirstNameAndBirthdate(String name, String firstName, Date birthdate);


}
