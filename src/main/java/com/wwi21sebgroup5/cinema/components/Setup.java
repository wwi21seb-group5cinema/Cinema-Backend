package com.wwi21sebgroup5.cinema.components;

import com.wwi21sebgroup5.cinema.entities.Cinema;
import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.entities.Genre;
import com.wwi21sebgroup5.cinema.entities.SeatType;
import com.wwi21sebgroup5.cinema.exceptions.CinemaAlreadyExistsException;
import com.wwi21sebgroup5.cinema.exceptions.CinemaNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.UserAlreadyExistsException;
import com.wwi21sebgroup5.cinema.requestObjects.CinemaHallRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.CinemaRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.RegistrationRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.TmdbMovieRequestObject;
import com.wwi21sebgroup5.cinema.services.*;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Setup {

    private static final String CITY_FILE = "static/data/cities.csv";
    private static final String GENRE_FILE = "static/data/genres.csv";
    private static final String SEAT_TYPE_FILE = "static/data/seatTypes.csv";

    @Autowired
    private CityService cityService;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private CinemaHallService cinemaHallService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private SeatTypeService seatTypeService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TmdbService tmdbService;

    @Autowired
    private TmdbApi tmdbApi;

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
        setupCinemaWithHalls();
        setupAdminUser();
        setupMovies();
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

    private void setupMovies() {
        List<MovieDb> movies = tmdbApi.getMovies()
                .getNowPlayingMovies("de-DE", 1, "DE").getResults();

        movies.forEach(movie -> {
            try {
                tmdbService.addMovie(new TmdbMovieRequestObject(movie.getId()));
            } catch (Exception e) {
                System.out.println("Error while adding movie");
            }
        });
    }

    private void setupCinemaWithHalls() {
        CinemaRequestObject cinemaRequestObject = new CinemaRequestObject(
                "Cineverse 5", "68259", "Mannheim", "Rentnerstraße", "69", 3
        );

        Cinema cinema;
        try {
            cinema = cinemaService.add(cinemaRequestObject);
        } catch (CinemaAlreadyExistsException e) {
            cinema = cinemaService.getAllCinemas().get(0);
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }

        List<CinemaHallRequestObject> requestObjects = new ArrayList<>();
        requestObjects.add(new CinemaHallRequestObject(
                cinema.getId(), 12, 12, "SAP-Saal", 0
        ));
        requestObjects.add(new CinemaHallRequestObject(
                cinema.getId(), 10, 13, "DHBW-Saal", 1
        ));
        requestObjects.add(new CinemaHallRequestObject(
                cinema.getId(), 10, 12, "Top-G-Stage", 2
        ));

        requestObjects.forEach(hall -> {
            try {
                cinemaHallService.addCinemaHall(hall);
            } catch (CinemaNotFoundException e) {
                throw new RuntimeException(e.getCause());
            }
        });
    }

    private void setupAdminUser() {
        RegistrationRequestObject requestObject = new RegistrationRequestObject(
                "admin", "admin123", "admin", "admin", "admin@cineverse.com",
                "68259", "Mannheim", "Rentnerstraße", "69", true
        );

        try {
            loginService.register(requestObject);
            loginService.confirmToken(tokenService.getAll().get(0).getToken());
        } catch (UserAlreadyExistsException e) {
            System.out.println("passt");
        } catch (Exception e) {
            throw new RuntimeException("Well, that didn't work, did it?");
        }
    }

}
