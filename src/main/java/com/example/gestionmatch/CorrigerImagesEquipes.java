package com.example.gestionmatch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.gestionmatch.database.DatabaseConnection;

/**
 * Programme pour corriger automatiquement les chemins d'images des équipes
 * dans la base de données pour qu'ils correspondent aux fichiers disponibles.
 */
public class CorrigerImagesEquipes {

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("  CORRECTION DES IMAGES DES ÉQUIPES");
        System.out.println("===========================================\n");

        try (Connection conn = DatabaseConnection.getConnection()) {
            
            // 1. Afficher les équipes actuelles
            System.out.println("📋 Équipes dans la base de données:");
            afficherEquipes(conn);
            
            // 2. Corriger les images
            System.out.println("\n🔧 Correction des chemins d'images...\n");
            int nbCorrections = corrigerImages(conn);
            
            // 3. Afficher le résultat
            System.out.println("\n✅ " + nbCorrections + " équipes corrigées\n");
            System.out.println("📋 Équipes après correction:");
            afficherEquipes(conn);
            
            System.out.println("\n===========================================");
            System.out.println("  CORRECTION TERMINÉE");
            System.out.println("===========================================");
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void afficherEquipes(Connection conn) throws SQLException {
        String sql = "SELECT id, nom, pays, image_path FROM equipe ORDER BY nom";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String pays = rs.getString("pays");
                String imagePath = rs.getString("image_path");
                
                String imageInfo = (imagePath != null && !imagePath.isEmpty()) 
                    ? "🏴 " + imagePath 
                    : "❌ Pas d'image";
                
                System.out.println(String.format("  %d. %-20s %-20s %s", id, nom, pays, imageInfo));
            }
        }
    }

    private static int corrigerImages(Connection conn) throws SQLException {
        int nbCorrections = 0;
        
        // Tableau de correspondances: nom/pays -> fichier image
        String[][] correspondances = {
            // Afrique
            {"algerie", "algerie.png"},
            {"maroc", "maroc.png"},
            {"tunisie", "tunisie.png"},
            {"egypte", "egypte.png"},
            {"senegal", "senegal.png"},
            {"nigeria", "nigeria.png"},
            {"cameroun", "cameroun.png"},
            {"ghana", "ghana.png"},
            {"cote", "cote_d_ivoire.png"},  // Côte d'Ivoire
            {"ivoire", "cote_d_ivoire.png"},
            {"afrique", "afrique_du_sud.png"},  // Afrique du Sud
            
            // Europe
            {"france", "france.png"},
            {"allemagne", "allemagne.png"},
            {"espagne", "espagne.png"},
            {"italie", "italie.png"},
            {"angleterre", "angleterre.png"},
            {"portugal", "portugal.png"},
            {"pays", "pays_bas.png"},  // Pays-Bas
            {"belgique", "belgique.png"},
            {"croatie", "croatie.png"},
            {"danemark", "danemark.png"},
            
            // Amérique
            {"bresil", "bresil.png"},
            {"argentine", "argentine.png"},
            {"uruguay", "uruguay.png"},
            {"colombie", "colombie.png"},
            {"chili", "chili.png"},
            {"mexique", "mexique.png"},
            {"etats", "etats_unis.png"},  // États-Unis
            {"canada", "canada.png"},
            {"costa", "costa_rica.png"},  // Costa Rica
            {"equateur", "equateur.png"},
            {"perou", "perou.png"},
            
            // Asie
            {"japon", "japon.png"},
            {"coree", "coree_du_sud.png"},  // Corée du Sud
            {"australie", "australie.png"},
            {"arabie", "arabie_saoudite.png"},  // Arabie Saoudite
            {"iran", "iran.png"},
            {"qatar", "qatar.png"},
            {"emirats", "emirats_arabes_unis.png"},  // Émirats Arabes Unis
            
            // Océanie
            {"zelande", "nouvelle_zelande.png"}  // Nouvelle-Zélande
        };
        
        for (String[] corresp : correspondances) {
            String motCle = corresp[0];
            String imagePath = corresp[1];
            
            String sql = "UPDATE equipe SET image_path = ? " +
                        "WHERE (LOWER(nom) LIKE ? OR LOWER(pays) LIKE ?) " +
                        "AND (image_path IS NULL OR image_path = '' OR image_path != ?)";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, imagePath);
                pstmt.setString(2, "%" + motCle + "%");
                pstmt.setString(3, "%" + motCle + "%");
                pstmt.setString(4, imagePath);
                
                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("  ✓ " + rows + " équipe(s) corrigée(s) pour: " + imagePath);
                    nbCorrections += rows;
                }
            }
        }
        
        return nbCorrections;
    }
}
