package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Event;
import com.wwi21sebgroup5.cinema.entities.Seat;
import com.wwi21sebgroup5.cinema.entities.Ticket;
import com.wwi21sebgroup5.cinema.enums.SeatState;
import com.wwi21sebgroup5.cinema.exceptions.SeatDoesNotExistException;
import com.wwi21sebgroup5.cinema.exceptions.SeatNotAvailableException;
import com.wwi21sebgroup5.cinema.exceptions.TicketAlreadyExistsException;
import com.wwi21sebgroup5.cinema.exceptions.TicketNotFoundException;
import com.wwi21sebgroup5.cinema.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SeatService seatService;


    public Ticket saveTicket(Event pEvent, Seat pSeat) throws TicketAlreadyExistsException {
        Optional<Ticket> foundTicket = ticketRepository.findByEventAndSeat(pEvent, pSeat);
        if (foundTicket.isPresent()) {
            throw new TicketAlreadyExistsException(pEvent, pSeat);
        }
        Ticket ticket = new Ticket(
                pEvent,
                pSeat
        );
        ticketRepository.save(ticket);
        return ticket;
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Ticket findById(UUID id) throws TicketNotFoundException {
        Optional<Ticket> foundTicket = ticketRepository.findById(id);

        if (foundTicket.isEmpty()) {
            throw new TicketNotFoundException(id);
        }

        return foundTicket.get();
    }

    public List<Ticket> getByEventId(UUID eventId) throws TicketNotFoundException {
        Optional<List<Ticket>> foundTickets = ticketRepository.findTicketsByEvent_Id(eventId);
        if (foundTickets.isEmpty()) {
            throw new TicketNotFoundException(eventId);
        }
        return foundTickets.get();
    }

    public void tempReserveSeat(UUID eventID, int row, int place, LocalDateTime expTimeStamp) throws SeatDoesNotExistException, SeatNotAvailableException{
        Optional<Ticket> foundTicket = ticketRepository.findByEvent_IdAndSeat_RowAndSeat_Place(eventID, row, place);
        if(foundTicket.isEmpty()){
            throw new SeatDoesNotExistException(row, place);
        }

        Seat currSeat = foundTicket.get().getSeat();
        if (currSeat.getSeatState() != SeatState.FREE) {
            throw new SeatNotAvailableException(row, place);
        }
        currSeat.setSeatState(SeatState.TEMPORAL_RESERVED);
        currSeat.setExpirationTimeStamp(expTimeStamp);
        seatService.save(currSeat);
    }

    public Ticket save(Ticket t) {
        return ticketRepository.save(t);
    }
}
