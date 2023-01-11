package com.wwi21sebgroup5.cinema.exceptions;

public class TokenExpiredException extends Exception {

    public TokenExpiredException() {
        super("Token expired!");
    }

}
