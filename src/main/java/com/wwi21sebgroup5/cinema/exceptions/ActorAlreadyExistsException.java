package com.wwi21sebgroup5.cinema.exceptions;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

public class ActorAlreadyExistsException extends Exception {
    public ActorAlreadyExistsException(String name, @NotNull @NotEmpty String firstName, @NotNull @NotEmpty Date birthdate) {
        super(String.format("Actor with the name %s, the firstname %s and the birthdate %s already exists", name, firstName, birthdate));
    }
}
