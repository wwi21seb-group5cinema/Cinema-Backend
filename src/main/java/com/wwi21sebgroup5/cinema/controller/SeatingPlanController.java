package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.SeatingPlan;
import com.wwi21sebgroup5.cinema.services.SeatingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
