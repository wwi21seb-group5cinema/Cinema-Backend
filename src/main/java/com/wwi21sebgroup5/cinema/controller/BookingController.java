package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Booking;
import com.wwi21sebgroup5.cinema.exceptions.BookingNotFoundException;
import com.wwi21sebgroup5.cinema.requestObjects.BookingRequestObject;
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
    public ResponseEntity<?> getBookingByBookingId(@RequestParam UUID id){
        try{
            Booking requestedBooking = bookingService.findBookingById(id);
            return new ResponseEntity<Booking>(requestedBooking, HttpStatus.OK);
        }catch(BookingNotFoundException bnfe){
            return new ResponseEntity<>(bnfe.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/tempReserve")
    public ResponseEntity<?> temporarilyReserveSeat(@RequestBody List<BookingRequestObject> SeatsToReserve){

        bookingService.temporarilyReserveSeats(SeatsToReserve);
        return null;
    }


}
