package com.example.gestionmatch;

import java.sql.Connection;
import java.sql.Statement;

import com.example.gestionmatch.database.DatabaseConnection;

public class SetupDatabaseAuto {

    public static void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.println("\n========================================");
            System.out.println("🔧 INITIALISATION DE LA BASE DE DONNÉES");
            System.out.println("========================================\n");

            // Créer la table utilisateur (SINGULIER)
            String createUtilisateur = "CREATE TABLE IF NOT EXISTS utilisateur (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "nom_utilisateur VARCHAR(50) UNIQUE," +
                    "email VARCHAR(100) UNIQUE NOT NULL," +
                    "mot_de_passe VARCHAR(255) NOT NULL," +
                    "nom_complet VARCHAR(100) NOT NULL," +
                    "role ENUM('ADMIN', 'USER') DEFAULT 'USER'," +
                    "actif TINYINT(1) DEFAULT 1," +
                    "date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
            stmt.execute(createUtilisateur);
            System.out.println("✅ Table 'utilisateur' créée/vérifiée");

            // Créer la table groupe (SINGULIER)
            String createGroupe = "CREATE TABLE IF NOT EXISTS groupe (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "nom VARCHAR(100) UNIQUE NOT NULL," +
                    "description TEXT," +
                    "equipes TEXT," +
                    "date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
            stmt.execute(createGroupe);
            System.out.println("✅ Table 'groupe' créée/vérifiée");
            
            // Ajouter la colonne 'equipes' si elle n'existe pas (migration)
            try {
                // Vérifier si la colonne existe
                String checkColumn = "SELECT COUNT(*) as count FROM INFORMATION_SCHEMA.COLUMNS " +
                        "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'groupe' AND COLUMN_NAME = 'equipes'";
                var rsCheck = stmt.executeQuery(checkColumn);
                if (rsCheck.next() && rsCheck.getInt("count") == 0) {
                    String addEquipesColumn = "ALTER TABLE groupe ADD COLUMN equipes TEXT AFTER description";
                    stmt.execute(addEquipesColumn);
                    System.out.println("✅ Colonne 'equipes' ajoutée à la table 'groupe'");
                } else {
                    System.out.println("ℹ️  Colonne 'equipes' déjà présente dans la table 'groupe'");
                }
            } catch (Exception e) {
                System.err.println("⚠️  Erreur lors de l'ajout de la colonne 'equipes': " + e.getMessage());
            }

            // Créer la table equipe (SINGULIER)
            String createEquipe = "CREATE TABLE IF NOT EXISTS equipe (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "nom VARCHAR(100) UNIQUE NOT NULL," +
                    "pays VARCHAR(100) NOT NULL," +
                    "entraineur VARCHAR(100)," +
                    "nombre_joueurs INT DEFAULT 0," +
                    "groupe_id INT," +
                    "matchs_joues INT DEFAULT 0," +
                    "victoires INT DEFAULT 0," +
                    "nuls INT DEFAULT 0," +
                    "defaites INT DEFAULT 0," +
                    "buts_marques INT DEFAULT 0," +
                    "buts_encaisses INT DEFAULT 0," +
                    "points INT DEFAULT 0," +
                    "date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (groupe_id) REFERENCES groupe(id) ON DELETE SET NULL" +
                    ")";
            stmt.execute(createEquipe);
            System.out.println("✅ Table 'equipe' créée/vérifiée");

            // Créer la table joueur (SINGULIER)
            String createJoueur = "CREATE TABLE IF NOT EXISTS joueur (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "nom VARCHAR(100) NOT NULL," +
                    "prenom VARCHAR(100) NOT NULL," +
                    "date_naissance DATE," +
                    "poste VARCHAR(50)," +
                    "numero INT," +
                    "equipe_id INT NOT NULL," +
                    "date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (equipe_id) REFERENCES equipe(id) ON DELETE CASCADE" +
                    ")";
            stmt.execute(createJoueur);
            System.out.println("✅ Table 'joueur' créée/vérifiée");

            // Créer la table stade (SINGULIER)
            String createStade = "CREATE TABLE IF NOT EXISTS stade (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "nom VARCHAR(100) UNIQUE NOT NULL," +
                    "ville VARCHAR(100) NOT NULL," +
                    "pays VARCHAR(100) NOT NULL," +
                    "capacite INT," +
                    "date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
            stmt.execute(createStade);
            System.out.println("✅ Table 'stade' créée/vérifiée");

            // Créer la table match_data
            String createMatch = "CREATE TABLE IF NOT EXISTS match_data (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "nom VARCHAR(200) NOT NULL," +
                    "score VARCHAR(20)," +
                    "date_match DATE NOT NULL," +
                    "date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
            stmt.execute(createMatch);
            System.out.println("✅ Table 'match_data' créée/vérifiée");

            // Insérer l'admin par défaut avec mot de passe hashé BCrypt
            String checkAdmin = "SELECT COUNT(*) as count FROM utilisateur WHERE email = 'admin@fifa2030.com'";
            var rs = stmt.executeQuery(checkAdmin);
            if (rs.next() && rs.getInt("count") == 0) {
                // Mot de passe: Admin123 (hashé avec BCrypt)
                // Hash généré avec: BCrypt.hashpw("Admin123", BCrypt.gensalt(10))
                String insertAdmin = "INSERT INTO utilisateur (nom_utilisateur, email, mot_de_passe, nom_complet, role, actif) " +
                        "VALUES ('admin', 'admin@fifa2030.com', '" + org.mindrot.jbcrypt.BCrypt.hashpw("Admin123", org.mindrot.jbcrypt.BCrypt.gensalt(10)) + "', 'Administrateur FIFA 2030', 'ADMIN', 1)";
                stmt.execute(insertAdmin);
                System.out.println("✅ Compte administrateur créé (admin@fifa2030.com / Admin123)");
            } else {
                System.out.println("ℹ️  Compte administrateur déjà existant");
            }

            System.out.println("\n========================================");
            System.out.println("✅ BASE DE DONNÉES INITIALISÉE");
            System.out.println("========================================\n");

        } catch (Exception e) {
            System.err.println("\n❌ Erreur lors de l'initialisation de la base de données:");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
