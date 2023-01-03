package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Ticket;
import com.wwi21sebgroup5.cinema.exceptions.TicketAlreadyExistsException;
import com.wwi21sebgroup5.cinema.exceptions.TicketNotFoundException;
import com.wwi21sebgroup5.cinema.requestObjects.TicketRequestObject;
import com.wwi21sebgroup5.cinema.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    /* only for Testingpurposes
    @PostMapping
    public ResponseEntity saveTicket(@RequestBody TicketRequestObject ticketRequesobject){
        try{
            return new ResponseEntity<>(ticketService.saveTicket(ticketRequesobject), HttpStatus.OK);
        }catch(TicketAlreadyExistsException tae){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    */

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<Ticket>> getAllTickets(){
        return new ResponseEntity<>(ticketService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/get/{eventId}")
    public ResponseEntity<List<Ticket>> getTicketsByEventId(@PathVariable UUID eventId){
        try{
            List<Ticket> ticketsOfEvent = ticketService.getByEventId(eventId);
        }catch(TicketNotFoundException tnfe){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return null;
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable UUID id){
        try {
            Ticket foundTicket = ticketService.findById(id);
            return new ResponseEntity<>(foundTicket, HttpStatus.OK);
        }catch(TicketNotFoundException tnfe){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

