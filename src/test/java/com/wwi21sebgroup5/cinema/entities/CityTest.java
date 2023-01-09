package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

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
        UUID id = UUID.randomUUID();
        City firstCity = getCity(id);
        City secondCity = getCity(id);

        assertEquals(firstCity, secondCity);
        assertEquals(firstCity.hashCode(), secondCity.hashCode());
        assertEquals(firstCity, firstCity);

        assertNotEquals(firstCity, "String");
        assertNotEquals(firstCity, null);

        secondCity.setPlz(null);
        assertNotEquals(firstCity, secondCity);

        secondCity = getCity(id);
        secondCity.setName(null);
        assertNotEquals(firstCity, secondCity);

        secondCity = getCity(UUID.randomUUID());
        assertNotEquals(firstCity, secondCity);

        secondCity = getCityNull();
        assertNotEquals(firstCity.hashCode(), secondCity.hashCode());
    }

    private City getCity(UUID id) {
        String plz = "68259", firstName = "Mannheim";
        City city = new City(plz, firstName);
        city.setId(id);
        return city;
    }

    private City getCityNull() {
        return new City(null, null);
    }

}
