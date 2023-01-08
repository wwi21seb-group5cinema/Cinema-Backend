package com.wwi21sebgroup5.cinema.entities;

import com.wwi21sebgroup5.cinema.enums.FSK;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    private Event setupEvent(UUID uuid) {
        Movie movie = new Movie(
                new Producer(), new Director(), FSK.SIX, new Genre(), new ImageData(),
                "testName", "testDescription",
                LocalDate.of(2022, 12, 2), LocalDate.of(2023, 5, 3)
        );
        CinemaHall cinemaHall = new CinemaHall(
                new Cinema(), new SeatingPlan(), "testCinemaHall", 3
        );
        List<Ticket> tickets = List.of(new Ticket(), new Ticket(), new Ticket(), new Ticket());

        Event event = new Event(
                movie, cinemaHall, tickets, LocalDateTime.of(2023, 1, 12, 20, 15)
        );
        event.setId(uuid);

        return event;
    }

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        Event event = new Event(
                new Movie(), new CinemaHall(), List.of(),
                LocalDateTime.of(2022, 12, 2, 18, 0)
        );

        assertAll(
                "Validating parameters..",
                () -> assertNotNull(event.getMovie()),
                () -> assertNotNull(event.getCinemaHall()),
                () -> assertNotNull(event.getTickets()),
                () -> assertEquals(LocalDate.of(2022, 12, 2), event.getEventDay()),
                () -> assertEquals(LocalTime.of(18, 0), event.getEventTime())
        );
    }

    @Test
    @DisplayName("Test equality")
    public void testEquality() {
        Event firstEvent = setupEvent(UUID.randomUUID());
        Event secondEvent = setupEvent(firstEvent.getId());

        assertEquals(firstEvent, secondEvent);
        assertEquals(firstEvent.hashCode(), secondEvent.hashCode());

        // Test wrong class
        assertNotEquals(firstEvent, "TestString");

        // Test different id
        secondEvent.setId(UUID.randomUUID());
        assertNotEquals(firstEvent, secondEvent);

        // Test different movie
        secondEvent = setupEvent(firstEvent.getId());
        secondEvent.setMovie(new Movie());
        assertNotEquals(firstEvent, secondEvent);

        // Test different cinema hall
        secondEvent = setupEvent(firstEvent.getId());
        secondEvent.setCinemaHall(new CinemaHall());
        assertNotEquals(firstEvent, secondEvent);

        // Test different tickets
        secondEvent = setupEvent(firstEvent.getId());
        secondEvent.setTickets(List.of(new Ticket(), new Ticket()));
        assertNotEquals(firstEvent, secondEvent);

        // Test different event date time
        secondEvent = setupEvent(firstEvent.getId());
        secondEvent.setEventDateTime(LocalDateTime.now());
        assertNotEquals(firstEvent, secondEvent);
    }

}
