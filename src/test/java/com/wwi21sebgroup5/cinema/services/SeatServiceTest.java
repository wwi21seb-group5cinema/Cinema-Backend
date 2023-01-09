package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.Seat;
import com.wwi21sebgroup5.cinema.repositories.SeatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SeatServiceTest {

    @Mock
    SeatRepository seatRepository;

    @InjectMocks
    SeatService seatService;

    @Test
    @DisplayName("Test saving")
    public void testSaving() {
        Seat seat = new Seat();

        when(seatRepository.save(seat)).thenReturn(seat);

        assertEquals(seat, seatService.save(seat));
    }

}
