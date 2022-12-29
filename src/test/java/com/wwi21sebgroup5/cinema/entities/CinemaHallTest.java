package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CinemaHallTest {

    private SeatingPlan setupSeatingPlan() {
        return new SeatingPlan();
    }

    private Cinema setupCinema() {
        String name = "testName", plz = "70565", cityName = "Stuttgart", street = "testStreet", houseNumber =
                "testHouseNumber";
        int floors = 3;
        City city = new City(plz, cityName);

        return new Cinema(
                name, List.of(), city, street, houseNumber, floors
        );
    }

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        Cinema cinema = setupCinema();
        SeatingPlan seatingPlan = setupSeatingPlan();
        String name = "testName";
        int floor = 2;

        CinemaHall allArgsCinemaHall = new CinemaHall(
                cinema, seatingPlan, name, floor
        );

        assertAll(
                "Validating parameters...",
                () -> assertEquals(cinema, allArgsCinemaHall.getCinema()),
                () -> assertEquals(seatingPlan, allArgsCinemaHall.getSeatingPlan()),
                () -> assertEquals(name, allArgsCinemaHall.getName()),
                () -> assertEquals(floor, allArgsCinemaHall.getFloor())
        );
    }

    @Test
    @DisplayName("Test equality")
    public void testEquality() {
        Cinema cinema = setupCinema();
        SeatingPlan seatingPlan = setupSeatingPlan();
        String name = "testName";
        int floor = 2;

        CinemaHall firstCinemaHall = new CinemaHall(
                cinema, seatingPlan, name, floor
        );
        CinemaHall secondCinemaHall = new CinemaHall(
                cinema, seatingPlan, name, floor
        );

        assertAll(
                "Validating equality",
                () -> assertEquals(firstCinemaHall, secondCinemaHall),
                () -> assertEquals(firstCinemaHall.hashCode(), secondCinemaHall.hashCode())
        );

        secondCinemaHall.setId(UUID.randomUUID());

        assertAll(
                "Validating equality",
                () -> assertNotEquals(firstCinemaHall, secondCinemaHall),
                () -> assertNotEquals(firstCinemaHall.hashCode(), secondCinemaHall.hashCode())
        );
    }

}
