package com.wwi21sebgroup5.cinema.exceptions;

public class ActorAlreadyExistsException extends Exception {
    public ActorAlreadyExistsException(String name, String firstName) {
        super(String.format("Actor with the name %s and the firstname %s already exists", name, firstName));
    }
}
