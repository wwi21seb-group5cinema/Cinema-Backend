package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {

    private Producer setupProducer() {
        return new Producer();
    }

    private Director setupDirector() {
        return new Director();
    }

    private Genre setupGenre() {
        return new Genre();
    }

    private ImageData setupImage() {
        return new ImageData();
    }

    @Test
    @DisplayName("Test constructo")
    public void testConstructor() {
        Producer producer = setupProducer();
        Director director = setupDirector();
        FSK fsk = FSK.SIX;
        Genre genre = setupGenre();
        ImageData imageData = setupImage();
        String name = "testName", description = "testDescription";
        Date startDate = Date.valueOf("2023-1-12"), endDate = Date.valueOf("2023-3-14");

        Movie allArgsMovie = new Movie(
                producer, director, fsk, genre, imageData, name, description, startDate, endDate
        );

        assertAll(
                "Asserting parameters...",
                () -> assertEquals(producer, allArgsMovie.getProducer()),
                () -> assertEquals(director, allArgsMovie.getDirector()),
                () -> assertEquals(fsk, allArgsMovie.getFsk()),
                () -> assertEquals(genre, allArgsMovie.getGenre()),
                () -> assertEquals(imageData, allArgsMovie.getImage()),
                () -> assertEquals(name, allArgsMovie.getName()),
                () -> assertEquals(description, allArgsMovie.getDescription()),
                () -> assertEquals(startDate, allArgsMovie.getStart_date()),
                () -> assertEquals(endDate, allArgsMovie.getEnd_date())
        );
    }

    @Test
    @DisplayName("Test equality")
    public void testEquality() {
        Producer producer = setupProducer();
        Director director = setupDirector();
        FSK fsk = FSK.SIX;
        Genre genre = setupGenre();
        ImageData imageData = setupImage();
        String name = "testName", description = "testDescription";
        Date startDate = Date.valueOf("2023-1-12"), endDate = Date.valueOf("2023-3-14");

        Movie firstMovie = new Movie(
                producer, director, fsk, genre, imageData, name, description, startDate, endDate
        );
        Movie secondMovie = new Movie(
                producer, director, fsk, genre, imageData, name, description, startDate, endDate
        );

        assertAll(
                "Asserting equality...",
                () -> assertEquals(firstMovie, secondMovie),
                () -> assertEquals(firstMovie.hashCode(), secondMovie.hashCode())
        );

        secondMovie.setId(UUID.randomUUID());

        assertAll(
                "Asserting equality...",
                () -> assertNotEquals(firstMovie, secondMovie),
                () -> assertNotEquals(firstMovie.hashCode(), secondMovie.hashCode())
        );
    }

}
