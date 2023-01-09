package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.SeatType;
import com.wwi21sebgroup5.cinema.services.SeatTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/seatType")
public class SeatTypeController {

    @Autowired
    SeatTypeService seatTypeService;

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<SeatType>> getAll() {
        List<SeatType> allSeatTypes = seatTypeService.getAll();

        if (allSeatTypes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(allSeatTypes, HttpStatus.OK);
        }
    }

}
