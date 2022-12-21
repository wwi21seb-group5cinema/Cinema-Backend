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
    MovieService movieService;

    /**
     * @return all existing Movies
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<Movie>> getAll() {
        return new ResponseEntity<>(movieService.findAll(), HttpStatus.OK);
    }

    /**
     * @param movieObject
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
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(m, HttpStatus.CREATED);
    }
}
