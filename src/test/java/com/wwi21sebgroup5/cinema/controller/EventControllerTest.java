package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.exceptions.CinemaHallNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.MovieNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.TicketAlreadyExistsException;
import com.wwi21sebgroup5.cinema.requestObjects.EventRequestObject;
import com.wwi21sebgroup5.cinema.services.EventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    private Event setupEvent(LocalDateTime eventDateTime, UUID id) {
        Event event =  new Event(
                new Movie(), new CinemaHall(), List.of(new Ticket(), new Ticket()), eventDateTime
        );
        event.setId(id);
        return event;
    }

    @Test
    @DisplayName("Test adding new event successful")
    public void testAddSuccessful() throws Exception {
        Movie movie = new Movie();
        movie.setId(UUID.randomUUID());

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setId(UUID.randomUUID());

        SeatingPlan seatingPlan = new SeatingPlan(cinemaHall, 3);
        SeatType seatType = new SeatType("Normal", 8.5);
        seatingPlan.setSeats(List.of(
                new SeatBlueprint(seatingPlan, seatType, 1, 1),
                new SeatBlueprint(seatingPlan, seatType, 1, 2),
                new SeatBlueprint(seatingPlan, seatType, 2, 1),
                new SeatBlueprint(seatingPlan, seatType, 3, 1),
                new SeatBlueprint(seatingPlan, seatType, 3, 2)
        ));
        cinemaHall.setSeatingPlan(seatingPlan);

        Event expectedEvent = new Event(
                movie, cinemaHall, List.of(), LocalDateTime.of(2023, 1, 8, 20, 15)
        );

        List<Ticket> tickets = new ArrayList<>();

        for (SeatBlueprint seatBlueprint : seatingPlan.getSeats()) {
            Seat newSeat = new Seat(
                    seatingPlan, seatBlueprint.getSeatType(), expectedEvent, seatBlueprint.getRow(), seatBlueprint.getPlace()
            );

            Ticket newTicket = new Ticket(expectedEvent, newSeat);
            tickets.add(newTicket);
        }

        expectedEvent.setTickets(tickets);

        EventRequestObject requestObject = new EventRequestObject(
                movie.getId(), cinemaHall.getId(), "08-01-2023 20:15"
        );

        when(eventService.addEvent(requestObject)).thenReturn(expectedEvent);
        ResponseEntity<Object> response = eventController.addEvent(requestObject);

        assertAll(
                "Validating parameters...",
                () -> assertEquals(expectedEvent, response.getBody()),
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test ticket already exists exception thrown when adding new event")
    public void testTicketAlreadyExistsWhileAdding() throws Exception {
        EventRequestObject requestObject = new EventRequestObject(
                UUID.randomUUID(), UUID.randomUUID(), "08-02-2023 21:00"
        );

        when(eventService.addEvent(requestObject)).thenThrow(TicketAlreadyExistsException.class);
        ResponseEntity<Object> response = eventController.addEvent(requestObject);

        assertAll(
                "Validating parameters...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test cinemaHall not found exception thrown when adding new event")
    public void testCinemaHallNotFoundWhileAdding() throws Exception {
        EventRequestObject requestObject = new EventRequestObject(
                UUID.randomUUID(), UUID.randomUUID(), "08-02-2023 21:00"
        );

        when(eventService.addEvent(requestObject)).thenThrow(CinemaHallNotFoundException.class);
        ResponseEntity<Object> response = eventController.addEvent(requestObject);

        assertAll(
                "Validating parameters...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test movie not found exception thrown when adding new event")
    public void testMovieNotFoundWhileAdding() throws Exception {
        EventRequestObject requestObject = new EventRequestObject(
                UUID.randomUUID(), UUID.randomUUID(), "08-02-2023 21:00"
        );

        when(eventService.addEvent(requestObject)).thenThrow(MovieNotFoundException.class);
        ResponseEntity<Object> response = eventController.addEvent(requestObject);

        assertAll(
                "Validating parameters...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test internal server error when adding new event")
    public void testInternalServerErrorWhileAdding() throws Exception {
        EventRequestObject requestObject = new EventRequestObject(
                UUID.randomUUID(), UUID.randomUUID(), "08-02-2023 21:00"
        );

        when(eventService.addEvent(requestObject)).thenThrow(RuntimeException.class);
        ResponseEntity<Object> response = eventController.addEvent(requestObject);

        assertAll(
                "Validating parameters...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test get event by id successful")
    public void testGetEventByIdSuccessful() {
        Event event = setupEvent(
                LocalDateTime.of(2023, 2, 12, 17, 30), UUID.randomUUID());

        when(eventService.findById(event.getId())).thenReturn(Optional.of(event));
        ResponseEntity<Event> response = eventController.getEventById(event.getId());

        assertAll(
                "Validating response...",
                () -> assertEquals(event, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test get event by id not successful")
    public void testGetEventByIdNotSuccessful() {
        Event event = setupEvent(
                LocalDateTime.of(2023, 2, 12, 17, 30), UUID.randomUUID());

        when(eventService.findById(event.getId())).thenReturn(Optional.empty());
        ResponseEntity<Event> response = eventController.getEventById(event.getId());

        assertAll(
                "Validating response...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test get events by movie successful")
    public void testGetEventsByMovieSuccessful() {
        Event event = setupEvent(
                LocalDateTime.of(2023, 2, 12, 17, 30), UUID.randomUUID());

        when(eventService.findById(event.getId())).thenReturn(Optional.of(event));
        ResponseEntity<Event> response = eventController.getEventById(event.getId());

        assertAll(
                "Validating response...",
                () -> assertEquals(event, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test get events by movie not successful")
    public void testGetEventsByMovieNotSuccessful() {
        Event event = setupEvent(
                LocalDateTime.of(2023, 2, 12, 17, 30), UUID.randomUUID());

        when(eventService.findById(event.getId())).thenReturn(Optional.empty());
        ResponseEntity<Event> response = eventController.getEventById(event.getId());

        assertAll(
                "Validating response...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test get events between two dates successful")
    public void testGetEventsBetweenTwoDatesSuccessful() {
        Event firstEvent = setupEvent(
                LocalDateTime.of(2023, 2, 12, 17, 30), UUID.randomUUID());
        Event secondEvent = setupEvent(
                LocalDateTime.of(2023, 2, 13, 17, 30), UUID.randomUUID());
        Event thirdEvent = setupEvent(
                LocalDateTime.of(2023, 2, 14, 17, 30), UUID.randomUUID());
        List<Event> expectedEvents = List.of(firstEvent, secondEvent, thirdEvent);

        when(eventService.findEventsBetweenTwoDates("12-02-2023 17:00", "14-02-2023 20:30")).thenReturn(expectedEvents);
        ResponseEntity<List<Event>> response =
                eventController.getEventsBetweenTwoDates("12-02-2023 17:00", "14-02-2023 20:30");

        assertAll(
                "Validating response...",
                () -> assertEquals(expectedEvents, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test get events between two dates not successful")
    public void testGetEventsBetweenTwoDatesNotSuccessful() {
        when(eventService.findEventsBetweenTwoDates("12-02-2023 17:00", "14-02-2023 20:30")).thenReturn(List.of());
        ResponseEntity<List<Event>> response =
                eventController.getEventsBetweenTwoDates("12-02-2023 17:00", "14-02-2023 20:30");

        assertAll(
                "Validating response...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test get events between two dates for movie successful")
    public void testGetEventsBetweenTwoDatesForMovieSuccessful() {
        Event firstEvent = setupEvent(
                LocalDateTime.of(2023, 2, 12, 17, 30), UUID.randomUUID());
        Event secondEvent = setupEvent(
                LocalDateTime.of(2023, 2, 13, 17, 30), UUID.randomUUID());
        Event thirdEvent = setupEvent(
                LocalDateTime.of(2023, 2, 14, 17, 30), UUID.randomUUID());
        List<Event> expectedEvents = List.of(firstEvent, secondEvent, thirdEvent);
        UUID movieId = UUID.randomUUID();

        when(eventService.findEventsForMovieBetweenTwoDates(
                movieId, "12-02-2023 17:00", "14-02-2023 20:30"))
                .thenReturn(expectedEvents);
        ResponseEntity<List<Event>> response = eventController.getEventsForMovieBetweenTwoDates(
                movieId, "12-02-2023 17:00", "14-02-2023 20:30");

        assertAll(
                "Validating response...",
                () -> assertEquals(expectedEvents, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test get events between two dates for movie not successful")
    public void testGetEventsBetweenTwoDatesForMovieNotSuccessful() {
        UUID movieId = UUID.randomUUID();

        when(eventService.findEventsForMovieBetweenTwoDates(
                movieId, "12-02-2023 17:00", "14-02-2023 20:30"))
                .thenReturn(List.of());
        ResponseEntity<List<Event>> response = eventController.getEventsForMovieBetweenTwoDates(
                movieId, "12-02-2023 17:00", "14-02-2023 20:30");

        assertAll(
                "Validating response...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test get events for day successful")
    public void testGetEventsForDaySuccessful() {
        Event firstEvent = setupEvent(
                LocalDateTime.of(2023, 2, 12, 14, 30), UUID.randomUUID());
        Event secondEvent = setupEvent(
                LocalDateTime.of(2023, 2, 12, 17, 30), UUID.randomUUID());
        Event thirdEvent = setupEvent(
                LocalDateTime.of(2023, 2, 12, 19, 30), UUID.randomUUID());
        List<Event> expectedEvents = List.of(firstEvent, secondEvent, thirdEvent);

        when(eventService.findAllEventsForDay("12-02-2020")).thenReturn(expectedEvents);
        ResponseEntity<List<Event>> response = eventController.getEventsForDay("12-02-2020");

        assertAll(
                "Validating response...",
                () -> assertEquals(expectedEvents, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test get events for day not successful")
    public void testGetEventsForDayNotSuccessful() {
        when(eventService.findAllEventsForDay("12-02-2020")).thenReturn(List.of());
        ResponseEntity<List<Event>> response = eventController.getEventsForDay("12-02-2020");

        assertAll(
                "Validating response...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test get events for day and movie successful")
    public void testGetEventsForDayAndMovieSuccessful() {
        Event firstEvent = setupEvent(
                LocalDateTime.of(2023, 2, 12, 14, 30), UUID.randomUUID());
        Event secondEvent = setupEvent(
                LocalDateTime.of(2023, 2, 12, 17, 30), UUID.randomUUID());
        Event thirdEvent = setupEvent(
                LocalDateTime.of(2023, 2, 12, 19, 30), UUID.randomUUID());
        List<Event> expectedEvents = List.of(firstEvent, secondEvent, thirdEvent);
        UUID movieId = UUID.randomUUID();

        when(eventService.findAllEventsForMovieAndDay(movieId, "12-02-2020")).thenReturn(expectedEvents);
        ResponseEntity<List<Event>> response = eventController.getEventsForMovieAndDay(movieId, "12-02-2020");

        assertAll(
                "Validating response...",
                () -> assertEquals(expectedEvents, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    @DisplayName("Test get events for day and movie not successful")
    public void testGetEventsForDayAndMovieNotSuccessful() {
        UUID movieId = UUID.randomUUID();

        when(eventService.findAllEventsForMovieAndDay(movieId, "12-02-2020")).thenReturn(List.of());
        ResponseEntity<List<Event>> response = eventController.getEventsForMovieAndDay(movieId, "12-02-2020");

        assertAll(
                "Validating response...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

}
