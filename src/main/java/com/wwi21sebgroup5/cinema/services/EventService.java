package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.enums.SeatState;
import com.wwi21sebgroup5.cinema.exceptions.CinemaHallNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.MovieNotFoundException;
import com.wwi21sebgroup5.cinema.repositories.EventRepository;
import com.wwi21sebgroup5.cinema.requestObjects.EventRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    SeatService seatService;

    @Autowired
    EventRepository eventRepository;

    public Optional<Event> findById(UUID id) {
        return eventRepository.findById(id);
    }

    public List<Event> findAllByCinemaId(UUID movieId) {
        return eventRepository.findByMovie_Id(movieId);
    }

    /**
     * Creates a new event, for this it creates tickets and seats according to the seating plan of the
     * cinema hall. The initial seat state that gets set is FREE.
     *
     * @param requestObject DTO that holds the necessary information to create a new event
     * @return Returns the new event if added properly
     * @throws MovieNotFoundException Thrown if no matching movie was found
     * @throws CinemaHallNotFoundException Thrown if no matching cinema hall was found
     */
    public Event addEvent(EventRequestObject requestObject) throws MovieNotFoundException, CinemaHallNotFoundException {
        Event newEvent = new Event();

        Optional<Movie> foundMovie = movieService.findById(requestObject.getMovieId());

        if (foundMovie.isEmpty()) {
            throw new MovieNotFoundException(requestObject.getMovieId());
        }

        Optional<CinemaHall> foundHall = cinemaHallService.getCinemaHallById(requestObject.getCinemaHallId());
        CinemaHall cinemaHall;

        if (foundHall.isEmpty()) {
            throw new CinemaHallNotFoundException(requestObject.getCinemaHallId());
        } else {
            cinemaHall = foundHall.get();
        }

        SeatingPlan seatingPlan = cinemaHall.getSeatingPlan();
        List<Ticket> tickets = new ArrayList<>();

        for (SeatBlueprint seatBlueprint : seatingPlan.getSeats()) {
            Seat newSeat = new Seat(
                    seatingPlan, seatBlueprint.getSeatType(), newEvent, seatBlueprint.getRow(), seatBlueprint.getRow()
            );
            newSeat.setSeatState(SeatState.FREE);

            Ticket newTicket = new Ticket();
            /*
            As soon as tickets are implemented, this will get implemented
             */
            // newTicket.setSeat(newSeat);
            newTicket.setEvent(newEvent);
            tickets.add(newTicket);
        }

        LocalDate eventDay = LocalDate.parse(requestObject.getEventDay(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalTime eventTime = LocalTime.parse(requestObject.getEventTime(), DateTimeFormatter.ofPattern("HH:mm"));

        newEvent.setMovie(foundMovie.get());
        newEvent.setCinemaHall(cinemaHall);
        newEvent.setTickets(tickets);
        newEvent.setEventDay(eventDay);
        newEvent.setEventTime(eventTime);

        return eventRepository.save(newEvent);
    }
}
