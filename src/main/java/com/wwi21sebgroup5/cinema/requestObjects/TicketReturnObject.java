package com.wwi21sebgroup5.cinema.requestObjects;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
public class TicketReturnObject {

    private UUID id;

    private String title;

    private LocalDateTime date;

    private String cinemaHall;

    private int row;

    private int place;

    public TicketReturnObject() {
        super();
    }

}
