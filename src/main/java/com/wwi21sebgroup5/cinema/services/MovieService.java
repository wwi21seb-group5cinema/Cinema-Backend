package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.enums.FSK;
import com.wwi21sebgroup5.cinema.exceptions.*;
import com.wwi21sebgroup5.cinema.repositories.ImageDataRepository;
import com.wwi21sebgroup5.cinema.repositories.MovieRepository;
import com.wwi21sebgroup5.cinema.requestObjects.DirectorRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.MovieRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.ProducerRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wwi21sebgroup5.cinema.helper.DateFormatter.DATE_FORMATTER;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private DirectorService directorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private ActsInService actsInService;

    @Autowired
    private ImageDataRepository imageDataRepository;


    /**
     * @param movieObject request object
     * @return the newly created movie object
     * @throws GenreDoesNotExistException thrown if the genre can´t be found
     * @throws FSKNotFoundException       thrown if the fsk can´t be found
     * @throws ActorNotFoundException     thrown if an actor can´t be found
     * @throws ImageNotFoundException     thrown if the imageDataObject can´t be found
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
        for (UUID actor : movieObject.getActors().keySet()) {
            Optional<Actor> a = actorService.findById(actor);
            if (a.isEmpty()) {
                throw new ActorNotFoundException(actor);
            }
            actors.add(a.get());
        }

        Optional<ImageData> foundImageData = imageDataRepository.findById(movieObject.getImage());
        if (foundImageData.isEmpty()) {
            throw new ImageNotFoundException(movieObject.getImage());
        }

        LocalDate start = LocalDate.parse(movieObject.getStart_date(), DATE_FORMATTER);
        LocalDate end = LocalDate.parse(movieObject.getEnd_date(), DATE_FORMATTER);

        Movie m = new Movie(
                foundProducer.get(),
                foundDirector.get(),
                FSK.getFSKFromInt(movieObject.getFsk()),
                foundGenre.get(),
                foundImageData.get(),
                movieObject.getName(),
                movieObject.getDescription(),
                movieObject.getRating(),
                movieObject.getLength(),
                start,
                end);

        movieRepository.save(m);

        for (Actor a : actors) {
            actsInService.save(new ActsIn(m, a, movieObject.getActors().get(a.getId())));
        }

        return m;
    }


    public List<Movie> findAll() {
        return movieRepository.findAll();

    }

    public Optional<Movie> findById(UUID id) {
        return movieRepository.findById(id);
    }


}
