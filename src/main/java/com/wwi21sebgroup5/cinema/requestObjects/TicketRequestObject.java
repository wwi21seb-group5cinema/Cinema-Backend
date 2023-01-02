package com.wwi21sebgroup5.cinema.requestObjects;

import com.wwi21sebgroup5.cinema.entities.Event;
import com.wwi21sebgroup5.cinema.entities.Seat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TicketRequestObject {
    @NotNull
    @NotEmpty
    private Event event;

    @NotNull
    @NotEmpty
    private Seat seat;

    public TicketRequestObject(){
        super();
    }
}
