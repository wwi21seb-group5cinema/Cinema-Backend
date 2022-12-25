package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Cinema;
import com.wwi21sebgroup5.cinema.entities.City;
import com.wwi21sebgroup5.cinema.exceptions.CinemaAlreadyExistsException;
import com.wwi21sebgroup5.cinema.repositories.CinemaRepository;
import com.wwi21sebgroup5.cinema.requestObjects.CinemaRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private CityService cityService;

    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    public Optional<Cinema> getCinemaById(UUID id) {
        return cinemaRepository.findById(id);
    }

    public Cinema add(CinemaRequestObject cinemaObject) throws CinemaAlreadyExistsException {
        City city = cityService.findByPlzAndName(cinemaObject.getPlz(), cinemaObject.getCityName());

        if (cinemaRepository.findByCityAndStreetAndHouseNumber(city, cinemaObject.getStreet(),
                cinemaObject.getHouseNumber()).isPresent()) {
            throw new CinemaAlreadyExistsException(cinemaObject.getPlz(), cinemaObject.getCityName(),
                    cinemaObject.getStreet(), cinemaObject.getHouseNumber());
        }

        Cinema newCinema = new Cinema(
            cinemaObject.getName(),
            List.of(),
            city,
            cinemaObject.getStreet(),
            cinemaObject.getHouseNumber(),
            cinemaObject.getFloors()
        );

        return cinemaRepository.save(newCinema);

    }

}
