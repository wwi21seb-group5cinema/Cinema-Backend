package com.wwi21sebgroup5.cinema.requestObjects;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AddImageReturnObject {

    @NotNull
    @NotEmpty
    private UUID id;

    @NotNull
    @NotEmpty
    private String type;

    @NotNull
    @NotEmpty
    private boolean compressed;

    public AddImageReturnObject(UUID id, String type, boolean compressed) {
        this.id = id;
        this.type = type;
        this.compressed = compressed;

    }

}
