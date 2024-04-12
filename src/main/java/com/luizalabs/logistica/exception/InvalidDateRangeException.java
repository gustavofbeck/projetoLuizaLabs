package com.luizalabs.logistica.exception;

import java.io.Serial;

public class InvalidDateRangeException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -7449190765242253157L;

    public InvalidDateRangeException(final String message) {
        super(message);
    }
}