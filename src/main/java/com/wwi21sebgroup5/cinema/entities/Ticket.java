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
@Table(name = "ticket")
public class Ticket {

    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    @OneToOne
    @JoinColumn(name = "qrcode_id", referencedColumnName = "id")
    private QR_Code qr_code;

    @OneToOne
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    private Seat seat;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private Booking booking;

    public Ticket(Event pEvent, Seat pSeat) {
        event = pEvent;
        seat = pSeat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (!Objects.equals(id, ticket.id)) return false;
        if (!Objects.equals(event, ticket.event)) return false;
        if (!Objects.equals(qr_code, ticket.qr_code)) return false;
        if (!Objects.equals(seat, ticket.seat)) return false;
        return Objects.equals(booking, ticket.booking);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (event != null ? event.hashCode() : 0);
        result = 31 * result + (qr_code != null ? qr_code.hashCode() : 0);
        result = 31 * result + (seat != null ? seat.hashCode() : 0);
        result = 31 * result + (booking != null ? booking.hashCode() : 0);
        return result;
    }
}

