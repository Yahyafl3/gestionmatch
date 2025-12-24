package com.example.gestionmatch.model;

public enum TypeUtilisateur {
    ADMIN("Administrateur"),
    USER("Utilisateur"),
    MODERATEUR("Modérateur");

    private final String label;

    TypeUtilisateur(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
