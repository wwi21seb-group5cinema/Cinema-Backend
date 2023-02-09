package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.SeatType;
import com.wwi21sebgroup5.cinema.repositories.SeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class SeatTypeService {

    public static final String REGULAR_TYPE = "REGULAR";
    public static final String LOGE_TYPE = "LOGE";

    private static final HashMap<String, SeatType> seatTypes = new HashMap<>();

    @Autowired
    SeatTypeRepository seatTypeRepository;

    public SeatType getSeatType(String seatType) {
        return seatTypes.computeIfAbsent(seatType,
                (key) -> this.getByName(key).get());
    }

    public List<SeatType> getAll() {
        return seatTypeRepository.findAll();
    }

    public SeatType addSeatType(SeatType seatType) {
        Optional<SeatType> foundSeatType = seatTypeRepository.findByName(seatType.getName());
        return foundSeatType.orElseGet(() -> seatTypeRepository.save(seatType));
    }

    public Optional<SeatType> getByName(String name) {
        return seatTypeRepository.findByName(name);
    }

}
