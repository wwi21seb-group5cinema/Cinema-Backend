package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.exceptions.CinemaHallNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.MovieNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.TicketAlreadyExistsException;
import com.wwi21sebgroup5.cinema.repositories.EventRepository;
import com.wwi21sebgroup5.cinema.requestObjects.EventRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wwi21sebgroup5.cinema.helper.DateFormatter.DATE_FORMATTER;
import static com.wwi21sebgroup5.cinema.helper.DateFormatter.DATE_TIME_FORMATTER;

@Service
public class EventService {

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

        newEvent.setEventDateTime(LocalDateTime.parse(requestObject.getEventDateTime(), DATE_TIME_FORMATTER));
        newEvent = eventRepository.save(newEvent);

        SeatingPlan seatingPlan = cinemaHall.getSeatingPlan();
        List<Ticket> tickets = new ArrayList<>();

        for (SeatBlueprint seatBlueprint : seatingPlan.getSeats()) {
            Seat newSeat = new Seat(
                    seatBlueprint.getSeatType(), seatBlueprint.getRow(), seatBlueprint.getPlace()
            );
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
        Optional<Event> foundEvent = eventRepository.findById(id);
        foundEvent.ifPresent(
                event -> seatService.updateStatesofSeats(event));
        return foundEvent;
    }

    /**
     * @param movieId UUID of the movie
     * @return Returns all events associated with the movieId
     */
    public List<Event> findAllByMovie(UUID movieId) {
        return eventRepository.findByMovie_Id(movieId);
    }

    /**
     * @param startDate First date
     * @param endDate   Second date
     * @return Returns all events between startDate and endDate
     */
    public List<Event> findEventsBetweenTwoDates(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate, DATE_TIME_FORMATTER);
        LocalDateTime end = LocalDateTime.parse(endDate, DATE_TIME_FORMATTER);

        return eventRepository.findByEventDateTimeIsBetween(start, end);
    }

    /**
     * @param movieId   Id of the movie
     * @param startDate First date
     * @param endDate   Second date
     * @return Returns all events associated with the movie between startDate and endDate
     */
    public List<Event> findEventsForMovieBetweenTwoDates(UUID movieId, String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate, DATE_TIME_FORMATTER);
        LocalDateTime end = LocalDateTime.parse(endDate, DATE_TIME_FORMATTER);

        return eventRepository.findByMovie_IdAndEventDateTimeIsBetween(movieId, start, end);
    }

    /**
     * @param date Specific day
     * @return Returns all events on the given day in date
     */
    public List<Event> findAllEventsForDay(String date) {
        LocalDate eventDay = LocalDate.parse(date, DATE_FORMATTER);

        return eventRepository.findByEventDateTimeIsBetween(
                eventDay.atStartOfDay(), eventDay.plusDays(1).atStartOfDay());
    }

    /**
     * @param movieId Id of the movie
     * @param date    Specific day
     * @return Returns all events associated with the movie on the given day in date
     */
    public List<Event> findAllEventsForMovieAndDay(UUID movieId, String date) {
        LocalDate eventDay = LocalDate.parse(date, DATE_FORMATTER);

        return eventRepository.findByMovie_IdAndEventDateTimeIsBetween(
                movieId, eventDay.atStartOfDay(), eventDay.plusDays(1).atStartOfDay());
    }

}
