package com.wwi21sebgroup5.cinema.entities;

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
@Table(name = "seattype")
public class SeatType {

    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private double price;

    public SeatType(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeatType seatType = (SeatType) o;

        if (Double.compare(seatType.price, price) != 0) return false;
        if (!Objects.equals(id, seatType.id)) return false;
        return Objects.equals(name, seatType.name);
    }

    @Override
    public int hashCode() {
        int hash = id != null ? id.hashCode() : 0;
        int prime = 31;

        hash = prime * hash + (name != null ? name.hashCode() : 0);
        long temp = Double.doubleToLongBits(price);
        hash = prime * hash + (int) (temp ^ (temp >>> 32));
        return hash;
    }
}
