package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.CinemaHall;
import com.wwi21sebgroup5.cinema.services.CinemaHallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/cinemaHall")
public class CinemaHallController {

    @Autowired
    private CinemaHallService cinemaHallService;

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<CinemaHall>> getAllCinemaHalls() {
        List<CinemaHall> allCinemaHalls = cinemaHallService.getAllCinemaHalls();

        if (allCinemaHalls.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(allCinemaHalls, HttpStatus.OK);
        }
    }

}
