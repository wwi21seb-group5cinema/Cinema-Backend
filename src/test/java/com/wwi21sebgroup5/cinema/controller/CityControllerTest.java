package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.services.CityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityControllerTest {

    @Mock
    CityService cityService;

    @InjectMocks
    CityController cityController;

    @Test
    @DisplayName("Test successfully getting all cities")
    public void testGetAllCitiesSuccessful() {
        City firstCity = new City("68259", "Mannheim");
        City secondCity = new City("71672", "Marbach am Neckar");
        City thirdCity = new City("70565", "Stuttgart-Vaihingen");
        List<City> expectedCities = List.of(firstCity, secondCity, thirdCity);

        when(cityService.getAllCities()).thenReturn(expectedCities);

        ResponseEntity<List<City>> response = cityController.getAllCities();

        assertAll(
                "Validating correct response from controller...",
                () -> assertIterableEquals(response.getBody(), expectedCities),
                () -> assertEquals(response.getStatusCode(), HttpStatus.OK)
        );
    }

    @Test
    @DisplayName("Test unsuccessfully getting all cities")
    public void testGetAllCitiesNotSuccessful() {
        when(cityService.getAllCities()).thenReturn(List.of());

        ResponseEntity<List<City>> response = cityController.getAllCities();

        assertAll(
                "Validating correct response from controller...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND)
        );
    }

    @Test
    @DisplayName("Test successfully getting city by plz")
    public void testGetCityByPlzSuccessful() {
        City expectedCity = new City("68259", "Mannheim");

        when(cityService.getCityByPlz("68259")).thenReturn(Optional.of(expectedCity));

        ResponseEntity<City> response = cityController.getCityByPlz("68259");

        assertAll(
                "Validating correct response from controller...",
                () -> assertEquals(response.getBody(), expectedCity),
                () -> assertEquals(response.getStatusCode(), HttpStatus.OK)
        );
    }

    @Test
    @DisplayName("Test unsuccessfully getting all cities by plz")
    public void testGetCityByPlzNotSuccessful() {
        when(cityService.getCityByPlz("68259")).thenReturn(Optional.empty());

        ResponseEntity<City> response = cityController.getCityByPlz("68259");

        assertAll(
                "Validating correct response from controller...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND)
        );
    }

    @Test
    @DisplayName("Test successfully getting all cities by city name")
    public void testGetAllCitiesByCityNameSuccessful() {
        City firstCity = new City("70565", "Stuttgart");
        City secondCity = new City("70564", "Stuttgart");
        List<City> expectedCities = List.of(firstCity, secondCity);
        when(cityService.getAllCitiesByName("Stuttgart")).thenReturn(expectedCities);

        ResponseEntity<List<City>> response = cityController.getAllCitiesByName("Stuttgart");

        assertAll(
                "Validating correct response from controller...",
                () -> assertIterableEquals(response.getBody(), expectedCities),
                () -> assertEquals(response.getStatusCode(), HttpStatus.OK)
        );
    }

    @Test
    @DisplayName("Test unsuccessfully getting all cities by city name")
    public void testGetAllCitiesByCityNameNotSuccessful() {
        when(cityService.getAllCitiesByName("Mannheim")).thenReturn(List.of());

        ResponseEntity<List<City>> response = cityController.getAllCitiesByName("Mannheim");

        assertAll(
                "Validating correct response from controller...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND)
        );
    }

}
