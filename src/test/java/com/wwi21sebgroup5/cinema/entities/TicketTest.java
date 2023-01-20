package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        Event event = new Event();
        Seat seat = new Seat();

        Ticket allArgsTicket = new Ticket(event, seat);

        assertAll(
                "Validating parameters..",
                () -> assertEquals(event, allArgsTicket.getEvent()),
                () -> assertEquals(seat, allArgsTicket.getSeat())
        );
    }

    @Test
    @DisplayName("Test equality")
    public void testEquality() {
        UUID id = UUID.randomUUID();
        Event event = new Event(new Movie(), new CinemaHall(), List.of(), LocalDateTime.of(12, 12, 12, 12, 12, 12));
        Seat seat = new Seat();
        Booking b = new Booking();
        Ticket first = getTicket(id, event, seat, b);
        Ticket second = getTicket(id, event, seat, b);

        assertEquals(first, first);
        assertEquals(first.hashCode(), second.hashCode());
        assertEquals(first, second);

        assertNotEquals(first, "String");
        assertNotEquals(first, null);

        second.setBooking(null);
        assertNotEquals(first, second);

        second = getTicket(id, event, seat, b);
        second.setId(null);
        assertNotEquals(first, second);

        second = getTicket(id, event, seat, b);
        second.setEvent(null);
        assertNotEquals(first, second);

        second = getTicket(id, event, seat, b);
        second.setSeat(null);
        assertNotEquals(first, second);

        second = getTicketNull();
        assertNotEquals(first.hashCode(), second.hashCode());

    }

    private Ticket getTicket(UUID id, Event event, Seat seat, Booking b) {
        Ticket allArgsTicket = new Ticket(event, seat);
        allArgsTicket.setId(id);
        allArgsTicket.setBooking(b);
        return allArgsTicket;
    }

    private Ticket getTicketNull() {
        return new Ticket(null, null);
    }

}

