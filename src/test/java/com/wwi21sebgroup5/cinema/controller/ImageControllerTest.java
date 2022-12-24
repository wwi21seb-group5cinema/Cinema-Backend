package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.ImageData;
import com.wwi21sebgroup5.cinema.exceptions.ImageNotFoundException;
import com.wwi21sebgroup5.cinema.services.ImageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImageControllerTest {
    @Mock
    ImageService imageService;

    @InjectMocks
    ImageController imageController;

    @Test
    @DisplayName("Test successfully getting Image by ID")
    public void testGetImageByIDSuccessful() {
        try {
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());

            ImageData image = new ImageData("image/png", data);
            UUID id = image.getId();

            when(imageService.downloadImage(id))
                    .thenReturn(image);
            ResponseEntity<?> response = imageController.downloadImage(id);
            assertAll(
                    "Validating correct response from controller...",
                    () -> assertEquals(response.getBody(), image.getImageData()),
                    () -> assertEquals(response.getStatusCode(), HttpStatus.OK)
            );
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed");
        }
    }

    @Test
    @DisplayName("Test unsuccessfully getting Image by ID")
    public void testGetImageByIDUnsuccessfully() {
        UUID id = UUID.randomUUID();
        ImageNotFoundException e = new ImageNotFoundException(id);
        try {
            when(imageService.downloadImage(id))
                    .thenThrow(e);
        } catch (ImageNotFoundException ex) {
            fail("Failed to get Image");
        }
        ResponseEntity<?> response = imageController.downloadImage(id);
        assertAll(
                "Validating correct response from controller...",
                () -> assertEquals(response.getBody(), e.getMessage()),
                () -> assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND)
        );
    }


}
