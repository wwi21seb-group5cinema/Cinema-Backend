package com.wwi21sebgroup5.cinema.requestObjects;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class SeatingPlanRequestObject {

    @NotNull
    @NotEmpty
    private UUID cinemaHallId;

    @NotNull
    @NotEmpty
    private int rows;

    @NotNull
    @NotEmpty
    private int placesPerRow;

}
