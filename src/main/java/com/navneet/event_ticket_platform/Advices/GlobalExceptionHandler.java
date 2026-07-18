package com.navneet.event_ticket_platform.Advices;

import com.navneet.event_ticket_platform.Exceptions.*;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ApiError> handleTicketNotFoundException(TicketNotFoundException ex) {
        log.error("Caught TicketNotFoundException", ex);
        ApiError error = new ApiError();
        error.setError("Ticket not found");
        error.setStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TicketSoldOutException.class)
    public ResponseEntity<ApiError> handleQRCodeGenerationException(TicketSoldOutException ex) {
        log.error("Caught TicketSoldOutException", ex);
        ApiError error = new ApiError();
        error.setError("Tickets are Sold Out for this TicketType");
        error.setStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QRCodeNotFoundException.class)
    public ResponseEntity<ApiError> handleQRCodeGenerationException(QRCodeNotFoundException ex) {
        log.error("Caught QRCodeNotFoundException", ex);
        ApiError error = new ApiError();
        error.setError("QRCode Not Found");
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(QRCodeGenerationException.class)
    public ResponseEntity<ApiError> handleQRCodeGenerationException(QRCodeGenerationException ex) {
        log.error("Caught QRCodeGenerationException", ex);
        ApiError error = new ApiError();
        error.setError("Unable to Generate QR Code");
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EventUpdateException.class)
    public ResponseEntity<ApiError> handleEventUpdateException(EventUpdateException ex) {
        log.error("Caught EventUpdateException", ex);
        ApiError error = new ApiError();
        error.setError("Unable to update event");
        error.setStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketTypeNotFoundException.class)
    public ResponseEntity<ApiError> handleTicketTypeNotFoundException(TicketTypeNotFoundException ex) {
        log.error("Caught TicketTypeNotFoundException", ex);
        ApiError error = new ApiError();
        error.setError("TicketType not found");
        error.setStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ApiError> handleEventNotFoundException(EventNotFoundException ex) {
        log.error("Caught EventNotFoundException", ex);
        ApiError error = new ApiError();
        error.setError("Event not found");
        error.setStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex) {
        log.error("Caught UserNotFoundException", ex);
        ApiError error = new ApiError();
        error.setError("User not found");
        error.setStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Caught MethodArgumentNotValidException", ex);
        ApiError error = new ApiError();

        BindingResult bindingResult=ex.getBindingResult();
        List<FieldError> fieldErrors=bindingResult.getFieldErrors();

        String errorMessage=fieldErrors.stream()
                .findFirst()
                .map(fieldError ->
                        fieldError.getField()+":"+fieldError.getDefaultMessage()
                ).orElse("Validation error occurred");
        error.setError(errorMessage);
        error.setStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("Caught ConstraintViolationException", ex);
        ApiError error = new ApiError();
        String errorMessage=ex.getConstraintViolations().
                stream()
                .findFirst()
                    .map(violation->
                            violation.getPropertyPath()+": "+violation.getMessage()
                    ).orElse("ConstraintViolationError occurred");
        error.setError(errorMessage);
        error.setStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception exception) {
        log.error("Caught Exception", exception);
        ApiError error = new ApiError();
        error.setError("An unknown error occurred");
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
