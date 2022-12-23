package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.SeatingPlan;
import com.wwi21sebgroup5.cinema.repositories.SeatingPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SeatingPlanService {

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

}
