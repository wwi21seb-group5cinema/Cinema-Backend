package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.exceptions.CinemaNotFoundException;
import com.wwi21sebgroup5.cinema.repositories.CinemaHallRepository;
import com.wwi21sebgroup5.cinema.requestObjects.CinemaHallRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CinemaHallService {

    @Autowired
    CinemaService cinemaService;

    @Autowired
    SeatTypeService seatTypeService;

    @Autowired
    CinemaHallRepository cinemaHallRepository;

    public CinemaHall addCinemaHall(CinemaHallRequestObject requestObject) throws CinemaNotFoundException {
        CinemaHall newCinemaHall = new CinemaHall();
        SeatingPlan newSeatingPlan = new SeatingPlan(newCinemaHall, requestObject.getRows());

        Optional<Cinema> foundCinema = cinemaService.getCinemaById(requestObject.getCinemaId());

        if (foundCinema.isEmpty()) {
            throw new CinemaNotFoundException(requestObject.getCinemaId());
        } else {
            newCinemaHall.setCinema(foundCinema.get());
        }

        SeatType regularType = seatTypeService.getSeatType(SeatTypeService.REGULAR_TYPE);
        SeatType logeType = seatTypeService.getSeatType(SeatTypeService.LOGE_TYPE);

        List<SeatBlueprint> seats = new ArrayList<>();

        for (int row = 1; row <= requestObject.getRows(); row++) {
            // Loges are only in the last row, otherwise the halls offer regular seats
            SeatType activeType = row == requestObject.getRows() ? logeType : regularType;

            for (int place = 1; place <= requestObject.getPlacesPerRow(); place++) {
                seats.add(new SeatBlueprint(newSeatingPlan, activeType, row, place));
            }
        }

        newSeatingPlan.setSeats(seats); // set seat blueprint for the seating plan

        newCinemaHall.setSeatingPlan(newSeatingPlan);
        newCinemaHall.setName(requestObject.getName());
        newCinemaHall.setFloor(requestObject.getFloor());

        return cinemaHallRepository.save(newCinemaHall);
    }

    public List<CinemaHall> getAllCinemaHallsByCinema(UUID cinemaId) {
        return cinemaHallRepository.findByCinema_Id(cinemaId);
    }

    public Optional<CinemaHall> getCinemaHallById(UUID id) {
        return cinemaHallRepository.findById(id);
    }

    public List<CinemaHall> getAllCinemaHalls() {
        return cinemaHallRepository.findAll();
    }
}
