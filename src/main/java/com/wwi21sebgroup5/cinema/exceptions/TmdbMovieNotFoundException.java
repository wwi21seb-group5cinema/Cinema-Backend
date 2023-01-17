package com.wwi21sebgroup5.cinema.exceptions;

public class TmdbMovieNotFoundException extends Exception {

    public TmdbMovieNotFoundException(int id) {
        super(String.format("Tmdb movie with the id %d not found!", id));
    }

}
