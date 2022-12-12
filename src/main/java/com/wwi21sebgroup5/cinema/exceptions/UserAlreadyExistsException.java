package com.wwi21sebgroup5.cinema.exceptions;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String userName) {
        super(String.format("User with the name %s already exists", userName));
    }

}
