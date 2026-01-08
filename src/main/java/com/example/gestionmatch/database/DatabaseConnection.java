package com.example.gestionmatch.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe de gestion de la connexion à la base de données MySQL
 * Utilise le pattern Singleton pour une connexion unique
 */
public class DatabaseConnection {

    // ========================================
    // CONFIGURATION DE LA BASE DE DONNÉES
    // ========================================

    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "gestionmatch";
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
            + "?useSSL=false"
            + "&serverTimezone=UTC"
            + "&allowPublicKeyRetrieval=true"
            + "&useUnicode=true"
            + "&characterEncoding=UTF-8";

    private static final String USER = "root";
    private static final String PASSWORD = "";  // ⚠️ Changez si vous avez un mot de passe MySQL

    // ========================================
    // SINGLETON
    // ========================================

    private static Connection connection = null;

    /**
     * Constructeur privé pour empêcher l'instanciation
     */
    private DatabaseConnection() {
    }

    // ========================================
    // MÉTHODES PUBLIQUES
    // ========================================

    /**
     * Obtenir une connexion à la base de données
     *
     * @return Connection active
     * @throws SQLException si la connexion échoue
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Charger le driver JDBC MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Créer une nouvelle connexion à chaque appel
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Connexion à la BD réussie : " + DATABASE);
                return conn;
            } else {
                throw new SQLException("La connexion est null ou fermée");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver MySQL non trouvé !");
            System.err.println("   Vérifiez que mysql-connector-j est dans pom.xml");
            throw new SQLException("Driver MySQL introuvable : " + e.getMessage(), e);

        } catch (SQLException e) {
            System.err.println("❌ Erreur de connexion à la base de données !");
            System.err.println("   URL      : " + URL);
            System.err.println("   User     : " + USER);
            System.err.println("   Database : " + DATABASE);
            System.err.println("   Message  : " + e.getMessage());
            throw e;
        }
    }

    /**
     * Fermer proprement une connexion
     *
     * @param conn La connexion à fermer
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                    System.out.println("✅ Connexion fermée");
                }
            } catch (SQLException e) {
                System.err.println("⚠️ Erreur lors de la fermeture : " + e.getMessage());
            }
        }
    }

    /**
     * Tester la connexion à la base de données
     *
     * @return true si la connexion fonctionne
     */
    public static boolean testConnection() {
        Connection conn = null;
        try {
            conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * Obtenir les informations de connexion
     *
     * @return String avec les infos de connexion
     */
    public static String getConnectionInfo() {
        return String.format(
                "Base de données : %s\nServeur : %s:%s\nUtilisateur : %s",
                DATABASE, HOST, PORT, USER
        );
    }

    // ========================================
    // MAIN POUR TESTER LA CONNEXION
    // ========================================

    /**
     * Méthode main pour tester la connexion
     */
    public static void main(String[] args) {
        System.out.println("\n========================================");
        System.out.println("🔌 TEST DE CONNEXION À LA BASE DE DONNÉES");
        System.out.println("========================================\n");

        System.out.println("📋 Configuration :");
        System.out.println(getConnectionInfo());
        System.out.println("\n⏳ Tentative de connexion...\n");

        Connection conn = null;
        try {
            // Tester la connexion
            conn = getConnection();

            if (conn != null && !conn.isClosed()) {
                System.out.println("\n========================================");
                System.out.println("✅ TEST RÉUSSI !");
                System.out.println("========================================");
                System.out.println("La connexion à MySQL fonctionne correctement.");
                System.out.println("Vous pouvez maintenant utiliser l'application.\n");
            }

        } catch (SQLException e) {
            System.out.println("\n========================================");
            System.out.println("❌ TEST ÉCHOUÉ !");
            System.out.println("========================================");
            System.out.println("\n🔧 VÉRIFIEZ LES POINTS SUIVANTS :\n");
            System.out.println("1. ✓ XAMPP est lancé");
            System.out.println("2. ✓ MySQL est démarré (bouton Start dans XAMPP)");
            System.out.println("3. ✓ La base 'gestionmatch' existe dans phpMyAdmin");
            System.out.println("4. ✓ L'utilisateur est 'root' sans mot de passe");
            System.out.println("5. ✓ Le port MySQL est bien 3306");
            System.out.println("\n📋 Détails de l'erreur :");
            System.out.println(e.getMessage());
            System.out.println("\n========================================\n");

        } finally {
            closeConnection(conn);
        }
    }
}