package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Cinema;
import com.wwi21sebgroup5.cinema.entities.CinemaHall;
import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.exceptions.CinemaAlreadyExistsException;
import com.wwi21sebgroup5.cinema.requestObjects.CinemaRequestObject;
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
import java.util.Optional;
import java.util.UUID;

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

    @Test
    @DisplayName("Test successfully getting cinema by id")
    public void testGettingCinemaByIdSuccessful() {
        UUID id = UUID.randomUUID();
        City city = new City("68259", "Mannheim");
        List<CinemaHall> cinemaHalls = List.of(new CinemaHall(), new CinemaHall(), new CinemaHall());
        Cinema expectedCinema = new Cinema("firstTestCinema", cinemaHalls, city, "firstTestStreet",
                "firstTestHouseNumber", 2);
        expectedCinema.setId(id);

        when(cinemaService.getCinemaById(id)).thenReturn(Optional.of(expectedCinema));

        ResponseEntity<Cinema> response = cinemaController.getCinemaById(id);
        assertAll(
                "Validating response...",
                () -> assertEquals(expectedCinema, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test unsuccessfully getting cinema by id")
    public void testGettingCinemaByIdNotSuccessful() {
        UUID id = UUID.randomUUID();

        when(cinemaService.getCinemaById(id)).thenReturn(Optional.empty());

        ResponseEntity<Cinema> response = cinemaController.getCinemaById(id);
        assertAll(
                "Validating response...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test adding cinema successful")
    public void testAddCinemaSuccessful() {
        CinemaRequestObject requestObject = new CinemaRequestObject(
                "TestName", "68259", "Wallstadt", "TestStraße", "28", 4
        );
        City city = new City("68259", "Wallstadt");

        Cinema expectedCinema = new Cinema(
            "TestName", List.of(), city, "TestSta0e", "28", 4
        );

        try {
            when(cinemaService.add(requestObject)).thenReturn(expectedCinema);
        } catch (CinemaAlreadyExistsException e) {
            fail();
        }

        ResponseEntity<Object> response = cinemaController.addCinema(requestObject);

        assertAll(
                "Validating response...",
                () -> assertEquals(expectedCinema, response.getBody()),
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test cinema already exists while adding cinema")
    public void testCinemaAlreadyExistsWhileAddingCinema() {
        String plz = "68259", cityName = "Wallstadt", street = "TestStraße", houseNumber = "28";
        CinemaRequestObject requestObject = new CinemaRequestObject(
                "TestName", plz, cityName, street, houseNumber, 4
        );

        try {
            when(cinemaService.add(requestObject))
                    .thenThrow(new CinemaAlreadyExistsException(plz, cityName, street, houseNumber));
        } catch (CinemaAlreadyExistsException e) {
            fail();
        }

        ResponseEntity<Object> response = cinemaController.addCinema(requestObject);

        assertAll(
                "Validating response...",
                () -> assertEquals("Cinema already exists at the location 68259 Wallstadt, TestStraße 28",
                        response.getBody()),
                () -> assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test interal server error while adding cinema")
    public void testInternalServerErrorWhileAddingCinema() {
        String plz = "68259", cityName = "Wallstadt", street = "TestStraße", houseNumber = "28";
        CinemaRequestObject requestObject = new CinemaRequestObject(
                "TestName", plz, cityName, street, houseNumber, 4
        );

        try {
            when(cinemaService.add(requestObject))
                    .thenThrow(new RuntimeException());
        } catch (CinemaAlreadyExistsException e) {
            fail();
        }

        ResponseEntity<Object> response = cinemaController.addCinema(requestObject);

        assertAll(
                "Validating response...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode())
        );
    }

}
