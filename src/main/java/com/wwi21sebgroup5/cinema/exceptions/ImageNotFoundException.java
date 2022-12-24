package com.wwi21sebgroup5.cinema.exceptions;

import java.util.UUID;

public class ImageNotFoundException extends Exception {

    public ImageNotFoundException(UUID id) {
        super(String.format("Image with the ID %s was not found", id));
    }

}
