package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ActsInTest {

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        Movie movie = new Movie();
        Actor actor = new Actor();

        ActsIn actsIn = new ActsIn(movie, actor);
        assertAll(
                "Validatiing parameters...",
                () -> assertEquals(movie, actsIn.getMovie()),
                () -> assertEquals(actor, actsIn.getActor())
        );
    }

    @Test
    @DisplayName("Test equality")
    public void testEquality() {
        Movie movie = new Movie();
        Actor actor = new Actor();
        Movie movie2 = new Movie();
        movie2.setId(UUID.randomUUID());
        ActsIn firstActsIn = new ActsIn(movie, actor);
        ActsIn secondActsIn = new ActsIn(movie, actor);
        ActsIn thirdActsIn = new ActsIn(movie2, actor);
        ActsIn forthActsIn = new ActsIn(null, null);

        assertAll(
                "Validating parameters...",
                () -> assertEquals(firstActsIn, secondActsIn),
                () -> assertEquals(firstActsIn.hashCode(), secondActsIn.hashCode()),
                () -> assertEquals(firstActsIn, firstActsIn)
        );

        assertAll(
                "Validating parameters...",
                () -> assertNotEquals(firstActsIn, thirdActsIn),
                () -> assertNotEquals(firstActsIn, "String"),
                () -> assertNotEquals(firstActsIn, null)
        );

        secondActsIn.setId(UUID.randomUUID());

        assertAll(
                "Validating parameters...",
                () -> assertNotEquals(firstActsIn, secondActsIn),
                () -> assertNotEquals(firstActsIn.hashCode(), secondActsIn.hashCode())
        );
        assertAll(
                "Validating parameters...",
                () -> assertNotEquals(firstActsIn.hashCode(), forthActsIn.hashCode())
        );
    }

}
