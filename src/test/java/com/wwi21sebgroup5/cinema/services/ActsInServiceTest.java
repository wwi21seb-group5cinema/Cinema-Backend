package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.enums.FSK;
import com.wwi21sebgroup5.cinema.exceptions.ActorNotFoundException;
import com.wwi21sebgroup5.cinema.repositories.ActsInRepository;
import com.wwi21sebgroup5.cinema.repositories.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActsInServiceTest {

    @Mock
    ActsInRepository actsInRepository;

    @Mock
    MovieRepository movieRepository;

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
            ImageData image = new ImageData("image/png", data, false);
            Movie firstMovie = new Movie(producer, director, FSK.SIX, genre, image, "film1",
                    "beschreibung", 1.2F, 193, LocalDate.of(2023, 12, 4), LocalDate.of(2023, 12, 6));
            ActsIn actsIn = new ActsIn(firstMovie, actor, "");
            actsInService.save(firstMovie, actor);
            verify(actsInRepository, times(1)).save(actsIn);
        } catch (Exception e) {
            fail("Failed to read bytes");
        }
    }

    @Test
    @DisplayName("Test get Actors By Movie ID")
    public void testGetActorsByMovieIDSuccessfully() throws IOException, ActorNotFoundException {
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
        firstMovie.setId(id);
        when(actsInRepository.findByMovie(firstMovie)).thenReturn(expectedActors);
        when(movieRepository.findById(id)).thenReturn(Optional.of(firstMovie));
        List<ActsIn> actual = actsInService.findAllByMovie(id);
        assertEquals(actual, expectedActors);

    }

    @Test
    @DisplayName("Test get Actors By Movie ID Unsuccessfully")
    public void testGetActorsByMovieIDUnsuccessfully() throws IOException {
        Producer producer = new Producer("prod");
        Director director = new Director("director", "dir");
        Genre genre = new Genre(UUID.randomUUID(), "Action");
        File fi = new File("src/test/resources/beispielbild2.png");
        byte[] data = Files.readAllBytes(fi.toPath());
        ImageData image = new ImageData("image/png", data, false);
        Movie firstMovie = new Movie(producer, director, FSK.SIX, genre, image, "film1",
                "beschreibung", 1.2F, 193, LocalDate.of(2023, 12, 4), LocalDate.of(2023, 12, 6));

        UUID id = UUID.randomUUID();
        firstMovie.setId(id);
        when(actsInRepository.findByMovie(firstMovie)).thenReturn(new ArrayList<>());
        when(movieRepository.findById(id)).thenReturn(Optional.of(firstMovie));

        assertThrows(ActorNotFoundException.class, () -> actsInService.findAllByMovie(id));

    }
}
