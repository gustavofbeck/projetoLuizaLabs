package com.luizalabs.logistica.exception;

import java.io.Serial;

public class InvalidOrdersFieldsException extends RuntimeException {


    @Serial
    private static final long serialVersionUID = -30862850858478932L;

    public InvalidOrdersFieldsException(final String message) {
        super(message);
    }
}
