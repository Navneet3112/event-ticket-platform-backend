package com.navneet.event_ticket_platform.Exceptions;


public class TicketNotFoundException extends EventTicketException {

    public TicketNotFoundException(String message) {
        super(message);
    }

    public TicketNotFoundException() {
    }

    public TicketNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TicketNotFoundException(Throwable cause) {
        super(cause);
    }

    public TicketNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
