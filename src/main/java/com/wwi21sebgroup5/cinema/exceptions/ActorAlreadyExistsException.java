package com.wwi21sebgroup5.cinema.exceptions;

import java.sql.Date;

public class ActorAlreadyExistsException extends Exception {
    public ActorAlreadyExistsException(String name, String firstName, Date birthdate) {
        super(String.format("Actor with the name %s, the firstname %s and the birthdate %s already exists", name, firstName, birthdate));
    }
}
