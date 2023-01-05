package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.enums.SeatState;
import com.wwi21sebgroup5.cinema.exceptions.CinemaHallNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.MovieNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.TicketAlreadyExistsException;
import com.wwi21sebgroup5.cinema.repositories.EventRepository;
import com.wwi21sebgroup5.cinema.requestObjects.EventRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Autowired
    CinemaHallService cinemaHallService;

    @Autowired
    MovieService movieService;

    @Autowired
    SeatService seatService;

    @Autowired
    TicketService ticketService;

    @Autowired
    EventRepository eventRepository;

    /**
     * Creates a new event, for this it creates tickets and seats according to the seating plan of the
     * cinema hall. The initial seat state that gets set is FREE.
     *
     * @param requestObject DTO that holds the necessary information to create a new event
     * @return Returns the new event if added properly
     * @throws MovieNotFoundException      Thrown if no matching movie was found
     * @throws CinemaHallNotFoundException Thrown if no matching cinema hall was found
     */
    public Event addEvent(EventRequestObject requestObject) throws MovieNotFoundException, CinemaHallNotFoundException,
            TicketAlreadyExistsException {
        Event newEvent = new Event();

        Optional<Movie> foundMovie = movieService.findById(requestObject.getMovieId());

        if (foundMovie.isEmpty()) {
            throw new MovieNotFoundException(requestObject.getMovieId());
        } else {
            newEvent.setMovie(foundMovie.get());
        }

        Optional<CinemaHall> foundHall = cinemaHallService.getCinemaHallById(requestObject.getCinemaHallId());
        CinemaHall cinemaHall;

        if (foundHall.isEmpty()) {
            throw new CinemaHallNotFoundException(requestObject.getCinemaHallId());
        } else {
            cinemaHall = foundHall.get();
            newEvent.setCinemaHall(cinemaHall);
        }

        newEvent.setEventDateTime(LocalDateTime.parse(requestObject.getEventDateTime(), dateTimeFormatter));

        SeatingPlan seatingPlan = cinemaHall.getSeatingPlan();
        List<Ticket> tickets = new ArrayList<>();

        for (SeatBlueprint seatBlueprint : seatingPlan.getSeats()) {
            Seat newSeat = new Seat(
                    seatingPlan, seatBlueprint.getSeatType(), newEvent, seatBlueprint.getRow(), seatBlueprint.getPlace()
            );
            newSeat.setSeatState(SeatState.FREE);
            newSeat = seatService.save(newSeat);

            Ticket newTicket = ticketService.saveTicket(newEvent, newSeat);
            tickets.add(newTicket);
        }

        newEvent.setTickets(tickets);

        return eventRepository.save(newEvent);
    }

    /**
     * @param id Id of the event
     * @return Returns event associated with the id
     */
    public Optional<Event> findById(UUID id) {
        return eventRepository.findById(id);
    }

    /**
     * @param movieId UUID of the movie
     * @return Returns all events associated with the movieId
     */
    public List<Event> findAllByMovie(UUID movieId) {
        return eventRepository.findByMovie_Id(movieId);
    }

    /**
     *
     * @param startDate First date
     * @param endDate Second date
     * @return Returns all events between startDate and endDate
     */
    public List<Event> findEventsBetweenTwoDates(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate, dateTimeFormatter);
        LocalDateTime end = LocalDateTime.parse(endDate, dateTimeFormatter);

        return eventRepository.findEventsByEventDateTimeBetween(start, end);
    }

    /**
     *
     * @param movieId Id of the movie
     * @param startDate First date
     * @param endDate Second date
     * @return Returns all events associated with the movie between startDate and endDate
     */
    public List<Event> findEventsForMovieBetweenTwoDates(UUID movieId, String startDate, String endDate) {
        return List.of();
    }

    /**
     *
     * @param date Specific day
     * @return Returns all events on the given day in date
     */
    public List<Event> findAllEventsForDay(String date) {
        return List.of();
    }

    /**
     *
     * @param movieId Id of the movie
     * @param date Specific day
     * @return Returns all events associated with the movie on the given day in date
     */
    public List<Event> findAllEventsForMovieAndDay(UUID movieId, String date) {
        return List.of();
    }

}
