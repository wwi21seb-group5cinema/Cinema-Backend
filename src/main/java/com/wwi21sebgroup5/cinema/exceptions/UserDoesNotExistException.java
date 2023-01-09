package com.wwi21sebgroup5.cinema.exceptions;
import java.util.UUID;

public class UserDoesNotExistException extends Exception{
    public UserDoesNotExistException(UUID id){
        super(String.format("User with the id %s does not exist", id));
    }
}
