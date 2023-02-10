package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Ticket;
import com.wwi21sebgroup5.cinema.exceptions.TicketNotFoundException;
import com.wwi21sebgroup5.cinema.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping(path = "/get", params = "eventId")
    public ResponseEntity<List<Ticket>> getTicketsByEventId(@RequestParam UUID eventId) {
        try {
            List<Ticket> ticketsOfEvent = ticketService.getByEventId(eventId);
            return new ResponseEntity<>(ticketsOfEvent, HttpStatus.OK);
        } catch (TicketNotFoundException tnfe) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/get", params = "id")
    public ResponseEntity<Ticket> getTicketById(@RequestParam UUID id) {
        try {
            Ticket foundTicket = ticketService.findById(id);
            return new ResponseEntity<>(foundTicket, HttpStatus.OK);
        } catch (TicketNotFoundException tnfe) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/get", params = "userId")
    public ResponseEntity<List<Ticket>> getTicketsByUserId(@RequestParam UUID id) {
        List<Ticket> tickets = ticketService.getByUserId(id);

        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @PostMapping(path = "cancel")
    public ResponseEntity<Object> cancelTicket(@RequestBody UUID ticketId) {
        try {
            ticketService.cancelTicket(ticketId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

