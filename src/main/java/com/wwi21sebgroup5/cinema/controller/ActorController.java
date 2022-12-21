package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Actor;
import com.wwi21sebgroup5.cinema.exceptions.ActorAlreadyExistsException;
import com.wwi21sebgroup5.cinema.exceptions.ActorNotFoundException;
import com.wwi21sebgroup5.cinema.requestObjects.ActorRequestObject;
import com.wwi21sebgroup5.cinema.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/v1/actor")
public class ActorController {


    @Autowired
    ActorService actorService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Actor>> getAll() {

        return new ResponseEntity<>(actorService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/get", params = {"name", "firstName"})
    public ResponseEntity<Object> getAll(@RequestParam("name") String name, @RequestParam("firstName") String firstName) {
        try {
            return new ResponseEntity<>(
                    actorService.findByNameAndFirstName(name, firstName), HttpStatus.OK);
        } catch (ActorNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(path = "/add")
    public ResponseEntity<Object> put(@RequestBody ActorRequestObject actorObject) {
        Actor a;
        try {
            a = actorService.add(actorObject);
        } catch (ActorAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(a, HttpStatus.CREATED);
    }
}
