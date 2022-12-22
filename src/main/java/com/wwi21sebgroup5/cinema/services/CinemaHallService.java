package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Cinema;
import com.wwi21sebgroup5.cinema.entities.CinemaHall;
import com.wwi21sebgroup5.cinema.repositories.CinemaHallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CinemaHallService {

    @Autowired
    private CinemaHallRepository cinemaHallRepository;

    public List<CinemaHall> getAllCinemaHallsByCinema(Cinema cinema) {
        return cinemaHallRepository.findByCinema(cinema);
    }

    public List<CinemaHall> getAllCinemaHalls() {
        return cinemaHallRepository.findAll();
    }

    public Optional<CinemaHall> getCinemaHallById(UUID id) {
        return cinemaHallRepository.findById(id);
    }

}
