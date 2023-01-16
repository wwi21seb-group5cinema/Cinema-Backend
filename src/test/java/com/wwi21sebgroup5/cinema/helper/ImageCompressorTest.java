package com.wwi21sebgroup5.cinema.helper;

import com.wwi21sebgroup5.cinema.exceptions.ImageCouldNotBeCompressedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImageCompressorTest {

    @Test
    @DisplayName("Test compress image")
    public void testCompressImageSmallImage() throws IOException, ImageCouldNotBeCompressedException {
        File fi = new File("src/test/resources/beispielbild2.png");
        byte[] data = Files.readAllBytes(fi.toPath());
        assertEquals(data, ImageCompressor.compressImage(data, 1, "image/png"));
    }

    @Test
    @DisplayName("Test compress image - compression failed")
    public void testCompressImageFailed() throws IOException {
        File fi = new File("src/test/resources/rocky.jpg");
        byte[] data = Files.readAllBytes(fi.toPath());
        assertThrows(ImageCouldNotBeCompressedException.class, () -> ImageCompressor.compressImage(data, 0, "image/jpeg"));
    }

    @Test
    @DisplayName("Test compress image - compression failed")
    public void testCompressBigImageFailed() throws IOException, ImageCouldNotBeCompressedException {
        File fi = new File("src/test/resources/astronaut.jpg");
        byte[] data = Files.readAllBytes(fi.toPath());
        byte[] after = ImageCompressor.compressImage(data, 1, "image/jpeg");
        assert (after.length <= ImageCompressor.MAX_IMAGE_SIZE_IN_BYTE);
    }

}
