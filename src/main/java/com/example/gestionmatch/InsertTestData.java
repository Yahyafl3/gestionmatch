package com.example.gestionmatch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.example.gestionmatch.database.DatabaseConnection;

public class InsertTestData {

    public static void insererDonneesTest() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.println("\n========================================");
            System.out.println("📊 CHARGEMENT DES DONNÉES COUPE DU MONDE 2030");
            System.out.println("========================================\n");

            // Vérifier si des données existent déjà
            var rs = stmt.executeQuery("SELECT COUNT(*) as count FROM equipe");
            if (rs.next() && rs.getInt("count") > 0) {
                System.out.println("⚠️  Anciennes données détectées - Suppression...");
                
                // Désactiver les contraintes
                stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
                
                // Supprimer les anciennes données
                stmt.execute("TRUNCATE TABLE groupe_equipe");
                stmt.execute("TRUNCATE TABLE joueur");
                stmt.execute("TRUNCATE TABLE match_data");
                stmt.execute("TRUNCATE TABLE equipe");
                stmt.execute("TRUNCATE TABLE stade");
                stmt.execute("TRUNCATE TABLE groupe");
                
                // Réactiver les contraintes
                stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
                
                System.out.println("✅ Anciennes données supprimées");
            }

            // ============================================
            // INSERTION DES GROUPES
            // ============================================
            System.out.println("📋 Création des groupes...");
            stmt.execute("INSERT INTO groupe (nom, description) VALUES " +
                    "('Groupe A', 'Premier groupe de la phase de groupes'), " +
                    "('Groupe B', 'Deuxième groupe de la phase de groupes'), " +
                    "('Groupe C', 'Troisième groupe de la phase de groupes'), " +
                    "('Groupe D', 'Quatrième groupe de la phase de groupes')");
            System.out.println("✅ 4 groupes créés");

            // ============================================
            // INSERTION DES ÉQUIPES NATIONALES
            // ============================================
            System.out.println("\n🏆 Insertion des équipes nationales...");
            
            // Groupe A
            insererEquipe(conn, "Maroc", "Maroc", "Walid Regragui", 26, 1);
            insererEquipe(conn, "Brésil", "Brésil", "Fernando Diniz", 26, 1);
            insererEquipe(conn, "Allemagne", "Allemagne", "Julian Nagelsmann", 26, 1);
            insererEquipe(conn, "Australie", "Australie", "Graham Arnold", 26, 1);
            
            // Groupe B
            insererEquipe(conn, "France", "France", "Didier Deschamps", 26, 2);
            insererEquipe(conn, "Argentine", "Argentine", "Lionel Scaloni", 26, 2);
            insererEquipe(conn, "Japon", "Japon", "Hajime Moriyasu", 26, 2);
            insererEquipe(conn, "Danemark", "Danemark", "Kasper Hjulmand", 26, 2);
            
            // Groupe C
            insererEquipe(conn, "Espagne", "Espagne", "Luis de la Fuente", 26, 3);
            insererEquipe(conn, "Portugal", "Portugal", "Roberto Martínez", 26, 3);
            insererEquipe(conn, "Mexique", "Mexique", "Jaime Lozano", 26, 3);
            insererEquipe(conn, "Croatie", "Croatie", "Zlatko Dalić", 26, 3);
            
            // Groupe D
            insererEquipe(conn, "Angleterre", "Angleterre", "Gareth Southgate", 26, 4);
            insererEquipe(conn, "Pays-Bas", "Pays-Bas", "Ronald Koeman", 26, 4);
            insererEquipe(conn, "Sénégal", "Sénégal", "Aliou Cissé", 26, 4);
            insererEquipe(conn, "États-Unis", "États-Unis", "Gregg Berhalter", 26, 4);
            
            System.out.println("✅ 16 équipes nationales insérées");

