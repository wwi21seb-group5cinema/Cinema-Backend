package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.Booking;
import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.exceptions.BookingNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.SeatDoesNotExistException;
import com.wwi21sebgroup5.cinema.exceptions.SeatNotAvailableException;
import com.wwi21sebgroup5.cinema.requestObjects.BookingRequestObject;
import com.wwi21sebgroup5.cinema.services.BookingService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {
    @Mock
    BookingService bookingService;

    @InjectMocks
    BookingController bookingController;

    @Test
    @DisplayName("Test getBookingByBookingId successfully")
    public void testGetBookingByBookingId() throws BookingNotFoundException {
        UUID sampleId = UUID.randomUUID();
        User sampleUser = new User();
        Booking sampleBooking = new Booking(sampleUser);

        when(bookingService.findBookingById(sampleId)).thenReturn(sampleBooking);

        ResponseEntity<?> exp = new ResponseEntity<>(sampleBooking, HttpStatus.OK);
        ResponseEntity<?> act = bookingController.getBookingByBookingId(sampleId);

        assertEquals(exp, act);
    }

    @Test
    @DisplayName("Test getBookingByBookingId with non existent Booking unsuccessfully")
    public void testGetBookingbyBookingId() throws BookingNotFoundException {
        UUID sampleId = UUID.randomUUID();

        when(bookingService.findBookingById(sampleId)).thenThrow(new BookingNotFoundException(sampleId));

        ResponseEntity<?> exp = new ResponseEntity<>(String.format("Booking with the Id %s does not exist", sampleId), HttpStatus.NOT_FOUND);
        ResponseEntity<?> act = bookingController.getBookingByBookingId(sampleId);

        assertEquals(exp, act);
    }

    @Test
    @DisplayName("Test getBookingByBookingId with Internal Server Error unsuccessfully")
    public void testGetBookingByBookingIdRuntimeError() throws BookingNotFoundException {
        UUID sampleId = UUID.randomUUID();

        when(bookingService.findBookingById(sampleId)).thenThrow(new RuntimeException("Error!"));

        ResponseEntity<?> exp = new ResponseEntity<>("Error!", HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseEntity<?> act = bookingController.getBookingByBookingId(sampleId);

        assertEquals(exp, act);
    }

    @Test
    @DisplayName("Test temporarilyReserveSeats with 1 Seat successfully")
    public void testTemporarilyReserveSeats() throws SeatNotAvailableException, SeatDoesNotExistException {
        UUID sampleEventId = UUID.randomUUID();
        int sRow = 1;
        int sPlace = 1;
        BookingRequestObject sampleSeat = new BookingRequestObject(sampleEventId, sRow, sPlace);

        List<BookingRequestObject> sampleSeats = List.of(sampleSeat);
        ResponseEntity<Object> res = new ResponseEntity<>(sampleSeats, HttpStatus.OK);

        when(bookingService.temporarilyReserveSeats(sampleSeats)).thenReturn(res);

        ResponseEntity<?> act = bookingController.temporarilyReserveSeats(sampleSeats);

        assertEquals(res, act);
    }

    @Test
    @DisplayName("Test temporarilyReserveSeats with 2 Seats successfully")
    public void testTemporarilyReserveSeats2() throws SeatNotAvailableException, SeatDoesNotExistException {
        UUID sampleEventId = UUID.randomUUID();
        int sRow = 1;
        int sPlace = 1;
        BookingRequestObject sampleSeat = new BookingRequestObject(sampleEventId, sRow, sPlace);
        UUID sampleEventId1 = UUID.randomUUID();
        int sRow1 = 1;
        int sPlace1 = 1;
        BookingRequestObject sampleSeat1 = new BookingRequestObject(sampleEventId1, sRow1, sPlace1);

        List<BookingRequestObject> sampleSeats = List.of(sampleSeat, sampleSeat1);
        ResponseEntity<Object> res = new ResponseEntity<>(sampleSeats, HttpStatus.OK);

        when(bookingService.temporarilyReserveSeats(sampleSeats)).thenReturn(res);

        ResponseEntity<?> act = bookingController.temporarilyReserveSeats(sampleSeats);

        assertEquals(res, act);
    }

    @Test
    @DisplayName("Test temporarilyReserveSeats with non existent Seat")
    public void testTemporarilyReserveNonexistentSeat() throws SeatNotAvailableException, SeatDoesNotExistException {
        UUID sampleEventId = UUID.randomUUID();
        int sRow = 1;
        int sPlace = 1;
        BookingRequestObject sampleSeat = new BookingRequestObject(sampleEventId, sRow, sPlace);

        List<BookingRequestObject> sampleSeats = List.of(sampleSeat);
        doThrow(SeatDoesNotExistException.class).when(bookingService).temporarilyReserveSeats(sampleSeats);

        ResponseEntity<?> exp = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ResponseEntity<?> act = bookingController.temporarilyReserveSeats(sampleSeats);

        assertEquals(exp, act);

    }


    @Test
    @DisplayName("Test temporarilyReserveSeats with not available Seat")
    public void testTemporarilyReserveNotAvailableSeat() throws SeatNotAvailableException, SeatDoesNotExistException {
        UUID sampleEventId = UUID.randomUUID();
        int sRow = 1;
        int sPlace = 1;
        BookingRequestObject sampleSeat = new BookingRequestObject(sampleEventId, sRow, sPlace);
        List<BookingRequestObject> sampleSeats = List.of(sampleSeat);

        doThrow(SeatNotAvailableException.class).when(bookingService).temporarilyReserveSeats(sampleSeats);

        ResponseEntity<?> exp = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        ResponseEntity<?> act = bookingController.temporarilyReserveSeats(sampleSeats);

        assertEquals(exp, act);
    }
}
