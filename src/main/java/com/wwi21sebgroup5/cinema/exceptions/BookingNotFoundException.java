package com.wwi21sebgroup5.cinema.exceptions;
import java.util.UUID;
public class BookingNotFoundException extends Exception{
    public BookingNotFoundException(UUID id){
        super(String.format("Booking with the Id %s does not exist", id));
    }
}
