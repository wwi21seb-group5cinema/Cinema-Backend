package com.wwi21sebgroup5.cinema.requestObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class registrationRequestObject {

    private String uesrName;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String email;
    private String plz;
    private String cityName;
    private String street;
    private String houseNumber;

}
