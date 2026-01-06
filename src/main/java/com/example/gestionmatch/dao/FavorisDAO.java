package com.example.gestionmatch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.gestionmatch.database.DatabaseConnection;
import com.example.gestionmatch.model.Equipe;

/**
 * DAO pour gérer les équipes favorites des utilisateurs
 */
public class FavorisDAO {

    /**
     * Ajoute une équipe aux favoris d'un utilisateur
     */
    public boolean ajouterFavori(int utilisateurId, int equipeId) {
        String sql = "INSERT INTO favoris (utilisateur_id, equipe_id) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, utilisateurId);
            pstmt.setInt(2, equipeId);
            
            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("✅ Équipe " + equipeId + " ajoutée aux favoris de l'utilisateur " + utilisateurId);
                return true;
            }
            
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Duplicate entry
                System.out.println("ℹ️ Cette équipe est déjà dans les favoris");
            } else {
                System.err.println("❌ Erreur ajout favori: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Retire une équipe des favoris d'un utilisateur
     */
    public boolean retirerFavori(int utilisateurId, int equipeId) {
        String sql = "DELETE FROM favoris WHERE utilisateur_id = ? AND equipe_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, utilisateurId);
            pstmt.setInt(2, equipeId);
            
            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("✅ Équipe " + equipeId + " retirée des favoris");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur retrait favori: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Récupère toutes les équipes favorites d'un utilisateur
     */
    public List<Equipe> getFavoris(int utilisateurId) {
        List<Equipe> favoris = new ArrayList<>();
        String sql = "SELECT e.* FROM equipe e " +
                    "INNER JOIN favoris f ON e.id = f.equipe_id " +
                    "WHERE f.utilisateur_id = ? " +
                    "ORDER BY f.date_ajout DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, utilisateurId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Equipe equipe = new Equipe();
                equipe.setId(rs.getInt("id"));
                equipe.setNom(rs.getString("nom"));
                equipe.setPays(rs.getString("pays"));
                equipe.setEntraineur(rs.getString("entraineur"));
                equipe.setNombreJoueurs(rs.getInt("nombre_joueurs"));
                equipe.setGroupeId(rs.getInt("groupe_id"));
                equipe.setMatchsJoues(rs.getInt("matchs_joues"));
                equipe.setVictoires(rs.getInt("victoires"));
                equipe.setNuls(rs.getInt("nuls"));
                equipe.setDefaites(rs.getInt("defaites"));
                equipe.setButsMarques(rs.getInt("buts_marques"));
                equipe.setButsEncaisses(rs.getInt("buts_encaisses"));
                equipe.setPoints(rs.getInt("points"));
                
                favoris.add(equipe);
            }
            
            System.out.println("✅ " + favoris.size() + " favoris chargés pour l'utilisateur " + utilisateurId);
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur chargement favoris: " + e.getMessage());
            e.printStackTrace();
        }
        
        return favoris;
    }

    /**
     * Vérifie si une équipe est dans les favoris d'un utilisateur
     */
    public boolean estFavori(int utilisateurId, int equipeId) {
        String sql = "SELECT COUNT(*) FROM favoris WHERE utilisateur_id = ? AND equipe_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, utilisateurId);
            pstmt.setInt(2, equipeId);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur vérification favori: " + e.getMessage());
        }
        
        return false;
    }

    /**
     * Compte le nombre de favoris d'un utilisateur
     */
    public int compterFavoris(int utilisateurId) {
        String sql = "SELECT COUNT(*) FROM favoris WHERE utilisateur_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, utilisateurId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur comptage favoris: " + e.getMessage());
        }
        
        return 0;
    }

    /**
     * Supprime tous les favoris d'un utilisateur
     */
    public boolean supprimerTousFavoris(int utilisateurId) {
        String sql = "DELETE FROM favoris WHERE utilisateur_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, utilisateurId);
            pstmt.executeUpdate();
            System.out.println("✅ Tous les favoris supprimés pour l'utilisateur " + utilisateurId);
            return true;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur suppression favoris: " + e.getMessage());
        }
        
        return false;
    }
}
