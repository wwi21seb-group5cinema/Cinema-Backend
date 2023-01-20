package com.wwi21sebgroup5.cinema.exceptions;

public class SeatNotAvailableException extends Exception{
    public SeatNotAvailableException(int row, int place){
        super(String.format("The Seat on row %S and place %s is not available", row, place));
    }
}

