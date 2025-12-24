package com.example.gestionmatch.services;


import org.mindrot.jbcrypt.BCrypt;

public class AuthentificationService {

    public static String hasherMotDePasse(String motDePasse) {
        return BCrypt.hashpw(motDePasse, BCrypt.gensalt());
    }

    public static String hasherMotDePasseAvecComplexite(String motDePasse, int workFactor) {
        if (workFactor < 10 || workFactor > 31) {
            throw new IllegalArgumentException("Work factor doit être entre 10 et 31");
        }
        return BCrypt.hashpw(motDePasse, BCrypt.gensalt(workFactor));
    }

    public static boolean verifierMotDePasse(String motDePasseCandidat, String hashStocke) {
        try {
            return BCrypt.checkpw(motDePasseCandidat, hashStocke);
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur lors de la vérification du mot de passe : " + e.getMessage());
            return false;
        }
    }

    public static boolean validerEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }

    public static boolean validerMotDePasse(String motDePasse) {
        if (motDePasse == null || motDePasse.length() < 8) {
            return false;
        }
        boolean aMajuscule = motDePasse.matches(".*[A-Z].*");
        boolean aMinuscule = motDePasse.matches(".*[a-z].*");
        boolean aChiffre = motDePasse.matches(".*[0-9].*");
        return aMajuscule && aMinuscule && aChiffre;
    }

    public static boolean validerMotDePasseStrict(String motDePasse) {
        if (motDePasse == null || motDePasse.length() < 12) {
            return false;
        }
        boolean aMajuscule = motDePasse.matches(".*[A-Z].*");
        boolean aMinuscule = motDePasse.matches(".*[a-z].*");
        boolean aChiffre = motDePasse.matches(".*[0-9].*");
        boolean aSpecial = motDePasse.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
        return aMajuscule && aMinuscule && aChiffre && aSpecial;
    }

    public static boolean validerUsername(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        String regex = "^[a-zA-Z][a-zA-Z0-9_]{3,19}$";
        return username.matches(regex);
    }

    public static int calculerForceMotDePasse(String motDePasse) {
        if (motDePasse == null || motDePasse.isEmpty()) {
            return 0;
        }
        int force = 0;
        if (motDePasse.length() >= 8) force += 20;
        if (motDePasse.length() >= 12) force += 10;
        if (motDePasse.length() >= 16) force += 10;
        if (motDePasse.matches(".*[A-Z].*")) force += 15;
        if (motDePasse.matches(".*[a-z].*")) force += 15;
        if (motDePasse.matches(".*[0-9].*")) force += 15;
        if (motDePasse.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) force += 15;
        return Math.min(force, 100);
    }
}
