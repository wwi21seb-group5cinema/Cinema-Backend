package com.wwi21sebgroup5.cinema.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Table(name = "cinemahall")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class CinemaHall {

    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id", referencedColumnName = "id")
    @ToString.Exclude
    private Cinema cinema;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seatingplan_id", referencedColumnName = "id")
    private SeatingPlan seatingPlan;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private int floor;

    public CinemaHall(Cinema cinema, SeatingPlan seatingPlan, String name, int floor) {
        this.cinema = cinema;
        this.seatingPlan = seatingPlan;
        this.name = name;
        this.floor = floor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CinemaHall that = (CinemaHall) o;

        if (floor != that.floor) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(cinema, that.cinema)) return false;
        if (!Objects.equals(seatingPlan, that.seatingPlan)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int hash = id != null ? id.hashCode() : 0;
        int prime = 31;

        hash = prime * hash + (cinema != null ? cinema.hashCode() : 0);
        hash = prime * hash + (seatingPlan != null ? seatingPlan.hashCode() : 0);
        hash = prime * hash + (name != null ? name.hashCode() : 0);
        hash = prime * hash + floor;
        return hash;
    }
}
