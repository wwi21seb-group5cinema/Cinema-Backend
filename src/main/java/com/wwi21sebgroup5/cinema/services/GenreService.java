package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Genre;
import com.wwi21sebgroup5.cinema.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class GenreService {

    @Autowired
    GenreRepository genreRepository;

    public Optional<Genre> findByName(String name) {
        return genreRepository.findByName(name);
    }

    /**
     * Persists a given genre in the database
     *
     * @param genre Genre to be persisted
     */
    public Genre save(Genre genre) {
        Optional<Genre> foundGenre = genreRepository.findByName(genre.getName());
        return foundGenre.orElseGet(() -> genreRepository.save(genre));
    }
}
