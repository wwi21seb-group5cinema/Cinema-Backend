package com.wwi21sebgroup5.cinema.repositories;

import com.wwi21sebgroup5.cinema.entities.Cinema;
import com.wwi21sebgroup5.cinema.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, UUID> {

    Optional<Cinema> findByCityAndStreetAndHouseNumber(City city, String street, String houseNumber);
    
}
