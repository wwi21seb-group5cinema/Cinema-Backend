package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Director;
import com.wwi21sebgroup5.cinema.entities.Genre;
import com.wwi21sebgroup5.cinema.entities.Movie;
import com.wwi21sebgroup5.cinema.entities.Producer;
import com.wwi21sebgroup5.cinema.enums.FSK;
import com.wwi21sebgroup5.cinema.exceptions.*;
import com.wwi21sebgroup5.cinema.exceptions.TmdbInformationException.InformationType;
import com.wwi21sebgroup5.cinema.requestObjects.DirectorRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.ProducerRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.TmdbMovieRequestObject;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbMovies.MovieMethod;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TmdbService {

    private static final String LANGUAGE = "de-DE";
    private static final boolean INCLUDE_ADULT = false;
    private static final int PAGE = 1;
    private static final int SEARCH_YEAR = -1;

    private static final String NAME_SPLIT_PATTERN = "\\s+";

    @Autowired
    private TmdbApi tmdbApi;

    @Autowired
    private DirectorService directorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private MovieService movieService;

    public Movie addMovie(TmdbMovieRequestObject requestObject) throws TmdbMovieNotFoundException,
            ProducerAlreadyExistsException, DirectorAlreadyExistsException, FSKNotFoundException,
            TmdbInformationException {
        Optional<MovieDb> foundMovieDb = this.getMovieById(requestObject.getTmdbMovieId());
        MovieDb movieDb;

        if (foundMovieDb.isEmpty()) {
            throw new TmdbMovieNotFoundException(requestObject.getTmdbMovieId());
        } else {
            movieDb = foundMovieDb.get();
        }

        Producer producer = getProducerFromMovieDb(movieDb);
        Director director = getDirectorFromMovieDB(movieDb);
        FSK fsk = getFSKfromMovieDb(movieDb);
        Genre genre = getGenreFromMovieDb(movieDb);

        return null;
    }

    private Genre getGenreFromMovieDb(MovieDb movieDb) throws TmdbInformationException {
        Genre genre;

        try {
            String genreName = movieDb.getGenres().get(0).getName();
            Optional<Genre> foundGenre = genreService.findByName(genreName);

            if (foundGenre.isEmpty()) {
                throw new GenreDoesNotExistException(genreName);
            } else {
                genre = foundGenre.get();
            }
        } catch (IndexOutOfBoundsException | GenreDoesNotExistException e) {
            throw new TmdbInformationException(InformationType.Genre);
        }

        return genre;
    }

    private FSK getFSKfromMovieDb(MovieDb movieDb) throws FSKNotFoundException, TmdbInformationException {
        FSK fsk;
        String certification = "-1";

        try {
            certification = movieDb.getReleases().stream()
                    .filter(release -> release.getCountry().equals("DE"))
                    .findFirst().get().getReleaseDates().get(0).getCertification();

            fsk = FSK.getFSKFromInt(Integer.parseInt(certification));
        } catch (FSKNotFoundException e) {
            throw new FSKNotFoundException(Integer.parseInt(certification));
        } catch (NoSuchElementException e) {
            throw new TmdbInformationException(InformationType.FSK);
        }

        return fsk;
    }

    private Director getDirectorFromMovieDB(MovieDb movieDb) throws DirectorAlreadyExistsException,
            TmdbInformationException {
        Director director = null;

        try {
            String[] directorNames = movieDb.getCrew().stream()
                    .filter(personCrew -> personCrew.getJob().equals("Director"))
                    .findFirst().get().getName().split(NAME_SPLIT_PATTERN);
            String firstName = directorNames[0], lastName = directorNames[directorNames.length - 1];

            Optional<Director> foundDirector = directorService.findByNameAndFirstName(lastName, firstName);

            director = foundDirector.isPresent() ? foundDirector.get()
                    : directorService.add(new DirectorRequestObject(lastName, firstName));
        } catch (NoSuchElementException e) {
            throw new TmdbInformationException(InformationType.Director);
        }

        return director;
    }

    private Producer getProducerFromMovieDb(MovieDb movieDb) throws ProducerAlreadyExistsException,
            TmdbInformationException {
        Producer producer = null;

        try {
            String productionCompanyName = movieDb.getProductionCompanies().get(0).getName();
            Optional<Producer> foundProducer = producerService.findByName(productionCompanyName);

            producer = foundProducer.isPresent() ? foundProducer.get()
                    : producerService.add(new ProducerRequestObject(productionCompanyName));
        } catch (IndexOutOfBoundsException e) {
            throw new TmdbInformationException(InformationType.Producer);
        }

        return producer;
    }

    public List<MovieDb> getMoviesByName(String name) {
        TmdbSearch search = new TmdbSearch(tmdbApi);
        MovieResultsPage resultsPage = search.searchMovie(
                name, SEARCH_YEAR, LANGUAGE, INCLUDE_ADULT, PAGE
        );
        List<MovieDb> resultList = resultsPage.getResults();

        for (int i = PAGE + 1; i <= resultsPage.getTotalPages(); i++) {
            resultsPage = search.searchMovie(name, SEARCH_YEAR, LANGUAGE, INCLUDE_ADULT, i);
            resultList.addAll(resultsPage.getResults());
        }

        return resultList;
    }

    public Optional<MovieDb> getMovieById(int movieId) {
        TmdbMovies movies = new TmdbMovies(tmdbApi);
        MovieDb movie = movies.getMovie(movieId, LANGUAGE,
                MovieMethod.videos, MovieMethod.credits, MovieMethod.release_dates);

        if (movie != null) {
            return Optional.of(movie);
        } else {
            return Optional.empty();
        }
    }
}
