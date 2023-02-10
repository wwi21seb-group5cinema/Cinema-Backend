package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Cinema;
import com.wwi21sebgroup5.cinema.entities.CinemaHall;
import com.wwi21sebgroup5.cinema.entities.SeatingPlan;
import com.wwi21sebgroup5.cinema.exceptions.CinemaNotFoundException;
import com.wwi21sebgroup5.cinema.requestObjects.CinemaHallRequestObject;
import com.wwi21sebgroup5.cinema.services.CinemaHallService;
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
public class CinemaHallControllerTest {

    @Mock
    CinemaHallService cinemaHallService;

    @InjectMocks
    CinemaHallController cinemaHallController;

    @Test
    @DisplayName("Test add cinemahall successfully")
    public void testAddCinemaHallSuccessful() {
        Cinema cinema = new Cinema();
        UUID id = UUID.randomUUID();
        cinema.setId(id);

        CinemaHallRequestObject requestObject = new CinemaHallRequestObject(
                id, 8, 9, "TestName", 3
        );
        CinemaHall expectedHall = new CinemaHall(
                cinema, new SeatingPlan(), "TestName", 3
        );

        try {
            when(cinemaHallService.addCinemaHall(requestObject)).thenReturn(expectedHall);
        } catch (CinemaNotFoundException e) {
            fail("Error when adding cinema hall");
        }

        ResponseEntity<Object> response = cinemaHallController.addCinemaHall(requestObject);

        assertAll(
                "Validating response...",
                () -> assertEquals(expectedHall, response.getBody()),
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test cinema not found when adding cinemahall")
    public void testCinemaNotFoundDuringAddProcess() {
        UUID id = UUID.randomUUID();
        CinemaHallRequestObject requestObject = new CinemaHallRequestObject(
                id, 8, 9, "TestName", 3
        );

        try {
            when(cinemaHallService.addCinemaHall(requestObject)).thenThrow(new CinemaNotFoundException(id));
        } catch (CinemaNotFoundException e) {
            fail("Error when adding cinema hall");
        }

        ResponseEntity<Object> response = cinemaHallController.addCinemaHall(requestObject);

        assertAll(
                "Validating response...",
                () -> assertEquals(String.format("Cinema with id %s not found", id), response.getBody()),
                () -> assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test internal server error thrown when adding cinemahall")
    public void testInternalServerErrorDuringAddProcess() {
        UUID id = UUID.randomUUID();
        CinemaHallRequestObject requestObject = new CinemaHallRequestObject(
                id, 8, 9, "TestName", 3
        );

        try {
            when(cinemaHallService.addCinemaHall(requestObject)).thenThrow(new RuntimeException());
        } catch (CinemaNotFoundException e) {
            fail("Error when adding cinema hall");
        }

        ResponseEntity<Object> response = cinemaHallController.addCinemaHall(requestObject);

        assertAll(
                "Validating response...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test getting all cinemaHalls successfully")
    public void testGetAllCinemaHallsSuccessful() {
        Cinema cinema = new Cinema();
        UUID id = UUID.randomUUID();
        cinema.setId(id);

        CinemaHall firstHall = new CinemaHall(cinema, new SeatingPlan(), "firstTestHall", 1);
        CinemaHall secondHall = new CinemaHall(cinema, new SeatingPlan(), "secondTestHall", 2);
        CinemaHall thirdHall = new CinemaHall(cinema, new SeatingPlan(), "thirdTestHall", 3);
        List<CinemaHall> expectedHalls = List.of(firstHall, secondHall, thirdHall);

        when(cinemaHallService.getAllCinemaHallsByCinema(id)).thenReturn(expectedHalls);

        ResponseEntity<List<CinemaHall>> response = cinemaHallController.getAllByCinema(id);
        assertAll(
                "Validating response..",
                () -> assertEquals(expectedHalls, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test getting all cinemaHalls not successfully")
    public void testGetAllCinemaHallsNotSuccessful() {
        UUID id = UUID.randomUUID();

        when(cinemaHallService.getAllCinemaHallsByCinema(id)).thenReturn(List.of());

        ResponseEntity<List<CinemaHall>> response = cinemaHallController.getAllByCinema(id);
        assertAll(
                "Validating response..",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test getting cinemaHall by id successfully")
    public void testGetCinemaHallByIdSuccessful() {
        UUID id = new UUID(2, 2);
        CinemaHall expectedHall = new CinemaHall(new Cinema(), new SeatingPlan(), "firstTestHall", 1);
        expectedHall.setId(id);

        when(cinemaHallService.getCinemaHallById(id)).thenReturn(Optional.of(expectedHall));

        ResponseEntity<CinemaHall> response = cinemaHallController.getById(id);
        assertAll(
                "Validating response..",
                () -> assertEquals(expectedHall, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test getting cinemaHall by id not successfully")
    public void testGetCinemaHallByIdNotSuccessful() {
        UUID id = new UUID(2, 2);
        when(cinemaHallService.getCinemaHallById(id)).thenReturn(Optional.empty());

        ResponseEntity<CinemaHall> response = cinemaHallController.getById(id);
        assertAll(
                "Validating response..",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

}
