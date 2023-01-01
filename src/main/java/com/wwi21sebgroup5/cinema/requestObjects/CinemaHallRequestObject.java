package com.wwi21sebgroup5.cinema.requestObjects;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class CinemaHallRequestObject {

    @NotNull
    @NotEmpty
    private UUID cinemaId;

    @NotNull
    @NotEmpty
    private int rows;

    @NotNull
    @NotEmpty
    private int placesPerRow;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private int floor;


}
