package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.CinemaHall;
import com.wwi21sebgroup5.cinema.entities.SeatingPlan;
import com.wwi21sebgroup5.cinema.repositories.SeatingPlanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SeatingPlanServiceTest {

    @Mock
    SeatingPlanRepository seatingPlanRepository;

    @InjectMocks
    SeatingPlanService seatingPlanService;

    @Test
    @DisplayName("Test getting all seating plans successfully")
    public void testGetAllSeatingPlansSuccessful() {
        SeatingPlan firstPlan = new SeatingPlan();
        SeatingPlan secondPlan = new SeatingPlan();
        SeatingPlan thirdPlan = new SeatingPlan();
        List<SeatingPlan> expectedPlans = List.of(firstPlan, secondPlan, thirdPlan);

        when(seatingPlanRepository.findAll()).thenReturn(expectedPlans);

        List<SeatingPlan> actualPlans = seatingPlanService.getAllSeatingPlans();
        assertEquals(expectedPlans, actualPlans);
    }

    @Test
    @DisplayName("Test getting all seating plans not successfully")
    public void testGetAllSeatingPlansNotSuccessful() {
        when(seatingPlanRepository.findAll()).thenReturn(List.of());

        List<SeatingPlan> actualPlans = seatingPlanService.getAllSeatingPlans();
        assertTrue(actualPlans.isEmpty());
    }

    @Test
    @DisplayName("Test getting seating plan by id successful")
    public void testGetSeatingPlanByIdSuccessful() {
        UUID id = UUID.randomUUID();
        SeatingPlan expectedPlan = new SeatingPlan();
        expectedPlan.setId(id);

        when(seatingPlanRepository.findById(id)).thenReturn(Optional.of(expectedPlan));

        Optional<SeatingPlan> actualPlan = seatingPlanService.getSeatingPlanById(id);
        assertEquals(expectedPlan, actualPlan.get());
    }

    @Test
    @DisplayName("Test getting seating plan by id not successful")
    public void testGetSeatingPlanByIdNotSuccessful() {
        UUID id = UUID.randomUUID();
        when(seatingPlanRepository.findById(id)).thenReturn(Optional.empty());

        Optional<SeatingPlan> actualPlan = seatingPlanService.getSeatingPlanById(id);
        assertTrue(actualPlan.isEmpty());
    }

    @Test
    @DisplayName("Test getting seating plan by cinema hall id successful")
    public void testGetSeatingPlanByCinemaHallIdSuccessful() {
        UUID id = UUID.randomUUID();
        SeatingPlan expectedPlan = new SeatingPlan();
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setId(id);

        when(seatingPlanRepository.findByCinemaHall_Id(id)).thenReturn(Optional.of(expectedPlan));

        Optional<SeatingPlan> actualPlan = seatingPlanService.getSeatingPlanByCinemaHall(id);
        assertEquals(expectedPlan, actualPlan.get());
    }

    @Test
    @DisplayName("Test getting seating plan by cinema hall id not successful")
    public void testGetSeatingPlanByCinemaHallIdNotSuccessful() {
        UUID id = UUID.randomUUID();

        when(seatingPlanRepository.findByCinemaHall_Id(id)).thenReturn(Optional.empty());

        Optional<SeatingPlan> actualPlan = seatingPlanService.getSeatingPlanByCinemaHall(id);
        assertTrue(actualPlan.isEmpty());
    }

}
