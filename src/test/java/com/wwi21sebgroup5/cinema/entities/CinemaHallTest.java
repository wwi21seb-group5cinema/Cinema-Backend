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
        UUID id = UUID.randomUUID();
        CinemaHall firstCinemaHall = getCinemaHall(id);
        CinemaHall secondCinemaHall = getCinemaHall(id);

        assertEquals(firstCinemaHall, secondCinemaHall);
        assertEquals(firstCinemaHall.hashCode(), secondCinemaHall.hashCode());
        assertEquals(firstCinemaHall, firstCinemaHall);

        assertNotEquals(firstCinemaHall, "String");
        assertNotEquals(firstCinemaHall, null);

        secondCinemaHall.setCinema(null);
        assertNotEquals(firstCinemaHall, secondCinemaHall);

        secondCinemaHall = getCinemaHall(id);
        secondCinemaHall.setName(null);
        assertNotEquals(firstCinemaHall, secondCinemaHall);

        secondCinemaHall = getCinemaHall(UUID.randomUUID());
        assertNotEquals(firstCinemaHall, secondCinemaHall);

        secondCinemaHall = getCinemaHall(id);
        secondCinemaHall.setFloor(9);
        assertNotEquals(firstCinemaHall, secondCinemaHall);

        secondCinemaHall = getCinemaHall(id);
        secondCinemaHall.setSeatingPlan(null);
        assertNotEquals(firstCinemaHall, secondCinemaHall);

        secondCinemaHall = getCinemaHallNull();
        assertNotEquals(firstCinemaHall.hashCode(), secondCinemaHall.hashCode());
    }

    private CinemaHall getCinemaHallNull() {
        return new CinemaHall(
                null, null, null, 0
        );
    }

    private CinemaHall getCinemaHall(UUID id) {
        Cinema cinema = setupCinema();
        SeatingPlan seatingPlan = setupSeatingPlan();
        String name = "testName";
        int floor = 2;

        CinemaHall c = new CinemaHall(
                cinema, seatingPlan, name, floor
        );
        c.setId(id);
        return c;
    }

}
