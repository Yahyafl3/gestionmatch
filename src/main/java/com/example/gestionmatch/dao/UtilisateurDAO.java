package com.example.gestionmatch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.example.gestionmatch.database.DatabaseConnection;
import com.example.gestionmatch.model.Utilisateur;

public class UtilisateurDAO {

    public boolean inscrire(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateur (email, mot_de_passe, nom_complet, role, actif) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Hasher le mot de passe avec BCrypt
            String motDePasseHash = org.mindrot.jbcrypt.BCrypt.hashpw(utilisateur.getMotDePasse(), org.mindrot.jbcrypt.BCrypt.gensalt(10));

            pstmt.setString(1, utilisateur.getEmail());
            pstmt.setString(2, motDePasseHash);
            pstmt.setString(3, utilisateur.getPrenom() + " " + utilisateur.getNom());  // Prénom d'abord
            pstmt.setString(4, "USER");
            pstmt.setBoolean(5, true);

            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                System.out.println("✅ Utilisateur inscrit avec succès: " + utilisateur.getEmail());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'inscription: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public Utilisateur connexion(String email, String motDePasse) {
        System.out.println("=== DEBUG CONNEXION ===");
        System.out.println("Email saisi: " + email);
        System.out.println("Mot de passe saisi: " + motDePasse);
        
        String sql = "SELECT * FROM utilisateur WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("✅ Utilisateur trouvé dans la base");
                String motDePasseHash = rs.getString("mot_de_passe");
                String role = rs.getString("role");
                
                System.out.println("Role: " + role);
                System.out.println("Hash en base (20 premiers caractères): " + motDePasseHash.substring(0, Math.min(20, motDePasseHash.length())));
                
                // Vérifier le mot de passe avec BCrypt
                try {
                    boolean match = org.mindrot.jbcrypt.BCrypt.checkpw(motDePasse, motDePasseHash);
                    System.out.println("Résultat BCrypt.checkpw: " + match);
                    
                    if (match) {
                        System.out.println("✅ Mot de passe correct - Connexion réussie");
                        
                        // Séparer le nom complet en prénom et nom
                        String nomComplet = rs.getString("nom_complet");
                        String[] parts = nomComplet.split(" ", 2);
                        String prenom = parts.length > 0 ? parts[0] : "";
                        String nom = parts.length > 1 ? parts[1] : nomComplet;
                        
                        Utilisateur utilisateur = new Utilisateur(
                                rs.getInt("id"),
                                rs.getString("email"),
                                nom,
                                prenom,
                                role,
                                true
                        );
                        utilisateur.setDateCreation(rs.getTimestamp("date_creation").toLocalDateTime());
                        return utilisateur;
                    } else {
                        System.out.println("❌ Mot de passe incorrect selon BCrypt");
                    }
                } catch (Exception e) {
                    System.err.println("❌ Erreur lors de la vérification BCrypt: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("❌ Aucun utilisateur trouvé avec cet email");
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL lors de la connexion: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=== FIN DEBUG CONNEXION ===");
        return null;
    }

    public boolean emailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM utilisateur WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification de l'email: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public Utilisateur getParId(int id) {
        String sql = "SELECT * FROM utilisateurs WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("role"),
                        rs.getBoolean("actif")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'utilisateur: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Utilisateur> getTous() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM utilisateurs";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("role"),
                        rs.getBoolean("actif")
                );
                utilisateurs.add(utilisateur);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des utilisateurs: " + e.getMessage());
            e.printStackTrace();
        }
        return utilisateurs;
    }

    public boolean modifier(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateurs SET email = ?, nom = ?, prenom = ?, role = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, utilisateur.getEmail());
            pstmt.setString(2, utilisateur.getNom());
            pstmt.setString(3, utilisateur.getPrenom());
            pstmt.setString(4, utilisateur.getRole());
            pstmt.setInt(5, utilisateur.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean supprimer(int id) {
        String sql = "DELETE FROM utilisateurs WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static Utilisateur findByUsernameOrEmail(String identifiant) throws SQLException {
        String sql = "SELECT * FROM utilisateurs WHERE username = ? OR email = ? LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, identifiant);
            pstmt.setString(2, identifiant);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Utilisateur utilisateur = new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("role"),
                        rs.getBoolean("actif")
                );
                utilisateur.setUsername(rs.getString("username"));
                utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
                return utilisateur;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'utilisateur: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean existeEmail(String email) {
        return emailExiste(email);
    }

    public boolean existeUsername(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM utilisateurs WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public void insert(Utilisateur utilisateur) {
        inscrire(utilisateur);
    }
    
    /**
     * Change le mot de passe d'un utilisateur
     */
    public boolean changerMotDePasse(int utilisateurId, String ancienMotDePasse, String nouveauMotDePasse) {
        // Vérifier d'abord l'ancien mot de passe
        String sqlVerif = "SELECT mot_de_passe FROM utilisateur WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmtVerif = conn.prepareStatement(sqlVerif)) {
            
            pstmtVerif.setInt(1, utilisateurId);
            ResultSet rs = pstmtVerif.executeQuery();
            
            if (rs.next()) {
                String motDePasseHash = rs.getString("mot_de_passe");
                
                // Vérifier l'ancien mot de passe
                if (!BCrypt.checkpw(ancienMotDePasse, motDePasseHash)) {
                    System.err.println("❌ Ancien mot de passe incorrect");
                    return false;
                }
                
                // Hasher le nouveau mot de passe
                String nouveauHash = BCrypt.hashpw(nouveauMotDePasse, BCrypt.gensalt());
                
                // Mettre à jour le mot de passe
                String sqlUpdate = "UPDATE utilisateur SET mot_de_passe = ? WHERE id = ?";
                try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
                    pstmtUpdate.setString(1, nouveauHash);
                    pstmtUpdate.setInt(2, utilisateurId);
                    
                    int result = pstmtUpdate.executeUpdate();
                    if (result > 0) {
                        System.out.println("✅ Mot de passe changé avec succès");
                        return true;
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur changement mot de passe: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
}
