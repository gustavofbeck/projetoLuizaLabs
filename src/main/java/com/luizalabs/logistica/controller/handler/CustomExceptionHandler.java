package com.luizalabs.logistica.controller.handler;

import com.luizalabs.logistica.exception.DataBaseErrorException;
import com.luizalabs.logistica.exception.InvalidDateRangeException;
import com.luizalabs.logistica.exception.InvalidFilterWithOrderIdAndDateException;
import com.luizalabs.logistica.exception.InvalidOrdersFieldsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler({InvalidDateRangeException.class, InvalidFilterWithOrderIdAndDateException.class})
    public ResponseEntity<String> handleInvalidFilterException(final RuntimeException ex) {
        log.info("Handling with {}.", ex.getClass().getSimpleName());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler({InvalidOrdersFieldsException.class})
    public ResponseEntity<String> handleInvalidOrdersFieldsException(final RuntimeException ex) {
        log.info("Handling with InvalidOrdersFieldsException");
        return ResponseEntity.unprocessableEntity().body(ex.getMessage());
    }

    @ExceptionHandler({DataBaseErrorException.class})
    public ResponseEntity<String> handleDataBaseErrorException(final DataBaseErrorException ex) {
        log.info("Handling with DataBaseErrorException");
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}