            // ============================================
            // INSERTION DES JOUEURS
            // ============================================
            System.out.println("\n👤 Insertion des joueurs...");
            
            int marocId = getEquipeId(conn, "Maroc");
            insererJoueursBatch(conn, marocId, new String[][]{
                {"Bounou", "Yassine", "1", "Gardien"},
                {"Hakimi", "Achraf", "2", "Défenseur"},
                {"Mazraoui", "Noussair", "3", "Défenseur"},
                {"Amrabat", "Sofyan", "4", "Milieu"},
                {"Aguerd", "Nayef", "5", "Défenseur"},
                {"Saïss", "Romain", "6", "Défenseur"},
                {"Ziyech", "Hakim", "7", "Attaquant"},
                {"Ounahi", "Azzedine", "8", "Milieu"},
                {"En-Nesyri", "Youssef", "19", "Attaquant"},
                {"Boufal", "Sofiane", "17", "Attaquant"}
            });

            int bresilId = getEquipeId(conn, "Brésil");
            insererJoueursBatch(conn, bresilId, new String[][]{
                {"Alisson", "Becker", "1", "Gardien"},
                {"Marquinhos", "", "4", "Défenseur"},
                {"Militão", "Éder", "3", "Défenseur"},
                {"Casemiro", "", "5", "Milieu"},
                {"Paquetá", "Lucas", "8", "Milieu"},
                {"Neymar", "Jr", "10", "Attaquant"},
                {"Vinícius", "Júnior", "7", "Attaquant"},
                {"Richarlison", "", "9", "Attaquant"},
                {"Rodrygo", "", "11", "Attaquant"},
                {"Raphinha", "", "19", "Attaquant"}
            });

            int franceId = getEquipeId(conn, "France");
            insererJoueursBatch(conn, franceId, new String[][]{
                {"Maignan", "Mike", "16", "Gardien"},
                {"Pavard", "Benjamin", "2", "Défenseur"},
                {"Varane", "Raphaël", "4", "Défenseur"},
                {"Hernández", "Theo", "22", "Défenseur"},
                {"Tchouaméni", "Aurélien", "8", "Milieu"},
                {"Griezmann", "Antoine", "7", "Milieu"},
                {"Mbappé", "Kylian", "10", "Attaquant"},
                {"Dembélé", "Ousmane", "11", "Attaquant"},
                {"Giroud", "Olivier", "9", "Attaquant"},
                {"Coman", "Kingsley", "20", "Attaquant"}
            });

            int argentineId = getEquipeId(conn, "Argentine");
            insererJoueursBatch(conn, argentineId, new String[][]{
                {"Martínez", "Emiliano", "23", "Gardien"},
                {"Romero", "Cristian", "13", "Défenseur"},
                {"Otamendi", "Nicolás", "19", "Défenseur"},
                {"Acuña", "Marcos", "8", "Défenseur"},
                {"De Paul", "Rodrigo", "7", "Milieu"},
                {"Mac Allister", "Alexis", "20", "Milieu"},
                {"Messi", "Lionel", "10", "Attaquant"},
                {"Di María", "Ángel", "11", "Attaquant"},
                {"Álvarez", "Julián", "9", "Attaquant"},
                {"Lautaro", "Martínez", "22", "Attaquant"}
            });

            System.out.println("✅ Joueurs insérés pour les principales équipes");

            // ============================================
            // INSERTION DES STADES OFFICIELS 2030
            // ============================================
            System.out.println("\n🏟️  Insertion des stades de la Coupe du Monde 2030...");
            
            // Stades du Maroc
            insererStade(conn, "Stade Hassan II", "Casablanca", "Maroc", 115000);
            insererStade(conn, "Stade Mohammed V", "Casablanca", "Maroc", 67000);
            insererStade(conn, "Stade Prince Moulay Abdellah", "Rabat", "Maroc", 52000);
            insererStade(conn, "Stade de Marrakech", "Marrakech", "Maroc", 45000);
            insererStade(conn, "Stade de Fès", "Fès", "Maroc", 45000);
            insererStade(conn, "Stade Ibn Batouta", "Tanger", "Maroc", 65000);
            
