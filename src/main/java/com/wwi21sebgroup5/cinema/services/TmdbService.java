package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.enums.FSK;
import com.wwi21sebgroup5.cinema.exceptions.*;
import com.wwi21sebgroup5.cinema.exceptions.TmdbInformationException.InformationType;
import com.wwi21sebgroup5.cinema.repositories.MovieRepository;
import com.wwi21sebgroup5.cinema.requestObjects.DirectorRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.ProducerRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.TmdbMovieRequestObject;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbMovies.MovieMethod;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.config.TmdbConfiguration;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.people.PersonCast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.wwi21sebgroup5.cinema.helper.DateFormatter.TMDB_DATE_FORMATTER;

@Service
public class TmdbService {

    private static final String LANGUAGE = "de-DE";
    private static final String GERMANY_COUNTRY_CODE = "DE";
    private static final String DIRECTOR = "Director";
    private static final String TRAILER = "Trailer";
    private static final String YOUTUBE = "YouTube";
    private static final boolean INCLUDE_ADULT = false;
    private static final int PAGE = 1;
    private static final int SEARCH_YEAR = -1;
    private static final String NAME_SPLIT_PATTERN = "\\s+";
    private static final String BACKDROP_SIZE = "original";
    private static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

    @Autowired
    private TmdbApi tmdbApi;

    @Autowired
    private TmdbConfiguration tmdbConfig;

    @Autowired
    private ActorService actorService;

    @Autowired
    private ActsInService actsInService;

    @Autowired
    private DirectorService directorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private MovieRepository movieRepository;

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
        String imageUrl = getImageUrlFromMovieDb(movieDb);
        String trailerUrl = getTrailerUrlFromMovieDb(movieDb);
        float rating = movieDb.getVoteAverage();
        String title = movieDb.getTitle();
        String description = movieDb.getOverview();
        int length = movieDb.getRuntime();
        LocalDate startDate = LocalDate.parse(movieDb.getReleaseDate(), TMDB_DATE_FORMATTER);

        Movie newMovie = new Movie(
                producer, director, fsk, genre, imageUrl, trailerUrl, rating, title, description, length, startDate,
                null // end date can be set later on, since it depends on the cinema
        );

        for (PersonCast personCast : movieDb.getCast()) {
            String[] names = personCast.getName().split(NAME_SPLIT_PATTERN);
            String firstName = names[0], lastName = names[names.length - 1];
            Optional<Actor> foundActor = actorService.findByNameAndFirstName(firstName, lastName);
            Actor actor = foundActor.orElseGet(() -> new Actor(firstName, lastName));
            ActsIn actsIn = new ActsIn(newMovie, actor, personCast.getCharacter());
            actsInService.save(actsIn);
        }

        return movieRepository.save(newMovie);
    }


    private String getTrailerUrlFromMovieDb(MovieDb movieDb) throws TmdbInformationException {
        String trailerUrl;

        try {
            String key = movieDb.getVideos().stream()
                    .filter(video -> video.getType().equals(TRAILER) && video.getSite().equals(YOUTUBE))
                    .findFirst().get().getKey();
            trailerUrl = String.format("%s%s", YOUTUBE_BASE_URL, key);
        } catch (NoSuchElementException e) {
            throw new TmdbInformationException(InformationType.TrailerUrl);
        }

        return trailerUrl;
    }

    private String getImageUrlFromMovieDb(MovieDb movieDb) {
        return String.format("%s%s%s", tmdbConfig.getBaseUrl(), BACKDROP_SIZE, movieDb.getBackdropPath());
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
                    .filter(release -> release.getCountry().equals(GERMANY_COUNTRY_CODE))
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
        Director director;

        try {
            String[] directorNames = movieDb.getCrew().stream()
                    .filter(personCrew -> personCrew.getJob().equals(DIRECTOR))
                    .findFirst().get().getName().split(NAME_SPLIT_PATTERN);
            String firstName = directorNames[0], lastName = directorNames[directorNames.length - 1];

            Optional<Director> foundDirector = directorService.findByNameAndFirstName(lastName, firstName);

            director = foundDirector.isPresent() ? foundDirector.get()
                    : directorService.add(new DirectorRequestObject(lastName, firstName));
        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            throw new TmdbInformationException(InformationType.Director);
        }

        return director;
    }

    private Producer getProducerFromMovieDb(MovieDb movieDb) throws ProducerAlreadyExistsException,
            TmdbInformationException {
        Producer producer;

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
