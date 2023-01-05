package com.wwi21sebgroup5.cinema.exceptions;

import com.wwi21sebgroup5.cinema.entities.Event;
import com.wwi21sebgroup5.cinema.entities.Seat;

public class TicketAlreadyExistsException extends Exception{
    public TicketAlreadyExistsException(Event event, Seat seat){
        super(String.format("Ticket with the Event %s and the Seat %s does already exist", event, seat));
    }
}
