package com.wwi21sebgroup5.cinema.exceptions;

public class PasswordsNotMatchingException extends Exception {

    public PasswordsNotMatchingException(String email) {
        super(String.format("Passwords for user with the email %s don't match!", email));
    }

}
