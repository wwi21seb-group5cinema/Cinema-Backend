package com.wwi21sebgroup5.cinema.requestObjects;


import com.wwi21sebgroup5.cinema.entities.Event;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookingRequestObject {

    @NotNull
    @NotEmpty
    private Event event;

    @NotNull
    @NotEmpty
    private int rowNumber;

    @NotNull
    @NotEmpty
    private int seatNumber;

    public BookingRequestObject(){super();}
}
