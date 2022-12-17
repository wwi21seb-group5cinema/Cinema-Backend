package com.wwi21sebgroup5.cinema.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "actor")
public class Actor {

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
    @Column
    @NotNull
    private Date birthdate;

    public Actor(String name, String firstName, Date birthdate) {
        this.name = name;
        this.firstName = firstName;
        this.birthdate = birthdate;
    }


}
