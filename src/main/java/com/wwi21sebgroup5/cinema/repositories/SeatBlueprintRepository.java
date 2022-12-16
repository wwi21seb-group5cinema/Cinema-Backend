package com.wwi21sebgroup5.cinema.repositories;

import com.wwi21sebgroup5.cinema.entities.SeatBlueprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeatBlueprintRepository extends JpaRepository<SeatBlueprint, UUID> {

}
