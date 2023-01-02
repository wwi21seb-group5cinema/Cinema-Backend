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
@Table(name = "director")
public class Director {

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
    private String firstName;

    public Director(String name, String firstName) {
        this.name = name;
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Director director = (Director) o;

        if (!Objects.equals(id, director.id)) return false;
        if (!Objects.equals(name, director.name)) return false;
        return Objects.equals(firstName, director.firstName);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        return result;
    }
}
