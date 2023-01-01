package com.wwi21sebgroup5.cinema.exceptions;

import java.util.UUID;

public class CinemaNotFoundException extends Exception {

    public CinemaNotFoundException(UUID id) {
        super(String.format("Cinema with id %s not found", id));
    }

}
