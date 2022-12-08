package com.wwi21sebgroup5.cinema.exceptions;

public class PasswordsNotMatchingException extends Exception {

    public PasswordsNotMatchingException(String username) {
        super(String.format("Passwords for user %s don't match!", username));
    }

}
