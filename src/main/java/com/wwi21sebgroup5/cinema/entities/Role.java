package com.wwi21sebgroup5.cinema.entities;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "role")
public class Role {

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
    private String description;

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
