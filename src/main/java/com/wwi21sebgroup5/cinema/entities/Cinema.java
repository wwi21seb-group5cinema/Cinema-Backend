package com.wwi21sebgroup5.cinema.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "cinema")
public class Cinema {

    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    @NotNull
    private String name;

    @OneToMany(fetch = FetchType.LAZY,
            targetEntity = CinemaHall.class,
            cascade = CascadeType.ALL,
            mappedBy = "cinema")
    @ToString.Exclude
    private List<CinemaHall> halls;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    @ToString.Exclude
    private City city;

    @Column
    @NotNull
    private String street;

    @Column
    @NotNull
    private String houseNumber;

    @Column
    @NotNull
    private int floors;

    @Transient
    private int cinemaRooms;

    public int getCinemaRooms() {
        return halls.size();
    }

    public Cinema(String name,
                  List<CinemaHall> halls,
                  City city,
                  String street,
                  String houseNumber,
                  int floors) {
        this.name = name;
        this.halls = halls;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.floors = floors;
    }
}
