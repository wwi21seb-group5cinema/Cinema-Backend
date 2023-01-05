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

    /**
     *
     * @param requestObject RequestObject that holds the necessary information to add an event, this includes a
     *                      movie id, a cinema hall id, a start date and an end date
     * @return Returns the newly added event embedded in a ResponseEntity
     */
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

    /**
     *
     * @param id Id of the event
     * @return Returns the event associated with the event embedded in a ResponseEntity
     */
    @GetMapping(path = "/get", params = "id")
    public ResponseEntity<Event> getEventById(@RequestParam UUID id) {
        Optional<Event> foundEvent = eventService.findById(id);

        return foundEvent.map(
                        event -> new ResponseEntity<>(event, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     *
     * @param movieId Id of the movie
     * @return Returns all events associated with the movie embedded in a ResponseEntity
     */
    @GetMapping(path = "/get", params = "movieId")
    public ResponseEntity<List<Event>> getEventsByMovie(@RequestParam UUID movieId) {
        List<Event> foundEvents = eventService.findAllByMovie(movieId);

        if (foundEvents.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(foundEvents, HttpStatus.OK);
        }
    }

    /**
     *
     * @param startDate First date
     * @param endDate Second date
     * @return Returns all events between the startDate and endDate embedded in a ResponseEntity
     */
    @GetMapping(path = "/get", params = {"startDate", "endDate"})
    public ResponseEntity<List<Event>> getEventsBetweenTwoDates(@RequestParam String startDate,
                                                                @RequestParam String endDate) {
        List<Event> foundEvents = eventService.findEventsBetweenTwoDates(startDate, endDate);

        if (foundEvents.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(foundEvents, HttpStatus.OK);
        }
    }

    /**
     *
     * @param movieId Id of the movie
     * @param startDate First date
     * @param endDate Second date
     * @return Returns all events associated with the movie between the startDate and endDate embedded in
     * a ResponseEntity
     */
    @GetMapping(path = "/get", params = {"movieId", "startDate", "endDate"})
    public ResponseEntity<List<Event>> getEventsForMovieBetweenTwoDates(@RequestParam UUID movieId,
                                                                        @RequestParam String startDate,
                                                                        @RequestParam String endDate) {
        List<Event> foundEvents = eventService.findEventsForMovieBetweenTwoDates(movieId, startDate, endDate);

        if (foundEvents.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(foundEvents, HttpStatus.OK);
        }
    }

    /**
     *
     * @param date Specific day
     * @return Returns all events on the given day in date embedded in a ResponseEntity
     */
    @GetMapping(path = "/get", params = {"date"})
    public ResponseEntity<List<Event>> getEventsForDay(@RequestParam String date) {
        List<Event> foundEvents = eventService.findAllEventsForDay(date);

        if (foundEvents.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(foundEvents, HttpStatus.OK);
        }
    }

    /**
     *
     * @param movieId Id of the movie
     * @param date Specific day
     * @return Returns all events associated with the movie on the given day in date embedded in a ResponseEntity
     */
    @GetMapping(path = "/get", params = {"movieId", "date"})
    public ResponseEntity<List<Event>> getEventsForMovieAndDay(@RequestParam UUID movieId, @RequestParam String date) {
        List<Event> foundEvents = eventService.findAllEventsForMovieAndDay(movieId, date);

        if (foundEvents.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(foundEvents, HttpStatus.OK);
        }
    }

}
