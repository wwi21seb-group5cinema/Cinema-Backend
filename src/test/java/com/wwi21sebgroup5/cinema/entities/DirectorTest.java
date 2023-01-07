package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DirectorTest {

    @Test
    @DisplayName("Testing the constructor")
    public void constructorTest() {
        String name = "name", firstname = "firstname";
        Director director = new Director(name, firstname);

        assertAll(
                "Testing all parameters",
                () -> assertEquals(name, director.getName(), "Name returned wrong"),
                () -> assertEquals(firstname, director.getFirstName(), "FirstName returned wrong")
        );
    }

    @Test
    @DisplayName("Testing equality")
    public void equalityTest() {
        UUID id = UUID.randomUUID();
        Director first = getDirector(id);
        Director second = getDirector(id);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertEquals(first, first);

        assertNotEquals(first, "String");
        assertNotEquals(first, null);

        second.setName(null);
        assertNotEquals(first, second);

        second = getDirector(id);
        second.setFirstName(null);
        assertNotEquals(first, second);

        second = getDirectorNull();
        assertNotEquals(first.hashCode(), second.hashCode());
    }

    private Director getDirector(UUID id) {
        String name = "name", firstname = "firstname";
        Director director = new Director(name, firstname);
        director.setId(id);
        return director;
    }

    private Director getDirectorNull() {
        return new Director(null, null);
    }

}