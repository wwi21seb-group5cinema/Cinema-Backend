package com.wwi21sebgroup5.cinema.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "city")
public class City {
    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    @NotNull
    private String plz;

    @Column
    @NotNull
    private String name;

}
