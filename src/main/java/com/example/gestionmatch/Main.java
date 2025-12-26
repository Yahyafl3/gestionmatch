package com.example.gestionmatch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.DepthTest;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale de l'application
 * Coupe du Monde 2030 - Gestion des Équipes et Matchs
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("\n========================================");
            System.out.println("🏆 COUPE DU MONDE 2030");
            System.out.println("   Application de Gestion");
            System.out.println("========================================\n");

            // Initialiser la base de données
            SetupDatabaseAuto.initializeDatabase();

            // ⭐ CHARGER LA PAGE DE CONNEXION EN PREMIER
            System.out.println("🔐 Chargement de la page de connexion...");

            // Utiliser FXMLLoader avec un chemin relatif pour éviter les problèmes d'espaces
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();

            // Workaround : désactiver le cache sur tous les nœuds (prévenir ClassCastException liée aux bounds)
            disableCacheRecursively(root);

            // Si utilisation d'éléments 3D, activer le DepthTest sur la racine
            root.setDepthTest(DepthTest.ENABLE);

            System.out.println("✅ Page de connexion chargée avec succès\n");

            // Créer la scène avec une taille moyenne
            Scene scene = new Scene(root, 1200, 800);

            // Configuration de la fenêtre
            primaryStage.setTitle("🏆 Coupe du Monde 2030 - Connexion");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.centerOnScreen();

            // Afficher la fenêtre
            primaryStage.show();

            System.out.println("========================================");
            System.out.println("✅ APPLICATION LANCÉE");
            System.out.println("========================================\n");

            System.out.println("💡 Navigation :");
            System.out.println("   • Connectez-vous avec votre compte");
            System.out.println("   • Créez un nouveau compte si vous n'en avez pas\n");

            System.out.println("🔐 Compte administrateur par défaut :");
            System.out.println("   Email    : admin@fifa2030.com");
            System.out.println("   Password : Admin123\n");

        } catch (Exception e) {
            System.err.println("\n========================================");
            System.err.println("❌ ERREUR LORS DU LANCEMENT");
            System.err.println("========================================");
            System.err.println("Type d'erreur : " + e.getClass().getName());
            System.err.println("Message : " + e.getMessage());
            System.err.println("\n📋 Stack trace complet :");
            e.printStackTrace();
            System.err.println("\n🔧 Vérifications à faire :");
            System.err.println("   1. Le fichier login.fxml existe dans resources/fxml/");
            System.err.println("   2. Le fichier login-style.css existe dans resources/styles/");
            System.err.println("   3. LoginController existe dans le package controller");
            System.err.println("   4. L'image de fond existe dans resources/images/");
            System.err.println("========================================\n");
        }
    }

    /**
     * Désactive récursivement le cache sur tous les nœuds (workaround pour les bugs de bounds/transform)
     */
    private void disableCacheRecursively(Node node) {
        if (node == null) return;
        node.setCache(false);
        if (node instanceof Parent parent) {
            for (Node child : parent.getChildrenUnmodifiable()) {
                disableCacheRecursively(child);
            }
        }
    }

    /**
     * Méthode principale - Point d'entrée de l'application
     */
    public static void main(String[] args) {
        System.out.println("\n🚀 Démarrage de l'application Coupe du Monde 2030...\n");
        launch(args);
    }

    /**
     * Méthode appelée lors de l'arrêt de l'application
     */
    @Override
    public void stop() {
        System.out.println("\n========================================");
        System.out.println("👋 FERMETURE DE L'APPLICATION");
        System.out.println("========================================");
        System.out.println("Merci d'avoir utilisé l'application !");
        System.out.println("À bientôt pour la Coupe du Monde 2030 ! 🏆\n");
    }
}