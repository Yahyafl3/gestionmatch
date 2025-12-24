package com.example.gestionmatch.controller;

import java.io.IOException;

import com.example.gestionmatch.dao.UtilisateurDAO;
import com.example.gestionmatch.model.Utilisateur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InscriptionController {

    @FXML private TextField registerPrenom;
    @FXML private TextField registerNom;
    @FXML private TextField registerEmail;
    @FXML private PasswordField registerPassword;
    @FXML private PasswordField registerPasswordConfirm;
    @FXML private Button btnSInscrire;
    @FXML private Button btnRetour;

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @FXML
    public void initialize() {
        System.out.println("✅ InscriptionController initialisé");
    }

    @FXML
    public void sInscrire() {
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
    private void allerConnexion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) registerEmail.getScene().getWindow();
            Scene scene = new Scene(root, 1600, 1000);
            stage.setScene(scene);
            stage.setTitle("Connexion - FIFA 2030");
        } catch (IOException e) {
            System.err.println("Erreur lors de la navigation vers la connexion: " + e.getMessage());
        }
    }

    @FXML
    private void retourAccueil() {
        try {
            System.out.println("🔄 Retour à l'accueil...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/accueil.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) registerEmail.getScene().getWindow();
            Scene scene = new Scene(root, 1600, 1000);
            stage.setScene(scene);
            stage.setTitle("Accueil - FIFA 2030");
            System.out.println("✅ Retour à l'accueil réussi");
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du retour à l'accueil: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}