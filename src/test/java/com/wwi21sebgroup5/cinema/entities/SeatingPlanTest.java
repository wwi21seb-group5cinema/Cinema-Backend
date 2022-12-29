package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SeatingPlanTest {

    private CinemaHall setupCinemaHall() {
        return new CinemaHall();
    }

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        CinemaHall cinemaHall = setupCinemaHall();
        int rows = 13;

        SeatingPlan allArgsSeatingPlan = new SeatingPlan(
                cinemaHall, rows
        );

        assertAll(
                "Validating parameters...",
                () -> assertEquals(cinemaHall, allArgsSeatingPlan.getCinemaHall()),
                () -> assertEquals(rows, allArgsSeatingPlan.getRows())
        );
    }

    @Test
    @DisplayName("Test equality")
    public void testEquality() {
        CinemaHall cinemaHall = setupCinemaHall();
        int rows = 13;

        SeatingPlan firstSeatingPlan = new SeatingPlan(
                cinemaHall, rows
        );
        SeatingPlan secondSeatingPlan = new SeatingPlan(
                cinemaHall, rows
        );

        assertAll(
                "Validating equality...",
                () -> assertEquals(firstSeatingPlan, secondSeatingPlan),
                () -> assertEquals(firstSeatingPlan.hashCode(), secondSeatingPlan.hashCode())
        );

        secondSeatingPlan.setId(UUID.randomUUID());

        assertAll(
                "Validating inequality...",
                () -> assertNotEquals(firstSeatingPlan, secondSeatingPlan),
                () -> assertNotEquals(firstSeatingPlan.hashCode(), secondSeatingPlan.hashCode())
        );
    }

}
