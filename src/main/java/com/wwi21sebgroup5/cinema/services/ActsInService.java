package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Actor;
import com.wwi21sebgroup5.cinema.entities.ActsIn;
import com.wwi21sebgroup5.cinema.entities.Movie;
import com.wwi21sebgroup5.cinema.repositories.ActsInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActsInService {

    @Autowired
    ActsInRepository actsInRepository;

    public void save(Movie m, Actor a) {
        actsInRepository.save(new ActsIn(m, a, ""));
    }

    public void save(ActsIn actsIn) {
        actsInRepository.save(actsIn);
    }

}
