package com.govtech.exception;

public class GovtechException extends RuntimeException {

    public GovtechException(String message) {
        super(message);
    }

    public GovtechException(String message, Throwable cause) {
        super(message, cause);
    }
}