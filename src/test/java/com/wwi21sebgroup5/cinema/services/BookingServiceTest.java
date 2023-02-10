package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.*;
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
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
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
    @Mock
    EventService eventService;

    @Mock
    Ticket t;

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

    /*@Test
    @DisplayName("Test temporarily reserve Seats with 1 seat successfully")
    public void testTemporarilyReserveSeats() throws SeatDoesNotExistException, SeatNotAvailableException {
        UUID sampleEventId = UUID.randomUUID();
        int sRow = 1;
        int sPlace = 1;
        UUID sampleUserId = UUID.randomUUID();

        Seat seat = new Seat();
        Movie m = new Movie();
        CinemaHall cH = new CinemaHall();
        LocalDateTime ldt1 = LocalDateTime.now();

        Event sampleEvent = new Event(m, cH, List.of(t), ldt1);
        Optional<Event> eventOptional = Optional.of(sampleEvent);

        LocalDateTime expTimeStamp = LocalDateTime.now();
        List<BookingRequestObject> input = List.of(new BookingRequestObject(sampleEventId, sRow, sPlace, sampleUserId));

        try(MockedStatic<LocalDateTime> ldt = mockStatic(LocalDateTime.class)) {
            ldt.when(LocalDateTime::now).thenReturn(expTimeStamp);


            expTimeStamp = expTimeStamp.plusMinutes(15);
            doNothing().when(ticketService).tempReserveSeat(sampleEventId, sRow, sPlace, expTimeStamp, sampleUserId);
            when(eventService.findById(sampleEventId)).thenReturn(eventOptional);
            when(t.getSeat()).thenReturn(seat);
            when(t.getSeat().getUserId()).thenReturn(sampleUserId);
            ResponseEntity<?> act = bookingService.temporarilyReserveSeats(input);
            ResponseEntity<?> exp = new ResponseEntity<>(expTimeStamp.toString(), HttpStatus.OK);

            assertAll("Validating output...",
                    () -> assertEquals(act.getBody(), exp.getBody()),
                    () -> assertEquals(act.getStatusCode(), exp.getStatusCode()));
        }
    }

    @Test
    @DisplayName("Test temporarily reserve Seats with 2 seats successfully")
    public void testTemporarilyReserveSeats2() throws SeatDoesNotExistException, SeatNotAvailableException {
        UUID sampleEventId = UUID.randomUUID();
        int sRow = 1;
        int sPlace = 1;
        UUID sampleUserId = UUID.randomUUID();
        LocalDateTime expTimeStamp = LocalDateTime.now();

        UUID sampleEventId1 = UUID.randomUUID();
        int sRow1 =2;
        int sPlace1 = 2;
        List<BookingRequestObject> input = List.of(
                new BookingRequestObject(sampleEventId, sRow, sPlace, sampleUserId),
                new BookingRequestObject(sampleEventId1, sRow1, sPlace1, sampleUserId));

        try(MockedStatic<LocalDateTime> ldt = mockStatic(LocalDateTime.class)) {
            ldt.when(LocalDateTime::now).thenReturn(expTimeStamp);


            expTimeStamp = expTimeStamp.plusMinutes(15);

            doNothing().when(ticketService).tempReserveSeat(sampleEventId, sRow, sPlace, expTimeStamp, sampleUserId);
            doNothing().when(ticketService).tempReserveSeat(sampleEventId1, sRow1, sPlace1, expTimeStamp, sampleUserId);

            ResponseEntity<?> act = bookingService.temporarilyReserveSeats(input);
            ResponseEntity<?> exp = new ResponseEntity<>(expTimeStamp, HttpStatus.OK);

            assertEquals(exp, act);
        }
    }
*/
    @Test
    @DisplayName("Test temporarily reserve Seats with non existent seat unsuccessfully")
    public void testTemporarilyReserveSeatsUnsuccesfully() throws SeatNotAvailableException, SeatDoesNotExistException {
        UUID sampleEventId = UUID.randomUUID();
        UUID sampleUserId = UUID.randomUUID();
        int sRow = 1;
        int sPlace = 1;
        LocalDateTime expTimeStamp = LocalDateTime.now();

        List<BookingRequestObject> input = List.of(new BookingRequestObject(sampleEventId, sRow, sPlace, sampleUserId));

        try(MockedStatic<LocalDateTime> ldt = mockStatic(LocalDateTime.class)) {
            ldt.when(LocalDateTime::now).thenReturn(expTimeStamp);


            expTimeStamp = expTimeStamp.plusMinutes(15);
            doThrow(new SeatDoesNotExistException(sRow, sPlace)).when(ticketService).tempReserveSeat(sampleEventId, sRow, sPlace, expTimeStamp, sampleUserId);
            assertThrows(SeatDoesNotExistException.class, () -> bookingService.temporarilyReserveSeats(input));


        }
    }

    @Test
    @DisplayName("Test temporarily reserve Seats with non available seat unsuccessfully")
    public void testTemporarilyReserveSeatsUnsuccessfully2() throws SeatNotAvailableException, SeatDoesNotExistException{
        UUID sampleEventId = UUID.randomUUID();
        UUID sampleUserId = UUID.randomUUID();
        int sRow = 1;
        int sPlace = 1;
        LocalDateTime expTimeStamp = LocalDateTime.now();

        List<BookingRequestObject> input = List.of(new BookingRequestObject(sampleEventId, sRow, sPlace, sampleUserId));

        try(MockedStatic<LocalDateTime> ldt = mockStatic(LocalDateTime.class)) {
            ldt.when(LocalDateTime::now).thenReturn(expTimeStamp);


            expTimeStamp = expTimeStamp.plusMinutes(15);
            doThrow(new SeatNotAvailableException(sRow, sPlace)).when(ticketService).tempReserveSeat(sampleEventId, sRow, sPlace, expTimeStamp, sampleUserId);

            assertThrows(SeatNotAvailableException.class,()-> bookingService.temporarilyReserveSeats(input));

        }
    }
}
