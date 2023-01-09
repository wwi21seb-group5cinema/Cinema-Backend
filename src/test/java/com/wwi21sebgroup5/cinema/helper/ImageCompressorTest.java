package com.wwi21sebgroup5.cinema.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImageCompressorTest {

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        ImageCompressor imageCompressor = new ImageCompressor();
        assertNotNull(imageCompressor);
    }

    @Test
    @DisplayName("Test compress image")
    public void testCompressImage() {
        byte[] bytes = new byte[12];
        assertEquals(bytes, ImageCompressor.compressImage(bytes));
    }

    @Test
    @DisplayName("Test decompress image")
    public void testDecompressImage() {
        byte[] bytes = new byte[12];
        assertEquals(bytes, ImageCompressor.decompressImage(bytes));
    }

}
