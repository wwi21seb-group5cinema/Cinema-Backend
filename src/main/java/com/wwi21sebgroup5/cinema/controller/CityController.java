package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/city")
public class CityController {

    @Autowired
    CityService cityService;

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<City>> getAllCities() {
        List<City> allCities = cityService.getAllCities();

        if (allCities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(allCities, HttpStatus.OK);
        }
    }

    @GetMapping(path = "/get/{plz}")
    public ResponseEntity<List<City>> getAllCitiesByPlz(@PathVariable String plz) {
        List<City> allCities = cityService.getAllCitiesByPlz(plz);

        if (allCities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(allCities, HttpStatus.OK);
        }
    }

    @GetMapping(path = "/get/{cityName}")
    public ResponseEntity<List<City>> getAllCitiesByName(@PathVariable String cityName) {
        List<City> allCities = cityService.getAllCitiesByName(cityName);

        if (allCities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(allCities, HttpStatus.OK);
        }
    }

}
