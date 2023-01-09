package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.exceptions.CinemaHallNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.MovieNotFoundException;
import com.wwi21sebgroup5.cinema.repositories.EventRepository;
import com.wwi21sebgroup5.cinema.requestObjects.EventRequestObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    EventRepository eventRepository;

    @Mock
    MovieService movieService;

    @Mock
    CinemaHallService cinemaHallService;

    @Mock
    SeatService seatService;

    @Mock
    TicketService ticketService;

    @InjectMocks
    EventService eventService;

    private Event setupEvent(LocalDateTime eventDateTime, UUID id) {
        Event event =  new Event(
                new Movie(), new CinemaHall(), List.of(new Ticket(), new Ticket()), eventDateTime
        );
        event.setId(id);
        return event;
    }

    @Test
    @DisplayName("Test adding event successfully")
    public void testAddEventSuccessful() throws Exception {
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
                    seatBlueprint.getSeatType(), seatBlueprint.getRow(), seatBlueprint.getPlace()
            );

            Ticket newTicket = new Ticket(expectedEvent, newSeat);
            tickets.add(newTicket);
        }

        expectedEvent.setTickets(tickets);

        EventRequestObject requestObject = new EventRequestObject(
                movie.getId(), cinemaHall.getId(), "08-01-2023 20:15"
        );

        when(movieService.findById(movie.getId())).thenReturn(Optional.of(movie));
        when(cinemaHallService.getCinemaHallById(cinemaHall.getId())).thenReturn(Optional.of(cinemaHall));
        when(seatService.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(ticketService.saveTicket(any(), any())).thenAnswer(
                invocation -> new Ticket(invocation.getArgument(0), invocation.getArgument(1)));
        when(eventRepository.save(expectedEvent)).thenReturn(expectedEvent);

        assertEquals(expectedEvent, eventService.addEvent(requestObject));
    }

    @Test
    @DisplayName("Test movie not found while adding new event")
    public void testMovieNotFoundWhileAdding() {
        Movie movie = new Movie();
        movie.setId(UUID.randomUUID());

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setId(UUID.randomUUID());

        EventRequestObject requestObject = new EventRequestObject(
                movie.getId(), cinemaHall.getId(), "08-01-2023 20:15"
        );

        when(movieService.findById(movie.getId())).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> eventService.addEvent(requestObject));
    }

    @Test
    @DisplayName("Test cinema hall not found while adding new event")
    public void testCinemaHallNotFoundWhileAdding() {
        Movie movie = new Movie();
        movie.setId(UUID.randomUUID());

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setId(UUID.randomUUID());

        EventRequestObject requestObject = new EventRequestObject(
                movie.getId(), cinemaHall.getId(), "08-01-2023 20:15"
        );

        when(movieService.findById(movie.getId())).thenReturn(Optional.of(movie));
        when(cinemaHallService.getCinemaHallById(cinemaHall.getId())).thenReturn(Optional.empty());

        assertThrows(CinemaHallNotFoundException.class, () -> eventService.addEvent(requestObject));
    }

    @Test
    @DisplayName("Test ticket already exists while adding new event")
    public void testTicketAlreadyExistsWhileAdding() {
        Movie movie = new Movie();
        movie.setId(UUID.randomUUID());

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setId(UUID.randomUUID());

        EventRequestObject requestObject = new EventRequestObject(
                movie.getId(), cinemaHall.getId(), "08-01-2023 20:15"
        );

        when(movieService.findById(movie.getId())).thenReturn(Optional.of(movie));
        when(cinemaHallService.getCinemaHallById(cinemaHall.getId())).thenReturn(Optional.empty());

        assertThrows(CinemaHallNotFoundException.class, () -> eventService.addEvent(requestObject));
    }

    @Test
    @DisplayName("Test find by id")
    public void testFindById() {
        UUID id = UUID.randomUUID();
        LocalDateTime eventDateTime = LocalDateTime.of(2023, 1, 8, 18, 0);
        Event expectedEvent = setupEvent(eventDateTime, id);

        when(eventRepository.findById(id)).thenReturn(Optional.of(expectedEvent));

        Optional<Event> actualEvent = eventService.findById(id);

        assertEquals(expectedEvent, actualEvent.get());
    }

    @Test
    @DisplayName("Test find all events by movie")
    public void testFindAllByMovie() {
        LocalDateTime firstEventTime = LocalDateTime.of(2023, 1, 8, 18, 0);
        LocalDateTime secondEventTime = LocalDateTime.of(2023, 4, 12, 12, 0);
        LocalDateTime thirdEventTime = LocalDateTime.of(2022, 12, 4, 20, 15);

        Event firstEvent = setupEvent(firstEventTime, UUID.randomUUID());
        Event secondEvent = setupEvent(secondEventTime, UUID.randomUUID());
        Event thirdEvent = setupEvent(thirdEventTime, UUID.randomUUID());
        List<Event> expectedEvents = List.of(firstEvent, secondEvent, thirdEvent);

        UUID movieId = UUID.randomUUID();
        when(eventRepository.findByMovie_Id(movieId)).thenReturn(expectedEvents);

        List<Event> actualEvents = eventService.findAllByMovie(movieId);

        assertEquals(expectedEvents, actualEvents);
    }

    @Test
    @DisplayName("Test find events between two dates")
    public void testFindEventsBetweenTwoDates() {
        LocalDateTime firstEventTime = LocalDateTime.of(2023, 1, 8, 18, 0);
        LocalDateTime secondEventTime = LocalDateTime.of(2023, 4, 12, 12, 0);

        Event firstEvent = setupEvent(firstEventTime, UUID.randomUUID());
        Event secondEvent = setupEvent(secondEventTime, UUID.randomUUID());
        List<Event> expectedEvents = List.of(firstEvent, secondEvent);

        when(eventRepository.findByEventDateTimeIsBetween(firstEventTime, secondEventTime)).thenReturn(expectedEvents);

        List<Event> actualEvents = eventService.findEventsBetweenTwoDates("08-01-2023 18:00", "12-04-2023 12:00");

        assertEquals(expectedEvents, actualEvents);
    }

    @Test
    @DisplayName("Test find events between two dates by movie")
    public void testFindEventsBetweenTwoDatesByMovie() {
        LocalDateTime firstEventTime = LocalDateTime.of(2023, 1, 8, 18, 0);
        LocalDateTime secondEventTime = LocalDateTime.of(2022, 12, 4, 20, 15);

        Event firstEvent = setupEvent(firstEventTime, UUID.randomUUID());
        Event secondEvent = setupEvent(secondEventTime, UUID.randomUUID());
        List<Event> expectedEvents = List.of(firstEvent, secondEvent);

        UUID movieId = UUID.randomUUID();
        when(eventRepository.findByMovie_IdAndEventDateTimeIsBetween(movieId, secondEventTime, firstEventTime))
                .thenReturn(expectedEvents);

        List<Event> actualEvents = eventService.findEventsForMovieBetweenTwoDates(
                movieId, "04-12-2022 20:15", "08-01-2023 18:00");

        assertEquals(expectedEvents, actualEvents);
    }

    @Test
    @DisplayName("Test find all events for one day")
    public void testFindAllEventsForOneDay() {
        LocalDateTime eventDateTime = LocalDateTime.of(2022, 12, 4, 20, 15);

        Event firstEvent = setupEvent(eventDateTime, UUID.randomUUID());
        Event secondEvent = setupEvent(eventDateTime, UUID.randomUUID());
        List<Event> expectedEvents = List.of(firstEvent, secondEvent);

        when(eventRepository.findByEventDateTimeIsBetween(eventDateTime.toLocalDate().atStartOfDay(),
                eventDateTime.toLocalDate().plusDays(1).atStartOfDay())).thenReturn(expectedEvents);

        List<Event> actualEvents = eventService.findAllEventsForDay("04-12-2022");

        assertEquals(expectedEvents, actualEvents);
    }

    @Test
    @DisplayName("Test find all events for one day by movie")
    public void testFindAllEventsForMovieAndDay() {
        LocalDateTime eventDateTime = LocalDateTime.of(2023, 4, 12, 12, 0);

        Event firstEvent = setupEvent(eventDateTime, UUID.randomUUID());
        Event secondEvent = setupEvent(eventDateTime, UUID.randomUUID());
        List<Event> expectedEvents = List.of(firstEvent, secondEvent);

        UUID movieId = UUID.randomUUID();
        when(eventRepository.findByMovie_IdAndEventDateTimeIsBetween(
                movieId,
                eventDateTime.toLocalDate().atStartOfDay(),
                eventDateTime.toLocalDate().plusDays(1).atStartOfDay())).thenReturn(expectedEvents);

        List<Event> actualEvents = eventService.findAllEventsForMovieAndDay(movieId, "12-04-2023");

        assertEquals(expectedEvents, actualEvents);
    }

}
