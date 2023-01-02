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
@Table(name = "ticket")
public class Ticket {

    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "Event_id", referencedColumnName = "id")
    private Event event;

    @OneToOne
    @JoinColumn(name = "QR_Code_id", referencedColumnName = "id")
    private QR_Code qr_code;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "Seat_id", referencedColumnName = "id")
    private Seat seat;

/*
    @Column
    private String event;

    @Column
    private int seat;
*/

    public Ticket(Event pEvent, Seat pSeat){
        event = pEvent;
        seat = pSeat;
    }
}

