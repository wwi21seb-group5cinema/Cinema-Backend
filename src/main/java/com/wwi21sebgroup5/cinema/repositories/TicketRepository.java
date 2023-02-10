package com.wwi21sebgroup5.cinema.repositories;

import com.wwi21sebgroup5.cinema.entities.Event;
import com.wwi21sebgroup5.cinema.entities.Seat;
import com.wwi21sebgroup5.cinema.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    @Override
    List<Ticket> findAll();

    Optional<Ticket> findById(UUID id);

    Optional<List<Ticket>> findTicketsByEvent_Id(UUID id);

    Optional<Ticket> findByEventAndSeat(Event event, Seat seat);

    Optional<Ticket> findByEvent_IdAndSeat_RowAndSeat_Place(UUID id, int row, int place);

    List<Ticket> findByBooking_User_Id(UUID id);
}
