package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Booking;
import com.wwi21sebgroup5.cinema.entities.Seat;
import com.wwi21sebgroup5.cinema.exceptions.BookingNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.SeatDoesNotExistException;
import com.wwi21sebgroup5.cinema.exceptions.SeatNotAvailableException;
import com.wwi21sebgroup5.cinema.repositories.BookingRepository;

import java.util.List;
import java.util.UUID;

import com.wwi21sebgroup5.cinema.requestObjects.BookingRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingrepository;
    @Autowired
    private SeatService seatService;

    public Booking findBookingById(UUID id) throws BookingNotFoundException {
        Optional<Booking> foundBooking = bookingrepository.findBookingById(id);

        if(foundBooking.isEmpty()){
            throw new BookingNotFoundException(id);
        }
        return foundBooking.get();
    }

    public ResponseEntity<?> temporarilyReserveSeats(List<BookingRequestObject> seatsToReserve) {
        for(BookingRequestObject s : seatsToReserve){
            try{
                seatService.tempReserveSeat(s.getRow(), s.getPlace());
            }catch(SeatDoesNotExistException ex){
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            }catch(SeatNotAvailableException ex){
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
            }
        }
        return new ResponseEntity<>(seatsToReserve, HttpStatus.OK);
    }

    public ResponseEntity<?> temporarilyUnreserveSeats(List<BookingRequestObject> seatsToUnreserve){
        try{
            Optional<List<Seat>> unreservedSeats; // = ticketService.tempUnreserve(seatsToUnreserve); -> throws Exception if one of the Seats is already unresearved etc..
        }catch(Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        return null;
    }
}
