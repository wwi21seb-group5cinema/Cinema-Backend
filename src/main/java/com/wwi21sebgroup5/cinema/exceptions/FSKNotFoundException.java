package com.wwi21sebgroup5.cinema.exceptions;

public class FSKNotFoundException extends Exception {
    public FSKNotFoundException(int value) {
        super(String.format("The FSK %s does not exist", value));
    }
}
