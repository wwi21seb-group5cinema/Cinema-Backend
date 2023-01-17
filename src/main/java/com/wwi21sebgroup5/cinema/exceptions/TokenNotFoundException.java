package com.wwi21sebgroup5.cinema.exceptions;

public class TokenNotFoundException extends Exception {

    public TokenNotFoundException(String token) {
        super(String.format("Token with the value %s not found", token));
    }

}
