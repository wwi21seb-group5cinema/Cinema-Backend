package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ProducerTest {

    @Test
    @DisplayName("Test constructo")
    public void testConstructor() {
        String name = "testName";
        Producer producer = new Producer(name);

        assertAll(
                "Asserting parameters...",
                () -> assertEquals(producer.getName(), name)
        );
    }

    @Test
    @DisplayName("Test equality")
    public void testEquality() {
        UUID id = UUID.randomUUID();
        Producer first = getProducer(id);
        Producer second = getProducer(id);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertEquals(first, first);

        assertNotEquals(first, "String");
        assertNotEquals(first, null);

        second.setName(null);
        assertNotEquals(first, second);

        second = getProducerNull();
        assertNotEquals(first.hashCode(), second.hashCode());

    }

    private Producer getProducer(UUID id) {
        Producer producer = new Producer("name");
        producer.setId(id);
        return producer;
    }

    private Producer getProducerNull() {
        return new Producer(null);
    }

}
