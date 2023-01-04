package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.repositories.CityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @Mock
    CityRepository cityRepository;

    @InjectMocks
    CityService cityService;

    private List<City> getExpectedCities() {
        City firstCity = new City("68259", "Wallstadt");
        City secondCity = new City("71672", "Marbach am Neckar");
        City thirdCity = new City("70499", "Weilimdorf");
        City fourthCity = new City("68259", "Mannheim");
        return List.of(firstCity, secondCity, thirdCity, fourthCity);
    }

    @Test
    @DisplayName("Test getting all cities")
    public void testGetAllCities() {
        List<City> expectedCities = getExpectedCities();
        when(cityRepository.findAll()).thenReturn(expectedCities);

        List<City> actualCities = cityService.getAllCities();
        assertEquals(expectedCities, actualCities, "Returned wrong list of cities");
    }

    @Test
    @DisplayName("Test getting city by plz")
    public void testGetCityByPlz() {
        City expectedCity = new City("68259", "Mannheim");
        when(cityRepository.findByPlz("68259")).thenReturn(Optional.of(expectedCity));

        Optional<City> actualCity = cityService.getCityByPlz("68259");
        assertEquals(expectedCity, actualCity.get(), "Returned wrong list of cities");
    }

    @Test
    @DisplayName("Test getting all cities by name")
    public void testGetAllCitiesByName() {
        City expectedCity = new City("68259", "Mannheim");
        when(cityRepository.findByName("Mannheim")).thenReturn(List.of(expectedCity));

        List<City> actualCities = cityService.getAllCitiesByName("Mannheim");
        assertEquals(expectedCity, actualCities.get(0), "Returned wrong list of cities");
    }

    @Test
    @DisplayName("Test get city by plz and name (1)")
    public void testGetCityByPlzAndNameFirst() {
        String plz = "68259", cityName = "Wallstadt";
        City expectedCity = new City(plz, cityName);
        when(cityRepository.findByPlz(plz)).thenReturn(Optional.of(expectedCity));

        City actualCity = cityService.findByPlzAndName(plz, cityName);
        assertEquals(expectedCity, actualCity, "Returned wrong list of cities");
    }

    @Test
    @DisplayName("Test get city by plz and name (2)")
    public void testGetCityByPlzAndNameSecond() {
        String plz = "68259", cityName = "Wallstadt";
        City expectedCity = new City(plz, cityName);

        when(cityRepository.findByPlz(plz)).thenReturn(Optional.empty());
        when(cityRepository.findByName(cityName)).thenReturn(List.of(expectedCity));

        City actualCity = cityService.findByPlzAndName(plz, cityName);
        assertEquals(expectedCity, actualCity, "Returned wrong list of cities");
    }

    @Test
    @DisplayName("Test get city by plz and name (3)")
    public void testGetCityByPlzAndNameThird() {
        String plz = "68259", cityName = "Wallstadt";
        City expectedCity = new City(plz, cityName);

        when(cityRepository.findByPlz(plz)).thenReturn(Optional.empty());
        when(cityRepository.findByName(cityName)).thenReturn(List.of());
        when(cityRepository.save(expectedCity)).thenReturn(expectedCity);

        City actualCity = cityService.findByPlzAndName(plz, cityName);
        assertEquals(expectedCity, actualCity, "Returned wrong list of cities");
    }

}
