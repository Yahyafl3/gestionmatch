package com.example.gestionmatch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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

            // ⭐ CHARGER LA PAGE D'ACCUEIL EN PREMIER
            System.out.println("🏠 Chargement de la page d'accueil...");
            
            // Utiliser FXMLLoader avec un chemin relatif pour éviter les problèmes d'espaces
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/accueil.fxml"));
            Parent root = loader.load();

            System.out.println("✅ Page d'accueil chargée avec succès\n");

            // Créer la scène
            Scene scene = new Scene(root, 1600, 1000);

            // Configuration de la fenêtre
            primaryStage.setTitle("🏆 Coupe du Monde 2030 - Connexion");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);  // Plein écran
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
            System.err.println("   1. Le fichier accueil.fxml existe dans resources/fxml/");
            System.err.println("   2. Le fichier accueil-style.css existe dans resources/styles/");
            System.err.println("   3. AccueilController existe dans le package controller");
            System.err.println("   4. L'image 'le monde.jpg' existe dans resources/images/");
            System.err.println("========================================\n");
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