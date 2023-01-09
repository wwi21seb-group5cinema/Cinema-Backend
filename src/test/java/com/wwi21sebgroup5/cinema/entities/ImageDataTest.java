package com.wwi21sebgroup5.cinema.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ImageDataTest {

    @Test
    @DisplayName("Testing the constructor")
    public void constructorTest() throws IOException {
        File fi = new File("src/test/resources/beispielbild2.png");
        byte[] data = Files.readAllBytes(fi.toPath());
        ImageData imageData = new ImageData("image/png", data);

        assertAll(
                "Testing all parameters",
                () -> assertEquals(data, imageData.getImageData(), "Data returned wrong"),
                () -> assertEquals("image/png", imageData.getType(), "Type returned wrong")
        );
    }

    @Test
    @DisplayName("Testing equality")
    public void equalityTest() throws IOException {
        UUID id = UUID.randomUUID();
        ImageData first = getImageData(id);
        ImageData second = getImageData(id);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertEquals(first, first);

        assertNotEquals(first, "String");
        assertNotEquals(first, null);

        second.setImageData(null);
        assertNotEquals(first, second);

        second = getImageData(id);
        second.setType(null);
        assertNotEquals(first, second);

        second = getImageData(UUID.randomUUID());
        assertNotEquals(first, second);

        second = getImageNull();
        assertNotEquals(first.hashCode(), second.hashCode());
    }

    private ImageData getImageData(UUID id) throws IOException {
        File fi = new File("src/test/resources/beispielbild2.png");
        byte[] data = Files.readAllBytes(fi.toPath());
        ImageData image = new ImageData("image/png", data);
        image.setId(id);
        return image;
    }

    private ImageData getImageNull() {
        return new ImageData(null, null);
    }

}
