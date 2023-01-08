package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.CinemaHall;
import com.wwi21sebgroup5.cinema.entities.Event;
import com.wwi21sebgroup5.cinema.entities.Movie;
import com.wwi21sebgroup5.cinema.entities.Ticket;
import com.wwi21sebgroup5.cinema.repositories.EventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    EventRepository eventRepository;

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
    public void testAddEventSuccessful() {

    }

    @Test
    @DisplayName("Test movie not found while adding new event")
    public void testMovieNotFoundWhileAdding() {

    }

    @Test
    @DisplayName("Test cinema hall not found while adding new event")
    public void testCinemaHallNotFoundWhileAdding() {

    }

    @Test
    @DisplayName("Test ticket already exists while adding new event")
    public void testTicketAlreadyExistsWhileAdding() {

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
