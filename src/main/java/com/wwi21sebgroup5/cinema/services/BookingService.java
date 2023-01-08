package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Booking;
import com.wwi21sebgroup5.cinema.entities.Seat;
import com.wwi21sebgroup5.cinema.entities.Ticket;
import com.wwi21sebgroup5.cinema.enums.SeatState;
import com.wwi21sebgroup5.cinema.exceptions.*;
import com.wwi21sebgroup5.cinema.repositories.BookingRepository;

import java.util.List;
import java.util.UUID;

import com.wwi21sebgroup5.cinema.repositories.SeatRepository;
import com.wwi21sebgroup5.cinema.repositories.TicketRepository;
import com.wwi21sebgroup5.cinema.requestObjects.BookingRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.FinalBookingRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private TicketService ticketService;

    @Autowired
    private SeatRepository seatRepository;

    public Booking findBookingById(UUID id) throws BookingNotFoundException {
        Optional<Booking> foundBooking = bookingRepository.findBookingById(id);

        if(foundBooking.isEmpty()){
            throw new BookingNotFoundException(id);
        }
        return foundBooking.get();
    }

    public ResponseEntity<?> temporarilyReserveSeats(List<BookingRequestObject> seatsToReserve) {
        for (BookingRequestObject s : seatsToReserve) {
            try {
                ticketService.tempReserveSeat(s.getEventID(), s.getRow(), s.getPlace());
            } catch (SeatDoesNotExistException ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            } catch (SeatNotAvailableException ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
            }
        }
            return new ResponseEntity<>(seatsToReserve, HttpStatus.OK);
    }

    public ResponseEntity<?> reserveSeats(List<FinalBookingRequestObject> seatsToReserve){
        Booking b = new Booking(seatsToReserve.get(0).getUser());
        try{
            List<Ticket> ticketsOfEvent = ticketService.getByEventId(seatsToReserve.get(0).getEventID());
            ticketsOfEvent.forEach(t -> {
                        Seat currSeat = t.getSeat();
                        currSeat.setSeatState(SeatState.RESERVED);
                        t.setBooking(b);
                        seatRepository.save(currSeat);
                    });
            bookingRepository.save(b);
        }catch(TicketNotFoundException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(seatsToReserve, HttpStatus.OK);
    }
}
