package com.wwi21sebgroup5.cinema.exceptions;

import java.util.UUID;

public class CinemaHallNotFoundException extends Exception {

    public CinemaHallNotFoundException(UUID id) {
        super(String.format("Cinemahall with id %s not found", id.toString()));
    }
}
