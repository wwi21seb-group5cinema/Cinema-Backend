package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.SeatType;
import com.wwi21sebgroup5.cinema.repositories.SeatTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeatTypeServiceTest {

    @Mock
    SeatTypeRepository seatTypeRepository;

    @InjectMocks
    SeatTypeService seatTypeService;

    @Test
    @DisplayName("Test get seat type")
    public void testGetSeatType() {
        String name = "TestSeatType";
        SeatType seatType = new SeatType(name, 12.0);

        when(seatTypeRepository.findByName(name)).thenReturn(Optional.of(seatType));

        SeatType actualType = seatTypeService.getSeatType(name);
        assertEquals(seatType, actualType);
        actualType = seatTypeService.getSeatType(name);
        assertEquals(seatType, actualType);

        verify(seatTypeRepository, times(1)).findByName(name);
    }

    @Test
    @DisplayName("Test getting all seat types")
    public void testGetAlLSeatTypes() {
        SeatType firstType = new SeatType("Regular", 9.0),
                secondType = new SeatType("Loge", 11.0);
        List<SeatType> expectedTypes = List.of(firstType, secondType);

        when(seatTypeRepository.findAll()).thenReturn(expectedTypes);

        List<SeatType> actualTypes = seatTypeService.getAll();

        assertEquals(expectedTypes, actualTypes);
    }

    @Test
    @DisplayName("Test adding seat type")
    public void testAddSeatType() {
        SeatType seatType = new SeatType("Regular", 9.0);

        when(seatTypeRepository.save(seatType)).thenReturn(seatType);

        SeatType savedSeatType = seatTypeService.addSeatType(seatType);

        assertEquals(seatType, savedSeatType);
    }

    @Test
    @DisplayName("Test getting seat type by name")
    public void testGetSeatTypeByName() {
        String name = "Regular";
        SeatType seatType = new SeatType(name, 9.0);

        when(seatTypeRepository.findByName(name)).thenReturn(Optional.of(seatType));

        Optional<SeatType> actualType = seatTypeService.getByName(name);

        assertEquals(seatType, actualType.get());
    }

}
