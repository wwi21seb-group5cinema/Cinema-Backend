package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Cinema;
import com.wwi21sebgroup5.cinema.entities.CinemaHall;
import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.services.CinemaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CinemaControllerTest {

    @Mock
    CinemaService cinemaService;

    @InjectMocks
    CinemaController cinemaController;

    @Test
    @DisplayName("Test successfully getting all cinemas")
    public void testGettingAllCinemasSuccessful() {
        City firstCity = new City("68259", "Mannheim");
        City secondCity = new City("71672", "Marbach am Neckar");
        City thirdCity = new City("70565", "Stuttgart-Vaihingen");

        List<CinemaHall> hallsFirstCinema = List.of(new CinemaHall(), new CinemaHall(), new CinemaHall());
        List<CinemaHall> hallsSecondCinema = List.of(new CinemaHall(), new CinemaHall(), new CinemaHall());
        List<CinemaHall> hallsThirdCinema = List.of(new CinemaHall(), new CinemaHall(), new CinemaHall());

        Cinema firstCinema = new Cinema("firstTestCinema", hallsFirstCinema, firstCity, "firstTestStreet",
                "firstTestHouseNumber", 2);
        Cinema secondCinema = new Cinema("secondTestCinema", hallsSecondCinema, secondCity, "secondTestStreet",
                "secondHouseNumber", 3);
        Cinema thirdCinema = new Cinema("thirdTestCinema", hallsThirdCinema, thirdCity, "thirdTestStreet",
                "thirdTestHouseNumber", 4);

        List<Cinema> expectedCinemas = List.of(firstCinema, secondCinema, thirdCinema);
        when(cinemaService.getAllCinemas()).thenReturn(expectedCinemas);

        ResponseEntity<List<Cinema>> response = cinemaController.getAllCinemas();
        assertAll(
                "Validating response...",
                () -> assertEquals(expectedCinemas, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test getting all cinemas without success")
    public void testGettingAllCinemasNotSuccessful() {
        when(cinemaService.getAllCinemas()).thenReturn(List.of());
        ResponseEntity<List<Cinema>> response = cinemaController.getAllCinemas();

        assertAll(
                "Validationg response...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

}
