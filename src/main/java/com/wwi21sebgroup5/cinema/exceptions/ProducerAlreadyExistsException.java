package com.wwi21sebgroup5.cinema.exceptions;

public class ProducerAlreadyExistsException extends Exception {
    public ProducerAlreadyExistsException(String name) {
        super(String.format("Producer with the name %s already exists", name));
    }
}
