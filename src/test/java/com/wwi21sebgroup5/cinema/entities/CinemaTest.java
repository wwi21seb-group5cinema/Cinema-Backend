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

        Cinema firstCinema = getCinema();
        Cinema secondCinema = getCinema();

        assertEquals(firstCinema, secondCinema);
        assertEquals(firstCinema.hashCode(), secondCinema.hashCode());
        assertEquals(firstCinema, firstCinema);
        
        assertNotEquals(firstCinema, "String");
        assertNotEquals(firstCinema, null);

        secondCinema.setStreet(null);
        assertNotEquals(firstCinema, secondCinema);

        secondCinema = getCinema();
        secondCinema.setCity(null);
        assertNotEquals(firstCinema, secondCinema);

        secondCinema = getCinema();
        secondCinema.setHalls(null);
        assertNotEquals(firstCinema, secondCinema);

        secondCinema = getCinema();
        secondCinema.setName(null);
        assertNotEquals(firstCinema, secondCinema);

        secondCinema = getCinema();
        secondCinema.setId(UUID.randomUUID());
        assertNotEquals(firstCinema, secondCinema);

        secondCinema = getCinema();
        secondCinema.setCinemaRooms(2);
        assertNotEquals(firstCinema, secondCinema);

        secondCinema = getCinema();
        secondCinema.setFloors(0);
        assertNotEquals(firstCinema, secondCinema);

        secondCinema = getCinemaNull();
        assertNotEquals(firstCinema.hashCode(), secondCinema.hashCode());

    }

    private Cinema getCinemaNull() {
        return new Cinema(
                null, null, null, null, null, 0
        );
    }

    private Cinema getCinema() {
        String name = "testName", plz = "70565", cityName = "Stuttgart", street = "testStreet", houseNumber =
                "testHouseNumber";
        int floors = 3;
        City city = new City(plz, cityName);

        return new Cinema(
                name, List.of(), city, street, houseNumber, floors
        );
    }

}
