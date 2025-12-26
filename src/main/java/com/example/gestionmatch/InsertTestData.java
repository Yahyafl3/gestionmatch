package com.example.gestionmatch;

import java.sql.Connection;
import java.sql.Statement;

import com.example.gestionmatch.database.DatabaseConnection;

public class InsertTestData {

    public static void insererDonneesTest() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.println("\n========================================");
            System.out.println("📊 INSERTION DES DONNÉES DE TEST");
            System.out.println("========================================\n");

            // Vérifier si des données existent déjà
            var rs = stmt.executeQuery("SELECT COUNT(*) as count FROM equipe");
            if (rs.next() && rs.getInt("count") > 0) {
                System.out.println("ℹ️  Données déjà présentes, insertion annulée");
                return;
            }

            // Insertion des 4 équipes
            stmt.execute("INSERT INTO equipe (nom, pays, entraineur, points) VALUES " +
                    "('Maroc', 'Maroc', 'Walid Regragui', 0), " +
                    "('France', 'France', 'Didier Deschamps', 0), " +
                    "('Brésil', 'Brésil', 'Fernando Diniz', 0), " +
                    "('Argentine', 'Argentine', 'Lionel Scaloni', 0)");
            System.out.println("✅ 4 équipes insérées");

            // Récupérer les IDs
            var rsMaroc = stmt.executeQuery("SELECT id FROM equipe WHERE nom = 'Maroc'");
            int marocId = rsMaroc.next() ? rsMaroc.getInt("id") : 0;
            
            var rsFrance = stmt.executeQuery("SELECT id FROM equipe WHERE nom = 'France'");
            int franceId = rsFrance.next() ? rsFrance.getInt("id") : 0;
            
            var rsBresil = stmt.executeQuery("SELECT id FROM equipe WHERE nom = 'Brésil'");
            int bresilId = rsBresil.next() ? rsBresil.getInt("id") : 0;
            
            var rsArgentine = stmt.executeQuery("SELECT id FROM equipe WHERE nom = 'Argentine'");
            int argentineId = rsArgentine.next() ? rsArgentine.getInt("id") : 0;

            // Joueurs du Maroc
            stmt.execute("INSERT INTO joueur (nom, prenom, numero, poste, equipe_id) VALUES " +
                    "('Bounou', 'Yassine', 1, 'Gardien', " + marocId + "), " +
                    "('Hakimi', 'Achraf', 2, 'Défenseur', " + marocId + "), " +
                    "('Mazraoui', 'Noussair', 3, 'Défenseur', " + marocId + "), " +
                    "('Amrabat', 'Sofyan', 4, 'Milieu', " + marocId + "), " +
                    "('Ounahi', 'Azzedine', 8, 'Milieu', " + marocId + "), " +
                    "('Ziyech', 'Hakim', 7, 'Attaquant', " + marocId + "), " +
                    "('En-Nesyri', 'Youssef', 19, 'Attaquant', " + marocId + "), " +
                    "('Boufal', 'Sofiane', 17, 'Attaquant', " + marocId + "), " +
                    "('Saiss', 'Romain', 6, 'Défenseur', " + marocId + "), " +
                    "('Aguerd', 'Nayef', 5, 'Défenseur', " + marocId + "), " +
                    "('Amallah', 'Selim', 15, 'Milieu', " + marocId + ")");


            // Joueurs de la France
            stmt.execute("INSERT INTO joueur (nom, prenom, numero, poste, equipe_id) VALUES " +
                    "('Lloris', 'Hugo', 1, 'Gardien', " + franceId + "), " +
                    "('Pavard', 'Benjamin', 2, 'Défenseur', " + franceId + "), " +
                    "('Varane', 'Raphaël', 4, 'Défenseur', " + franceId + "), " +
                    "('Koundé', 'Jules', 5, 'Défenseur', " + franceId + "), " +
                    "('Tchouaméni', 'Aurélien', 8, 'Milieu', " + franceId + "), " +
                    "('Griezmann', 'Antoine', 7, 'Milieu', " + franceId + "), " +
                    "('Mbappé', 'Kylian', 10, 'Attaquant', " + franceId + "), " +
                    "('Giroud', 'Olivier', 9, 'Attaquant', " + franceId + "), " +
                    "('Dembélé', 'Ousmane', 11, 'Attaquant', " + franceId + "), " +
                    "('Rabiot', 'Adrien', 14, 'Milieu', " + franceId + "), " +
                    "('Hernandez', 'Theo', 22, 'Défenseur', " + franceId + ")");