            // Stades d'Espagne
            insererStade(conn, "Camp Nou", "Barcelone", "Espagne", 99354);
            insererStade(conn, "Santiago Bernabéu", "Madrid", "Espagne", 81044);
            insererStade(conn, "Metropolitano", "Madrid", "Espagne", 68456);
            insererStade(conn, "Ramón Sánchez-Pizjuán", "Séville", "Espagne", 43883);
            
            // Stades du Portugal
            insererStade(conn, "Estádio da Luz", "Lisbonne", "Portugal", 64642);
            insererStade(conn, "Estádio do Dragão", "Porto", "Portugal", 50033);
            insererStade(conn, "José Alvalade", "Lisbonne", "Portugal", 50095);
            
            System.out.println("✅ 13 stades officiels insérés");

            // ============================================
            // INSERTION DES MATCHS DE LA PHASE DE GROUPES
            // ============================================
            System.out.println("\n⚽ Insertion des matchs...");
            
            stmt.execute("INSERT INTO match_data (nom, date_match, score) VALUES " +
                    "('Maroc vs Brésil', '2030-06-11', ''), " +
                    "('Allemagne vs Australie', '2030-06-11', ''), " +
                    "('France vs Argentine', '2030-06-12', ''), " +
                    "('Japon vs Danemark', '2030-06-12', ''), " +
                    "('Espagne vs Portugal', '2030-06-13', ''), " +
                    "('Mexique vs Croatie', '2030-06-13', ''), " +
                    "('Angleterre vs Pays-Bas', '2030-06-14', ''), " +
                    "('Sénégal vs États-Unis', '2030-06-14', '')");
            
            System.out.println("✅ 8 matchs de la phase de groupes insérés");

            System.out.println("\n========================================");
            System.out.println("✅ DONNÉES COUPE DU MONDE 2030 CHARGÉES");
            System.out.println("========================================");
            System.out.println("📊 Résumé :");
            System.out.println("   • 16 équipes nationales");
            System.out.println("   • 40+ joueurs");
            System.out.println("   • 13 stades officiels");
            System.out.println("   • 8 matchs programmés");
            System.out.println("========================================\n");

        } catch (Exception e) {
            System.err.println("❌ Erreur insertion données : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void insererEquipe(Connection conn, String nom, String pays, String entraineur, int nbJoueurs, int groupeId) throws Exception {
        String sql = "INSERT INTO equipe (nom, pays, entraineur, nombre_joueurs, groupe_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, pays);
            pstmt.setString(3, entraineur);
            pstmt.setInt(4, nbJoueurs);
            pstmt.setInt(5, groupeId);
            pstmt.executeUpdate();
        }
    }

    private static void insererStade(Connection conn, String nom, String ville, String pays, int capacite) throws Exception {
        String sql = "INSERT INTO stade (nom, ville, pays, capacite) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, ville);
            pstmt.setString(3, pays);
            pstmt.setInt(4, capacite);
            pstmt.executeUpdate();
        }
    }

    private static int getEquipeId(Connection conn, String nomEquipe) throws Exception {
        String sql = "SELECT id FROM equipe WHERE nom = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomEquipe);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return 0;
    }

    private static void insererJoueursBatch(Connection conn, int equipeId, String[][] joueurs) throws Exception {
        String sql = "INSERT INTO joueur (nom, prenom, numero, poste, equipe_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (String[] joueur : joueurs) {
                pstmt.setString(1, joueur[0]);
                pstmt.setString(2, joueur[1]);
                pstmt.setInt(3, Integer.parseInt(joueur[2]));
                pstmt.setString(4, joueur[3]);
                pstmt.setInt(5, equipeId);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }
}
