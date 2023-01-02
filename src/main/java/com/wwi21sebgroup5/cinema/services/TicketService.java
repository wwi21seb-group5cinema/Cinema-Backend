package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Ticket;
import com.wwi21sebgroup5.cinema.exceptions.TicketAlreadyExistsException;
import com.wwi21sebgroup5.cinema.exceptions.TicketNotFoundException;
import com.wwi21sebgroup5.cinema.repositories.TicketRepository;
import com.wwi21sebgroup5.cinema.requestObjects.TicketRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket saveTicket(TicketRequestObject ticketRequestObject) throws TicketAlreadyExistsException{
        Optional<Ticket> foundTicket = ticketRepository.findByEventAndSeat(ticketRequestObject.getEvent(), ticketRequestObject.getSeat());
        if(foundTicket.isPresent()){
            throw new TicketAlreadyExistsException(ticketRequestObject.getEvent(), ticketRequestObject.getSeat());
        }
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

    public Ticket findById(UUID id) throws TicketNotFoundException{
        Optional<Ticket> foundTicket = ticketRepository.findById(id);

        if(foundTicket.isEmpty()){
            throw new TicketNotFoundException(id);
        }

        return foundTicket.get();
    }
}
