package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Cinema;
import com.wwi21sebgroup5.cinema.entities.CinemaHall;
import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.exceptions.CinemaAlreadyExistsException;
import com.wwi21sebgroup5.cinema.repositories.CinemaRepository;
import com.wwi21sebgroup5.cinema.requestObjects.CinemaRequestObject;
import org.junit.jupiter.api.Assertions;
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
public class CinemaServiceTest {

    @Mock
    CityService cityService;

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

    @Test
    @DisplayName("Test get cinema by id")
    public void testGetCinemaById() {
        UUID id = UUID.randomUUID();
        City city = new City("68259", "Mannheim");
        List<CinemaHall> hallsCinema = List.of(new CinemaHall(), new CinemaHall(), new CinemaHall());
        Cinema expectedCinema = new Cinema("firstTestCinema", hallsCinema, city, "firstTestStreet",
                "firstTestHouseNumber", 2);

        when(cinemaRepository.findById(id)).thenReturn(Optional.of(expectedCinema));

        Optional<Cinema> actualCinema = cinemaService.getCinemaById(id);
        assertEquals(expectedCinema, actualCinema.get());
    }

    @Test
    @DisplayName("Test adding cinema successfully")
    public void testAddCinemaSuccessful() {
        String name = "testName", plz = "68259", cityName = "Wallstadt", street = "TestStreet", houseNumber = "28";
        int floors = 3;

        City city = new City(plz, cityName);
        CinemaRequestObject requestObject = new CinemaRequestObject(
                name, plz, cityName, street, houseNumber, floors
        );
        Cinema expectedCinema = new Cinema(
                name, List.of(), city, street, houseNumber, floors
        );

        when(cityService.findByPlzAndName(plz, cityName)).thenReturn(city);
        when(cinemaRepository.findByCityAndStreetAndHouseNumber(city, street, houseNumber))
                .thenReturn(Optional.empty());
        when(cinemaRepository.save(expectedCinema)).thenReturn(expectedCinema);

        Cinema actualCinema = null;
        try {
            actualCinema = cinemaService.add(requestObject);
        } catch (CinemaAlreadyExistsException e) {
            fail();
        }
        Assertions.assertEquals(expectedCinema, actualCinema);
    }

    @Test
    @DisplayName("Test cinema already exists during add process")
    public void testCinemaAlreadyExistsDuringAddProcess() {
        String name = "testName", plz = "68259", cityName = "Wallstadt", street = "TestStreet", houseNumber = "28";
        int floors = 3;

        City city = new City(plz, cityName);
        CinemaRequestObject requestObject = new CinemaRequestObject(
                name, plz, cityName, street, houseNumber, floors
        );
        Cinema existingCinema = new Cinema(
                name, List.of(), city, street, houseNumber, floors
        );

        when(cityService.findByPlzAndName(plz, cityName)).thenReturn(city);
        when(cinemaRepository.findByCityAndStreetAndHouseNumber(city, street, houseNumber))
                .thenReturn(Optional.of(existingCinema));

        assertThrows(CinemaAlreadyExistsException.class, () -> cinemaService.add(requestObject));
    }

}
