package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.*;
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
    public void testGetTicktesByEventIdsuccessful(){
        Event sampleEvent = new Event();
        Seat sampleSeat = new Seat();
        Seat sampleSeat1 = new Seat();
        UUID sampleId = new UUID(1 ,1);
        Booking sampleBooking = new Booking();

        Ticket t1 = new Ticket(sampleEvent, sampleSeat, sampleBooking);
        Ticket t2 = new Ticket(sampleEvent, sampleSeat1, sampleBooking);

        List<Ticket> expectedTickets = List.of(t1, t2);

        try {
            when(ticketService.getByEventId(sampleId)).thenReturn(expectedTickets);
            ResponseEntity<List<Ticket>> res = ticketController.getTicketsByEventId(sampleId);
            assertAll(
                    "Validating correct Response from controller...",
                    () -> assertIterableEquals(expectedTickets, res.getBody()),
                    () -> assertEquals(res.getStatusCode(), HttpStatus.OK)
            );
        }catch(Exception e){
            e.printStackTrace();
            fail("Failed");
        }
    }

    @Test
    @DisplayName("Test get Tickets by event unsuccessful")
    public void testgetTicketsByEventUnsuccessful() throws TicketNotFoundException{
        Event sampleEvent = new Event();
        UUID sampleId = new UUID(1, 1);
        TicketNotFoundException ex = new TicketNotFoundException(sampleId);
        when(ticketService.getByEventId(sampleId)).thenThrow(ex);

        ResponseEntity<List<Ticket>> res = ticketController.getTicketsByEventId(sampleId);
        assertAll("Validating Response of controller...",
                () -> assertEquals(res.getStatusCode(), HttpStatus.NOT_FOUND),
                () -> assertEquals(null, res.getBody()));
    }

    @Test
    @DisplayName("Test getting Ticket by Ticket Id successfully")
    public void testGetTicketByIdSuccessful() throws TicketNotFoundException{
        Event sampleEvent = new Event();
        Seat sampleSeat = new Seat();
        Booking sampleBooking = new Booking();
        Ticket t = new Ticket(sampleEvent, sampleSeat, sampleBooking);
        UUID sampleId = new UUID(1,1);

        when(ticketService.findById(sampleId)).thenReturn(t);
        ResponseEntity<Ticket> res = ticketController.getTicketById(sampleId);

        assertAll("Validating response of controller...",
                () -> assertEquals(t, res.getBody()),
                () -> assertEquals(HttpStatus.OK, res.getStatusCode()));
    }

    @Test
    @DisplayName("Test getting Ticket by Ticket Id unsuccessfully")
    public void testGetTicketByIdUnsuccessful() throws TicketNotFoundException{
        UUID sampleId = new UUID(1,1);

        when(ticketService.findById(sampleId)).thenThrow(TicketNotFoundException.class);
        ResponseEntity<Ticket> res = ticketController.getTicketById(sampleId);

        assertEquals(res.getStatusCode(), HttpStatus.NOT_FOUND);
    }


}