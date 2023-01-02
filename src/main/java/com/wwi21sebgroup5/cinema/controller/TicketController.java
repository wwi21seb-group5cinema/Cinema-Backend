package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Ticket;
import com.wwi21sebgroup5.cinema.requestObjects.TicketRequestObject;
import com.wwi21sebgroup5.cinema.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public ResponseEntity<Object> getAllTickets(){
        return new ResponseEntity<>(ticketService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> SaveTicket(@RequestBody TicketRequestObject ticketRequestObject){
        Ticket t = ticketService.saveTicket(ticketRequestObject);
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }


}

