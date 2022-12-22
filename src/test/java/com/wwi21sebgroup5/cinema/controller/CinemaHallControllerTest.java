package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Cinema;
import com.wwi21sebgroup5.cinema.entities.CinemaHall;
import com.wwi21sebgroup5.cinema.entities.SeatingPlan;
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
    @DisplayName("Test getting all cinemaHalls successfully")
    public void testGetAllCinemaHallsSuccessful() {
        CinemaHall firstHall = new CinemaHall(new Cinema(), new SeatingPlan(), "firstTestHall", 1);
        CinemaHall secondHall = new CinemaHall(new Cinema(), new SeatingPlan(), "secondTestHall", 2);
        CinemaHall thirdHall = new CinemaHall(new Cinema(), new SeatingPlan(), "thirdTestHall", 3);
        List<CinemaHall> expectedHalls = List.of(firstHall, secondHall, thirdHall);

        when(cinemaHallService.getAllCinemaHalls()).thenReturn(expectedHalls);

        ResponseEntity<List<CinemaHall>> response = cinemaHallController.getAll();
        assertAll(
                "Validating response..",
                () -> assertEquals(expectedHalls, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test getting all cinemaHalls not successfully")
    public void testGetAllCinemaHallsNotSuccessful() {
        when(cinemaHallService.getAllCinemaHalls()).thenReturn(List.of());

        ResponseEntity<List<CinemaHall>> response = cinemaHallController.getAll();
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

        ResponseEntity<Optional<CinemaHall>> response = cinemaHallController.getById(id);
        assertAll(
                "Validating response..",
                () -> assertEquals(expectedHall, response.getBody().get()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test getting cinemaHall by id not successfully")
    public void testGetCinemaHallByIdNotSuccessful() {
        UUID id = new UUID(2, 2);
        when(cinemaHallService.getCinemaHallById(id)).thenReturn(Optional.empty());

        ResponseEntity<Optional<CinemaHall>> response = cinemaHallController.getById(id);
        assertAll(
                "Validating response..",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

}
