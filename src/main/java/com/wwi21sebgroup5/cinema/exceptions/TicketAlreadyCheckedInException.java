package com.wwi21sebgroup5.cinema.exceptions;

public class TicketAlreadyCheckedInException extends Exception {

    public TicketAlreadyCheckedInException(String barcode) {
        super(String.format("Barcode %s is already checked in!", barcode));
    }

}
