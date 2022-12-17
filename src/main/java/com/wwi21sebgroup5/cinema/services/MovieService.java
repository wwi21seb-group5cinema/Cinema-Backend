package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.exceptions.DirectorAlreadyExistsException;
import com.wwi21sebgroup5.cinema.exceptions.FSKNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.GenreDoesNotExistException;
import com.wwi21sebgroup5.cinema.exceptions.ProducerAlreadyExistsException;
import com.wwi21sebgroup5.cinema.repositories.MovieRepository;
import com.wwi21sebgroup5.cinema.requestObjects.DirectorRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.MovieRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.ProducerRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Movie add(MovieRequestObject movieObject) throws GenreDoesNotExistException, FSKNotFoundException {
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


        Movie m = new Movie(
                foundProducer.get(),
                foundDirector.get(),
                FSK.getFSKFromInt(movieObject.getFsk()),
                foundGenre.get(),
                movieObject.getName(),
                movieObject.getDescription(),
                movieObject.getStart_date(),
                movieObject.getEnd_date());
        movieRepository.save(m);
        return m;
    }


    public List<Movie> findAll() {
        return movieRepository.findAll();

    }
}
