package com.wwi21sebgroup5.cinema.repositories;

import com.wwi21sebgroup5.cinema.entities.SeatingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SeatingPlanRepository extends JpaRepository<SeatingPlan, UUID> {

    Optional<SeatingPlan> findByCinemaHall_Id(UUID cinemaHallId);

}
