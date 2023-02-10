package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.services.TmdbService;
import info.movito.themoviedbapi.model.Credits;
import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Video;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TmdbControllerTest {

    @Mock
    private TmdbService tmdbService;

    @InjectMocks
    private TmdbController tmdbController;

    private MovieDb mockMovieDbObject(int id, Credits credits) {
        MovieDb movieDb = new MovieDb();
        movieDb.setAdult(true);
        movieDb.setBackdropPath("/randomPath.jpg");
        movieDb.setGenres(List.of(new Genre(), new Genre()));
        movieDb.setId(id);
        movieDb.setOriginalLanguage("de");
        movieDb.setOriginalTitle("Amina II");
        movieDb.setOverview("Hallo, das ist eine Testbeschreibung!");
        movieDb.setPopularity(103.2F);
        movieDb.setPosterPath("/anotherRandomPath.jpg");
        movieDb.setReleaseDate("2014-11-19");
        movieDb.setTitle(movieDb.getOriginalTitle());
        movieDb.setVideos(new Video.Results());
        movieDb.setVoteAverage(5.2F);
        movieDb.setVoteCount(id << 1);

        // Set credits
        movieDb.setCredits(credits);
        return movieDb;
    }

    @Test
    @DisplayName("Add tmdb movie successfully")
    public void testAddTmdbMovieSuccessful() {

    }

    @Test
    @DisplayName("Test tmdb movie not found when adding new movie")
    public void testTmdbMovieNotFoundDuringAddProcess() {

    }

    @Test
    @DisplayName("Test fsk not found when adding new movie")
    public void testFskNotFoundDuringAddProcess() {

    }

    @Test
    @DisplayName("Test tmdb information parse error when adding new movie")
    public void testTmdbInformationParseErrorDuringAddProcess() {

    }

    @Test
    @DisplayName("Test internal server error when adding new movie")
    public void testInternalServerErrorDuringAddProcess() {

    }

    @Test
    @DisplayName("Test get tmdb movies by name successful")
    public void testGetTmdbMoviesByNameSuccessful() {
        MovieDb firstMovie = mockMovieDbObject(1234, new Credits());
        MovieDb secondMovie = mockMovieDbObject(4321, new Credits());
        List<MovieDb> expectedMovies = List.of(firstMovie, secondMovie);

        when(tmdbService.getMoviesByName("Amina")).thenReturn(expectedMovies);

        ResponseEntity<List<MovieDb>> response = tmdbController.getTmdbMoviesByMovieName("Amina");

        assertAll(
                "Validating response...",
                () -> assertEquals(expectedMovies, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test get tmdb movies by name not successful")
    public void testGetTmdbMoviesByNameNotSuccessful() {
        when(tmdbService.getMoviesByName("Amina")).thenReturn(List.of());

        ResponseEntity<List<MovieDb>> response = tmdbController.getTmdbMoviesByMovieName("Amina");

        assertAll(
                "Validating response...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test get tmdb movie by id successful")
    public void testGetTmdbMovieByIdSuccessful() {
        MovieDb movieDb = mockMovieDbObject(1234, new Credits());

        when(tmdbService.getMovieById(movieDb.getId())).thenReturn(Optional.of(movieDb));

        ResponseEntity<MovieDb> response = tmdbController.getTmdbMoviesByMovieId(movieDb.getId());

        assertAll(
                "Validating response...",
                () -> assertEquals(movieDb, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test get tmdb movie by id not successful")
    public void testGetTmdbMovieByIdNotSuccessful() {
        MovieDb movieDb = mockMovieDbObject(1234, new Credits());

        when(tmdbService.getMovieById(movieDb.getId())).thenReturn(Optional.empty());

        ResponseEntity<MovieDb> response = tmdbController.getTmdbMoviesByMovieId(movieDb.getId());

        assertAll(
                "Validating response...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

}
