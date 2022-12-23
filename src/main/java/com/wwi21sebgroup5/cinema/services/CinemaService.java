package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Cinema;
import com.wwi21sebgroup5.cinema.repositories.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;

    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    public Optional<Cinema> getCinemaById(UUID id) {
        return cinemaRepository.findById(id);
    }

}
