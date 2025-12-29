package com.example.gestionmatch.dao;

import com.example.gestionmatch.model.Groupe;
import com.example.gestionmatch.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GroupeDAO {

    public List<Groupe> getAllGroupes() {
        List<Groupe> groupes = new ArrayList<>();
        String query = "SELECT g.*, GROUP_CONCAT(e.nom SEPARATOR ', ') as equipes " +
                       "FROM groupe g " +
                       "LEFT JOIN equipe e ON e.groupe_id = g.id " +
                       "GROUP BY g.id, g.nom, g.description " +
                       "ORDER BY g.nom";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Groupe groupe = new Groupe(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("description")
                );
                
                // Récupérer les noms des équipes
                String equipesStr = rs.getString("equipes");
                if (equipesStr != null && !equipesStr.isEmpty()) {
                    List<String> equipesNoms = new ArrayList<>();
                    for (String nom : equipesStr.split(", ")) {
                        equipesNoms.add(nom);
                    }
                    groupe.setEquipesNoms(equipesNoms);
                }
                
                groupes.add(groupe);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des groupes: " + e.getMessage());
        }
        return groupes;
    }

    public boolean ajouterGroupe(Groupe groupe) {
        String query = "INSERT INTO groupe (nom, description) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, groupe.getNom());
            pstmt.setString(2, groupe.getDescription());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    groupe.setId(rs.getInt(1));
                }
                System.out.println("✅ Groupe créé avec l'ID : " + groupe.getId());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'ajout du groupe: " + e.getMessage());
            return false;
        }
        return false;
    }

    public boolean modifierGroupe(Groupe groupe) {
        String query = "UPDATE groupe SET nom = ?, description = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, groupe.getNom());
            pstmt.setString(2, groupe.getDescription());
            pstmt.setInt(3, groupe.getId());
            
            boolean success = pstmt.executeUpdate() > 0;
            if (success) {
                System.out.println("✅ Groupe modifié : " + groupe.getNom());
            }
            return success;
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la modification du groupe: " + e.getMessage());
            return false;
        }
    }

    public boolean supprimerGroupe(int id) {
        String query = "DELETE FROM groupe WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            boolean success = pstmt.executeUpdate() > 0;
            if (success) {
                System.out.println("✅ Groupe supprimé (ID: " + id + ")");
            }
            return success;
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression du groupe: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateEquipesGroupe(int groupeId, List<String> equipesNoms) {
        String query = "UPDATE groupe SET equipes = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            String equipesStr = equipesNoms != null && !equipesNoms.isEmpty() 
                ? String.join(", ", equipesNoms) 
                : null;
            
            pstmt.setString(1, equipesStr);
            pstmt.setInt(2, groupeId);
            
            boolean success = pstmt.executeUpdate() > 0;
            if (success) {
                System.out.println("✅ Champ 'equipes' mis à jour pour le groupe ID: " + groupeId);
            }
            return success;
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la mise à jour du champ equipes: " + e.getMessage());
            return false;
        }
    }
}
