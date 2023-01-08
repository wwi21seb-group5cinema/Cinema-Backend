package com.wwi21sebgroup5.cinema.exceptions;
import java.util.UUID;

public class SeatDoesNotExistException extends Exception{
    public SeatDoesNotExistException(int row, int place){super(String.format("The Seat on row %s and place %s does not exist", row, place));}
}
