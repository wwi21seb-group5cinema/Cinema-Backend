package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
    public void testEquality() {
        UUID id = UUID.randomUUID();
        User user = new User();
        Booking first = getBooking(id, user);
        Booking second = getBooking(id, user);

        assertEquals(first, first);
        assertEquals(first.hashCode(), second.hashCode());
        assertEquals(first, second);

        second.setId(null);
        assertNotEquals(first, second);

        second = getBooking(id, user);
        second.setUser(null);
        assertNotEquals(first, second);

        second.setUser(null);
        second.setId(null);
        assertNotEquals(first.hashCode(), second.hashCode());

        assertEquals(first.hashCode(), first.hashCode());
        second = getBooking(id, user);
        assertEquals(second.hashCode(), second.hashCode());

    }

    public Booking getBooking(UUID id, User user) {
        Booking b = new Booking(user);
        b.setId(id);
        return b;
    }
}
