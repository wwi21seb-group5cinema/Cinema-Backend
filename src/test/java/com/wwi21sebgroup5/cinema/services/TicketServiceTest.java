package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Event;
import com.wwi21sebgroup5.cinema.entities.Seat;
import com.wwi21sebgroup5.cinema.entities.Ticket;
import com.wwi21sebgroup5.cinema.exceptions.TicketAlreadyExistsException;
import com.wwi21sebgroup5.cinema.exceptions.TicketNotFoundException;
import com.wwi21sebgroup5.cinema.repositories.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    TicketRepository ticketRepository;

    @InjectMocks
    TicketService ticketService;


    @Test
    @DisplayName("Test adding Ticket successfully")
    public void testAddTicketSuccessful() throws TicketAlreadyExistsException {
        Event sampleEvent = new Event();
        Seat sampleSeat = new Seat();
        when(ticketRepository.findByEventAndSeat(sampleEvent, sampleSeat)).thenReturn(Optional.empty());
        Ticket response = ticketService.saveTicket(sampleEvent, sampleSeat);
        assertEquals(response.getSeat(), sampleSeat);
        assertEquals(response.getEvent(), sampleEvent);
    }

    @Test
    @DisplayName("Test adding Ticket unsuccessfully")
    public void testAddTicketUnsuccessful() {
        UUID id = UUID.randomUUID();
        Event sampleEvent = new Event();
        LocalDateTime localDateTime = LocalDateTime.now();
        sampleEvent.setEventDateTime(localDateTime);
        Seat sampleSeat = new Seat();
        Ticket ticket = new Ticket(sampleEvent, sampleSeat);
        ticket.setId(id);
        when(ticketRepository.findByEventAndSeat(sampleEvent, sampleSeat)).thenReturn(Optional.of(ticket));
        assertThrows(TicketAlreadyExistsException.class, () -> ticketService.saveTicket(sampleEvent, sampleSeat));
    }

    @Test
    @DisplayName("Test get all Tickets successfully")
    public void testGetAllTickets() {
        Event sampleEvent = new Event();
        Seat sampleSeat = new Seat();
        Ticket ticket = new Ticket(sampleEvent, sampleSeat);
        List<Ticket> expected = List.of(ticket);
        when(ticketRepository.findAll()).thenReturn(expected);
        List<Ticket> actual = ticketService.findAll();
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Test find Ticket by Id successfully")
    public void testFindById() throws TicketNotFoundException {
        Event sampleEvent = new Event();
        Seat sampleSeat = new Seat();
        Ticket ticket = new Ticket(sampleEvent, sampleSeat);
        UUID sampleID = UUID.randomUUID();
        when(ticketRepository.findById(sampleID)).thenReturn(Optional.of(ticket));
        Ticket actual = ticketService.findById(sampleID);
        assertEquals(actual, ticket);
    }

    @Test
    @DisplayName("Test find Ticket by Id unsuccessfully")
    public void testFindByIdUnsuccessfully() {
        UUID sampleID = UUID.randomUUID();
        when(ticketRepository.findById(sampleID)).thenReturn(Optional.empty());
        assertThrows(TicketNotFoundException.class, () -> ticketService.findById(sampleID));
    }

    @Test
    @DisplayName("Test find Ticket by EventId unsuccessfully")
    public void testFindByEventIdUnsuccessfully() {
        UUID sampleID = UUID.randomUUID();
        when(ticketRepository.findTicketsByEvent_Id(sampleID)).thenReturn(Optional.empty());
        assertThrows(TicketNotFoundException.class, () -> ticketService.getByEventId(sampleID));
    }

    @Test
    @DisplayName("Test find Ticket by EventId successfully")
    public void testFindByEventIdSuccessfully() throws TicketNotFoundException {
        Event sampleEvent = new Event();
        Seat sampleSeat = new Seat();
        Ticket ticket = new Ticket(sampleEvent, sampleSeat);
        List<Ticket> expected = List.of(ticket);
        UUID sampleID = UUID.randomUUID();
        when(ticketRepository.findTicketsByEvent_Id(sampleID)).thenReturn(Optional.of(expected));
        List<Ticket> actual = ticketService.getByEventId(sampleID);
        assertEquals(actual, expected);
    }
}
