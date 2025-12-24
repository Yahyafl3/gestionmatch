package com.example.gestionmatch.exceptions;

public class GroupeDejaExistantException extends Exception {

    public GroupeDejaExistantException(String message) {
        super(message);
    }

    public GroupeDejaExistantException(String message, Throwable cause) {
        super(message, cause);
    }
}