package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.ImageData;
import com.wwi21sebgroup5.cinema.exceptions.ImageCouldNotBeCompressedException;
import com.wwi21sebgroup5.cinema.exceptions.ImageNotFoundException;
import com.wwi21sebgroup5.cinema.helper.ImageCompressor;
import com.wwi21sebgroup5.cinema.repositories.ImageDataRepository;
import com.wwi21sebgroup5.cinema.requestObjects.AddImageReturnObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @Mock
    ImageDataRepository imageDataRepository;
    @InjectMocks
    ImageService imageService;

    @Test
    @DisplayName("Upload image succesfully")
    public void uploadImageSuccessfully() throws ImageCouldNotBeCompressedException {
        try {
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            MultipartFile multipartFile = new MockMultipartFile("name",
                    "originalFileName", "image/png", data);
            ImageData image = new ImageData("image/png", data, false);
            try (MockedStatic<ImageCompressor> utilities = Mockito.mockStatic(ImageCompressor.class)) {
                utilities.when(() -> ImageCompressor.compressImage(data, 1, "image/png"))
                        .thenReturn(data);
            }
            when(imageDataRepository.save(image)).thenReturn(image);
            AddImageReturnObject actual = imageService.uploadImage(multipartFile);

            verify(imageDataRepository, times(1)).save(image);
            assertEquals(image.getType(), actual.getType());
            assertFalse(actual.isCompressed());

        } catch (IOException e) {
            fail("Failed to read bytes");
        }
    }

    @Test
    @DisplayName("Upload image unsuccesfully - IOExcepton")
    public void uploadImageUnsuccessfully() {
        try (MockedStatic<ImageCompressor> utilities = Mockito.mockStatic(ImageCompressor.class)) {
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            MultipartFile multipartFile = new MockMultipartFile("name",
                    "originalFileName", "image/png", data);
            IOException e = new IOException();
            try {
                utilities.when(() -> ImageCompressor.compressImage(data, 1, "image/png"))
                        .thenThrow(e);
            } catch (Exception ex) {
            }
            assertThrows(IOException.class, () -> imageService.uploadImage(multipartFile));
        } catch (IOException e) {
            fail("Failed to read bytes");
        }
    }

    @Test
    @DisplayName("Upload image unsuccesfully - ImageCouldNotBeCompressedException")
    public void uploadImageUnsuccessfullyCompressionFailed() {
        try (MockedStatic<ImageCompressor> utilities = Mockito.mockStatic(ImageCompressor.class)) {
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            MultipartFile multipartFile = new MockMultipartFile("name",
                    "originalFileName", "image/png", data);
            ImageCouldNotBeCompressedException e = new ImageCouldNotBeCompressedException();
            try {
                utilities.when(() -> ImageCompressor.compressImage(data, 1, "image/png"))
                        .thenThrow(e);
            } catch (Exception ex) {
            }
            assertThrows(ImageCouldNotBeCompressedException.class, () -> imageService.uploadImage(multipartFile));
        } catch (IOException e) {
            fail("Failed to read bytes");
        }
    }

    @Test
    @DisplayName("Download image succesfully by ID")
    public void downloadImageSuccessfully() {
        try {
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData expected = new ImageData("image/png", data, false);
            UUID id = UUID.randomUUID();
            when(imageDataRepository.findById(id)).thenReturn(Optional.of(expected));
            ImageData actual = null;
            try {
                actual = imageService.downloadImage(id);
            } catch (ImageNotFoundException e) {
                fail("Failed");
            }
            assertEquals(expected, actual, "False image");
        } catch (IOException e) {
            fail("Failed to read bytes");
        }
    }

    @Test
    @DisplayName("Download image unsuccesfully by ID")
    public void downloadImageUnsuccessfully() {
        UUID id = UUID.randomUUID();
        when(imageDataRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ImageNotFoundException.class, () -> imageService.downloadImage(id));
    }
}
