package com.navneet.event_ticket_platform.Exceptions;

public class QRCodeGenerationException extends EventTicketException {
    public QRCodeGenerationException(String message) {
        super(message);
    }

    public QRCodeGenerationException() {
    }

    public QRCodeGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public QRCodeGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public QRCodeGenerationException(Throwable cause) {
        super(cause);
    }
}
