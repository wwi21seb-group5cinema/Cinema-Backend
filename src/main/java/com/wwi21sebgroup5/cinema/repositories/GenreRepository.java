package com.wwi21sebgroup5.cinema.repositories;

import com.wwi21sebgroup5.cinema.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {

    Optional<Genre> findByName(String name);
}