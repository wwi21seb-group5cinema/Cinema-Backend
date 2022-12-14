package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ActorTest {

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        Actor a = new Actor("name", "firstname");
        assertAll(
                "Validatiing parameters...",
                () -> assertEquals("name", a.getName()),
                () -> assertEquals("firstname", a.getFirstName())
        );
    }

    @Test
    @DisplayName("Test equality")
    public void testEquality() {
        Actor first = new Actor("name", "firstname");
        Actor second = new Actor("name", "firstname");
        Actor third = new Actor("name2", "firstname2");
        assertAll(
                "Validating parameters...",
                () -> assertEquals(first, second),
                () -> assertEquals(first.hashCode(), second.hashCode()),
                () -> assertEquals(first, first)
        );

        assertAll(
                "Validating parameters...",
                () -> assertNotEquals(first, "second"),
                () -> assertNotEquals(first, third),
                () -> assertNotEquals(first, null)
        );

        second.setId(UUID.randomUUID());
        assertAll(
                "Validating parameters...",
                () -> assertNotEquals(first, second),
                () -> assertNotEquals(first.hashCode(), second.hashCode())
        );
    }
}
