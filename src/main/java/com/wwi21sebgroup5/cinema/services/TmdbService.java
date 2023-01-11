package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Movie;
import com.wwi21sebgroup5.cinema.requestObjects.TmdbMovieRequestObject;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TmdbService {

    private static final String LANGUAGE = "de-DE";
    private static final boolean INCLUDE_ADULT = false;
    private static final int PAGE = 1;
    private static final int SEARCH_YEAR = -1;

    @Autowired
    private TmdbApi tmdbApi;

    @Autowired
    private MovieService movieService;

    public Movie addMovie(TmdbMovieRequestObject requestObject) {
        return null;
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
        MovieDb movie = movies.getMovie(movieId, LANGUAGE);

        if (movie != null) {
            return Optional.of(movie);
        } else {
            return Optional.empty();
        }
    }
}
