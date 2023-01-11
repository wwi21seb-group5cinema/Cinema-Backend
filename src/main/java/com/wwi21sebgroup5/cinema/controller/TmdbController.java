package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Movie;
import com.wwi21sebgroup5.cinema.requestObjects.TmdbMovieRequestObject;
import com.wwi21sebgroup5.cinema.services.TmdbService;
import info.movito.themoviedbapi.model.MovieDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/movie/tmdb")
public class TmdbController {

    @Autowired
    private TmdbService tmdbService;

    @PostMapping(path = "/addTmdb")
    public ResponseEntity<Object> add(@RequestBody TmdbMovieRequestObject requestObject) {
        Movie newMovie;

        try {
            newMovie = tmdbService.addMovie(requestObject);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(newMovie, HttpStatus.OK);
    }

    @GetMapping(path = "/getTmdb", params = {"movieName"})
    public ResponseEntity<List<MovieDb>> getTmdbMoviesByMovieName(@RequestParam String movieName) {
        List<MovieDb> foundMovies = tmdbService.getMoviesByName(movieName);

        if (foundMovies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(foundMovies, HttpStatus.OK);
        }
    }

    @GetMapping(path = "/getTmdb", params = {"movieId"})
    public ResponseEntity<MovieDb> getTmdbMoviesByMovieId(@RequestParam int movieId) {
        Optional<MovieDb> foundMovie = tmdbService.getMovieById(movieId);

        return foundMovie.map(
                        movieDb -> new ResponseEntity<>(movieDb, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
