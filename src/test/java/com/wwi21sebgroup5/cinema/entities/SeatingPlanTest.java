package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SeatingPlanTest {

    private SeatingPlan setupSeatingPlan(UUID uuid) {
        SeatingPlan seatingPlan = new SeatingPlan(
                new CinemaHall(), 3
        );
        seatingPlan.setSeats(List.of(new SeatBlueprint(), new SeatBlueprint()));
        seatingPlan.setId(uuid);
        return seatingPlan;
    }

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        CinemaHall cinemaHall = new CinemaHall();
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
        SeatingPlan firstSeatingPlan = setupSeatingPlan(UUID.randomUUID());
        SeatingPlan secondSeatingPlan = setupSeatingPlan(firstSeatingPlan.getId());

        // test equality
        assertEquals(firstSeatingPlan, secondSeatingPlan);
        assertEquals(firstSeatingPlan.hashCode(), secondSeatingPlan.hashCode());

        // test null and different class
        assertNotEquals(firstSeatingPlan, null);
        assertNotEquals(firstSeatingPlan, "");

        // test different id
        secondSeatingPlan.setId(UUID.randomUUID());
        assertNotEquals(firstSeatingPlan, secondSeatingPlan);

        // test different rows
        secondSeatingPlan = setupSeatingPlan(firstSeatingPlan.getId());
        secondSeatingPlan.setRows(4);
        assertNotEquals(firstSeatingPlan, secondSeatingPlan);

        // test different seats
        secondSeatingPlan = setupSeatingPlan(firstSeatingPlan.getId());
        secondSeatingPlan.setSeats(List.of(new SeatBlueprint(), new SeatBlueprint(), new SeatBlueprint()));
        assertNotEquals(firstSeatingPlan, secondSeatingPlan);
    }

}
