package com.example.gestionmatch.exceptions;

public class EquipeNonTrouveeException extends Exception {
    public EquipeNonTrouveeException(String message) {
        super(message);
    }

    public EquipeNonTrouveeException(String message, Throwable cause) {
        super(message, cause);
    }
}