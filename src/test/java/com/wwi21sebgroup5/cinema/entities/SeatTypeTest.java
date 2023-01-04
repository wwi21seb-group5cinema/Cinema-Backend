package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SeatTypeTest {

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        String name = "testName";
        double price = 14.03;

        SeatType allArgSeatType = new SeatType(name, price);

        assertAll(
                "Validating parameters",
                () -> assertEquals(name, allArgSeatType.getName()),
                () -> assertEquals(price, allArgSeatType.getPrice())
        );
    }

    @Test
    @DisplayName("Test equality")
    public void testEquality() {
        String name = "testName";
        double price = 14.03;

        SeatType firstSeatType = new SeatType(name, price);
        SeatType secondSeatType = new SeatType(name, price);

        assertAll(
                "Validating equality...",
                () -> assertEquals(firstSeatType, secondSeatType),
                () -> assertEquals(firstSeatType.hashCode(), secondSeatType.hashCode())
        );

        secondSeatType.setId(UUID.randomUUID());

        assertAll(
                "Validating equality...",
                () -> assertNotEquals(firstSeatType, secondSeatType),
                () -> assertNotEquals(firstSeatType.hashCode(), secondSeatType.hashCode())
        );
    }

}
