package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.CinemaHall;
import com.wwi21sebgroup5.cinema.entities.SeatBlueprint;
import com.wwi21sebgroup5.cinema.entities.SeatType;
import com.wwi21sebgroup5.cinema.entities.SeatingPlan;
import com.wwi21sebgroup5.cinema.exceptions.CinemaHallNotFoundException;
import com.wwi21sebgroup5.cinema.repositories.SeatingPlanRepository;
import com.wwi21sebgroup5.cinema.requestObjects.SeatingPlanRequestObject;
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

    @Mock
    CinemaHallService cinemaHallService;

    @Mock
    SeatTypeService seatTypeService;

    @InjectMocks
    SeatingPlanService seatingPlanService;

    @Test
    @DisplayName("Add new seating plan")
    public void testAddNewSeatingPlan() {
        UUID id = UUID.randomUUID();
        SeatingPlanRequestObject requestObject = new SeatingPlanRequestObject(
            id, 2, 3
        );

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setId(id);

        SeatingPlan seatingPlan = new SeatingPlan(cinemaHall, 2);
        SeatType regularType = new SeatType("REGULAR", 9.0);
        SeatType logeType = new SeatType("LOGE", 11.0);

        seatingPlan.setSeats(List.of(
                new SeatBlueprint(seatingPlan, regularType, 1, 1),
                new SeatBlueprint(seatingPlan, regularType, 1, 2),
                new SeatBlueprint(seatingPlan, regularType, 1, 3),
                new SeatBlueprint(seatingPlan, logeType, 2, 1),
                new SeatBlueprint(seatingPlan, logeType, 2, 2),
                new SeatBlueprint(seatingPlan, logeType, 2, 3)
        ));

        when(cinemaHallService.getCinemaHallById(id)).thenReturn(Optional.of(cinemaHall));
        when(seatTypeService.getByName("REGULAR")).thenReturn(Optional.of(regularType));
        when(seatTypeService.getByName("LOGE")).thenReturn(Optional.of(logeType));

        // if the repository gets called with this exact seating plan the method works correctly
        // thus we need no further assertions
        when(seatingPlanRepository.save(seatingPlan)).thenReturn(seatingPlan);

        SeatingPlan actualPlan = null;

        try {
            actualPlan = seatingPlanService.addSeatingPlan(requestObject);
        } catch (CinemaHallNotFoundException e) {
            fail("Error when adding seating plan");
        }

        assertEquals(seatingPlan, actualPlan);
    }

    @Test
    @DisplayName("Test cinema hall not found when adding new seating plan")
    public void testCinemaHallNotFoundWhenAddingSeatPlan() {
        UUID id = UUID.randomUUID();
        SeatingPlanRequestObject requestObject = new SeatingPlanRequestObject(
                id, 2, 3
        );

        when(cinemaHallService.getCinemaHallById(id)).thenReturn(Optional.empty());

        assertThrows(CinemaHallNotFoundException.class, () -> seatingPlanService.addSeatingPlan(requestObject));
    }

    @Test
    @DisplayName("Test getting all seating plans")
    public void testGetAllSeatingPlans() {
        SeatingPlan firstPlan = new SeatingPlan();
        SeatingPlan secondPlan = new SeatingPlan();
        SeatingPlan thirdPlan = new SeatingPlan();
        List<SeatingPlan> expectedPlans = List.of(firstPlan, secondPlan, thirdPlan);

        when(seatingPlanRepository.findAll()).thenReturn(expectedPlans);

        List<SeatingPlan> actualPlans = seatingPlanService.getAllSeatingPlans();
        assertEquals(expectedPlans, actualPlans);
    }

    @Test
    @DisplayName("Test getting seating plan by id")
    public void testGetSeatingPlanById() {
        UUID id = UUID.randomUUID();
        SeatingPlan expectedPlan = new SeatingPlan();
        expectedPlan.setId(id);

        when(seatingPlanRepository.findById(id)).thenReturn(Optional.of(expectedPlan));

        Optional<SeatingPlan> actualPlan = seatingPlanService.getSeatingPlanById(id);
        assertEquals(expectedPlan, actualPlan.get());
    }

    @Test
    @DisplayName("Test getting seating plan by cinema hall id")
    public void testGetSeatingPlanByCinemaHallId() {
        UUID id = UUID.randomUUID();
        SeatingPlan expectedPlan = new SeatingPlan();
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setId(id);

        when(seatingPlanRepository.findByCinemaHall_Id(id)).thenReturn(Optional.of(expectedPlan));

        Optional<SeatingPlan> actualPlan = seatingPlanService.getSeatingPlanByCinemaHall(id);
        assertEquals(expectedPlan, actualPlan.get());
    }

}
