package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GenreTest {

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        UUID id = UUID.randomUUID();
        String genreName = "TestGenre";
        Genre genre = new Genre(id, genreName);

        assertAll(
                "Validating parameters..",
                () -> assertEquals(id, genre.getId()),
                () -> assertEquals(genreName, genre.getName())
        );
    }

    @Test
    @DisplayName("Test equality")
    public void testEquality() {
        UUID id = UUID.randomUUID();
        String genreName = "TestGenre";

        Genre firstGenre = new Genre(id, genreName);
        Genre secondGenre = new Genre(id, genreName);

        assertAll(
                "Validating equality..",
                () -> assertEquals(firstGenre, secondGenre),
                () -> assertEquals(firstGenre.hashCode(), secondGenre.hashCode()),
                () -> assertEquals(firstGenre, firstGenre)
        );

        secondGenre.setId(UUID.randomUUID());

        assertAll(
                "Validating equality..",
                () -> assertNotEquals(firstGenre, secondGenre),
                () -> assertNotEquals(firstGenre, null),
                () -> assertNotEquals(firstGenre, "String"),
                () -> assertNotEquals(firstGenre.hashCode(), secondGenre.hashCode())
        );
    }

}
