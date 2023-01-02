package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.SeatType;
import com.wwi21sebgroup5.cinema.repositories.SeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatTypeService {

    public static final String REGULAR_TYPE = "REGULAR";
    public static final String LOGE_TYPE = "LOGE";


    @Autowired
    SeatTypeRepository seatTypeRepository;

    public List<SeatType> getAll() {
        return seatTypeRepository.findAll();
    }

    public SeatType addSeatType(SeatType seatType) {
        return seatTypeRepository.save(seatType);
    }

    public Optional<SeatType> getByName(String name) {
        return seatTypeRepository.findByName(name);
    }

}
