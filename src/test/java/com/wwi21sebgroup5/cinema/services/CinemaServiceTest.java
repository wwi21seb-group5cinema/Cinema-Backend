package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Cinema;
import com.wwi21sebgroup5.cinema.entities.CinemaHall;
import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.repositories.CinemaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CinemaServiceTest {

    @Mock
    CinemaRepository cinemaRepository;

    @InjectMocks
    CinemaService cinemaService;

    @Test
    @DisplayName("Test getting all cinemas")
    public void testGetAllCinemas() {
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
        when(cinemaRepository.findAll()).thenReturn(expectedCinemas);

        List<Cinema> actualCinemas = cinemaService.getAllCinemas();
        assertEquals(expectedCinemas, actualCinemas);
    }

}
