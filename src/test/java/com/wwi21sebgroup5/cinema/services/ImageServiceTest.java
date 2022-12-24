package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.ImageData;
import com.wwi21sebgroup5.cinema.exceptions.ImageNotFoundException;
import com.wwi21sebgroup5.cinema.repositories.ImageDataRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @Mock
    ImageDataRepository imageDataRepository;
    @InjectMocks
    ImageService imageService;

    @Test
    @DisplayName("Upload image succesfully")
    public void uploadImageSuccessfully() {
        try {
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            MultipartFile multipartFile = new MockMultipartFile("name",
                    "originalFileName", "image/png", data);
            ImageData expected = new ImageData("image/png", data);
            when(imageDataRepository.save(expected)).thenReturn(expected);
            ImageData actual = imageService.uploadImage(multipartFile);
            assertEquals(expected.getImageData(), actual.getImageData(), "False data");
            assertEquals(expected.getType(), actual.getType(), "False type");
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
            ImageData expected = new ImageData("image/png", data);
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
