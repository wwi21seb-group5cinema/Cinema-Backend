package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Cinema;
import com.wwi21sebgroup5.cinema.exceptions.CinemaAlreadyExistsException;
import com.wwi21sebgroup5.cinema.requestObjects.CinemaRequestObject;
import com.wwi21sebgroup5.cinema.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/cinema")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<Cinema>> getAllCinemas() {
        List<Cinema> allCinemas = cinemaService.getAllCinemas();

        if (allCinemas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(allCinemas, HttpStatus.OK);
        }
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<Cinema> getCinemaById(@PathVariable UUID id) {
        Optional<Cinema> foundCinema = cinemaService.getCinemaById(id);

        return foundCinema.map(
                        cinema -> new ResponseEntity<>(cinema, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addCinema(@RequestBody CinemaRequestObject cinemaObject) {
        Cinema newCinema;

        try {
            newCinema = cinemaService.add(cinemaObject);
        } catch (CinemaAlreadyExistsException caeE) {
            return new ResponseEntity<>(caeE.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(newCinema, HttpStatus.CREATED);
    }

}
