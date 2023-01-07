package com.wwi21sebgroup5.cinema.repositories;

import com.wwi21sebgroup5.cinema.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findByMovie_Id(UUID id);

    List<Event> findByEventDateTimeIsBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Event> findByMovie_IdAndEventDateTimeIsBetween(UUID id, LocalDateTime startDate, LocalDateTime endDate);

}
