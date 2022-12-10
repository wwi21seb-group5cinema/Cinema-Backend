package com.wwi21sebgroup5.cinema.components;

import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.services.CityService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Setup {

    @Autowired
    CityService cityService;

    @Autowired
    private CsvDataLoader csvDataLoader;

    private static final String CITY_FILE = "static/data/cities.csv";

    @PostConstruct
    private void setupData() {
        setupCities();
    }

    private void setupCities() {
        List<City> cities = csvDataLoader.loadObjectList(City.class, CITY_FILE);

        for (City city : cities) {
            cityService.save(city);
        }
    }

}
