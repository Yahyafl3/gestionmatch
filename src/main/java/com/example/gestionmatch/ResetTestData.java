package com.example.gestionmatch;

import java.sql.Connection;
import java.sql.Statement;

import com.example.gestionmatch.database.DatabaseConnection;

public class ResetTestData {

    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.println("\n========================================");
            System.out.println("🗑️  SUPPRESSION DES DONNÉES DE TEST");
            System.out.println("========================================\n");

            // Supprimer dans l'ordre (à cause des clés étrangères)
            stmt.execute("DELETE FROM joueur");
            System.out.println("✅ Joueurs supprimés");
            
            stmt.execute("DELETE FROM groupe_equipe");
            System.out.println("✅ Liaisons groupe-équipe supprimées");
            
            stmt.execute("DELETE FROM equipe");
            System.out.println("✅ Équipes supprimées");
            
            stmt.execute("DELETE FROM stade");
            System.out.println("✅ Stades supprimés");
            
            stmt.execute("DELETE FROM match_data");
            System.out.println("✅ Matchs supprimés");

            System.out.println("\n========================================");
            System.out.println("✅ DONNÉES SUPPRIMÉES");
            System.out.println("========================================\n");
            
            // Réinsérer les données
            InsertTestData.insererDonneesTest();

        } catch (Exception e) {
            System.err.println("❌ Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
