package com.wwi21sebgroup5.cinema.entities;

import com.wwi21sebgroup5.cinema.exceptions.FSKNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FskTest {

    @Test
    @DisplayName("test parsing from int to fsk")
    public void testParsingFromIntToFsk() {
        List<FSK> expectedFSKs = List.of(FSK.ZERO, FSK.SIX, FSK.TWELVE, FSK.SIXTEEN, FSK.EIGHTEEN);
        List<Integer> integers = List.of(0, 6, 12, 16, 18);

        for (int i = 0; i < expectedFSKs.size(); i++) {
            FSK actualFsk = null;

            try {
                actualFsk = FSK.getFSKFromInt(integers.get(i));
            } catch (FSKNotFoundException e) {
                fail("Error when parsing int to Fsk");
            }

            assertEquals(expectedFSKs.get(i), actualFsk);
        }

        try {
            FSK.getFSKFromInt(123);
            fail("No exception thrown when giving wrong input");
        } catch (FSKNotFoundException e) {
            // Test successful
        }
    }

}
