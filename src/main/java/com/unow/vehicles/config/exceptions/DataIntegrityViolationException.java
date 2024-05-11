package com.unow.vehicles.config.exceptions;

public class DataIntegrityViolationException extends RuntimeException {
    public DataIntegrityViolationException() {
        super();
    }

    public DataIntegrityViolationException(String message) {
        super(message);
    }
}
