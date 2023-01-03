package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Event;
import com.wwi21sebgroup5.cinema.entities.Movie;
import com.wwi21sebgroup5.cinema.entities.Seat;
import com.wwi21sebgroup5.cinema.entities.Ticket;
import com.wwi21sebgroup5.cinema.services.TicketService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {

    @Mock
    TicketService ticketService;

    @InjectMocks
    TicketController ticketController;

    @Test
    @DisplayName("Test getting all Tickets succesfully")
    public void testGetAllTicketsSuccessful(){
        Event sampleEvent = new Event();
        Seat sampleSeat = new Seat();
        Event sampleEvent1 = new Event();
        Seat sampleSeat1 = new Seat();

        Ticket ticket1 = new Ticket(sampleEvent, sampleSeat);
        Ticket ticket2 = new Ticket(sampleEvent1, sampleSeat1);

        List<Ticket> expectedTickets = List.of(ticket1, ticket2);

        when(ticketService.findAll()).thenReturn(expectedTickets);
        ResponseEntity<List<Ticket>> res = ticketController.getAllTickets();

        assertAll("Validation correct response from controller...",
                () -> assertIterableEquals(res.getBody(), expectedTickets),
                () -> assertEquals(res.getStatusCode(), HttpStatus.OK));
    }


}