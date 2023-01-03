package com.wwi21sebgroup5.cinema.components;

import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.entities.Genre;
import com.wwi21sebgroup5.cinema.entities.SeatType;
import com.wwi21sebgroup5.cinema.services.CityService;
import com.wwi21sebgroup5.cinema.services.GenreService;
import com.wwi21sebgroup5.cinema.services.SeatTypeService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Setup {

    private static final String CITY_FILE = "static/data/cities.csv";
    private static final String GENRE_FILE = "static/data/genres.csv";
    private static final String SEAT_TYPE_FILE = "static/data/seatTypes.csv";

    @Autowired
    CityService cityService;
    @Autowired
    GenreService genreService;

    @Autowired
    SeatTypeService seatTypeService;

    @Autowired
    private CsvDataLoader csvDataLoader;

    /**
     * Component which runs after start-up and initializes static data
     */
    @PostConstruct
    private void setupData() {
        setupCities();
        setupGenres();
        setupSeatTypes();
    }

    /**
     * Setup workflow for cities stored in a csv-file
     */
    private void setupCities() {
        List<City> cities = csvDataLoader.loadObjectList(City.class, CITY_FILE);

        for (City city : cities) {
            cityService.save(city);
        }
    }

    /**
     * Setup workflow for genres stored in a csv-file
     */
    private void setupGenres() {
        List<Genre> genres = csvDataLoader.loadObjectList(Genre.class, GENRE_FILE);

        for (Genre genre : genres) {
            genreService.save(genre);
        }
    }

    private void setupSeatTypes() {
        List<SeatType> seatTypes = csvDataLoader.loadObjectList(SeatType.class, SEAT_TYPE_FILE);

        for (SeatType seatType : seatTypes) {
            seatTypeService.addSeatType(seatType);
        }
    }

}