            // Joueurs du Brésil
            stmt.execute("INSERT INTO joueur (nom, prenom, numero, poste, equipe_id) VALUES " +
                    "('Alisson', 'Becker', 1, 'Gardien', " + bresilId + "), " +
                    "('Danilo', 'Luiz', 2, 'Défenseur', " + bresilId + "), " +
                    "('Thiago Silva', '', 3, 'Défenseur', " + bresilId + "), " +
                    "('Marquinhos', '', 4, 'Défenseur', " + bresilId + "), " +
                    "('Casemiro', '', 5, 'Milieu', " + bresilId + "), " +
                    "('Neymar', 'Jr', 10, 'Attaquant', " + bresilId + "), " +
                    "('Vinicius', 'Junior', 7, 'Attaquant', " + bresilId + "), " +
                    "('Richarlison', '', 9, 'Attaquant', " + bresilId + "), " +
                    "('Raphinha', '', 11, 'Attaquant', " + bresilId + "), " +
                    "('Paquetá', 'Lucas', 8, 'Milieu', " + bresilId + "), " +
                    "('Militão', 'Éder', 14, 'Défenseur', " + bresilId + ")");

            // Joueurs de l'Argentine
            stmt.execute("INSERT INTO joueur (nom, prenom, numero, poste, equipe_id) VALUES " +
                    "('Martínez', 'Emiliano', 23, 'Gardien', " + argentineId + "), " +
                    "('Molina', 'Nahuel', 26, 'Défenseur', " + argentineId + "), " +
                    "('Romero', 'Cristian', 13, 'Défenseur', " + argentineId + "), " +
                    "('Otamendi', 'Nicolás', 19, 'Défenseur', " + argentineId + "), " +
                    "('De Paul', 'Rodrigo', 7, 'Milieu', " + argentineId + "), " +
                    "('Fernández', 'Enzo', 24, 'Milieu', " + argentineId + "), " +
                    "('Messi', 'Lionel', 10, 'Attaquant', " + argentineId + "), " +
                    "('Di María', 'Ángel', 11, 'Attaquant', " + argentineId + "), " +
                    "('Álvarez', 'Julián', 9, 'Attaquant', " + argentineId + "), " +
                    "('Mac Allister', 'Alexis', 20, 'Milieu', " + argentineId + "), " +
                    "('Acuña', 'Marcos', 8, 'Défenseur', " + argentineId + ")");
            
            System.out.println("✅ 44 joueurs insérés (11 par équipe)");

            // Insertion des 2 stades
            stmt.execute("INSERT INTO stade (nom, ville, pays, capacite) VALUES " +
                    "('Stade Mohammed V', 'Casablanca', 'Maroc', 67000), " +
                    "('Stade Hassan II', 'Rabat', 'Maroc', 115000)");
            System.out.println("✅ 2 stades insérés");

            // Insertion des 2 matchs
            stmt.execute("INSERT INTO match_data (nom, score, date_match) VALUES " +
                    "('Maroc vs France', '0-0', '2030-06-15'), " +
                    "('Brésil vs Argentine', '0-0', '2030-06-16')");
            System.out.println("✅ 2 matchs insérés");

            System.out.println("\n========================================");
            System.out.println("✅ DONNÉES DE TEST INSÉRÉES");
            System.out.println("========================================\n");

        } catch (Exception e) {
            System.err.println("❌ Erreur insertion données : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
