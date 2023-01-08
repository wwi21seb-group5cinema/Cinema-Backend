package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Event;
import com.wwi21sebgroup5.cinema.entities.Seat;
import com.wwi21sebgroup5.cinema.entities.Ticket;
import com.wwi21sebgroup5.cinema.enums.SeatState;
import com.wwi21sebgroup5.cinema.exceptions.SeatDoesNotExistException;
import com.wwi21sebgroup5.cinema.exceptions.SeatNotAvailableException;
import com.wwi21sebgroup5.cinema.repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public Seat save(Seat newSeat) {
        return seatRepository.save(newSeat);
    }

    public void updateStatesofSeats(Event e){
        List<Ticket> ticketsOfEvent = e.getTickets();
        ticketsOfEvent.stream()
                .filter(t -> t.getSeat().getSeatState() == SeatState.TEMPORAL_RESERVED)
                .forEach(t -> checkTempReservationTime(t.getSeat()));

    }

    public void checkTempReservationTime(Seat seat) {
        if(seat.getExpirationTimeStamp().isAfter(LocalDateTime.now())){
            seat.setSeatState(SeatState.FREE);
            seatRepository.save(seat);
        }
    }
}
