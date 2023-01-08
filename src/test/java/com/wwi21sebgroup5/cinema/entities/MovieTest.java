package com.wwi21sebgroup5.cinema.entities;

import com.wwi21sebgroup5.cinema.enums.FSK;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
        LocalDate startDate = LocalDate.of(2023, 1, 12), endDate = LocalDate.of(2023, 3, 14);

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
        UUID id = UUID.randomUUID();
        Movie first = getMovie(id);
        Movie second = getMovie(id);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertEquals(first, first);

        assertNotEquals(first, "String");
        assertNotEquals(first, null);

        second.setName(null);
        assertNotEquals(first, second);

        second = getMovie(id);
        second.setId(null);
        assertNotEquals(first, second);

        second = getMovie(id);
        second.setDirector(null);
        assertNotEquals(first, second);

        second = getMovie(id);
        second.setFsk(null);
        assertNotEquals(first, second);

        second = getMovie(id);
        second.setDescription(null);
        assertNotEquals(first, second);

        second = getMovie(id);
        second.setGenre(null);
        assertNotEquals(first, second);

        second = getMovie(id);
        second.setImage(null);
        assertNotEquals(first, second);

        second = getMovie(id);
        second.setEnd_date(null);
        assertNotEquals(first, second);

        second = getMovie(id);
        second.setStart_date(null);
        assertNotEquals(first, second);

        second = getMovieNull();
        assertNotEquals(first.hashCode(), second.hashCode());

    }

    private Movie getMovie(UUID id) {
        Producer producer = setupProducer();
        Director director = setupDirector();
        FSK fsk = FSK.SIX;
        Genre genre = setupGenre();
        ImageData imageData = setupImage();
        String name = "testName", description = "testDescription";
        LocalDate startDate = LocalDate.of(2023, 1, 12), endDate = LocalDate.of(2023, 3, 14);

        Movie movie = new Movie(
                producer, director, fsk, genre, imageData, name, description, startDate, endDate
        );
        movie.setId(id);
        return movie;
    }

    private Movie getMovieNull() {
        return new Movie(null, null, null, null, null, null, null, null, null);
    }

}
