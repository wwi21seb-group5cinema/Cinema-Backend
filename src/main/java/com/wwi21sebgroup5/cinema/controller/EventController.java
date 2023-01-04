package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Event;
import com.wwi21sebgroup5.cinema.requestObjects.EventRequestObject;
import com.wwi21sebgroup5.cinema.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/event")
public class EventController {

    @Autowired
    EventService eventService;

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addEvent(@RequestBody EventRequestObject requestObject) {
        Event newEvent;

        try {
            newEvent = eventService.addEvent(requestObject);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    @GetMapping(path = "/get", params = "id")
    public ResponseEntity<Event> getEventById(@RequestParam UUID id) {
        Optional<Event> foundEvent = eventService.findById(id);

        return foundEvent.map(
                        event -> new ResponseEntity<>(event, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/get", params = "movieId")
    public ResponseEntity<List<Event>> getEventsByMovie(@RequestParam UUID movieId) {
        List<Event> foundEvents = eventService.findAllByCinemaId(movieId);

        if (foundEvents.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(foundEvents, HttpStatus.OK);
        }
    }

}
