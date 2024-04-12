package com.luizalabs.logistica.exception;

import java.io.Serial;

public class DataBaseErrorException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1134966056036613550L;

    public DataBaseErrorException(final String message) {
        super(message);
    }
}