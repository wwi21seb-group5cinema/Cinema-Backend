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
@Table(name = "seatblueprint")
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

}
