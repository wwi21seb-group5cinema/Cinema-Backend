package com.wwi21sebgroup5.cinema.exceptions;

import java.util.UUID;

public class MovieNotFoundException extends Exception {

    public MovieNotFoundException(UUID id) {
        super(String.format("Movie with the id %s not found", id));
    }

}
