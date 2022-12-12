package com.wwi21sebgroup5.cinema.requestObjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegistrationRequestObject {

    @NotNull
    @NotEmpty
    private String userName;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String plz;

    @NotNull
    @NotEmpty
    private String cityName;

    @NotNull
    @NotEmpty
    private String street;

    @NotNull
    @NotEmpty
    private String houseNumber;

    @NotNull
    @NotEmpty
    @JsonProperty
    private boolean isAdmin;

    public RegistrationRequestObject() {
        super();
    }

}
