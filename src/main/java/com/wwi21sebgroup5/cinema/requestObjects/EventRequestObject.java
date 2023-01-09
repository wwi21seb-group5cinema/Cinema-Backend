package com.wwi21sebgroup5.cinema.requestObjects;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class EventRequestObject {

    @NotNull
    @NotEmpty
    private UUID movieId;

    @NotNull
    @NotEmpty
    private UUID cinemaHallId;

    @NotNull
    @NotEmpty
    private String eventDateTime;

    public EventRequestObject() {
        super();
    }

}
