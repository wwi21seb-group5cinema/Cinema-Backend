package com.wwi21sebgroup5.cinema.exceptions;

import java.util.UUID;

public class ActorNotFoundException extends Exception {

    public ActorNotFoundException(UUID id) {
        super(String.format("Actor with the ID %s was not found", id));
    }

    public ActorNotFoundException(String name, String firstName) {
        super(String.format("Actor with the name %s and the firstname %s was not found", name, firstName));
    }

}
