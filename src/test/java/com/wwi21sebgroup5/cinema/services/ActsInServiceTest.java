package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.enums.FSK;
import com.wwi21sebgroup5.cinema.repositories.ActsInRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.UUID;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ActsInServiceTest {

    @Mock
    ActsInRepository actsInRepository;

    @InjectMocks
    ActsInService actsInService;

    @Test
    @DisplayName("Test getting actor by Id")
    public void testSave() {
        try {
            Actor actor = new Actor("Paul", "Bahde");
            Producer producer = new Producer("prod");
            Director director = new Director("director", "dir");
            Genre genre = new Genre(UUID.randomUUID(), "Action");
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData image = new ImageData("image/png", data);
            Movie firstMovie = new Movie(producer, director, FSK.SIX, genre, image, "film1",
                    "beschreibung", 1.2F, 193, LocalDate.of(2023, 12, 4), LocalDate.of(2023, 12, 6));
            ActsIn actsIn = new ActsIn(firstMovie, actor, "");
            actsInService.save(firstMovie, actor);
            verify(actsInRepository, times(1)).save(actsIn);
        } catch (Exception e) {
            fail("Failed to read bytes");
        }
    }
}
