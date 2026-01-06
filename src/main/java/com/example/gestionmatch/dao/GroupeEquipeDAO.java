package com.example.gestionmatch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.gestionmatch.database.DatabaseConnection;
import com.example.gestionmatch.model.Equipe;

public class GroupeEquipeDAO {
    
    private final EquipeDAO equipeDAO = new EquipeDAO();
    
    // Ajouter une équipe à un groupe
    public boolean ajouterEquipeAuGroupe(int groupeId, int equipeId) {
        String sql = "INSERT INTO groupe_equipe (groupe_id, equipe_id) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, groupeId);
            pstmt.setInt(2, equipeId);
            
            int result = pstmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur ajout équipe au groupe : " + e.getMessage());
            return false;
        }
    }
    
    // Retirer une équipe d'un groupe
    public boolean retirerEquipeDuGroupe(int groupeId, int equipeId) {
        String sql = "DELETE FROM groupe_equipe WHERE groupe_id = ? AND equipe_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, groupeId);
            pstmt.setInt(2, equipeId);
            
            int result = pstmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur retrait équipe du groupe : " + e.getMessage());
            return false;
        }
    }
    
    // Récupérer toutes les équipes d'un groupe
    public List<Equipe> getEquipesDuGroupe(int groupeId) {
        List<Equipe> equipes = new ArrayList<>();
        String sql = "SELECT e.* FROM equipe e " +
                    "INNER JOIN groupe_equipe ge ON e.id = ge.equipe_id " +
                    "WHERE ge.groupe_id = ? " +
                    "ORDER BY e.nom";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, groupeId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Equipe equipe = new Equipe();
                equipe.setId(rs.getInt("id"));
                equipe.setNom(rs.getString("nom"));
                equipe.setPays(rs.getString("pays"));
                equipe.setEntraineur(rs.getString("entraineur"));
                equipe.setPoints(rs.getInt("points"));
                equipe.setImagePath(rs.getString("image_path")); // IMPORTANT: Récupérer le chemin de l'image
                equipes.add(equipe);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur récupération équipes du groupe : " + e.getMessage());
        }
        
        return equipes;
    }
    
    // Vérifier si une équipe est déjà dans un groupe
    public boolean equipeEstDansGroupe(int groupeId, int equipeId) {
        String sql = "SELECT COUNT(*) as count FROM groupe_equipe WHERE groupe_id = ? AND equipe_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, groupeId);
            pstmt.setInt(2, equipeId);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur vérification équipe dans groupe : " + e.getMessage());
        }
        
        return false;
    }
    
    // Compter le nombre d'équipes dans un groupe
    public int compterEquipesDansGroupe(int groupeId) {
        String sql = "SELECT COUNT(*) as count FROM groupe_equipe WHERE groupe_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, groupeId);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur comptage équipes : " + e.getMessage());
        }
        
        return 0;
    }
}
