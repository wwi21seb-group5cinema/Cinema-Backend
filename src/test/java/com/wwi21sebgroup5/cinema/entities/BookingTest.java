package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BookingTest {

    @Test
    @DisplayName("Test Constructor")
    public void testConstructor() {
        User user = new User();

        Booking booking = new Booking(user);

        assertEquals(user, booking.getUser());
    }

    @Test
    @DisplayName("Test equality")
    public void testEquality(){
        UUID id = UUID.randomUUID();
        User user = new User();
        Invoice invoice = new Invoice();
        Booking first = getBooking(id, user, invoice);
        Booking second = getBooking(id, user, invoice);

        assertEquals(first, first);
        assertEquals(first.hashCode(), second.hashCode());
        assertEquals(first, second);

        second.setId(null);
        assertNotEquals(first, second);

        second = getBooking(id, user ,invoice);
        second.setUser(null);
        assertNotEquals(first, second);

        second = getBooking(id, user, invoice);
        second.setInvoice(null);
        assertNotEquals(first, second);

        second.setUser(null);
        second.setId(null);
        assertNotEquals(first.hashCode(), second.hashCode());

        assertEquals(first.hashCode(), first.hashCode());
        second = getBooking(id, user, invoice);
        assertEquals(second.hashCode(), second.hashCode());

    }

    public Booking getBooking(UUID id, User user, Invoice invoice){
        Booking b = new Booking(user);
        b.setId(id);
        b.setInvoice(invoice);
        return b;
    }
}
