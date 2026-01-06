package com.example.gestionmatch;

import java.sql.Connection;
import java.sql.Statement;

import com.example.gestionmatch.database.DatabaseConnection;

public class SupprimerEquipes {
    
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            System.out.println("🗑️  Suppression de toutes les équipes...");
            
            // Désactiver les contraintes
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            
            // Supprimer toutes les équipes
            int count = stmt.executeUpdate("DELETE FROM equipe");
            
            // Réinitialiser l'auto-increment
            stmt.execute("ALTER TABLE equipe AUTO_INCREMENT = 1");
            
            // Réactiver les contraintes
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
            
            System.out.println("✅ " + count + " équipe(s) supprimée(s) avec succès!");
            
        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
