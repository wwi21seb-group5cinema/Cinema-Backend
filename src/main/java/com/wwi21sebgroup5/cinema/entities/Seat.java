package com.wwi21sebgroup5.cinema.entities;

import com.wwi21sebgroup5.cinema.enums.SeatState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "seat")
public class Seat {

    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "seatingplan_id", referencedColumnName = "id")
    @ToString.Exclude
    private SeatingPlan seatingPlan;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "seattype_id", referencedColumnName = "id")
    @ToString.Exclude
    private SeatType seatType;

    @Enumerated(EnumType.STRING)
    private SeatState seatState;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @ToString.Exclude
    private Event event;

    @Column
    @NotNull
    private int row;

    @Column
    @NotNull
    private int place;

    public Seat(SeatingPlan seatingPlan, SeatType seatType, Event event, int row, int place) {
        this.seatingPlan = seatingPlan;
        this.seatType = seatType;
        this.event = event;
        this.row = row;
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seat seat = (Seat) o;

        if (row != seat.row) return false;
        if (place != seat.place) return false;
        if (!Objects.equals(id, seat.id)) return false;
        if (!Objects.equals(seatingPlan, seat.seatingPlan)) return false;
        if (!Objects.equals(seatType, seat.seatType)) return false;
        if (seatState != seat.seatState) return false;
        return Objects.equals(event, seat.event);
    }

    @Override
    public int hashCode() {
        int hash = id != null ? id.hashCode() : 0;
        int prime = 31;

        hash = prime * hash + (seatingPlan != null ? seatingPlan.hashCode() : 0);
        hash = prime * hash + (seatType != null ? seatType.hashCode() : 0);
        hash = prime * hash + (seatState != null ? seatState.hashCode() : 0);
        hash = prime * hash + (event != null ? event.hashCode() : 0);
        hash = prime * hash + row;
        hash = prime * hash + place;
        return hash;
    }
}
