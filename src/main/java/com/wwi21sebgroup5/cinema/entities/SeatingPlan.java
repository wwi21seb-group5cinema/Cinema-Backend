package com.wwi21sebgroup5.cinema.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "seatingplan")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class SeatingPlan {

    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(mappedBy = "seatingPlan")
    private CinemaHall cinemaHall;

    @OneToMany(mappedBy = "seatingPlan", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<SeatBlueprint> seats;

    @Column
    @NotNull
    private int rows;

    public SeatingPlan(CinemaHall cinemaHall, int rows) {
        this.cinemaHall = cinemaHall;
        this.rows = rows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeatingPlan that = (SeatingPlan) o;

        if (rows != that.rows) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(cinemaHall, that.cinemaHall)) return false;
        return Objects.equals(seats, that.seats);
    }

    @Override
    public int hashCode() {
        int hash = id != null ? id.hashCode() : 0;
        int prime = 31;

        hash = prime * hash + (cinemaHall != null ? cinemaHall.hashCode() : 0);
        hash = prime * hash + (seats != null ? seats.hashCode() : 0);
        hash = prime * hash + rows;
        return hash;
    }
}
