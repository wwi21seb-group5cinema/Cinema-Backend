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
@Table(name = "seatblueprint")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class SeatBlueprint {

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

    @Column
    @NotNull
    private int row;

    @Column
    @NotNull
    private int place;

    public SeatBlueprint(SeatingPlan seatingPlan, SeatType seatType, int row, int place) {
        this.seatingPlan = seatingPlan;
        this.seatType = seatType;
        this.row = row;
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeatBlueprint that = (SeatBlueprint) o;

        if (row != that.row) return false;
        if (place != that.place) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(seatingPlan, that.seatingPlan)) return false;
        return Objects.equals(seatType, that.seatType);
    }

    @Override
    public int hashCode() {
        int hash = id != null ? id.hashCode() : 0;
        int prime = 31;

        hash = prime * hash + (seatingPlan != null ? seatingPlan.hashCode() : 0);
        hash = prime * hash + (seatType != null ? seatType.hashCode() : 0);
        hash = prime * hash + row;
        hash = prime * hash + place;
        return hash;
    }
}
