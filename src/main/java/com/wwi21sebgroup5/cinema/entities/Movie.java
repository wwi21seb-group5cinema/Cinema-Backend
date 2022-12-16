package com.wwi21sebgroup5.cinema.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Producer_id", referencedColumnName = "id")
    @ToString.Exclude
    private Producer producer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "Director_id", referencedColumnName = "id")
    @ToString.Exclude
    private Director director;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "FSK_id", referencedColumnName = "id")
    private FSK fsk;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "Genre_id", referencedColumnName = "id")
    private Genre genre;

    @NotNull
    @Column
    private Date start_date;

    @NotNull
    @Column
    private Date end_date;

    }
