package com.example.gestionmatch;

import org.mindrot.jbcrypt.BCrypt;

public class TestBCrypt {
    public static void main(String[] args) {
        String motDePasse = "Admin123";
        
        // Générer un nouveau hash
        String nouveauHash = BCrypt.hashpw(motDePasse, BCrypt.gensalt(10));
        System.out.println("Nouveau hash pour 'Admin123': " + nouveauHash);
        
        // Tester avec le hash existant
        String hashExistant = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";
        boolean match = BCrypt.checkpw(motDePasse, hashExistant);
        System.out.println("Le mot de passe 'Admin123' correspond au hash existant: " + match);
        
        // Tester avec différents mots de passe
        String[] testPasswords = {"Admin123", "admin123", "ADMIN123", "Admin@123"};
        for (String pwd : testPasswords) {
            boolean result = BCrypt.checkpw(pwd, hashExistant);
            System.out.println("Test avec '" + pwd + "': " + result);
        }
    }
}
