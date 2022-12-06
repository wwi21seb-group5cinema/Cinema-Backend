package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.entities.User;
import com.wwi21sebgroup5.cinema.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(name = "/v1/user")
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping(name = "/register")
    public User register(String username)  {
        return userService.register(username);
    }

}
