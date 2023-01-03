package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.CinemaHall;
import com.wwi21sebgroup5.cinema.entities.Event;
import com.wwi21sebgroup5.cinema.entities.Movie;
import com.wwi21sebgroup5.cinema.exceptions.CinemaHallNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.MovieNotFoundException;
import com.wwi21sebgroup5.cinema.repositories.EventRepository;
import com.wwi21sebgroup5.cinema.requestObjects.EventRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    MovieService movieService;

    @Autowired
    CinemaHallService cinemaHallService;

    @Autowired
    EventRepository eventRepository;

    public Optional<Event> findById(UUID id) {
        return eventRepository.findById(id);
    }

    public List<Event> findAllByCinemaId(UUID movieId) {
        return eventRepository.findByMovie_Id(movieId);
    }

    public Event addEvent(EventRequestObject requestObject) throws MovieNotFoundException, CinemaHallNotFoundException {
        Event newEvent;

        Optional<Movie> foundMovie = movieService.findById(requestObject.getMovieId());

        if (foundMovie.isEmpty()) {
            throw new MovieNotFoundException(requestObject.getMovieId());
        }

        Optional<CinemaHall> foundHall = cinemaHallService.getCinemaHallById(requestObject.getCinemaHallId());

        if (foundHall.isEmpty()) {
            throw new CinemaHallNotFoundException(requestObject.getCinemaHallId());
        }

        /*
        Placeholder for method that creates tickets from a seating plan
         */

        LocalDate eventDay = LocalDate.parse(requestObject.getEventDay(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalTime eventTime = LocalTime.parse(requestObject.getEventTime(), DateTimeFormatter.ofPattern("HH:mm"));

        newEvent = new Event(
                foundMovie.get(),
                foundHall.get(),
                List.of(),
                eventDay,
                eventTime
        );

        return eventRepository.save(newEvent);
    }
}
