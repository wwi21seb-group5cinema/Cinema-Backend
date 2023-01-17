package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.enums.FSK;
import com.wwi21sebgroup5.cinema.exceptions.ActorNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.FSKNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.GenreDoesNotExistException;
import com.wwi21sebgroup5.cinema.exceptions.ImageNotFoundException;
import com.wwi21sebgroup5.cinema.requestObjects.MovieRequestObject;
import com.wwi21sebgroup5.cinema.services.MovieService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class MovieControllerTest {

    @Mock
    MovieService movieService;

    @InjectMocks
    MovieController movieController;

    @Test
    @DisplayName("Test successfully getting all movies")
    public void testGetAllMoviesSuccessful() {
        try {
            Producer producer = new Producer("prod");
            Director director = new Director("director", "dir");
            Genre genre = new Genre(UUID.randomUUID(), "Action");
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData image = new ImageData("image/png", data, false);
            Movie firstMovie = new Movie(producer, director, FSK.SIX, genre, image, "film1",
                    "beschreibung", 8.3F, 92,
                    LocalDate.of(2023, 12, 4), LocalDate.of(2023, 12, 6));
            Movie secondMovie = new Movie(producer, director, FSK.SIX, genre, image, "film2",
                    "beschreibung2", 9.2F, 94,
                    LocalDate.of(2023, 12, 4), LocalDate.of(2023, 12, 6));
            List<Movie> expected = List.of(firstMovie, secondMovie);

            when(movieService.findAll()).thenReturn(expected);

            ResponseEntity<?> response = movieController.getAll();
            assertAll(
                    "Validating correct response from controller...",
                    () -> assertEquals(response.getBody(), expected),
                    () -> assertEquals(response.getStatusCode(), HttpStatus.OK)
            );
        } catch (Exception e) {
            fail("Failed to get bytes");
        }
    }

    @Test
    @DisplayName("Test unsuccessfully getting all movies")
    public void testGetAllMoviesUnsuccessfully() {
        when(movieService.findAll()).thenReturn(List.of());

        ResponseEntity<?> response = movieController.getAll();
        assertAll(
                "Validating correct response from controller...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND)
        );
    }

    @Test
    @DisplayName("Test successfully adding movie")
    public void testAddMovieSuccessfully() {
        try {
            Producer producer = new Producer("prod");
            Director director = new Director("director", "dir");
            Genre genre = new Genre(UUID.randomUUID(), "Action");
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData image = new ImageData("image/png", data, false);
            Movie firstMovie = new Movie(producer, director, FSK.SIX, genre, image, "film1",
                    "beschreibung", 8.3F, 92, LocalDate.of(2023, 12, 4), LocalDate.of(2023, 12, 6));

            Actor a = new Actor("Bahde", "Paul");
            a.setId(UUID.randomUUID());
            List<UUID> actors = List.of(a.getId());
            MovieRequestObject movieRequestObject = new MovieRequestObject(
                    producer.getName(), director.getFirstName(), director.getName(), actors, image.getId(), 6,
                    genre.getName(), "film", "beschreibung", 8.3F, 91, "08-01-2023", "08-04-2023");

            when(movieService.add(movieRequestObject)).thenReturn(firstMovie);

            ResponseEntity<?> response = movieController.add(movieRequestObject);
            assertAll(
                    "Validating correct response from controller...",
                    () -> assertEquals(response.getBody(), firstMovie),
                    () -> assertEquals(response.getStatusCode(), HttpStatus.CREATED)
            );
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to get bytes");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed adding a new film");
        }
    }

    @Test
    @DisplayName("Test unsuccessfully adding movie with wrong FSK")
    public void testAddMovieUnsuccessfullyWithWrongFSK() {
        try {
            Producer producer = new Producer("prod");
            Director director = new Director("director", "dir");
            Genre genre = new Genre(UUID.randomUUID(), "Action");
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData image = new ImageData("image/png", data, false);
            Actor a = new Actor("Bahde", "Paul");
            a.setId(UUID.randomUUID());
            List<UUID> actors = List.of(a.getId());
            MovieRequestObject movieRequestObject = new MovieRequestObject(
                    producer.getName(), director.getFirstName(), director.getName(), actors, image.getId(), 6,
                    genre.getName(), "film", "beschreibung", 8.3F, 120, "08-01-2023", "08-04-2023");

            FSKNotFoundException e = new FSKNotFoundException(13);
            when(movieService.add(movieRequestObject)).thenThrow(e);

            ResponseEntity<?> response = movieController.add(movieRequestObject);
            assertAll(
                    "Validating correct response from controller...",
                    () -> assertEquals(response.getBody(), e.getMessage()),
                    () -> assertEquals(response.getStatusCode(), HttpStatus.NOT_ACCEPTABLE)
            );
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to get bytes");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed adding a new film");
        }
    }

    @Test
    @DisplayName("Test unsuccessfully adding movie with wrong Genre")
    public void testAddMovieUnsuccessfullyWithWrongGenre() {
        try {
            Producer producer = new Producer("prod");
            Director director = new Director("director", "dir");
            Genre genre = new Genre(UUID.randomUUID(), "Action");
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData image = new ImageData("image/png", data, false);
            Actor a = new Actor("Bahde", "Paul");
            a.setId(UUID.randomUUID());
            List<UUID> actors = List.of(a.getId());
            MovieRequestObject movieRequestObject = new MovieRequestObject(
                    producer.getName(), director.getFirstName(), director.getName(), actors, image.getId(), 6,
                    genre.getName(), "film", "beschreibung", 4.2F, 129, "08-01-2023", "08-04-2023");

            GenreDoesNotExistException e = new GenreDoesNotExistException("ActionP");
            when(movieService.add(movieRequestObject)).thenThrow(e);

            ResponseEntity<?> response = movieController.add(movieRequestObject);
            assertAll(
                    "Validating correct response from controller...",
                    () -> assertEquals(response.getBody(), e.getMessage()),
                    () -> assertEquals(response.getStatusCode(), HttpStatus.NOT_ACCEPTABLE)
            );
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to get bytes");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed adding a new film");
        }
    }

    @Test
    @DisplayName("Test unsuccessfully adding movie with wrong Actor")
    public void testAddMovieUnsuccessfullyWithWrongActor() {
        try {
            Producer producer = new Producer("prod");
            Director director = new Director("director", "dir");
            Genre genre = new Genre(UUID.randomUUID(), "Action");
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData image = new ImageData("image/png", data, false);
            Actor a = new Actor("Bahde", "Paul");
            a.setId(UUID.randomUUID());
            List<UUID> actors = List.of(a.getId());
            MovieRequestObject movieRequestObject = new MovieRequestObject(
                    producer.getName(), director.getFirstName(), director.getName(), actors, image.getId(), 6,
                    genre.getName(), "film", "beschreibung", 4.2F, 129, "08-01-2023", "08-04-2023");

            ActorNotFoundException e = new ActorNotFoundException(a.getId());
            when(movieService.add(movieRequestObject)).thenThrow(e);

            ResponseEntity<?> response = movieController.add(movieRequestObject);
            assertAll(
                    "Validating correct response from controller...",
                    () -> assertEquals(response.getBody(), e.getMessage()),
                    () -> assertEquals(response.getStatusCode(), HttpStatus.NOT_ACCEPTABLE)
            );
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to get bytes");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed adding a new film");
        }
    }

    @Test
    @DisplayName("Test unsuccessfully adding movie with wrong Image")
    public void testAddMovieUnsuccessfullyWithWrongImage() {
        try {
            Producer producer = new Producer("prod");
            Director director = new Director("director", "dir");
            Genre genre = new Genre(UUID.randomUUID(), "Action");
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData image = new ImageData("image/png", data, false);
            Actor a = new Actor("Bahde", "Paul");
            a.setId(UUID.randomUUID());
            List<UUID> actors = List.of(a.getId());
            MovieRequestObject movieRequestObject = new MovieRequestObject(
                    producer.getName(), director.getFirstName(), director.getName(), actors, image.getId(), 6,
                    genre.getName(), "film", "beschreibung", 4.2F, 129, "08-01-2023", "08-04-2023");

            ImageNotFoundException e = new ImageNotFoundException(image.getId());
            when(movieService.add(movieRequestObject)).thenThrow(e);

            ResponseEntity<?> response = movieController.add(movieRequestObject);
            assertAll(
                    "Validating correct response from controller...",
                    () -> assertEquals(response.getBody(), e.getMessage()),
                    () -> assertEquals(response.getStatusCode(), HttpStatus.NOT_ACCEPTABLE)
            );
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to get bytes");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed adding a new film");
        }
    }
}
