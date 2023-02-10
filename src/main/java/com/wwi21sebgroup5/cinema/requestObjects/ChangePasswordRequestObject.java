package com.wwi21sebgroup5.cinema.requestObjects;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class ChangePasswordRequestObject {

    private UUID id;

    private String newPassword;

    public ChangePasswordRequestObject() {
        super();
    }

}
