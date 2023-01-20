package com.wwi21sebgroup5.cinema.exceptions;

public class TicketNotPaidException extends Exception {

    public TicketNotPaidException(String barcode) {
        super(String.format("Ticket %s was not paid yet!", barcode));
    }

}
