package com.wwi21sebgroup5.cinema.exceptions;

public class TmdbInformationException extends Exception {

    public TmdbInformationException(InformationType informationType) {
        super(String.format("Information %s couldn't be parsed from Tmdb-Movie!", informationType.name()));
    }

    public enum InformationType {
        Producer, Director, FSK, Genre, TrailerUrl, StartDate
    }

}
