package com.wwi21sebgroup5.cinema.exceptions;

public class EmailAlreadyExistsException extends Exception {

    public EmailAlreadyExistsException(String email) {
        super(String.format("The email %s is already in use!", email));
    }

}
