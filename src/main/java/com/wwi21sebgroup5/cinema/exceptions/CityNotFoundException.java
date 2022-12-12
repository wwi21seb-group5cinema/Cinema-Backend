package com.wwi21sebgroup5.cinema.exceptions;

public class CityNotFoundException extends Exception {

    public CityNotFoundException(String plz, String cityName) {
        super(String.format("City with PLZ: %s and NAME: %s not found!", plz, cityName));
    }
}
