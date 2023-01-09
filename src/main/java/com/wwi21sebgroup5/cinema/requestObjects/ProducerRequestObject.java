package com.wwi21sebgroup5.cinema.requestObjects;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProducerRequestObject {

    @NotNull
    @NotEmpty
    private String name;

    public ProducerRequestObject() {
        super();
    }

}
