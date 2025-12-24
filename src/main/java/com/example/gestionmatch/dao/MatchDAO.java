package com.example.gestionmatch.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.gestionmatch.database.DatabaseConnection;
import com.example.gestionmatch.model.Match;

public class MatchDAO {

    // Ajouter un match
    public boolean ajouter(Match match) {
        String sql = "INSERT INTO match_data (nom, score, date_match) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, match.getNom());
            pstmt.setString(2, match.getScore());
            pstmt.setDate(3, Date.valueOf(match.getDateMatch()));

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    match.setId(rs.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du match: " + e.getMessage());
        }
        return false;
    }

    // Récupérer tous les matches
    public List<Match> getTous() {
        List<Match> matches = new ArrayList<>();
        String sql = "SELECT * FROM match_data ORDER BY date_match DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Match match = new Match(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("score"),
                        rs.getDate("date_match").toLocalDate()
                );
                matches.add(match);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des matches: " + e.getMessage());
        }
        return matches;
    }

    // Rechercher des matches par nom
    public List<Match> rechercherParNom(String nom) {
        List<Match> matches = new ArrayList<>();
        String sql = "SELECT * FROM match_data WHERE nom LIKE ? ORDER BY date_match DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nom + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Match match = new Match(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("score"),
                        rs.getDate("date_match").toLocalDate()
                );
                matches.add(match);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de matches: " + e.getMessage());
        }
        return matches;
    }

    // Récupérer un match par ID
    public Match getParId(int id) {
        String sql = "SELECT * FROM match_data WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Match(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("score"),
                        rs.getDate("date_match").toLocalDate()
                );
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du match: " + e.getMessage());
        }
        return null;
    }

    // Mettre à jour un match
    public boolean modifier(Match match) {
        String sql = "UPDATE match_data SET nom = ?, score = ?, date_match = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, match.getNom());
            pstmt.setString(2, match.getScore());
            pstmt.setDate(3, Date.valueOf(match.getDateMatch()));
            pstmt.setInt(4, match.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du match: " + e.getMessage());
        }
        return false;
    }

    // Supprimer un match par ID
    public boolean supprimer(int id) {
        String sql = "DELETE FROM match_data WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du match: " + e.getMessage());
        }
        return false;
    }

    // Supprimer tous les matches
    public boolean supprimerTous() {
        String sql = "DELETE FROM match_data";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            return stmt.executeUpdate(sql) >= 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de tous les matches: " + e.getMessage());
        }
        return false;
    }
    
    // Méthodes d'adaptation pour les nouveaux controllers
    public List<Match> getAllMatchs() {
        return getTous();
    }

    public boolean ajouterMatch(Match match) {
        return ajouter(match);
    }

    public boolean modifierMatch(Match match) {
        return modifier(match);
    }

    public boolean supprimerMatch(int id) {
        return supprimer(id);
    }
}
