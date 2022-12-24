package com.wwi21sebgroup5.cinema.components;

import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.entities.Genre;
import com.wwi21sebgroup5.cinema.services.CityService;
import com.wwi21sebgroup5.cinema.services.GenreService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Setup {

    private static final String CITY_FILE = "static/data/cities.csv";
    private static final String GENRE_FILE = "static/data/genres.csv";

    @Autowired
    CityService cityService;
    @Autowired
    GenreService genreService;
    @Autowired
    private CsvDataLoader csvDataLoader;

    /**
     * Component which runs after start-up and initializes static data
     */
    @PostConstruct
    private void setupData() {
        setupCities();
        setupGenres();
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

}
