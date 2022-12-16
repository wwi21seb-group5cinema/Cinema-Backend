package com.wwi21sebgroup5.cinema.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "cinemahall")
public class CinemaHall {

    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cinema_id", referencedColumnName = "id")
    private Cinema cinema;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seatingPlan_id", referencedColumnName = "id")
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

}
