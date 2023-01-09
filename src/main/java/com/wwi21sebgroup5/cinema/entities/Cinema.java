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
@Table(name = "cinema")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cinema cinema = (Cinema) o;

        if (floors != cinema.floors) return false;
        if (cinemaRooms != cinema.cinemaRooms) return false;
        if (!Objects.equals(id, cinema.id)) return false;
        if (!Objects.equals(name, cinema.name)) return false;
        if (!Objects.equals(halls, cinema.halls)) return false;
        if (!Objects.equals(city, cinema.city)) return false;
        if (!Objects.equals(street, cinema.street)) return false;
        return Objects.equals(houseNumber, cinema.houseNumber);
    }

    @Override
    public int hashCode() {
        int hash = id != null ? id.hashCode() : 0;
        int prime = 31;

        hash = prime * hash + (name != null ? name.hashCode() : 0);
        hash = prime * hash + (halls != null ? halls.hashCode() : 0);
        hash = prime * hash + (city != null ? city.hashCode() : 0);
        hash = prime * hash + (street != null ? street.hashCode() : 0);
        hash = prime * hash + (houseNumber != null ? houseNumber.hashCode() : 0);
        hash = prime * hash + floors;
        hash = prime * hash + cinemaRooms;
        return hash;
    }
}
