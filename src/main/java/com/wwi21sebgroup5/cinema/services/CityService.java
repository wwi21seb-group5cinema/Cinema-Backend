package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public List<City> getAllCitiesByName(String cityName) {
        return cityRepository.findByName(cityName);
    }

    public Optional<City> findByPlzAndName(String plz, String name) {
        return cityRepository.findByPlzAndNameContaining(plz, name);
    }

    public void save(City city) {
        cityRepository.save(city);
    }
}
