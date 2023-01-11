package com.wwi21sebgroup5.cinema.exceptions;

public class TokenAlreadyConfirmedException extends Exception {

    public TokenAlreadyConfirmedException() {
        super("Token was already confirmed!");
    }

}
