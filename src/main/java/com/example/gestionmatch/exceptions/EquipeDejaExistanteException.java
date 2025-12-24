package com.example.gestionmatch.exceptions;

public class EquipeDejaExistanteException extends Exception {
    public EquipeDejaExistanteException(String message) {
        super(message);
    }

    public EquipeDejaExistanteException(String message, Throwable cause) {
        super(message, cause);
    }
}