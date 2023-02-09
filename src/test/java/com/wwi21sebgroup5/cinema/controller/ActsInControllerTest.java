package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.enums.FSK;
import com.wwi21sebgroup5.cinema.exceptions.ActorNotFoundException;
import com.wwi21sebgroup5.cinema.services.ActsInService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class ActsInControllerTest {

    @Mock
    ActsInService actsInService;

    @InjectMocks
    ActsInController actsInController;

    @Test
    @DisplayName("Test successfully getting all actors")
    public void testGetAllActorsSuccessful() throws IOException {
        Actor firstActor = new Actor("Kevin", "Rieger");
        Actor secondActor = new Actor("Nico", "Niebisch");
        Producer producer = new Producer("prod");
        Director director = new Director("director", "dir");
        Genre genre = new Genre(UUID.randomUUID(), "Action");
        File fi = new File("src/test/resources/beispielbild2.png");
        byte[] data = Files.readAllBytes(fi.toPath());
        ImageData image = new ImageData("image/png", data, false);
        Movie firstMovie = new Movie(producer, director, FSK.SIX, genre, image, "film1",
                "beschreibung", 1.2F, 193, LocalDate.of(2023, 12, 4), LocalDate.of(2023, 12, 6));
        ActsIn firstActsIn = new ActsIn(firstMovie, firstActor, "Name");
        ActsIn secondActsIn = new ActsIn(firstMovie, secondActor, "Name2");

        List<ActsIn> expectedActors = List.of(firstActsIn, secondActsIn);
        UUID id = UUID.randomUUID();
        try {
            when(actsInService.findAllByMovie(id)).thenReturn(expectedActors);
        } catch (ActorNotFoundException e) {
            fail("Failed");
        }

        ResponseEntity<List<ActsIn>> response = actsInController.getActorsByMovie(id);

        assertAll(
                "Validating correct response from controller...",
                () -> assertEquals(response.getBody(), expectedActors),
                () -> assertEquals(response.getStatusCode(), HttpStatus.OK)
        );
    }

    @Test
    @DisplayName("Test unsuccessfully getting all actors")
    public void testGetAllActorsUnsuccessful() throws ActorNotFoundException {
        UUID id = UUID.randomUUID();
        Exception e = new ActorNotFoundException();
        when(actsInService.findAllByMovie(id)).thenThrow(e);
        ResponseEntity<List<ActsIn>> response = actsInController.getActorsByMovie(id);

        assertAll(
                "Validating correct response from controller...",
                () -> assertFalse(response.hasBody()),
                () -> assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND)
        );
    }
}
