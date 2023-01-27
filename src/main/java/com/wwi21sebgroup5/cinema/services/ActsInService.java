package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Actor;
import com.wwi21sebgroup5.cinema.entities.ActsIn;
import com.wwi21sebgroup5.cinema.entities.Movie;
import com.wwi21sebgroup5.cinema.exceptions.ActorNotFoundException;
import com.wwi21sebgroup5.cinema.repositories.ActsInRepository;
import com.wwi21sebgroup5.cinema.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActsInService {

    @Autowired
    ActsInRepository actsInRepository;
    @Autowired
    MovieRepository movieRepository;


    public void save(Movie m, Actor a) {
        actsInRepository.save(new ActsIn(m, a, ""));
    }

    public void save(ActsIn actsIn) {
        actsInRepository.save(actsIn);
    }

    public List<ActsIn> findAllByMovie(UUID movieId) throws ActorNotFoundException {
        List<ActsIn> actsIn = actsInRepository.findByMovie(movieRepository.findById(movieId).get());
        if (actsIn.isEmpty()) {
            throw new ActorNotFoundException();
        }
        return actsIn;
    }
}
