package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.*;
import com.wwi21sebgroup5.cinema.exceptions.CinemaHallNotFoundException;
import com.wwi21sebgroup5.cinema.repositories.SeatingPlanRepository;
import com.wwi21sebgroup5.cinema.requestObjects.SeatingPlanRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeatingPlanService {

    @Autowired
    CinemaHallService cinemaHallService;

    @Autowired
    SeatTypeService seatTypeService;

    @Autowired
    private SeatingPlanRepository seatingPlanRepository;

    public List<SeatingPlan> getAllSeatingPlans() {
        return seatingPlanRepository.findAll();
    }

    public Optional<SeatingPlan> getSeatingPlanById(UUID id) {
        return seatingPlanRepository.findById(id);
    }

    public Optional<SeatingPlan> getSeatingPlanByCinemaHall(UUID cinemaHallId) {
        return seatingPlanRepository.findByCinemaHall_Id(cinemaHallId);
    }

    public SeatingPlan addSeatingPlan(SeatingPlanRequestObject requestObject) throws CinemaHallNotFoundException {
        SeatingPlan newSeatingPlan = new SeatingPlan();

        Optional<CinemaHall> cinemaHall = cinemaHallService.getCinemaHallById(requestObject.getCinemaHallId());

        if (cinemaHall.isEmpty()) {
            throw new CinemaHallNotFoundException(requestObject.getCinemaHallId());
        } else {
            newSeatingPlan.setCinemaHall(cinemaHall.get());
        }

        SeatType regularType = seatTypeService.getSeatType(SeatTypeService.REGULAR_TYPE);
        SeatType logeType = seatTypeService.getSeatType(SeatTypeService.LOGE_TYPE);

        List<SeatBlueprint> seats = new ArrayList<>();

        for (int row = 1; row <= requestObject.getRows(); row++) {
            // Loges are only in the last row, otherwise the halls offer regular seats
            SeatType activeType = (row == requestObject.getRows()) ? logeType : regularType;

            for (int place = 1; place <= requestObject.getPlacesPerRow(); place++) {
                seats.add(new SeatBlueprint(newSeatingPlan, activeType, row, place));
            }
        }

        newSeatingPlan.setSeats(seats);
        newSeatingPlan.setRows(requestObject.getRows());

        return seatingPlanRepository.save(newSeatingPlan);
    }

}
