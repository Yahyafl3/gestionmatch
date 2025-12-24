package com.example.gestionmatch.exceptions;

public class StadeNonTrouveException extends Exception {
    public StadeNonTrouveException(String message) {
        super(message);
    }

    public StadeNonTrouveException(String message, Throwable cause) {
        super(message, cause);
    }
}