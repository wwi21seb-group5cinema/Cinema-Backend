package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        Event event = new Event();
        Seat seat = new Seat();
        QR_Code qr_code = new QR_Code();
        Booking booking = new Booking();

        Ticket firstTicket = new Ticket(event, seat);
        firstTicket.setBooking(booking);
        firstTicket.setQr_code(qr_code);

        Ticket secondTicket = new Ticket(event, seat);
        secondTicket.setBooking(booking);
        secondTicket.setQr_code(qr_code);

        assertAll(
                "Validating equality..",
                () -> assertEquals(firstTicket, secondTicket),
                () -> assertEquals(firstTicket.hashCode(), secondTicket.hashCode())
        );

        secondTicket.setId(UUID.randomUUID());

        assertAll(
                "Validating equality..",
                () -> assertNotEquals(firstTicket, secondTicket),
                () -> assertNotEquals(firstTicket.hashCode(), secondTicket.hashCode())
        );
    }
}

