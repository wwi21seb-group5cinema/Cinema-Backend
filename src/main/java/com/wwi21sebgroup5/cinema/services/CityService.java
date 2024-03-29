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

    /**
     * @return all cities stored in the database
     */
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    /**
     * @param plz Plz which shall be searched in the database
     * @return All cities with the given name
     */
    public Optional<City> getCityByPlz(String plz) {
        return cityRepository.findByPlz(plz);
    }

    /**
     * @param cityName City name which shall be searched in the database
     * @return All cities with the given name
     */
    public List<City> getAllCitiesByName(String cityName) {
        return cityRepository.findByName(cityName);
    }

    /**
     * This method returns a city which equals to the given plz and contains the given name
     *
     * @param plz  PLZ which shall be searched for in the database
     * @param name City name which shall be searched for in the database
     * @return Returns a city in the form of an optional
     */
    public City findByPlzAndName(String plz, String name) {
        Optional<City> foundCity = cityRepository.findByPlz(plz);

        if (foundCity.isEmpty()) {
            List<City> foundCities = cityRepository.findByName(name);

            if (foundCities.isEmpty()) {
                foundCity = Optional.of(cityRepository.save(
                        new City(plz, name)));
            } else {
                foundCity = Optional.of(foundCities.get(0));
            }
        }

        return foundCity.get();
    }


    /**
     * Persists a given city in the database
     *
     * @param city City to be persisted
     */
    public City save(City city) {
        return this.findByPlzAndName(city.getPlz(), city.getName());
    }
}
