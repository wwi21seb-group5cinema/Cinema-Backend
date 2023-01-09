package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SeatBlueprintTest {

    private SeatBlueprint setupSeatBlueprint(UUID id) {
        SeatBlueprint seatBlueprint = new SeatBlueprint(
                new SeatingPlan(),
                new SeatType(),
                3, 2
        );
        seatBlueprint.setId(id);
        return seatBlueprint;
    }

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        SeatingPlan seatingPlan = new SeatingPlan();
        SeatType seatType = new SeatType();
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
        SeatBlueprint firstSeatBlueprint = setupSeatBlueprint(UUID.randomUUID());
        SeatBlueprint secondSeatblueprint = firstSeatBlueprint;

        // Test equality
        assertEquals(firstSeatBlueprint, secondSeatblueprint);
        assertEquals(firstSeatBlueprint.hashCode(), secondSeatblueprint.hashCode());

        // Test null and different class
        assertNotEquals(firstSeatBlueprint, null);
        assertNotEquals(firstSeatBlueprint, "");


        // Test different id
        secondSeatblueprint = setupSeatBlueprint(UUID.randomUUID());
        assertNotEquals(firstSeatBlueprint, secondSeatblueprint);

        // Test different row
        secondSeatblueprint = setupSeatBlueprint(firstSeatBlueprint.getId());
        secondSeatblueprint.setRow(2);
        assertNotEquals(firstSeatBlueprint, secondSeatblueprint);

        // Test different place
        secondSeatblueprint = setupSeatBlueprint(firstSeatBlueprint.getId());
        secondSeatblueprint.setPlace(4);
        assertNotEquals(firstSeatBlueprint, secondSeatblueprint);
    }

}
