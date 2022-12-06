package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/city")
public class CityController {

    @Autowired
    CityService cityService;

}
