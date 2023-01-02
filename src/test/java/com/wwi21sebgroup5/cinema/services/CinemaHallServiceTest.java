package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Cinema;
import com.wwi21sebgroup5.cinema.entities.CinemaHall;
import com.wwi21sebgroup5.cinema.entities.SeatingPlan;
import com.wwi21sebgroup5.cinema.repositories.CinemaHallRepository;
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

    @InjectMocks
    CinemaHallService cinemaHallService;

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
