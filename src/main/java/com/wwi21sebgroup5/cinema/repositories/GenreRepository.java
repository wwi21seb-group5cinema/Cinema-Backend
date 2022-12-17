package com.wwi21sebgroup5.cinema.repositories;

import com.wwi21sebgroup5.cinema.entities.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface GenreRepository extends CrudRepository<Genre, UUID> {

    Optional<Genre> findByName(String name);
}