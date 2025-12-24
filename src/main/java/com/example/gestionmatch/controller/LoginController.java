package com.example.gestionmatch.controller;

import java.io.IOException;

import com.example.gestionmatch.dao.UtilisateurDAO;
import com.example.gestionmatch.model.Utilisateur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField loginEmail, registerEmail, registerPrenom, registerNom;
    @FXML private PasswordField loginPassword, registerPassword, registerPasswordConfirm;
    @FXML private CheckBox rememberMe;
    @FXML private javafx.scene.control.Button btnRetour;

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private static Utilisateur utilisateurConnecte;

    @FXML
    private void seConnecter() {
        String email = loginEmail.getText().trim();
        String password = loginPassword.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez remplir tous les champs");
            return;
        }

        Utilisateur utilisateur = utilisateurDAO.connexion(email, password);

        if (utilisateur != null) {
            utilisateurConnecte = utilisateur;
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Connexion réussie !");
            
            if ("ADMIN".equals(utilisateur.getRole())) {
                chargerDashboardAdmin();
            } else {
                chargerDashboardUser();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Email ou mot de passe incorrect");
        }
    }

    @FXML
    private void sInscrire() {
        String prenom = registerPrenom.getText().trim();
        String nom = registerNom.getText().trim();
        String email = registerEmail.getText().trim();
        String password = registerPassword.getText();
        String passwordConfirm = registerPasswordConfirm.getText();

        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez remplir tous les champs");
            return;
        }

        if (!password.equals(passwordConfirm)) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Les mots de passe ne correspondent pas");
            return;
        }

        if (password.length() < 6) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Le mot de passe doit contenir au moins 6 caractères");
            return;
        }

        if (utilisateurDAO.emailExiste(email)) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Cet email est déjà utilisé");
            return;
        }

        Utilisateur nouvelUtilisateur = new Utilisateur(email, password, nom, prenom);

        if (utilisateurDAO.inscrire(nouvelUtilisateur)) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Inscription réussie ! Vous pouvez maintenant vous connecter");
            allerConnexion();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'inscription");
        }
    }

    @FXML
    private void allerInscription() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/inscription.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) loginEmail.getScene().getWindow();
            Scene scene = new Scene(root, 1600, 1000);
            stage.setScene(scene);
            stage.setTitle("Inscription - FIFA 2030");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void retourAccueil() {
        try {
            System.out.println("🔄 Retour à l'accueil...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/accueil.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) loginEmail.getScene().getWindow();
            Scene scene = new Scene(root, 1600, 1000);
            stage.setScene(scene);
            stage.setTitle("Accueil - FIFA 2030");
            System.out.println("✅ Retour à l'accueil réussi");
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du retour à l'accueil: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void allerConnexion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) loginEmail.getScene().getWindow();
            Scene scene = new Scene(root, 1600, 1000);
            stage.setScene(scene);
            stage.setTitle("Connexion - FIFA 2030");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chargerDashboardAdmin() {
        try {
            System.out.println("📊 Chargement du dashboard admin...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin-dashboard.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) loginEmail.getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 900));
            stage.setTitle("Admin Dashboard - FIFA 2030");
            stage.setMaximized(true);
            System.out.println("✅ Dashboard admin chargé avec succès");
        } catch (IOException e) {
            System.err.println("❌ Erreur IO lors du chargement du dashboard admin: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger le dashboard admin: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du chargement du dashboard admin: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement du dashboard admin");
        }
    }

    private void chargerDashboardUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main-view.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) loginEmail.getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Dashboard - FIFA 2030");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger le dashboard");
            e.printStackTrace();
        }
    }

    private void clearFields() {
        loginEmail.clear();
        loginPassword.clear();
        registerPrenom.clear();
        registerNom.clear();
        registerEmail.clear();
        registerPassword.clear();
        registerPasswordConfirm.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public static void setUtilisateurConnecte(Utilisateur utilisateur) {
        utilisateurConnecte = utilisateur;
    }
}
