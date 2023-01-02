package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Ticket;
import com.wwi21sebgroup5.cinema.repositories.TicketRepository;
import com.wwi21sebgroup5.cinema.requestObjects.TicketRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket saveTicket(TicketRequestObject ticketRequestObject){
        Ticket ticket = new Ticket(
                ticketRequestObject.getEvent(),
                ticketRequestObject.getSeat()
        );
        ticketRepository.save(ticket);
        return ticket;
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }
}
