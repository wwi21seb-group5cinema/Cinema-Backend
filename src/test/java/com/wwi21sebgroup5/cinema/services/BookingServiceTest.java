package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Booking;
import com.wwi21sebgroup5.cinema.entities.Invoice;
import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.exceptions.BookingNotFoundException;
import com.wwi21sebgroup5.cinema.exceptions.SeatDoesNotExistException;
import com.wwi21sebgroup5.cinema.exceptions.SeatNotAvailableException;
import com.wwi21sebgroup5.cinema.repositories.BookingRepository;
import com.wwi21sebgroup5.cinema.requestObjects.BookingRequestObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    BookingRepository bookingRepository;
    @Mock
    TicketService ticketService;

    @InjectMocks
    BookingService bookingService;

    @Test
    @DisplayName("Test find booking by id successfully")
    public void testFindByIdSuccessfully() throws BookingNotFoundException{
        UUID sampleId = UUID.randomUUID();
        User user = new User();
        Booking booking = new Booking(user);
        when(bookingRepository.findBookingById(sampleId)).thenReturn(Optional.of(booking));

        Booking act = bookingService.findBookingById(sampleId);
        assertEquals(act, booking);
    }

    @Test
    @DisplayName("Test find booking by id unsuccessfully")
    public void testFindByIdUnsuccessfully() {
        UUID sampleId = UUID.randomUUID();
        when(bookingRepository.findBookingById(sampleId)).thenReturn(Optional.empty());
        assertThrows(BookingNotFoundException.class, () -> bookingService.findBookingById(sampleId));
    }

    @Test
    @DisplayName("Test temporarily reserve Seats with 1 seat successfully")
    public void testTemporarilyReserveSeats() throws SeatDoesNotExistException, SeatNotAvailableException {
        UUID sampleEventId = UUID.randomUUID();
        int sRow = 1;
        int sPlace = 1;
        List<BookingRequestObject> input = List.of(new BookingRequestObject(sampleEventId, sRow, sPlace));

        doNothing().when(ticketService).tempReserveSeat(sampleEventId, sRow, sPlace);
        ResponseEntity<?> act = bookingService.temporarilyReserveSeats(input);
        ResponseEntity<?> exp = new ResponseEntity<>(input, HttpStatus.OK);

        assertAll("Validating output...",
                () -> assertEquals(act.getBody(), exp.getBody()),
                () -> assertEquals(act.getStatusCode(), exp.getStatusCode()));
    }

    @Test
    @DisplayName("Test temporarily reserve Seats with 2 seats successfully")
    public void testTemporarilyReserveSeats2() throws SeatDoesNotExistException, SeatNotAvailableException {
        UUID sampleEventId = UUID.randomUUID();
        int sRow = 1;
        int sPlace = 1;

        UUID sampleEventId1 = UUID.randomUUID();
        int sRow1 =2;
        int sPlace1 = 2;
        List<BookingRequestObject> input = List.of(
                new BookingRequestObject(sampleEventId, sRow, sPlace),
                new BookingRequestObject(sampleEventId1, sRow1, sPlace1));

        doNothing().when(ticketService).tempReserveSeat(sampleEventId, sRow, sPlace);
        doNothing().when(ticketService).tempReserveSeat(sampleEventId1, sRow1, sPlace1);

        ResponseEntity<?> act = bookingService.temporarilyReserveSeats(input);
        ResponseEntity<?> exp = new ResponseEntity<>(input, HttpStatus.OK);

        assertEquals(exp, act);
    }

    @Test
    @DisplayName("Test temporarily reserve Seats with non existent seat unsuccessfully")
    public void testTemporarilyReserveSeatsUnsuccesfully() throws SeatNotAvailableException, SeatDoesNotExistException {
        UUID sampleEventId = UUID.randomUUID();
        int sRow = 1;
        int sPlace = 1;

        List<BookingRequestObject> input = List.of(new BookingRequestObject(sampleEventId, sRow, sPlace));
        doThrow(new SeatDoesNotExistException(sRow, sPlace)).when(ticketService).tempReserveSeat(sampleEventId, sRow, sPlace);

        ResponseEntity<?> exp = new ResponseEntity<>("The Seat on row 1 and place 1 does not exist", HttpStatus.NOT_FOUND);
        ResponseEntity<?> act = bookingService.temporarilyReserveSeats(input);

        assertEquals(exp, act);
    }

    @Test
    @DisplayName("Test temporarily reserve Seats with non available seat unsuccessfully")
    public void testTemporarilyReserveSeatsUnsuccessfully2() throws SeatNotAvailableException, SeatDoesNotExistException{
        UUID sampleEventId = UUID.randomUUID();
        int sRow = 1;
        int sPlace = 1;

        List<BookingRequestObject> input = List.of(new BookingRequestObject(sampleEventId, sRow, sPlace));
        doThrow(new SeatNotAvailableException(sRow, sPlace)).when(ticketService).tempReserveSeat(sampleEventId, sRow, sPlace);

        ResponseEntity<?> exp = new ResponseEntity<>("The Seat on row 1 and place 1 is not available", HttpStatus.NOT_ACCEPTABLE);
        ResponseEntity<?> act = bookingService.temporarilyReserveSeats(input);

        assertEquals(exp, act);
    }
}
