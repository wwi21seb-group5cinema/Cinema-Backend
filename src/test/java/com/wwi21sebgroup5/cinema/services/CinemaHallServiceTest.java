package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.exceptions.CinemaNotFoundException;
import com.wwi21sebgroup5.cinema.repositories.CinemaHallRepository;
import com.wwi21sebgroup5.cinema.requestObjects.CinemaHallRequestObject;
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
public class CinemaHallServiceTest {

    @Mock
    CinemaHallRepository cinemaHallRepository;

    @Mock
    CinemaService cinemaService;

    @Mock
    SeatTypeService seatTypeService;

    @InjectMocks
    CinemaHallService cinemaHallService;

    private Cinema setupCinema() {
        Cinema cinema = new Cinema();
        UUID id = UUID.randomUUID();
        cinema.setId(id);
        return cinema;
    }

    @Test
    @DisplayName("Test adding new cinemaHall")
    public void testAddNewCinemaHall() {
        Cinema cinema = setupCinema();
        String name = "TestName";
        CinemaHallRequestObject requestObject = new CinemaHallRequestObject(
                cinema.getId(), 2, 3, name, 2
        );

        SeatType regularType = new SeatType("REGULAR", 9.0);
        SeatType logeType = new SeatType("LOGE", 11.0);

        CinemaHall cinemaHall = new CinemaHall();
        SeatingPlan seatingPlan = new SeatingPlan(cinemaHall, 2);
        cinemaHall.setCinema(cinema);
        seatingPlan.setSeats(List.of(
                new SeatBlueprint(seatingPlan, regularType, 1, 1),
                new SeatBlueprint(seatingPlan, regularType, 1, 2),
                new SeatBlueprint(seatingPlan, regularType, 1, 3),
                new SeatBlueprint(seatingPlan, logeType, 2, 1),
                new SeatBlueprint(seatingPlan, logeType, 2, 2),
                new SeatBlueprint(seatingPlan, logeType, 2, 3)
        ));
        cinemaHall.setSeatingPlan(seatingPlan);
        cinemaHall.setName(name);
        cinemaHall.setFloor(2);

        when(cinemaService.getCinemaById(cinema.getId())).thenReturn(Optional.of(cinema));
        when(seatTypeService.getByName("REGULAR")).thenReturn(Optional.of(regularType));
        when(seatTypeService.getByName("LOGE")).thenReturn(Optional.of(logeType));

        // if the repository gets called with this exact cinema hall the method works correctly
        // thus we need no further assertions
        when(cinemaHallRepository.save(cinemaHall)).thenReturn(cinemaHall);

        CinemaHall actualHall;

        try {
            actualHall = cinemaHallService.addCinemaHall(requestObject);
        } catch (CinemaNotFoundException e) {
            throw new RuntimeException(e);
        }


        assertEquals(cinemaHall, actualHall);
    }

    @Test
    @DisplayName("Test getting cinemaHall by id")
    public void testGetCinemaHallByIdSuccessful() {
        UUID id = new UUID(2, 2);
        CinemaHall expectedHall = new CinemaHall(new Cinema(), new SeatingPlan(), "firstTestHall", 1);
        expectedHall.setId(id);

        when(cinemaHallRepository.findById(id)).thenReturn(Optional.of(expectedHall));

        Optional<CinemaHall> actualHall = cinemaHallService.getCinemaHallById(id);
        assertEquals(expectedHall, actualHall.get());
    }

    @Test
    @DisplayName("Test getting all cinemaHalls by cinema")
    public void testGetAllCinemaHallsByCinema() {
        Cinema cinema = new Cinema();
        UUID id = UUID.randomUUID();
        cinema.setId(id);

        CinemaHall firstHall = new CinemaHall(cinema, new SeatingPlan(), "firstTestHall", 1);
        CinemaHall secondHall = new CinemaHall(cinema, new SeatingPlan(), "secondTestHall", 2);
        CinemaHall thirdHall = new CinemaHall(cinema, new SeatingPlan(), "thirdTestHall", 3);
        List<CinemaHall> expectedHalls = List.of(firstHall, secondHall, thirdHall);

        when(cinemaHallRepository.findByCinema_Id(id)).thenReturn(expectedHalls);

        List<CinemaHall> actualHalls = cinemaHallService.getAllCinemaHallsByCinema(id);
        assertEquals(expectedHalls, actualHalls);
    }

}
