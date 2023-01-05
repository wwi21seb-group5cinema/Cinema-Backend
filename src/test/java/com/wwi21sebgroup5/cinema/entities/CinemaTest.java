package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CinemaTest {

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        String name = "testName", plz = "70565", cityName = "Stuttgart", street = "testStreet", houseNumber =
                "testHouseNumber";
        int floors = 3;
        City city = new City(plz, cityName);

        Cinema allArgsCinema = new Cinema(
                name, List.of(), city, street, houseNumber, floors
        );

        assertAll(
                "Testing all parameters...",
                () -> assertEquals(name, allArgsCinema.getName()),
                () -> assertTrue(allArgsCinema.getHalls().isEmpty()),
                () -> assertEquals(city, allArgsCinema.getCity()),
                () -> assertEquals(street, allArgsCinema.getStreet()),
                () -> assertEquals(houseNumber, allArgsCinema.getHouseNumber()),
                () -> assertEquals(floors, allArgsCinema.getFloors()),
                () -> assertEquals(0, allArgsCinema.getCinemaRooms())
        );
    }

    @Test
    @DisplayName("Test equality")
    public void testEquality() {
        String name = "testName", plz = "70565", cityName = "Stuttgart", street = "testStreet", houseNumber =
                "testHouseNumber";
        int floors = 3;
        City city = new City(plz, cityName);

        Cinema firstCinema = new Cinema(
                name, List.of(), city, street, houseNumber, floors
        );
        Cinema secondCinema = new Cinema(
                name, List.of(), city, street, houseNumber, floors
        );
        Cinema thirdCinema = new Cinema(
                name, List.of(), city, street, "testHouseNumber2", floors
        );

        assertAll(
                "Validating equality",
                () -> assertEquals(firstCinema, secondCinema),
                () -> assertEquals(firstCinema.hashCode(), secondCinema.hashCode()),
                () -> assertEquals(firstCinema, firstCinema)
        );

        assertAll(
                "Validating parameters...",
                () -> assertNotEquals(firstCinema, "String"),
                () -> assertNotEquals(firstCinema, thirdCinema),
                () -> assertNotEquals(firstCinema, null)
        );

        secondCinema.setId(UUID.randomUUID());

        assertAll(
                "Validating inequality",
                () -> assertNotEquals(firstCinema, secondCinema),
                () -> assertNotEquals(firstCinema.hashCode(), secondCinema.hashCode())
        );
    }

}
