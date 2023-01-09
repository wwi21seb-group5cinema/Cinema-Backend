package com.wwi21sebgroup5.cinema.components;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.wwi21sebgroup5.cinema.entities.SeatType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CsvDataLoaderTest {

    @InjectMocks
    private CsvDataLoader csvDataLoader;

    @Test
    @DisplayName("Test exception thrown when loading list")
    public void testExceptionThrownWhenLoadingList() {
        try (MockedStatic<CsvSchema> csvSchema = mockStatic(CsvSchema.class)) {
            csvSchema.when(CsvSchema::emptySchema).thenThrow(RuntimeException.class);

            List<SeatType> result = csvDataLoader.loadObjectList(SeatType.class, "/unknown/path");
            assertTrue(result.isEmpty());
        }
    }

}
