package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SeatBlueprintTest {

    private SeatingPlan setupSeatingPlan() {
        return new SeatingPlan();
    }

    private SeatType setupSeatType() {
        return new SeatType("Premium", 14.3);
    }

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        SeatingPlan seatingPlan = setupSeatingPlan();
        SeatType seatType = setupSeatType();
        int row = 3, place = 5;

        SeatBlueprint allArgSeatBlueprint = new SeatBlueprint(
                seatingPlan, seatType, row, place
        );

        assertAll(
                "Validating parameters",
                () -> assertEquals(seatingPlan, allArgSeatBlueprint.getSeatingPlan()),
                () -> assertEquals(seatType, allArgSeatBlueprint.getSeatType()),
                () -> assertEquals(row, allArgSeatBlueprint.getRow()),
                () -> assertEquals(place, allArgSeatBlueprint.getPlace())
        );
    }

    @Test
    @DisplayName("Test equality")
    public void testEquality() {
        SeatingPlan seatingPlan = setupSeatingPlan();
        SeatType seatType = setupSeatType();
        int row = 3, place = 5;

        SeatBlueprint firstSeatBlueprint = new SeatBlueprint(
                seatingPlan, seatType, row, place
        );
        SeatBlueprint secondSeatblueprint = new SeatBlueprint(
                seatingPlan, seatType, row, place
        );

        assertAll(
                "Validating equality...",
                () -> assertEquals(firstSeatBlueprint, secondSeatblueprint),
                () -> assertEquals(firstSeatBlueprint.hashCode(), secondSeatblueprint.hashCode())
        );

        secondSeatblueprint.setId(UUID.randomUUID());

        assertAll(
                "Validating equality...",
                () -> assertNotEquals(firstSeatBlueprint, secondSeatblueprint),
                () -> assertNotEquals(firstSeatBlueprint.hashCode(), secondSeatblueprint.hashCode())
        );
    }

}
