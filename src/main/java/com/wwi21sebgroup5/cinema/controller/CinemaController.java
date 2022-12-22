package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Cinema;
import com.wwi21sebgroup5.cinema.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}