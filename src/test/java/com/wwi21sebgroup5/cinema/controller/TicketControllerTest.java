package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Event;
import com.wwi21sebgroup5.cinema.entities.Seat;
import com.wwi21sebgroup5.cinema.entities.Ticket;
import com.wwi21sebgroup5.cinema.exceptions.TicketNotFoundException;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {
    @Mock
    TicketService ticketService;

    @InjectMocks
    TicketController ticketController;

    @Test
    @DisplayName("Test getting Tickets by EventId sucessfully")
    public void testGetTicktsByEventIdSuccessful() {
        Event sampleEvent = new Event();
        Seat sampleSeat = new Seat();
        Seat sampleSeat1 = new Seat();
        UUID sampleId = new UUID(1, 1);

        Ticket t1 = new Ticket(sampleEvent, sampleSeat);
        Ticket t2 = new Ticket(sampleEvent, sampleSeat1);

        List<Ticket> expectedTickets = List.of(t1, t2);

        try {
            when(ticketService.getByEventId(sampleId)).thenReturn(expectedTickets);
            ResponseEntity<List<Ticket>> res = ticketController.getTicketsByEventId(sampleId);
            assertAll(
                    "Validating correct Response from controller...",
                    () -> assertIterableEquals(expectedTickets, res.getBody()),
                    () -> assertEquals(res.getStatusCode(), HttpStatus.OK)
            );
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed");
        }
    }

    @Test
    @DisplayName("Test get Tickets by event unsuccessful")
    public void testgetTicketsByEventUnsuccessful() throws TicketNotFoundException {
        UUID sampleId = new UUID(1, 1);
        TicketNotFoundException ex = new TicketNotFoundException(sampleId);
        when(ticketService.getByEventId(sampleId)).thenThrow(ex);

        ResponseEntity<List<Ticket>> res = ticketController.getTicketsByEventId(sampleId);
        assertAll("Validating Response of controller...",
                () -> assertEquals(res.getStatusCode(), HttpStatus.NOT_FOUND),
                () -> assertNull(res.getBody()));
    }

    @Test
    @DisplayName("Test get Tickets by event unsuccessful - Internal Error")
    public void testgetTicketsByEventUnsuccessfulInternalError() throws TicketNotFoundException {
        UUID sampleId = new UUID(1, 1);
        RuntimeException ex = new RuntimeException();
        when(ticketService.getByEventId(sampleId)).thenThrow(ex);

        ResponseEntity<List<Ticket>> res = ticketController.getTicketsByEventId(sampleId);
        assertAll("Validating Response of controller...",
                () -> assertEquals(res.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR),
                () -> assertNull(res.getBody()));
    }

    @Test
    @DisplayName("Test getting Ticket by Ticket Id successfully")
    public void testGetTicketByIdSuccessful() throws TicketNotFoundException {
        Event sampleEvent = new Event();
        Seat sampleSeat = new Seat();
        Ticket t = new Ticket(sampleEvent, sampleSeat);
        UUID sampleId = new UUID(1, 1);

        when(ticketService.findById(sampleId)).thenReturn(t);
        ResponseEntity<Ticket> res = ticketController.getTicketById(sampleId);

        assertAll("Validating response of controller...",
                () -> assertEquals(t, res.getBody()),
                () -> assertEquals(HttpStatus.OK, res.getStatusCode()));
    }

    @Test
    @DisplayName("Test getting Ticket by Ticket Id unsuccessfully")
    public void testGetTicketByIdUnsuccessful() throws TicketNotFoundException {
        UUID sampleId = new UUID(1, 1);

        when(ticketService.findById(sampleId)).thenThrow(TicketNotFoundException.class);
        ResponseEntity<Ticket> res = ticketController.getTicketById(sampleId);

        assertEquals(res.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Test getting Ticket by Ticket Id unsuccessfully - Internal error")
    public void testGetTicketByIdUnsuccessfulInternalError() throws TicketNotFoundException {
        UUID sampleId = new UUID(1, 1);

        when(ticketService.findById(sampleId)).thenThrow(RuntimeException.class);
        ResponseEntity<Ticket> res = ticketController.getTicketById(sampleId);

        assertEquals(res.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}