package com.example.gestionmatch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.example.gestionmatch.database.DatabaseConnection;
import com.example.gestionmatch.model.Equipe;

public class EquipeDAO {


    public void insert(Equipe equipe) throws SQLException {
        String sql = "INSERT INTO equipe (nom, pays, entraineur, nombre_joueurs, matchs_joues, victoires, nuls, defaites, buts_marques, buts_encaisses, points, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, equipe.getNom());
            pstmt.setString(2, equipe.getPays());
            pstmt.setString(3, equipe.getEntraineur());
            pstmt.setInt(4, equipe.getNombreJoueurs());
            pstmt.setInt(5, equipe.getMatchsJoues());
            pstmt.setInt(6, equipe.getVictoires());
            pstmt.setInt(7, equipe.getNuls());
            pstmt.setInt(8, equipe.getDefaites());
            pstmt.setInt(9, equipe.getButsMarques());
            pstmt.setInt(10, equipe.getButsEncaisses());
            pstmt.setInt(11, equipe.getPoints());
            pstmt.setString(12, equipe.getImagePath());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        equipe.setId(generatedKeys.getInt(1));
                        System.out.println("✅ Équipe insérée avec l'ID : " + equipe.getId());
                    }
                }
            }
        }
    }


    public Equipe findById(int id) throws SQLException {
        String sql = "SELECT * FROM equipe WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEquipe(rs);
                }
            }
        }
        return null;
    }


    public Equipe findByNom(String nom) throws SQLException {
        String sql = "SELECT * FROM equipe WHERE nom = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEquipe(rs);
                }
            }
        }
        return null;
    }


    public List<Equipe> findByPays(String pays) throws SQLException {
        String sql = "SELECT * FROM equipe WHERE pays = ? ORDER BY nom";
        List<Equipe> equipes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pays);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    equipes.add(mapResultSetToEquipe(rs));
                }
            }
        }
        return equipes;
    }


    public List<Equipe> findByGroupe(int groupeId) throws SQLException {
        String sql = "SELECT * FROM equipe WHERE groupe_id = ? " +
                "ORDER BY points DESC, (buts_marques - buts_encaisses) DESC, buts_marques DESC";
        List<Equipe> equipes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, groupeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    equipes.add(mapResultSetToEquipe(rs));
                }
            }
        }
        return equipes;
    }

    public List<Equipe> findAll() throws SQLException {
        String sql = "SELECT * FROM equipe ORDER BY nom";
        List<Equipe> equipes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                equipes.add(mapResultSetToEquipe(rs));
            }
        }
        return equipes;
    }


    public void update(Equipe equipe) throws SQLException {
        String sql = "UPDATE equipe SET nom = ?, pays = ?, entraineur = ?, nombre_joueurs = ?, " +
                "groupe_id = ?, matchs_joues = ?, victoires = ?, nuls = ?, defaites = ?, " +
                "buts_marques = ?, buts_encaisses = ?, points = ?, image_path = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, equipe.getNom());
            pstmt.setString(2, equipe.getPays());
            pstmt.setString(3, equipe.getEntraineur());
            pstmt.setInt(4, equipe.getNombreJoueurs());

            if (equipe.getGroupeId() != null) {
                pstmt.setInt(5, equipe.getGroupeId());
            } else {
                pstmt.setNull(5, Types.INTEGER);
            }

            pstmt.setInt(6, equipe.getMatchsJoues());
            pstmt.setInt(7, equipe.getVictoires());
            pstmt.setInt(8, equipe.getNuls());
            pstmt.setInt(9, equipe.getDefaites());
            pstmt.setInt(10, equipe.getButsMarques());
            pstmt.setInt(11, equipe.getButsEncaisses());
            pstmt.setInt(12, equipe.getPoints());
            pstmt.setString(13, equipe.getImagePath());
            pstmt.setInt(14, equipe.getId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("✅ Équipe mise à jour : " + equipe.getNom());
            } else {
                System.out.println("⚠️ Aucune équipe trouvée avec l'ID : " + equipe.getId());
            }
        }
    }


    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM equipe WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("✅ Équipe supprimée avec l'ID : " + id);
            } else {
                System.out.println("⚠️ Aucune équipe trouvée avec l'ID : " + id);
            }
        }
    }


    public boolean existe(String nom) throws SQLException {
        String sql = "SELECT COUNT(*) FROM equipe WHERE nom = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }


    public void assignerGroupe(int equipeId, int groupeId) throws SQLException {
        String sql = "UPDATE equipe SET groupe_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, groupeId);
            pstmt.setInt(2, equipeId);

            pstmt.executeUpdate();
            System.out.println("✅ Équipe assignée au groupe");
        }
    }

    public void retirerDuGroupe(int equipeId) throws SQLException {
        String sql = "UPDATE equipe SET groupe_id = NULL WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, equipeId);

            pstmt.executeUpdate();
            System.out.println("✅ Équipe retirée du groupe");
        }
    }


    public void updateStatistiques(Equipe equipe) throws SQLException {
        String sql = "UPDATE equipe SET matchs_joues = ?, victoires = ?, nuls = ?, defaites = ?, " +
                "buts_marques = ?, buts_encaisses = ?, points = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, equipe.getMatchsJoues());
            pstmt.setInt(2, equipe.getVictoires());
            pstmt.setInt(3, equipe.getNuls());
            pstmt.setInt(4, equipe.getDefaites());
            pstmt.setInt(5, equipe.getButsMarques());
            pstmt.setInt(6, equipe.getButsEncaisses());
            pstmt.setInt(7, equipe.getPoints());
            pstmt.setInt(8, equipe.getId());

            pstmt.executeUpdate();
        }
    }


    public Equipe mapResultSetToEquipe(ResultSet rs) throws SQLException {
        Equipe equipe = new Equipe();

        equipe.setId(rs.getInt("id"));
        equipe.setNom(rs.getString("nom"));
        equipe.setPays(rs.getString("pays"));
        equipe.setEntraineur(rs.getString("entraineur"));
        equipe.setNombreJoueurs(rs.getInt("nombre_joueurs"));
        equipe.setImagePath(rs.getString("image_path"));

        Integer groupeId = (Integer) rs.getObject("groupe_id");
        equipe.setGroupeId(groupeId);

        equipe.setMatchsJoues(rs.getInt("matchs_joues"));
        equipe.setVictoires(rs.getInt("victoires"));
        equipe.setNuls(rs.getInt("nuls"));
        equipe.setDefaites(rs.getInt("defaites"));
        equipe.setButsMarques(rs.getInt("buts_marques"));
        equipe.setButsEncaisses(rs.getInt("buts_encaisses"));
        equipe.setPoints(rs.getInt("points"));

        Timestamp dateCreation = rs.getTimestamp("date_creation");
        if (dateCreation != null) {
            equipe.setDateCreation(dateCreation.toLocalDateTime());
        }

        return equipe;
    }
    
    // Méthodes d'adaptation pour les nouveaux controllers
    public List<Equipe> getAllEquipes() {
        try {
            return findAll();
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des équipes : " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public Equipe getEquipeByNom(String nom) {
        String sql = "SELECT * FROM equipe WHERE nom = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nom);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToEquipe(rs);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération de l'équipe par nom : " + e.getMessage());
        }
        return null;
    }

    public boolean ajouterEquipe(Equipe equipe) {
        try {
            insert(equipe);
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'ajout de l'équipe : " + e.getMessage());
            return false;
        }
    }

    public boolean modifierEquipe(Equipe equipe) {
        try {
            update(equipe);
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la modification de l'équipe : " + e.getMessage());
            return false;
        }
    }

    public boolean supprimerEquipe(int id) {
        try {
            delete(id);
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression de l'équipe : " + e.getMessage());
            return false;
        }
    }
}
