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
@Table(name = "User")
public class User {

    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    @NotNull
    private String userName;

    @Column
    @NotNull
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @Column
    @NotNull
    private String firstName;

    @Column
    @NotNull
    private String lastName;

    @Column
    @NotNull
    private String email;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @Column
    @NotNull
    private String street;

    @Column
    @NotNull
    private String houseNumber;

    @Column
    @NotNull
    private boolean locked;

    @Column
    @NotNull
    private boolean enabled;

    public User(String userName,
                String password,
                Role role,
                String firstName,
                String lastName,
                String email,
                City city,
                String street,
                String houseNumber) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.locked = false;
        this.enabled = false;
    }

}
