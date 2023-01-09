package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SeatTypeTest {

    private SeatType setupSeatType(UUID id) {
        SeatType seatType = new SeatType("TestName", 5.0);
        seatType.setId(id);
        return seatType;
    }

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
        SeatType firstType = setupSeatType(UUID.randomUUID());
        SeatType secondType = setupSeatType(firstType.getId());

        // test equality
        assertEquals(firstType, secondType);
        assertEquals(firstType.hashCode(), secondType.hashCode());

        // test null and different class
        assertNotEquals(firstType, null);
        assertNotEquals(firstType, "");

        // test different id
        secondType.setId(UUID.randomUUID());
        assertNotEquals(firstType, secondType);

        // test different name
        secondType = setupSeatType(firstType.getId());
        secondType.setName("otherName");
        assertNotEquals(firstType, secondType);

        // test different price
        secondType = setupSeatType(firstType.getId());
        secondType.setPrice(8.0);
        assertNotEquals(firstType, secondType);
    }

}
