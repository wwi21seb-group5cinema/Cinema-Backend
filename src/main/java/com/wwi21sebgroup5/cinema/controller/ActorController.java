package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Actor;
import com.wwi21sebgroup5.cinema.exceptions.ActorAlreadyExistsException;
import com.wwi21sebgroup5.cinema.requestObjects.ActorRequestObject;
import com.wwi21sebgroup5.cinema.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/v1/actor")
public class ActorController {


    @Autowired
    ActorService actorService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<Actor> actors = actorService.findAll();
        if (actors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(actors, HttpStatus.OK);
    }

    /**
     * @param name
     * @param firstName
     * @return the actor with the matching first- and lastname
     */
    @GetMapping(value = "/get", params = {"name", "firstName"})
    public ResponseEntity<Object> getActorByName(@RequestParam("name") String name, @RequestParam("firstName") String firstName) {
        Optional<Actor> a = actorService.findByNameAndFirstName(name, firstName);
        if (a.isPresent()) {
            return new ResponseEntity<>(a.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * @param actorObject
     * @return The newly created actor-object
     */
    @PostMapping(path = "/add")
    public ResponseEntity<Object> add(@RequestBody ActorRequestObject actorObject) {
        Actor a;
        try {
            a = actorService.add(actorObject);
        } catch (ActorAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(a, HttpStatus.CREATED);
    }
}
