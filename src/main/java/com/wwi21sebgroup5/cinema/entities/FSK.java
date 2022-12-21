package com.wwi21sebgroup5.cinema.entities;

import com.wwi21sebgroup5.cinema.exceptions.FSKNotFoundException;

public enum FSK {
    ZERO, SIX, TWELVE, SIXTEEN, EIGHTEEN;


    /**
     * @param value
     * @return The enumeration-value matching the parameter
     * @throws FSKNotFoundException
     */
    public static FSK getFSKFromInt(int value) throws FSKNotFoundException {
        switch (value) {
            case 0:
                return FSK.ZERO;
            case 6:
                return FSK.SIX;
            case 12:
                return FSK.TWELVE;
            case 16:
                return FSK.SIXTEEN;
            case 18:
                return FSK.EIGHTEEN;
            default:
                throw new FSKNotFoundException(value);
        }
    }
}
