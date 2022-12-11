package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CityTest {

    @Test
    @DisplayName("Testing the constructor")
    public void constructorTest() {
        String plz = "71672", cityName = "Marbach am Neckar";
        City allArgsCity = new City(plz, cityName);

        assertAll(
                "Testing all parameters",
                () -> assertEquals(plz, allArgsCity.getPlz(), "PLZ returned wrong"),
                () -> assertEquals(cityName, allArgsCity.getName(), "CityName returned wrong")
        );
    }

    @Test
    @DisplayName("Testing equality")
    public void equalityTest() {
        String plz = "68259", firstName = "Mannheim", secondName = "Wallstadt";
        City firstCity = new City(plz, firstName);
        City secondCity = new City(plz, secondName);

        assertAll(
                "Testing inequality with different cities",
                () -> assertNotEquals(firstCity, secondCity, "Equals returns wrong result"),
                () -> assertNotEquals(firstCity.hashCode(), secondCity.hashCode(),
                        "HashCode returns wrong result")
        );


        firstCity.setName(secondName);

        assertAll(
                "Testing equality with same cities",
                () -> assertEquals(firstCity, secondCity, "Equals returns wrong result"),
                () -> assertEquals(firstCity.hashCode(), secondCity.hashCode(),
                        "HashCode returns wrong result")
        );
    }

}
