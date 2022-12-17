package com.wwi21sebgroup5.cinema.exceptions;

public class GenreDoesNotExistException extends Exception {
    public GenreDoesNotExistException(String name) {
        super(String.format("Genre with the name %s does not exists", name));
    }
}
