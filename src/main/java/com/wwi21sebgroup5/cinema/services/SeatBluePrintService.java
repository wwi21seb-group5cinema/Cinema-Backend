package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.repositories.SeatBlueprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatBluePrintService {

    @Autowired
    private SeatBlueprintRepository seatBlueprintRepository;

}
