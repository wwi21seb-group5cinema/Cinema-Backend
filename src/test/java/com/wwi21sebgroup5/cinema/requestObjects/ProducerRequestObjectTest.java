package com.wwi21sebgroup5.cinema.requestObjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProducerRequestObjectTest {

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        ProducerRequestObject requestObject = new ProducerRequestObject();
        assertNotNull(requestObject);
    }

}
