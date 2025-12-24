package com.example.gestionmatch.exceptions;


public class EmailDejaUtiliseException extends Exception {


    public EmailDejaUtiliseException(String message) {
        super(message);
    }


    public EmailDejaUtiliseException(String message, Throwable cause) {
        super(message, cause);
    }
}