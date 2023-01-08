package com.wwi21sebgroup5.cinema.enums;

import com.wwi21sebgroup5.cinema.exceptions.FSKNotFoundException;

public enum FSK {
    ZERO, SIX, TWELVE, SIXTEEN, EIGHTEEN;


    /**
     * @param value Integer that should be parsed
     * @return The enumeration-value matching the parameter
     * @throws FSKNotFoundException If integer can't be associated with a correct FSK-value
     */
    public static FSK getFSKFromInt(int value) throws FSKNotFoundException {
        return switch (value) {
            case 0 -> FSK.ZERO;
            case 6 -> FSK.SIX;
            case 12 -> FSK.TWELVE;
            case 16 -> FSK.SIXTEEN;
            case 18 -> FSK.EIGHTEEN;
            default -> throw new FSKNotFoundException(value);
        };
    }
}
