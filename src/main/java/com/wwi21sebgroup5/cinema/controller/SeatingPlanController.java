package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.SeatingPlan;
import com.wwi21sebgroup5.cinema.services.SeatingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/seatingPlan")
public class SeatingPlanController {

    @Autowired
    private SeatingPlanService seatingPlanService;

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<SeatingPlan>> getAllSeatingPlans() {
        List<SeatingPlan> allSeatingPlans = seatingPlanService.getAllSeatingPlans();

        if (allSeatingPlans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(allSeatingPlans, HttpStatus.OK);
        }
    }

    @GetMapping(path = "/get", params = "id")
    public ResponseEntity<SeatingPlan> getSeatingPlanById(@RequestParam UUID id) {
        Optional<SeatingPlan> foundPlan = seatingPlanService.getSeatingPlanById(id);

        return foundPlan.map(
                        seatingPlan -> new ResponseEntity<>(seatingPlan, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/get", params = "cinemaHallId")
    public ResponseEntity<SeatingPlan> getSeatingPlanByCinemaHall(@RequestParam UUID cinemaHallId) {
        Optional<SeatingPlan> foundPlan = seatingPlanService.getSeatingPlanByCinemaHall(cinemaHallId);

        return foundPlan.map(
                        seatingPlan -> new ResponseEntity<>(seatingPlan, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
