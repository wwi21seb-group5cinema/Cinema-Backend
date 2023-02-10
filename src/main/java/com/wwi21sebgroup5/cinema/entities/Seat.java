package com.wwi21sebgroup5.cinema.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wwi21sebgroup5.cinema.enums.SeatState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "seat")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Seat {

    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "seattype_id", referencedColumnName = "id")
    @ToString.Exclude
    private SeatType seatType;

    @Enumerated(EnumType.STRING)
    private SeatState seatState;

    @Column
    @NotNull
    private int row;

    @Column
    @NotNull
    private int place;

    @Column
    private LocalDateTime expirationTimeStamp;

    @Column
    private UUID userId;


    public Seat(SeatType seatType, int row, int place) {
        this.seatType = seatType;
        this.row = row;
        this.place = place;
        this.seatState = SeatState.FREE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seat seat = (Seat) o;

        if (row != seat.row) return false;
        if (place != seat.place) return false;
        if (!Objects.equals(id, seat.id)) return false;
        if (!Objects.equals(seatType, seat.seatType)) return false;
        return Objects.equals(seatState, seat.seatState);
    }

    @Override
    public int hashCode() {
        int hash = id != null ? id.hashCode() : 0;
        int prime = 31;

        hash = prime * hash + (seatType != null ? seatType.hashCode() : 0);
        hash = prime * hash + (seatState != null ? seatState.hashCode() : 0);
        hash = prime * hash + row;
        hash = prime * hash + place;
        return hash;
    }
}
