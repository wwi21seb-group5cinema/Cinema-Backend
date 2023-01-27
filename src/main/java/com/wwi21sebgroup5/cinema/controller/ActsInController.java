package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.ActsIn;
import com.wwi21sebgroup5.cinema.exceptions.ActorNotFoundException;
import com.wwi21sebgroup5.cinema.services.ActsInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/actsIn")
public class ActsInController {

    @Autowired
    ActsInService actsInService;

    /**
     * @param movieId Id of the movie
     * @return Returns all actors associated with the movie embedded in a ResponseEntity
     */
    @GetMapping(path = "/getByMovie", params = "movieId")
    public ResponseEntity<List<ActsIn>> getActorsByMovie(@RequestParam UUID movieId) {
        List<ActsIn> foundActors = null;
        try {
            foundActors = actsInService.findAllByMovie(movieId);
        } catch (ActorNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(foundActors, HttpStatus.OK);

    }
}
