package com.wwi21sebgroup5.cinema.exceptions;

public class SeatNotAvailableException extends Exception{
    public SeatNotAvailableException(int row, int place){
        super(String.format("Seat on row %S and place %s does not exist", row, place));
    }
}

