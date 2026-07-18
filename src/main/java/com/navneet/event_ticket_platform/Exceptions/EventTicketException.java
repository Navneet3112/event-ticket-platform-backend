package com.navneet.event_ticket_platform.Exceptions;




public class EventTicketException extends RuntimeException {

    public EventTicketException(String message) {
        super(message);
    }

    public EventTicketException() {
    }

    public EventTicketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EventTicketException(Throwable cause) {
        super(cause);
    }

    public EventTicketException(String message, Throwable cause) {
        super(message, cause);
    }
}
