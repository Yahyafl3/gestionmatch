package com.example.gestionmatch.dao;

import com.example.gestionmatch.model.Stade;
import com.example.gestionmatch.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StadeDAO {

    public List<Stade> getAllStades() {
        List<Stade> stades = new ArrayList<>();
        String query = "SELECT * FROM stade ORDER BY nom";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                stades.add(new Stade(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("ville"),
                    rs.getString("pays"),
                    rs.getInt("capacite")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des stades: " + e.getMessage());
        }
        return stades;
    }

    public boolean ajouterStade(Stade stade) {
        String query = "INSERT INTO stade (nom, ville, pays, capacite) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, stade.getNom());
            pstmt.setString(2, stade.getVille());
            pstmt.setString(3, stade.getPays());
            pstmt.setInt(4, stade.getCapacite());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    stade.setId(rs.getInt(1));
                }
                System.out.println("✅ Stade ajouté avec succès : " + stade.getNom());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'ajout du stade: " + e.getMessage());
            System.err.println("   SQL State: " + e.getSQLState());
            System.err.println("   Error Code: " + e.getErrorCode());
            return false;
        }
        return false;
    }

    public boolean modifierStade(Stade stade) {
        String query = "UPDATE stade SET nom = ?, ville = ?, pays = ?, capacite = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, stade.getNom());
            pstmt.setString(2, stade.getVille());
            pstmt.setString(3, stade.getPays());
            pstmt.setInt(4, stade.getCapacite());
            pstmt.setInt(5, stade.getId());
            
            boolean success = pstmt.executeUpdate() > 0;
            if (success) {
                System.out.println("✅ Stade modifié avec succès : " + stade.getNom());
            }
            return success;
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la modification du stade: " + e.getMessage());
            return false;
        }
    }

    public boolean supprimerStade(int id) {
        String query = "DELETE FROM stade WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            boolean success = pstmt.executeUpdate() > 0;
            if (success) {
                System.out.println("✅ Stade supprimé avec succès (ID: " + id + ")");
            }
            return success;
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression du stade: " + e.getMessage());
            return false;
        }
    }
}
