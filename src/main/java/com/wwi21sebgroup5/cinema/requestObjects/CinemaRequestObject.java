package com.wwi21sebgroup5.cinema.requestObjects;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CinemaRequestObject {

    @NotNull
    @NotEmpty
    private String name;

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
    private int floors;

    public CinemaRequestObject() {
        super();
    }

}
