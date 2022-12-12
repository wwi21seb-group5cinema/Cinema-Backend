package com.wwi21sebgroup5.cinema.exceptions;

public class EmailNotFoundException extends Exception {

    public EmailNotFoundException(String email) {
        super(String.format("No user found with the email %s!", email));
    }

}
