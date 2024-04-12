package com.luizalabs.logistica.exception;

import java.io.Serial;

public class InvalidFilterWithOrderIdAndDateException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -357851961742734209L;

    public InvalidFilterWithOrderIdAndDateException(final String message) {
        super(message);
    }
}