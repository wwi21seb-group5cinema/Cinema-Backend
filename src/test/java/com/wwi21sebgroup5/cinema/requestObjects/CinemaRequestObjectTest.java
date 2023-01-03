package com.wwi21sebgroup5.cinema.requestObjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CinemaRequestObjectTest {

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        CinemaRequestObject requestObject = new CinemaRequestObject();
        assertNotNull(requestObject);
    }

}
