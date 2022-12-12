package com.wwi21sebgroup5.cinema.controller;

import com.wwi21sebgroup5.cinema.services.CityService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CityControllerTest {

    @Mock
    CityService cityService;

    @InjectMocks
    CityController cityController;

}
