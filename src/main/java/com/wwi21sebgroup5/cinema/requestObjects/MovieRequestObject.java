package com.wwi21sebgroup5.cinema.requestObjects;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Data
public class MovieRequestObject {

    @NotEmpty
    @NotNull
    private String producerName;

    @NotEmpty
    @NotNull
    private String directorFirstName;

    @NotEmpty
    @NotNull
    private String directorLastName;

    @NotNull
    private Map<UUID, String> actors;

    @NotEmpty
    @NotNull
    private UUID image;

    @NotEmpty
    @NotNull
    private int fsk;

    @NotEmpty
    @NotNull
    private String genre;

    @NotEmpty
    @NotNull
    private String name;

    @NotEmpty
    @NotNull
    private String description;

    @NotEmpty
    @NotNull
    private float rating;

    @NotEmpty
    @NotNull
    private int length;

    @NotEmpty
    @NotNull
    private String start_date;

    @NotEmpty
    @NotNull
    private String end_date;

    public MovieRequestObject() {
        super();
    }

}
