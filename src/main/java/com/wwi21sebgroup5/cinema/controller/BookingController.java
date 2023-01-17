package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Booking;
import com.wwi21sebgroup5.cinema.exceptions.BookingNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.SeatDoesNotExistException;
import com.wwi21sebgroup5.cinema.exceptions.SeatNotAvailableException;
import com.wwi21sebgroup5.cinema.exceptions.UserDoesNotExistException;
import com.wwi21sebgroup5.cinema.requestObjects.BookingRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.FinalBookingRequestObject;
import com.wwi21sebgroup5.cinema.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping(path = "/get", params = "id")
    public ResponseEntity<Object> getBookingByBookingId(@RequestParam UUID id){
        try{
            Booking requestedBooking = bookingService.findBookingById(id);
            return new ResponseEntity<>(requestedBooking, HttpStatus.OK);
        }catch(BookingNotFoundException bnfe){
            return new ResponseEntity<>(bnfe.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/tempReserve")
    public ResponseEntity<Object> temporarilyReserveSeats(@RequestBody List<BookingRequestObject> SeatsToReserve){
        try {
            return bookingService.temporarilyReserveSeats(SeatsToReserve);
        } catch (SeatDoesNotExistException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (SeatNotAvailableException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping(path = "/reserve")
    public ResponseEntity<?> reserveSeats(@RequestBody List<FinalBookingRequestObject> seatsToReserve){
        try{
            return bookingService.reserveSeats(seatsToReserve);
        }catch(UserDoesNotExistException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }



}
