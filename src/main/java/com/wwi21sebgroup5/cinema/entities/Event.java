package com.wwi21sebgroup5.cinema.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "event")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Event {

    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_hall_id", referencedColumnName = "id")
    @ToString.Exclude
    private CinemaHall cinemaHall;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = Ticket.class,
            mappedBy = "event")
    @ToString.Exclude
    private List<Ticket> tickets;

    @Column
    @NotNull
    private LocalDateTime eventDateTime;

    @Transient
    private LocalDate eventDay;

    @Transient
    private LocalTime eventTime;

    public Event(Movie movie, CinemaHall cinemaHall, List<Ticket> tickets, LocalDateTime eventDateTime) {
        this.movie = movie;
        this.cinemaHall = cinemaHall;
        this.tickets = tickets;
        this.eventDateTime = eventDateTime;
    }

    public LocalDate getEventDay() {
        return eventDateTime.toLocalDate();
    }

    public LocalTime getEventTime() {
        return eventDateTime.toLocalTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (!Objects.equals(id, event.id)) return false;
        if (!Objects.equals(movie, event.movie)) return false;
        if (!Objects.equals(cinemaHall, event.cinemaHall)) return false;
        if (!Objects.equals(tickets, event.tickets)) return false;
        if (!Objects.equals(eventTime, event.eventTime)) return false;
        return Objects.equals(eventDay, event.eventDay);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (movie != null ? movie.hashCode() : 0);
        result = 31 * result + (cinemaHall != null ? cinemaHall.hashCode() : 0);
        result = 31 * result + (tickets != null ? tickets.hashCode() : 0);
        result = 31 * result + (eventTime != null ? eventTime.hashCode() : 0);
        result = 31 * result + (eventDay != null ? eventDay.hashCode() : 0);
        return result;
    }

}
