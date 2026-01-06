package com.example.gestionmatch;

import java.sql.Connection;
import java.sql.Statement;

import com.example.gestionmatch.database.DatabaseConnection;

public class AjouterColonneImage {
    
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            System.out.println("🔧 Ajout de la colonne image_path...");
            
            // Vérifier si la colonne existe déjà
            String checkColumn = "SELECT COUNT(*) as count FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'equipe' AND COLUMN_NAME = 'image_path'";
            var rs = stmt.executeQuery(checkColumn);
            
            if (rs.next() && rs.getInt("count") == 0) {
                // La colonne n'existe pas, l'ajouter
                String addColumn = "ALTER TABLE equipe ADD COLUMN image_path VARCHAR(255) AFTER points";
                stmt.execute(addColumn);
                System.out.println("✅ Colonne image_path ajoutée avec succès!");
            } else {
                System.out.println("ℹ️  Colonne image_path existe déjà");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
