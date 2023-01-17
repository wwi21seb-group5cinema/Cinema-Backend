package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.enums.FSK;
import com.wwi21sebgroup5.cinema.exceptions.*;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
            ImageData image = new ImageData("image/png", data, false);
            Movie firstMovie = new Movie(producer, director, FSK.SIX, genre, image, "film1",
                    "beschreibung", 3.2F, 62, LocalDate.of(2023, 12, 4), LocalDate.of(2023, 12, 6));
            Movie secondMovie = new Movie(producer, director, FSK.SIX, genre, image, "film2",
                    "beschreibung2", 5.1F, 94, LocalDate.of(2023, 12, 4), LocalDate.of(2023, 12, 6));
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
    public void addMovieTest() throws GenreDoesNotExistException, FSKNotFoundException, ActorNotFoundException, ImageNotFoundException {
        try {
            Producer producer = new Producer("prod");
            Director director = new Director("director", "dir");
            Genre genre = new Genre(UUID.randomUUID(), "Action");
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData image = new ImageData("image/png", data, false);
            Movie firstMovie = new Movie(producer, director, FSK.SIX, genre, image, "film1",
                    "beschreibung", 6.2F, 102, LocalDate.of(2023, 1, 8), LocalDate.of(2023, 4, 8));

            Actor actor = new Actor("Paul", "Bahde");
            actor.setId(UUID.randomUUID());
            Map<UUID, String> actorList = Map.of(actor.getId(), "TestName");

            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(producerService.findByName(producer.getName())).thenReturn(Optional.of(producer));
            when(directorService.findByNameAndFirstName(director.getName(), director.getFirstName())).thenReturn(Optional.of(director));
            when(genreService.findByName(genre.getName())).thenReturn(Optional.of(genre));
            when(actorservice.findById(actor.getId())).thenReturn(Optional.of(actor));
            when(imageDataRepository.findById(image.getId())).thenReturn(Optional.of(image));

            MovieRequestObject movieRequestObject = new MovieRequestObject(
                    producer.getName(), director.getFirstName(), director.getName(), actorList, image.getId(), 6,
                    genre.getName(), "film1", "beschreibung", 6.2F, 102, "08-01-2023", "08-04-2023");

            Movie actual = null;
            actual = movieService.add(movieRequestObject);
            assertEquals(firstMovie, actual);
            verify(actsInService, times(1)).save(new ActsIn(actual, actor, "TestName"));

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
            ImageData image = new ImageData("image/png", data, false);
            Actor actor = new Actor("Paul", "Bahde");
            actor.setId(UUID.randomUUID());
            Map<UUID, String> actorList = Map.of(actor.getId(), "TestName");

            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(producerService.findByName(producer.getName())).thenReturn(Optional.of(producer));
            when(directorService.findByNameAndFirstName(director.getName(), director.getFirstName())).thenReturn(Optional.of(director));
            when(genreService.findByName(genre.getName())).thenReturn(Optional.of(genre));
            when(actorservice.findById(actor.getId())).thenReturn(Optional.empty());

            MovieRequestObject movieRequestObject = new MovieRequestObject(
                    producer.getName(), director.getFirstName(), director.getName(), actorList, image.getId(), 6,
                    genre.getName(), "film1", "beschreibung", 3.1F, 102, "08-01-2023", "08-04-2023");

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
            ImageData image = new ImageData("image/png", data, false);
            Actor actor = new Actor("Paul", "Bahde");
            actor.setId(UUID.randomUUID());
            Map<UUID, String> actorList = Map.of(actor.getId(), "TestName");

            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(producerService.findByName(producer.getName())).thenReturn(Optional.of(producer));
            when(directorService.findByNameAndFirstName(director.getName(), director.getFirstName())).thenReturn(Optional.of(director));
            when(genreService.findByName(genre.getName())).thenReturn(Optional.empty());

            MovieRequestObject movieRequestObject = new MovieRequestObject(
                    producer.getName(), director.getFirstName(), director.getName(), actorList, image.getId(), 6,
                    genre.getName(), "film1", "beschreibung", 3.1F, 102, "08-01-2023", "08-04-2023");

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
            ImageData image = new ImageData("image/png", data, false);
            Actor actor = new Actor("Paul", "Bahde");
            actor.setId(UUID.randomUUID());
            Map<UUID, String> actorList = Map.of(actor.getId(), "TestName");
            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(producerService.findByName(producer.getName())).thenReturn(Optional.of(producer));
            when(directorService.findByNameAndFirstName(director.getName(), director.getFirstName())).thenReturn(Optional.of(director));
            when(imageDataRepository.findById(image.getId())).thenReturn(Optional.empty());
            when(genreService.findByName(genre.getName())).thenReturn(Optional.of(genre));
            when(actorservice.findById(actor.getId())).thenReturn(Optional.of(actor));

            MovieRequestObject movieRequestObject = new MovieRequestObject(
                    producer.getName(), director.getFirstName(), director.getName(), actorList, image.getId(), 6,
                    genre.getName(), "film1", "beschreibung", 3.1F, 102, "08-01-2023", "08-04-2023");

            assertThrows(ImageNotFoundException.class, () -> movieService.add(movieRequestObject));
        } catch (IOException e) {
            fail("Failed to read bytes");
        }
    }

    @Test
    @DisplayName("Add a movie succesfully - create Director")
    public void addMovieWithNewDirectorTest() throws DirectorAlreadyExistsException, GenreDoesNotExistException, FSKNotFoundException, ActorNotFoundException, ImageNotFoundException {
        try {
            Producer producer = new Producer("prod");
            Director director = new Director("director", "dir");
            Genre genre = new Genre(UUID.randomUUID(), "Action");
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData image = new ImageData("image/png", data, false);
            Actor actor = new Actor("Paul", "Bahde");
            actor.setId(UUID.randomUUID());
            Map<UUID, String> actorList = Map.of(actor.getId(), "TestName");

            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(producerService.findByName(producer.getName())).thenReturn(Optional.of(producer));
            when(directorService.findByNameAndFirstName(director.getName(), director.getFirstName())).thenReturn(Optional.empty());
            when(imageDataRepository.findById(image.getId())).thenReturn(Optional.of(image));
            when(genreService.findByName(genre.getName())).thenReturn(Optional.of(genre));
            when(actorservice.findById(actor.getId())).thenReturn(Optional.of(actor));
            when(directorService.add(new DirectorRequestObject(director.getName(), director.getFirstName()))).thenReturn(director);
            MovieRequestObject movieRequestObject = new MovieRequestObject(
                    producer.getName(), director.getFirstName(), director.getName(), actorList, image.getId(), 6,
                    genre.getName(), "film1", "beschreibung", 3.1F, 102, "08-01-2023", "08-04-2023");

            movieService.add(movieRequestObject);
            verify(directorService, times(1)).add(new DirectorRequestObject(director.getName(), director.getFirstName()));

        } catch (IOException e) {
            fail("Failed to read bytes");
        }
    }

    @Test
    @DisplayName("Add a movie succesfully - create Producer")
    public void addMovieWithNewProducerTest() throws ProducerAlreadyExistsException, GenreDoesNotExistException, FSKNotFoundException, ActorNotFoundException, ImageNotFoundException {
        try {
            Producer producer = new Producer("prod");
            Director director = new Director("director", "dir");
            Genre genre = new Genre(UUID.randomUUID(), "Action");
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData image = new ImageData("image/png", data, false);
            Actor actor = new Actor("Paul", "Bahde");
            actor.setId(UUID.randomUUID());
            Map<UUID, String> actorList = Map.of(actor.getId(), "TestName");

            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(producerService.findByName(producer.getName())).thenReturn(Optional.empty());
            when(directorService.findByNameAndFirstName(director.getName(), director.getFirstName())).thenReturn(Optional.of(director));
            when(imageDataRepository.findById(image.getId())).thenReturn(Optional.of(image));
            when(genreService.findByName(genre.getName())).thenReturn(Optional.of(genre));
            when(actorservice.findById(actor.getId())).thenReturn(Optional.of(actor));
            when(producerService.add(new ProducerRequestObject(producer.getName()))).thenReturn(producer);
            MovieRequestObject movieRequestObject = new MovieRequestObject(
                    producer.getName(), director.getFirstName(), director.getName(), actorList, image.getId(), 6,
                    genre.getName(), "film1", "beschreibung", 3.1F, 102, "08-01-2023", "08-04-2023");

            movieService.add(movieRequestObject);
            verify(producerService, times(1)).add(new ProducerRequestObject(producer.getName()));

        } catch (IOException e) {
            fail("Failed to read bytes");
        }
    }

    @Test
    @DisplayName("Add a movie unsuccesfully - InternalError-Producer")
    public void addMovieWithNewProducerTestError() {
        try {
            Producer producer = new Producer("prod");
            Director director = new Director("director", "dir");
            Genre genre = new Genre(UUID.randomUUID(), "Action");
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData image = new ImageData("image/png", data, false);
            Actor actor = new Actor("Paul", "Bahde");
            actor.setId(UUID.randomUUID());
            Map<UUID, String> actorList = Map.of(actor.getId(), "TestName");

            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(producerService.findByName(producer.getName())).thenReturn(Optional.empty());
            try {
                when(producerService.add(new ProducerRequestObject(producer.getName()))).thenThrow(new ProducerAlreadyExistsException(producer.getName()));
            } catch (ProducerAlreadyExistsException e) {
                fail("Failed");
            }
            MovieRequestObject movieRequestObject = new MovieRequestObject(
                    producer.getName(), director.getFirstName(), director.getName(), actorList, image.getId(), 6,
                    genre.getName(), "film1", "beschreibung", 3.1F, 102, "08-01-2023", "08-04-2023");
            assertThrows(InternalError.class, () -> movieService.add(movieRequestObject));

        } catch (IOException e) {
            fail("Failed to read bytes");
        }
    }

    @Test
    @DisplayName("Add a movie unsuccesfully - Internal Error - Director")
    public void addMovieWithNewDirectorTestError() {
        try {
            Producer producer = new Producer("prod");
            Director director = new Director("director", "dir");
            Genre genre = new Genre(UUID.randomUUID(), "Action");
            File fi = new File("src/test/resources/beispielbild2.png");
            byte[] data = Files.readAllBytes(fi.toPath());
            ImageData image = new ImageData("image/png", data, false);
            Actor actor = new Actor("Paul", "Bahde");
            actor.setId(UUID.randomUUID());
            Map<UUID, String> actorList = Map.of(actor.getId(), "TestName");

            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(producerService.findByName(producer.getName())).thenReturn(Optional.of(producer));
            when(directorService.findByNameAndFirstName(director.getName(), director.getFirstName())).thenReturn(Optional.empty());
            try {
                when(directorService.add(new DirectorRequestObject(director.getName(), director.getFirstName()))).thenThrow(new DirectorAlreadyExistsException(director.getName(), director.getFirstName()));
            } catch (DirectorAlreadyExistsException e) {
                fail("Failed");
            }
            MovieRequestObject movieRequestObject = new MovieRequestObject(
                    producer.getName(), director.getFirstName(), director.getName(), actorList, image.getId(), 6,
                    genre.getName(), "film1", "beschreibung", 3.1F, 102, "08-01-2023", "08-04-2023");
            assertThrows(InternalError.class, () -> movieService.add(movieRequestObject));

        } catch (IOException e) {
            fail("Failed to read bytes");
        }
    }

    @Test
    @DisplayName("Test get movie by id")
    public void testGetMovieById() {
        UUID id = UUID.randomUUID();
        Movie movie = new Movie();
        movie.setId(id);

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        assertEquals(movie, movieService.findById(id).get());
    }

}
