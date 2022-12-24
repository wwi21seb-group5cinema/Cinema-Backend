package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.exceptions.*;
import com.wwi21sebgroup5.cinema.repositories.ImageDataRepository;
import com.wwi21sebgroup5.cinema.repositories.MovieRepository;
import com.wwi21sebgroup5.cinema.requestObjects.DirectorRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.MovieRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.ProducerRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ProducerService producerService;
    @Autowired
    DirectorService directorService;
    @Autowired
    GenreService genreService;
    @Autowired
    ActorService actorService;

    @Autowired
    ActsInService actsInService;
    @Autowired
    private ImageDataRepository imageDataRepository;


    /**
     * @param movieObject
     * @return the newly created movie object
     * @throws GenreDoesNotExistException thrown if the genre can´t be found
     * @throws FSKNotFoundException       thrown if the fsk cant´t be found
     * @throws ActorNotFoundException     thrown if an actor can´t be found
     * @throws ImageNotFoundException     thrown if the imageDataobject can´t be found
     *                                    If there is no matching producer in the database, a new one is created.
     *                                    If there is no matching director in the database, a new one is created.
     *                                    For each actor a new entry is made to the ActsIn Entity, which connects actor and movie
     */
    public Movie add(MovieRequestObject movieObject)
            throws GenreDoesNotExistException, FSKNotFoundException, ActorNotFoundException, ImageNotFoundException {
        Optional<Producer> foundProducer = producerService.findByName(movieObject.getProducerName());
        if (foundProducer.isEmpty()) {
            try {
                foundProducer = Optional.of(producerService.add(new ProducerRequestObject(movieObject.getProducerName())));
            } catch (ProducerAlreadyExistsException e) {
                throw new InternalError();
            }
        }

        Optional<Director> foundDirector = directorService.findByNameAndFirstName(movieObject.getDirectorLastName(), movieObject.getDirectorFirstName());
        if (foundDirector.isEmpty()) {
            try {
                foundDirector = Optional.of(directorService.add(new DirectorRequestObject(movieObject.getDirectorLastName(), movieObject.getDirectorFirstName())));
            } catch (DirectorAlreadyExistsException e) {
                throw new InternalError();
            }
        }

        Optional<Genre> foundGenre = genreService.findByName(movieObject.getGenre());
        if (foundGenre.isEmpty()) {
            throw new GenreDoesNotExistException(movieObject.getGenre());
        }

        ArrayList<Actor> actors = new ArrayList<>();
        for (UUID id : movieObject.getActors()) {
            Optional<Actor> a = actorService.findById(id);
            if (a.isEmpty()) {
                throw new ActorNotFoundException(id);
            }
            actors.add(a.get());
        }

        Optional<ImageData> foundImageData = imageDataRepository.findById(movieObject.getImage());
        if (foundImageData.isEmpty()) {
            throw new ImageNotFoundException(movieObject.getImage());
        }

        Movie m = new Movie(
                foundProducer.get(),
                foundDirector.get(),
                FSK.getFSKFromInt(movieObject.getFsk()),
                foundGenre.get(),
                foundImageData.get(),
                movieObject.getName(),
                movieObject.getDescription(),
                movieObject.getStart_date(),
                movieObject.getEnd_date());
        movieRepository.save(m);

        for (Actor a : actors) {
            actsInService.save(m, a);
        }

        return m;
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();

    }
}
