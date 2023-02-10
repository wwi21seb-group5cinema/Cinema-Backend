package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.enums.SeatState;
import com.wwi21sebgroup5.cinema.exceptions.*;
import com.wwi21sebgroup5.cinema.repositories.BookingRepository;
import com.wwi21sebgroup5.cinema.requestObjects.BookingRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.FinalBookingRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    public Booking findBookingById(UUID id) throws BookingNotFoundException {
        Optional<Booking> foundBooking = bookingRepository.findBookingById(id);

        if (foundBooking.isEmpty()) {
            throw new BookingNotFoundException(id);
        }
        return foundBooking.get();
    }

    public ResponseEntity<Object> temporarilyReserveSeats(List<BookingRequestObject> seatsToReserve) throws SeatDoesNotExistException, SeatNotAvailableException {
        LocalDateTime expTimeStamp = LocalDateTime.now().plusMinutes(15);
        for (BookingRequestObject s : seatsToReserve) {
            ticketService.tempReserveSeat(s.getEventID(), s.getRow(), s.getPlace(), expTimeStamp, s.getUserId());
        }
        // Take the first Item of list for eventId since it has to be same in any Item of the list anyway
        BookingRequestObject requestObject = seatsToReserve.get(0);
        Optional<Event> foundEvent = eventService.findById(requestObject.getEventID());
        Event event = foundEvent.get();
        event.getTickets().stream()
                .filter(ticket -> ticket.getSeat().getUserId() != null)
                .filter(ticket -> ticket.getSeat().getUserId().equals(requestObject.getUserId()))
                .forEach(ticket -> ticketService.updateExpTimeStamp(ticket.getSeat(), expTimeStamp));
        return new ResponseEntity<>(expTimeStamp.toString(), HttpStatus.OK);
    }

    public ResponseEntity<?> reserveSeats(List<FinalBookingRequestObject> seatsToReserve) throws UserDoesNotExistException {
        //Set user into Booking Entity
        UUID userID = seatsToReserve.get(0).getUserID();
        Optional<User> u = userService.getUserById(userID);
        if (u.isEmpty()) {
            throw new UserDoesNotExistException(userID);
        }

        Booking b = new Booking(u.get());
        b = bookingRepository.save(b);

        //try to Reserve Seats and link booking b with corresponding tickets
        List<Ticket> bookedTickets = new ArrayList<>();
        try {
            List<Ticket> ticketsOfEvent = ticketService.getByEventId(seatsToReserve.get(0).getEventID());
            for (Ticket t : ticketsOfEvent) {
                for (FinalBookingRequestObject o : seatsToReserve) {
                    if (o.getRow() == t.getSeat().getRow() && o.getPlace() == t.getSeat().getPlace()) {
                        Seat currSeat = t.getSeat();
                        currSeat.setSeatState(SeatState.RESERVED);
                        t.setBooking(b);
                        seatService.save(currSeat);
                        ticketService.save(t);
                        bookedTickets.add(t);
                    }
                }
            }

            bookingRepository.save(b);
        } catch (TicketNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

        //emailService.sendBookingConfirmation(bookedTickets, b);
        return new ResponseEntity<>(seatsToReserve, HttpStatus.OK);
    }

    public void scanQrCode(String code) throws TicketNotFoundException, TicketAlreadyCheckedInException,
            TicketNotPaidException {
        Ticket ticket = ticketService.findById(UUID.fromString(code));

        if (ticket.isScanned()) {
            throw new TicketAlreadyCheckedInException(code);
        } else if (!ticket.getSeat().getSeatState().equals(SeatState.PAID)) {
            throw new TicketNotPaidException(code);
        }

        ticket.setScanned(true);
    }

}
