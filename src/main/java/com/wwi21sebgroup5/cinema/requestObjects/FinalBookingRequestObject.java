package com.wwi21sebgroup5.cinema.requestObjects;


import com.wwi21sebgroup5.cinema.entities.Event;
import com.wwi21sebgroup5.cinema.entities.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@AllArgsConstructor
@Data
public class FinalBookingRequestObject {

    @NotNull
    @NotEmpty
    private UUID eventID;

    @NotNull
    @NotEmpty
    private UUID userID;

    @NotNull
    @NotEmpty
    private int row;

    @NotNull
    @NotEmpty
    private int place;

    public FinalBookingRequestObject(){super();}
}