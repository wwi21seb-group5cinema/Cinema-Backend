package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SeatTest {

    private SeatingPlan setupSeatingPlan() {
        return new SeatingPlan();
    }

    private SeatType setupSeatType() {
        return new SeatType("Loge", 15.2);
    }

    private Event setupEvent() {
        return new Event();
    }

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        SeatingPlan seatingPlan = setupSeatingPlan();
        SeatType seatType = setupSeatType();
        Event event = setupEvent();
        int row = 3, place = 5;

        Seat allArgSeat = new Seat(
                seatingPlan, seatType, event, row, place
        );

        assertAll(
                () -> assertEquals(seatingPlan, allArgSeat.getSeatingPlan()),
                () -> assertEquals(seatType, allArgSeat.getSeatType()),
                () -> assertEquals(event, allArgSeat.getEvent()),
                () -> assertEquals(row, allArgSeat.getRow()),
                () -> assertEquals(place, allArgSeat.getPlace())
        );
    }

    @Test
    @DisplayName("Test equality")
    public void testEquality() {
        SeatingPlan seatingPlan = setupSeatingPlan();
        SeatType seatType = setupSeatType();
        Event event = setupEvent();
        int row = 3, place = 5;

        Seat firstSeat = new Seat(
                seatingPlan, seatType, event, row, place
        );
        Seat secondSeat = new Seat(
                seatingPlan, seatType, event, row, place
        );

        assertAll(
                "Validating equality...",
                () -> assertEquals(firstSeat, secondSeat),
                () -> assertEquals(firstSeat.hashCode(), secondSeat.hashCode())
        );

        secondSeat.setId(UUID.randomUUID());

        assertAll(
                "Validating equality...",
                () -> assertNotEquals(firstSeat, secondSeat),
                () -> assertNotEquals(firstSeat.hashCode(), secondSeat.hashCode())
        );
    }

}
