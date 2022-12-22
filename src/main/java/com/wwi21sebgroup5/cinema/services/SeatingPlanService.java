package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.SeatingPlan;
import com.wwi21sebgroup5.cinema.repositories.SeatingPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatingPlanService {

    @Autowired
    private SeatingPlanRepository seatingPlanRepository;

    public List<SeatingPlan> getAllSeatingPlans() {
        return seatingPlanRepository.findAll();
    }

}
