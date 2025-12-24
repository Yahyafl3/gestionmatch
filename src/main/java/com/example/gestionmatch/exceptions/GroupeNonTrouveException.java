package com.example.gestionmatch.exceptions;

public class GroupeNonTrouveException extends Exception {

    public GroupeNonTrouveException(String message) {
        super(message);
    }

    public GroupeNonTrouveException(String message, Throwable cause) {
        super(message, cause);
    }
}
