package com.example.gestionmatch.exceptions;

/**
 * Exception levée lorsqu'un nom d'utilisateur existe déjà dans le système
 */
public class UsernameDejaExistantException extends Exception {

    /**
     * Constructeur avec message personnalisé
     *
     * @param message Le message d'erreur à afficher
     */
    public UsernameDejaExistantException(String message) {
        super(message);
    }

    /**
     * Constructeur avec message et cause
     *
     * @param message Le message d'erreur
     * @param cause La cause de l'exception
     */
    public UsernameDejaExistantException(String message, Throwable cause) {
        super(message, cause);
    }
}