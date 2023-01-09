package com.wwi21sebgroup5.cinema.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DateFormatterTest {

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        DateFormatter dateFormatter = new DateFormatter();
        assertNotNull(dateFormatter);
    }

}
