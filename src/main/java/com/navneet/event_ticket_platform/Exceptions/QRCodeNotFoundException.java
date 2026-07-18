package com.navneet.event_ticket_platform.Exceptions;

public class QRCodeNotFoundException extends EventTicketException {
    public QRCodeNotFoundException(String message) {
        super(message);
    }

    public QRCodeNotFoundException() {
    }

    public QRCodeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public QRCodeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public QRCodeNotFoundException(Throwable cause) {
        super(cause);
    }
}
