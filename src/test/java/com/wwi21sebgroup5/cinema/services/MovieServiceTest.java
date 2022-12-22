package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.exceptions.ActorNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.GenreDoesNotExistException;
import com.wwi21sebgroup5.cinema.exceptions.ImageNotFoundException;
import com.wwi21sebgroup5.cinema.repositories.ImageDataRepository;
import com.wwi21sebgroup5.cinema.repositories.MovieRepository;
import com.wwi21sebgroup5.cinema.requestObjects.DirectorRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.MovieRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.ProducerRequestObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    MovieRepository movieRepository;

    @Mock
    ProducerService producerService;

    @Mock
    GenreService genreService;

    @Mock
    ActorService actorservice;

    @Mock
    DirectorService directorService;

    @Mock
    ImageDataRepository imageDataRepository;

    @InjectMocks
    MovieService movieService;

    @Mock
    ActsInService actsInService;

    @Test
    @DisplayName("Get all movies succesfully")
    public void getAllMoviesTest() {
        try {
            Producer producer = new Producer("prod");
            Director director = new Director("director", "dir");
            Genre genre = new Genre(UUID.randomUUID(), "Action");
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData image = new ImageData("image/png", data);
            Movie firstMovie = new Movie(producer, director, FSK.SIX, genre, image, "film1",
                    "beschreibung", new Date(123), new Date(124));
            Movie secondMovie = new Movie(producer, director, FSK.SIX, genre, image, "film2",
                    "beschreibung2", new Date(123), new Date(124));
            List<Movie> expected = List.of(firstMovie, secondMovie);

            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(movieRepository.findAll()).thenReturn(expected);
            List<Movie> actual = movieService.findAll();
            assertEquals(expected, actual);
        } catch (IOException e) {
            fail("Failed to read bytes");
        }
    }

    @Test
    @DisplayName("Add a movie succesfully")
    public void addMovieTest() {
        try {
            Producer producer = new Producer("prod");
            Director director = new Director("director", "dir");
            Genre genre = new Genre(UUID.randomUUID(), "Action");
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData image = new ImageData("image/png", data);
            Movie firstMovie = new Movie(producer, director, FSK.SIX, genre, image, "film1",
                    "beschreibung", new Date(123), new Date(124));

            Actor actor = new Actor("Paul", "Bahde");
            actor.setId(UUID.randomUUID());
            UUID actorId = actor.getId();
            List<UUID> actors = List.of(actorId);

            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(producerService.findByName(producer.getName())).thenReturn(Optional.of(producer));

            when(directorService.findByNameAndFirstName(director.getName(), director.getFirstName())).thenReturn(Optional.of(director));
            when(genreService.findByName(genre.getName())).thenReturn(Optional.of(genre));
            when(actorservice.findById(actorId)).thenReturn(Optional.of(actor));
            when(imageDataRepository.findById(image.getId())).thenReturn(Optional.of(image));

            MovieRequestObject movieRequestObject = new MovieRequestObject(
                    producer.getName(), director.getFirstName(), director.getName(), actors, image.getId(), 6,
                    genre.getName(), "film1", "beschreibung", new Date(123), new Date(124));

            Movie actual = null;
            try {
                actual = movieService.add(movieRequestObject);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertEquals(firstMovie, actual);
            verify(actsInService, atLeast(1)).save(actual, actor);

        } catch (IOException e) {
            fail("Failed to read bytes");
        }
    }

    @Test
    @DisplayName("Add a movie failed Actor not found")
    public void addMovieFailedActorNotFound() {
        try {
            Producer producer = new Producer("prod");
            Director director = new Director("director", "dir");
            Genre genre = new Genre(UUID.randomUUID(), "Action");
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData image = new ImageData("image/png", data);
            Movie firstMovie = new Movie(producer, director, FSK.SIX, genre, image, "film1",
                    "beschreibung", new Date(123), new Date(124));

            Actor actor = new Actor("Paul", "Bahde");
            actor.setId(UUID.randomUUID());
            UUID actorId = actor.getId();
            List<UUID> actors = List.of(actorId);

            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(producerService.findByName(producer.getName())).thenReturn(Optional.of(producer));

            when(directorService.findByNameAndFirstName(director.getName(), director.getFirstName())).thenReturn(Optional.of(director));
            when(genreService.findByName(genre.getName())).thenReturn(Optional.of(genre));
            when(actorservice.findById(actorId)).thenReturn(Optional.empty());

            MovieRequestObject movieRequestObject = new MovieRequestObject(
                    producer.getName(), director.getFirstName(), director.getName(), actors, image.getId(), 6,
                    genre.getName(), "film1", "beschreibung", new Date(123), new Date(124));

            assertThrows(ActorNotFoundException.class, () -> movieService.add(movieRequestObject));
        } catch (IOException e) {
            fail("Failed to read bytes");
        }
    }

    @Test
    @DisplayName("Add a movie failed Genre not found")
    public void addMovieFailedGenreNotFound() {
        try {
            Producer producer = new Producer("prod");
            Director director = new Director("director", "dir");
            Genre genre = new Genre(UUID.randomUUID(), "Action");
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData image = new ImageData("image/png", data);
            Movie firstMovie = new Movie(producer, director, FSK.SIX, genre, image, "film1",
                    "beschreibung", new Date(123), new Date(124));

            Actor actor = new Actor("Paul", "Bahde");
            actor.setId(UUID.randomUUID());
            UUID actorId = actor.getId();
            List<UUID> actors = List.of(actorId);

            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(producerService.findByName(producer.getName())).thenReturn(Optional.of(producer));

            when(directorService.findByNameAndFirstName(director.getName(), director.getFirstName())).thenReturn(Optional.of(director));
            when(genreService.findByName(genre.getName())).thenReturn(Optional.empty());


            MovieRequestObject movieRequestObject = new MovieRequestObject(
                    producer.getName(), director.getFirstName(), director.getName(), actors, image.getId(), 6,
                    genre.getName(), "film1", "beschreibung", new Date(123), new Date(124));

            assertThrows(GenreDoesNotExistException.class, () -> movieService.add(movieRequestObject));
        } catch (IOException e) {
            fail("Failed to read bytes");
        }
    }

    @Test
    @DisplayName("Add a movie failed Image not found")
    public void addMovieFailedImageNotFound() {
        try {
            Producer producer = new Producer("prod");
            Director director = new Director("director", "dir");
            Genre genre = new Genre(UUID.randomUUID(), "Action");
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData image = new ImageData("image/png", data);
            Movie firstMovie = new Movie(producer, director, FSK.SIX, genre, image, "film1",
                    "beschreibung", new Date(123), new Date(124));

            Actor actor = new Actor("Paul", "Bahde");
            actor.setId(UUID.randomUUID());
            UUID actorId = actor.getId();
            List<UUID> actors = List.of(actorId);

            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(producerService.findByName(producer.getName())).thenReturn(Optional.of(producer));

            when(directorService.findByNameAndFirstName(director.getName(), director.getFirstName())).thenReturn(Optional.of(director));
            when(imageDataRepository.findById(image.getId())).thenReturn(Optional.empty());
            when(genreService.findByName(genre.getName())).thenReturn(Optional.of(genre));
            when(actorservice.findById(actorId)).thenReturn(Optional.of(actor));


            MovieRequestObject movieRequestObject = new MovieRequestObject(
                    producer.getName(), director.getFirstName(), director.getName(), actors, image.getId(), 6,
                    genre.getName(), "film1", "beschreibung", new Date(123), new Date(124));

            assertThrows(ImageNotFoundException.class, () -> movieService.add(movieRequestObject));
        } catch (IOException e) {
            fail("Failed to read bytes");
        }
    }

    @Test
    @DisplayName("Add a movie succesfully - create Producer and Director")
    public void addMovieWitheNewProducerAndDirectorTest() {
        try {
            Producer producer = new Producer("prod");
            Director director = new Director("director", "dir");
            Genre genre = new Genre(UUID.randomUUID(), "Action");
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData image = new ImageData("image/png", data);
            Movie firstMovie = new Movie(producer, director, FSK.SIX, genre, image, "film1",
                    "beschreibung", new Date(123), new Date(124));

            Actor actor = new Actor("Paul", "Bahde");
            actor.setId(UUID.randomUUID());
            UUID actorId = actor.getId();
            List<UUID> actors = List.of(actorId);

            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(producerService.findByName(producer.getName())).thenReturn(Optional.empty());
            

            MovieRequestObject movieRequestObject = new MovieRequestObject(
                    producer.getName(), director.getFirstName(), director.getName(), actors, image.getId(), 6,
                    genre.getName(), "film1", "beschreibung", new Date(123), new Date(124));


            try {
                movieService.add(movieRequestObject);
                verify(producerService, atLeast(1)).add(new ProducerRequestObject(producer.getName()));
                verify(directorService, atLeast(1)).add(new DirectorRequestObject(director.getName(), director.getFirstName()));
            } catch (Exception e) {
                fail("Failed");
            }
        } catch (IOException e) {
            fail("Failed to read bytes");
        }
    }


}
