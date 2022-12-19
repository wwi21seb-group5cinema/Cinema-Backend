package com.wwi21sebgroup5.cinema.entities;
// Relation between Actor in Movie. Many Actors act in many movies

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "ActsIn")
public class ActsIn {

    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "Movie_Id", referencedColumnName = "id")
    @ToString.Exclude
    private Movie movie;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "Actor_Id", referencedColumnName = "id")
    @ToString.Exclude
    private Actor actor;

    public ActsIn(Movie movie, Actor actor) {
        this.movie = movie;
        this.actor = actor;
    }

}
