package com.example.gestionmatch;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.example.gestionmatch.database.DatabaseConnection;

public class VerifierEquipes {
    
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            System.out.println("📋 Vérification des équipes:");
            System.out.println("=" .repeat(60));
            
            ResultSet rs = stmt.executeQuery("SELECT id, nom, pays, image_path FROM equipe");
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String pays = rs.getString("pays");
                String imagePath = rs.getString("image_path");
                
                System.out.println("ID: " + id);
                System.out.println("Nom: " + nom);
                System.out.println("Pays: " + pays);
                System.out.println("Image Path: " + (imagePath != null ? imagePath : "NULL"));
                System.out.println("-".repeat(60));
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
