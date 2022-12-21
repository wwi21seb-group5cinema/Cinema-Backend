package com.wwi21sebgroup5.cinema.entities;
// Relation between Actor in Movie. Many Actors act in many movies

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActsIn actsIn)) return false;

        if (!Objects.equals(id, actsIn.id)) return false;
        if (!Objects.equals(movie, actsIn.movie)) return false;
        return Objects.equals(actor, actsIn.actor);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (movie != null ? movie.hashCode() : 0);
        result = 31 * result + (actor != null ? actor.hashCode() : 0);
        return result;
    }
}
