package com.wwi21sebgroup5.cinema.exceptions;
import java.util.UUID;

public class TicketNotFoundException extends Exception{
    public TicketNotFoundException(UUID id){
        super(String.format("Ticket with the Id %s does not exist", id));
    }
}
