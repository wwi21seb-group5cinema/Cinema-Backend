package com.wwi21sebgroup5.cinema.exceptions;

public class UserNotEnabledException extends Exception {

    public UserNotEnabledException(String userName) {
        super(String.format("User with the username %s is not enabled yet, please check your mails!", userName));
    }

}
