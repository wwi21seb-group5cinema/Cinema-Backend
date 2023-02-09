package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.exceptions.*;
import com.wwi21sebgroup5.cinema.requestObjects.LoginRequestObject;
import com.wwi21sebgroup5.cinema.requestObjects.RegistrationRequestObject;
import com.wwi21sebgroup5.cinema.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(path = "/register")
    public ResponseEntity<Object> register(@RequestBody RegistrationRequestObject registrationObject) {
        User newUser;

        try {
            newUser = loginService.register(registrationObject);
        } catch (UserAlreadyExistsException | EmailAlreadyExistsException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestObject loginObject) {
        User user;

        try {
            user = loginService.login(loginObject);
        } catch (EmailNotFoundException enfE) {
            return new ResponseEntity<>(enfE.getMessage(), HttpStatus.NOT_FOUND);
        } catch (PasswordsNotMatchingException | UserNotEnabledException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(path = "/confirm", params = "token")
    public ResponseEntity<Object> confirmToken(@RequestParam String token) {
        try {
            loginService.confirmToken(token);
        } catch (TokenNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (TokenExpiredException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (TokenAlreadyConfirmedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.ALREADY_REPORTED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "forgotPassword", params = "email")
    public ResponseEntity<Object> forgotPassword(@RequestParam String email) {
        try {
            loginService.forgotPassword(email);
        } catch (EmailNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "update", params = "id")
    public ResponseEntity<Object> updateData(@RequestBody RegistrationRequestObject requestObject,
                                             @RequestParam UUID id) {
        try {
            loginService.updateData(requestObject, id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
