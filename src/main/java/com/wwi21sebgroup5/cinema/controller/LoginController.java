package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.exceptions.PasswordsNotMatchingException;
import com.wwi21sebgroup5.cinema.requestObjects.LoginRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.RegistrationRequestObject;
import com.wwi21sebgroup5.cinema.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(path = "/register")
    public ResponseEntity<Object> register(RegistrationRequestObject registrationObject) {
        User newUser;

        try {
            newUser = loginService.register(registrationObject);
        } catch (PasswordsNotMatchingException e) {
            return new ResponseEntity<>(e, HttpStatus.EXPECTATION_FAILED);
        }

        return new ResponseEntity<>(newUser.getId(), HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Object> login(LoginRequestObject loginObject) {
        User loginUser;

        try {
            loginUser = loginService.login(loginObject);
        } catch (PasswordsNotMatchingException | UsernameNotFoundException ex) {
            return new ResponseEntity<>(ex, HttpStatus.EXPECTATION_FAILED);
        }

        return new ResponseEntity<>(loginUser.getId(), HttpStatus.OK);
    }

}
