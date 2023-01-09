package com.wwi21sebgroup5.cinema.entities;

import com.wwi21sebgroup5.cinema.enums.SeatState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SeatTest {

    private Seat setupSeat(UUID id) {
        Seat seat = new Seat(
                new SeatType(), 3, 3
        );
        seat.setId(id);
        return seat;
    }

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        SeatType seatType = new SeatType("Loge", 15.2);
        int row = 3, place = 5;

        Seat allArgSeat = new Seat(
                seatType, row, place
        );

        assertAll(
                () -> assertEquals(seatType, allArgSeat.getSeatType()),
                () -> assertEquals(row, allArgSeat.getRow()),
                () -> assertEquals(place, allArgSeat.getPlace())
        );
    }

    @Test
    @DisplayName("Test equality")
    public void testEquality() {
        Seat firstSeat = setupSeat(UUID.randomUUID());
        Seat secondSeat = setupSeat(firstSeat.getId());

        // test equality
        assertEquals(firstSeat, secondSeat);
        assertEquals(firstSeat.hashCode(), secondSeat.hashCode());

        // test null and different class
        assertNotEquals(firstSeat, null);
        assertNotEquals(firstSeat, "");

        // test different id
        secondSeat.setId(UUID.randomUUID());
        assertNotEquals(firstSeat, secondSeat);

        // test different row
        secondSeat = setupSeat(firstSeat.getId());
        secondSeat.setRow(5);
        assertNotEquals(firstSeat, secondSeat);

        // test different place
        secondSeat = setupSeat(firstSeat.getId());
        secondSeat.setPlace(5);
        assertNotEquals(firstSeat, secondSeat);

        // test different seat type
        secondSeat = setupSeat(firstSeat.getId());
        secondSeat.setSeatType(new SeatType("Test", 5.0));
        assertNotEquals(firstSeat, secondSeat);

        // test different seat state
        secondSeat = setupSeat(firstSeat.getId());
        secondSeat.setSeatState(SeatState.RESERVED);
        assertNotEquals(firstSeat, secondSeat);
    }

}
