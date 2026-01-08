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
import com.example.gestionmatch.model.Joueur;

public class JoueurDAO {

    // Ajouter un joueur
    public boolean ajouter(Joueur joueur) {
        String sql = "INSERT INTO joueur (nom, prenom, date_naissance, poste, numero, equipe_id, nationalite) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, joueur.getNom());
            pstmt.setString(2, joueur.getPrenom());
            
            // Si la date de naissance est null, utiliser une date par défaut (2000-01-01)
            if (joueur.getDateNaissance() != null) {
                pstmt.setDate(3, Date.valueOf(joueur.getDateNaissance()));
            } else {
                pstmt.setDate(3, Date.valueOf("2000-01-01"));
            }
            
            pstmt.setString(4, joueur.getPoste());
            pstmt.setInt(5, joueur.getNumero());
            pstmt.setInt(6, joueur.getEquipeId());
            pstmt.setString(7, joueur.getNationalite());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    joueur.setId(rs.getInt(1));
                }
                System.out.println("✅ Joueur ajouté avec succès : " + joueur.getNomComplet());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'ajout du joueur: " + e.getMessage());
        }
        return false;
    }

    // Récupérer tous les joueurs
    public List<Joueur> getTous() {
        List<Joueur> joueurs = new ArrayList<>();
        String sql = "SELECT * FROM joueur ORDER BY nom, prenom";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                java.sql.Date sqlDate = rs.getDate("date_naissance");
                Joueur joueur = new Joueur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        sqlDate != null ? sqlDate.toLocalDate() : null,
                        rs.getString("poste"),
                        rs.getInt("numero"),
                        rs.getInt("equipe_id"),
                        rs.getString("nationalite")
                );
                joueurs.add(joueur);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des joueurs: " + e.getMessage());
        }
        return joueurs;
    }

    // Récupérer un joueur par ID
    public Joueur getParId(int id) {
        String sql = "SELECT * FROM joueur WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                java.sql.Date sqlDate = rs.getDate("date_naissance");
                return new Joueur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        sqlDate != null ? sqlDate.toLocalDate() : null,
                        rs.getString("poste"),
                        rs.getInt("numero"),
                        rs.getInt("equipe_id"),
                        rs.getString("nationalite")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du joueur: " + e.getMessage());
        }
        return null;
    }

    // Récupérer tous les joueurs d'une équipe
    public List<Joueur> getParEquipe(int equipeId) {
        List<Joueur> joueurs = new ArrayList<>();
        String sql = "SELECT * FROM joueur WHERE equipe_id = ? ORDER BY numero";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, equipeId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                java.sql.Date sqlDate = rs.getDate("date_naissance");
                Joueur joueur = new Joueur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        sqlDate != null ? sqlDate.toLocalDate() : null,
                        rs.getString("poste"),
                        rs.getInt("numero"),
                        rs.getInt("equipe_id"),
                        rs.getString("nationalite")
                );
                joueurs.add(joueur);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des joueurs de l'équipe: " + e.getMessage());
        }
        return joueurs;
    }

    // Rechercher des joueurs par nom
    public List<Joueur> rechercherParNom(String nom) {
        List<Joueur> joueurs = new ArrayList<>();
        String sql = "SELECT * FROM joueur WHERE nom LIKE ? OR prenom LIKE ? ORDER BY nom, prenom";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String recherche = "%" + nom + "%";
            pstmt.setString(1, recherche);
            pstmt.setString(2, recherche);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                java.sql.Date sqlDate = rs.getDate("date_naissance");
                Joueur joueur = new Joueur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        sqlDate != null ? sqlDate.toLocalDate() : null,
                        rs.getString("poste"),
                        rs.getInt("numero"),
                        rs.getInt("equipe_id"),
                        rs.getString("nationalite")
                );
                joueurs.add(joueur);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de joueurs: " + e.getMessage());
        }
        return joueurs;
    }

    // Rechercher des joueurs par poste
    public List<Joueur> rechercherParPoste(String poste) {
        List<Joueur> joueurs = new ArrayList<>();
        String sql = "SELECT * FROM joueur WHERE poste LIKE ? ORDER BY nom, prenom";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + poste + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                java.sql.Date sqlDate = rs.getDate("date_naissance");
                Joueur joueur = new Joueur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        sqlDate != null ? sqlDate.toLocalDate() : null,
                        rs.getString("poste"),
                        rs.getInt("numero"),
                        rs.getInt("equipe_id"),
                        rs.getString("nationalite")
                );
                joueurs.add(joueur);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche par poste: " + e.getMessage());
        }
        return joueurs;
    }

    // Modifier un joueur
    public boolean modifier(Joueur joueur) {
        String sql = "UPDATE joueur SET nom = ?, prenom = ?, date_naissance = ?, poste = ?, numero = ?, equipe_id = ?, nationalite = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, joueur.getNom());
            pstmt.setString(2, joueur.getPrenom());
            
            // Si la date de naissance est null, utiliser une date par défaut (2000-01-01)
            if (joueur.getDateNaissance() != null) {
                pstmt.setDate(3, Date.valueOf(joueur.getDateNaissance()));
            } else {
                pstmt.setDate(3, Date.valueOf("2000-01-01"));
            }
            
            pstmt.setString(4, joueur.getPoste());
            pstmt.setInt(5, joueur.getNumero());
            pstmt.setInt(6, joueur.getEquipeId());
            pstmt.setString(7, joueur.getNationalite());
            pstmt.setInt(8, joueur.getId());

            boolean success = pstmt.executeUpdate() > 0;
            if (success) {
                System.out.println("✅ Joueur modifié avec succès : " + joueur.getNomComplet());
            }
            return success;

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la modification du joueur: " + e.getMessage());
        }
        return false;
    }

    // Supprimer un joueur
    public boolean supprimer(int id) {
        String sql = "DELETE FROM joueur WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du joueur: " + e.getMessage());
        }
        return false;
    }

    // Compter le nombre de joueurs dans une équipe
    public int compterJoueursParEquipe(int equipeId) {
        String sql = "SELECT COUNT(*) FROM joueur WHERE equipe_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, equipeId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors du comptage des joueurs: " + e.getMessage());
        }
        return 0;
    }
  
    // Méthodes d'adaptation pour les nouveaux controllers
    public List<Joueur> getAllJoueurs() {
        return getTous();
    }

    public boolean ajouterJoueur(Joueur joueur) {
        return ajouter(joueur);
    }

    public boolean modifierJoueur(Joueur joueur) {
        return modifier(joueur);
    }

    public boolean supprimerJoueur(int id) {
        return supprimer(id);
    }
}
