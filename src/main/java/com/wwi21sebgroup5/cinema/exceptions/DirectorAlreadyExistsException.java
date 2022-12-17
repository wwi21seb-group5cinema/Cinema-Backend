package com.wwi21sebgroup5.cinema.exceptions;

public class DirectorAlreadyExistsException extends Exception {
    public DirectorAlreadyExistsException(String name, String firstname) {
        super(String.format("Director with the name %s %s already exists", firstname, name));
    }
}
