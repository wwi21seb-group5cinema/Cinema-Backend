package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.SeatType;
import com.wwi21sebgroup5.cinema.services.SeatTypeService;
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
public class SeatTypeControllerTest {

    @Mock
    SeatTypeService seatTypeService;

    @InjectMocks
    SeatTypeController seatTypeController;

    @Test
    @DisplayName("Test get all seat types successful")
    public void testGetAllSeatTypesSuccessful() {
        SeatType firstType = new SeatType("Regular", 9.0),
                secondtype = new SeatType("Loge", 11.0);
        List<SeatType> expectedTypes = List.of(firstType, secondtype);

        when(seatTypeService.getAll()).thenReturn(expectedTypes);

        ResponseEntity<List<SeatType>> response = seatTypeController.getAll();

        assertAll(
                "Validating response...",
                () -> assertEquals(expectedTypes, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test get all seat types not successful")
    public void testGetAllSeatTypesNotSuccessful() {
        when(seatTypeService.getAll()).thenReturn(List.of());

        ResponseEntity<List<SeatType>> response = seatTypeController.getAll();

        assertAll(
                "Validating response...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

}
