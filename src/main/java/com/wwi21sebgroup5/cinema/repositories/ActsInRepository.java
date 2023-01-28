package com.wwi21sebgroup5.cinema.repositories;

import com.wwi21sebgroup5.cinema.entities.ActsIn;
import com.wwi21sebgroup5.cinema.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActsInRepository extends JpaRepository<ActsIn, UUID> {

    List<ActsIn> findByMovie(Movie movie);

}
