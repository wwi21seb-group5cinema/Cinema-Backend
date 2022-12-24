package com.wwi21sebgroup5.cinema.requestObjects;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DirectorRequestObject {

    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String firstName;

    public DirectorRequestObject() {
        super();
    }

}
