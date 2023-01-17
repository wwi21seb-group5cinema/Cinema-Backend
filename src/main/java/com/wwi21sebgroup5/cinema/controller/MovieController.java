package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Movie;
import com.wwi21sebgroup5.cinema.exceptions.ActorNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.FSKNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.GenreDoesNotExistException;
import com.wwi21sebgroup5.cinema.exceptions.ImageNotFoundException;
import com.wwi21sebgroup5.cinema.requestObjects.MovieRequestObject;
import com.wwi21sebgroup5.cinema.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/v1/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    /**
     * @return all existing Movies
     */
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<Movie> m = movieService.findAll();
        if (m.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    /**
     * @param movieObject Request object which holds all necessary attributes for a new movie
     * @return the newly created movie-object
     */
    @PostMapping(path = "/add")
    public ResponseEntity<Object> add(@RequestBody MovieRequestObject movieObject) {
        Movie m;
        try {
            m = movieService.add(movieObject);
        } catch (GenreDoesNotExistException | FSKNotFoundException | ActorNotFoundException |
                 ImageNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(m, HttpStatus.CREATED);
    }

}
