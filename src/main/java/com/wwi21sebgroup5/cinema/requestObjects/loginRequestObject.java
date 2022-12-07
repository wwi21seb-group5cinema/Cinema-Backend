package com.wwi21sebgroup5.cinema.requestObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class loginRequestObject {

    private String userName;
    private String password;
    private String confirmPassword;

}
