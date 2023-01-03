package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.CinemaHall;
import com.wwi21sebgroup5.cinema.entities.SeatingPlan;
import com.wwi21sebgroup5.cinema.exceptions.CinemaHallNotFoundException;
import com.wwi21sebgroup5.cinema.requestObjects.SeatingPlanRequestObject;
import com.wwi21sebgroup5.cinema.services.SeatingPlanService;
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
public class SeatingPlanControllerTest {

    @Mock
    SeatingPlanService seatingPlanService;

    @InjectMocks
    SeatingPlanController seatingPlanController;

    private CinemaHall setupCinemaHall() {
        UUID id = UUID.randomUUID();
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setId(id);
        return cinemaHall;
    }

    @Test
    @DisplayName("Test add seating plan successfully")
    public void testAddSeatingPlanSuccessful() {
        CinemaHall cinemaHall = setupCinemaHall();
        SeatingPlanRequestObject requestObject = new SeatingPlanRequestObject(
                cinemaHall.getId(), 7, 10
        );
        SeatingPlan expectedPlan = new SeatingPlan(cinemaHall, 7);

        try {
            when(seatingPlanService.addSeatingPlan(requestObject)).thenReturn(expectedPlan);
        } catch (CinemaHallNotFoundException e) {
            fail("Error when adding seating plan");
        }

        ResponseEntity<Object> response = seatingPlanController.addSeatingPlan(requestObject);

        assertAll(
                "Validating response...",
                () -> assertEquals(expectedPlan, response.getBody()),
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test cinema hall not found when adding seating plan")
    public void testCinemaHallNotFoundDuringAddProcess() {
        UUID id = UUID.randomUUID();
        SeatingPlanRequestObject requestObject = new SeatingPlanRequestObject(
                id, 7, 10
        );

        try {
            when(seatingPlanService.addSeatingPlan(requestObject)).thenThrow(new CinemaHallNotFoundException(id));
        } catch (CinemaHallNotFoundException e) {
            fail("Error when adding seating plan");
        }

        ResponseEntity<Object> response = seatingPlanController.addSeatingPlan(requestObject);

        assertAll(
                "Validating response...",
                () -> assertEquals(String.format("Cinemahall with id %s not found", id), response.getBody()),
                () -> assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test internal server error thrown when adding seating plan")
    public void testInternalServerErrorDuringAddProcess() {
        UUID id = UUID.randomUUID();
        SeatingPlanRequestObject requestObject = new SeatingPlanRequestObject(
                id, 7, 10
        );

        try {
            when(seatingPlanService.addSeatingPlan(requestObject)).thenThrow(new RuntimeException());
        } catch (CinemaHallNotFoundException e) {
            fail("Error when adding seating plan");
        }

        ResponseEntity<Object> response = seatingPlanController.addSeatingPlan(requestObject);

        assertAll(
                "Validating response...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test getting all seating plans successfully")
    public void testGetAllSeatingPlansSuccessful() {
        SeatingPlan firstPlan = new SeatingPlan();
        SeatingPlan secondPlan = new SeatingPlan();
        SeatingPlan thirdPlan = new SeatingPlan();
        List<SeatingPlan> expectedPlans = List.of(firstPlan, secondPlan, thirdPlan);

        when(seatingPlanService.getAllSeatingPlans()).thenReturn(expectedPlans);

        ResponseEntity<List<SeatingPlan>> response = seatingPlanController.getAllSeatingPlans();
        assertAll(
                "Validating response...",
                () -> assertEquals(expectedPlans, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test getting all seating plans not successfully")
    public void testGetAllSeatingPlansNotSuccessful() {
        when(seatingPlanService.getAllSeatingPlans()).thenReturn(List.of());

        ResponseEntity<List<SeatingPlan>> response = seatingPlanController.getAllSeatingPlans();
        assertAll(
                "Validating response...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test getting seating plan by id successful")
    public void testGetSeatingPlanByIdSuccessful() {
        UUID id = UUID.randomUUID();
        SeatingPlan expectedPlan = new SeatingPlan();
        expectedPlan.setId(id);

        when(seatingPlanService.getSeatingPlanById(id)).thenReturn(Optional.of(expectedPlan));

        ResponseEntity<SeatingPlan> response = seatingPlanController.getSeatingPlanById(id);
        assertAll(
                "Validating response...",
                () -> assertEquals(expectedPlan, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test getting seating plan by id not successful")
    public void testGetSeatingPlanByIdNotSuccessful() {
        UUID id = UUID.randomUUID();
        when(seatingPlanService.getSeatingPlanById(id)).thenReturn(Optional.empty());

        ResponseEntity<SeatingPlan> response = seatingPlanController.getSeatingPlanById(id);
        assertAll(
                "Validating response...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test getting seating plan by cinema hall id successful")
    public void testGetSeatingPlanByCinemaHallIdSuccessful() {
        UUID id = UUID.randomUUID();
        SeatingPlan expectedPlan = new SeatingPlan();
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setId(id);

        when(seatingPlanService.getSeatingPlanByCinemaHall(id)).thenReturn(Optional.of(expectedPlan));

        ResponseEntity<SeatingPlan> response = seatingPlanController.getSeatingPlanByCinemaHall(id);
        assertAll(
                "Validating response...",
                () -> assertEquals(expectedPlan, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test getting seating plan by cinema hall id not successful")
    public void testGetSeatingPlanByCinemaHallIdNotSuccessful() {
        UUID id = UUID.randomUUID();

        when(seatingPlanService.getSeatingPlanByCinemaHall(id)).thenReturn(Optional.empty());

        ResponseEntity<SeatingPlan> response = seatingPlanController.getSeatingPlanByCinemaHall(id);
        assertAll(
                "Validating response...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

}
