package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Seat;
import com.wwi21sebgroup5.cinema.enums.SeatState;
import com.wwi21sebgroup5.cinema.exceptions.SeatDoesNotExistException;
import com.wwi21sebgroup5.cinema.exceptions.SeatNotAvailableException;
import com.wwi21sebgroup5.cinema.repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public Seat save(Seat newSeat) {
        return seatRepository.save(newSeat);
    }

    public Seat tempReserveSeat(int row, int place) throws SeatDoesNotExistException, SeatNotAvailableException {
        Optional<Seat> foundSeat = seatRepository.findByRowAndPlace(row, place);
        if (foundSeat.isEmpty()) {
            throw new SeatDoesNotExistException(row, place);
        }

        Seat seat = foundSeat.get();
        if (seat.getSeatState() != SeatState.FREE) {
            throw new SeatNotAvailableException(row, place);
        }

        seat.setSeatState(SeatState.TEMPORAL_RESERVED);
        return seat;
    }
}
