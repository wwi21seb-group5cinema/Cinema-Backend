package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.CinemaHall;
import com.wwi21sebgroup5.cinema.exceptions.CinemaNotFoundException;
import com.wwi21sebgroup5.cinema.requestObjects.CinemaHallRequestObject;
import com.wwi21sebgroup5.cinema.services.CinemaHallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/cinemaHall")
public class CinemaHallController {

    @Autowired
    private CinemaHallService cinemaHallService;

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addCinemaHall(@RequestBody CinemaHallRequestObject requestObject) {
        CinemaHall newCinemaHall;

        try {
            newCinemaHall = cinemaHallService.addCinemaHall(requestObject);
        } catch (CinemaNotFoundException cnfE) {
            return new ResponseEntity<>(cnfE.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(newCinemaHall, HttpStatus.CREATED);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<UUID>> getAll() {
        List<CinemaHall> allCinemaHalls = cinemaHallService.getAllCinemaHalls();

        if (allCinemaHalls.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<UUID> ids = new ArrayList<>();
            allCinemaHalls.forEach(hall -> ids.add(hall.getId()));
            return new ResponseEntity<>(ids, HttpStatus.OK);
        }
    }

    @GetMapping(path = "/get", params = "cinemaId")
    public ResponseEntity<List<CinemaHall>> getAllByCinema(@RequestParam UUID cinemaId) {
        List<CinemaHall> allCinemaHalls = cinemaHallService.getAllCinemaHallsByCinema(cinemaId);

        if (allCinemaHalls.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(allCinemaHalls, HttpStatus.OK);
        }
    }

    @GetMapping(path = "/get", params = "id")
    public ResponseEntity<Optional<CinemaHall>> getById(@RequestParam UUID id) {
        Optional<CinemaHall> foundHall = cinemaHallService.getCinemaHallById(id);

        if (foundHall.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(foundHall, HttpStatus.OK);
        }
    }

}
