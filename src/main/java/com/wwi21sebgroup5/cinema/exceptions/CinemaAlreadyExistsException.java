package com.wwi21sebgroup5.cinema.exceptions;

public class CinemaAlreadyExistsException extends Exception {

    public CinemaAlreadyExistsException(String plz, String cityName, String street, String houseNumber) {
        super(String.format("Cinema already exists at the location %s %s, %s %s",
                plz, cityName, street, houseNumber));
    }

}
