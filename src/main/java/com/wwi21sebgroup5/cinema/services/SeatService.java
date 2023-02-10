package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Event;
import com.wwi21sebgroup5.cinema.entities.Seat;
import com.wwi21sebgroup5.cinema.entities.Ticket;
import com.wwi21sebgroup5.cinema.enums.SeatState;
import com.wwi21sebgroup5.cinema.repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        if(seat.getExpirationTimeStamp().isBefore(LocalDateTime.now())){
            seat.setSeatState(SeatState.FREE);
            seatRepository.save(seat);
        }
    }
}
