package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.SeatingPlan;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SeatingPlanControllerTest {

    @Mock
    SeatingPlanService seatingPlanService;

    @InjectMocks
    SeatingPlanController seatingPlanController;

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

}
