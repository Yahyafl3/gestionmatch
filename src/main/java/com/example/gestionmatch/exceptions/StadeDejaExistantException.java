package com.example.gestionmatch.exceptions;

public class StadeDejaExistantException extends Exception {
    public StadeDejaExistantException(String message) {
        super(message);
    }

    public StadeDejaExistantException(String message, Throwable cause) {
        super(message, cause);
    }
}