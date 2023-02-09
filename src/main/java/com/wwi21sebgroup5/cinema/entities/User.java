package com.wwi21sebgroup5.cinema.entities;

import com.wwi21sebgroup5.cinema.enums.Role;
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

    @Enumerated(EnumType.STRING)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    @ToString.Exclude
    private City city;

    @Column
    @NotNull
    private String street;

    @Column
    @NotNull
    private String houseNumber;

    @Column
    @NotNull
    private Boolean enabled;

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
        this.enabled = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(id, user.id)) return false;
        if (!Objects.equals(userName, user.userName)) return false;
        if (!Objects.equals(password, user.password)) return false;
        if (role != user.role) return false;
        if (!Objects.equals(firstName, user.firstName)) return false;
        if (!Objects.equals(lastName, user.lastName)) return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(city, user.city)) return false;
        if (!Objects.equals(street, user.street)) return false;
        return Objects.equals(houseNumber, user.houseNumber);
    }

    @Override
    public int hashCode() {
        int hash = id != null ? id.hashCode() : 0;
        int prime = 31;

        hash = prime * hash + (userName != null ? userName.hashCode() : 0);
        hash = prime * hash + (password != null ? password.hashCode() : 0);
        hash = prime * hash + (role != null ? role.hashCode() : 0);
        hash = prime * hash + (firstName != null ? firstName.hashCode() : 0);
        hash = prime * hash + (lastName != null ? lastName.hashCode() : 0);
        hash = prime * hash + (email != null ? email.hashCode() : 0);
        hash = prime * hash + (city != null ? city.hashCode() : 0);
        hash = prime * hash + (street != null ? street.hashCode() : 0);
        hash = prime * hash + (houseNumber != null ? houseNumber.hashCode() : 0);
        return hash;
    }
}
