package com.example.gestionmatch.controller;

import java.io.IOException;

import com.example.gestionmatch.dao.UtilisateurDAO;
import com.example.gestionmatch.model.Utilisateur;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class LoginController {
    
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberMe;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Label errorLabel;

    @FXML
    public void initialize() {
        // Clear error when user types
        if (usernameField != null) {
            usernameField.textProperty().addListener((obs, oldV, newV) -> clearError());
            usernameField.addEventFilter(KeyEvent.KEY_PRESSED, this::handleEnterKey);
        }
        if (passwordField != null) {
            passwordField.textProperty().addListener((obs, oldV, newV) -> clearError());
            passwordField.addEventFilter(KeyEvent.KEY_PRESSED, this::handleEnterKey);
        }
        if (loginButton != null) {
            loginButton.setDefaultButton(true);
        }
    }

    private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            event.consume();
            allerConnexion(new ActionEvent());
        }
    }

    private void clearError() {
        if (errorLabel != null) {
            errorLabel.setText("");
        }
        if (usernameField != null) {
            usernameField.setStyle("");
        }
        if (passwordField != null) {
            passwordField.setStyle("");
        }
    }

    // Méthode appelée depuis le FXML : bouton 'Se connecter'
    @FXML
    public void allerConnexion(ActionEvent event) {
        clearError();

        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText();

        // Validation simple
        if (username.isEmpty()) {
            setError("Le nom d'utilisateur est requis.");
            usernameField.requestFocus();
            usernameField.setStyle("-fx-border-color: #C1272D; -fx-border-width: 2px; -fx-border-radius: 8px;");
            return;
        }
        if (password.isEmpty()) {
            setError("Le mot de passe est requis.");
            passwordField.requestFocus();
            passwordField.setStyle("-fx-border-color: #C1272D; -fx-border-width: 2px; -fx-border-radius: 8px;");
            return;
        }
        if (password.length() < 6) {
            setError("Le mot de passe doit contenir au moins 6 caractères.");
            passwordField.requestFocus();
            passwordField.setStyle("-fx-border-color: #C1272D; -fx-border-width: 2px; -fx-border-radius: 8px;");
            return;
        }

        // Authentification avec la base de données
        loginButton.setDisable(true);

        new Thread(() -> {
            try {
                // Authentifier l'utilisateur via le DAO
                Utilisateur utilisateur = utilisateurDAO.connexion(username, password);
                
                Platform.runLater(() -> {
                    loginButton.setDisable(false);
                    if (utilisateur != null) {
                        // Succès — navigation vers la vue selon le rôle
                        System.out.println("✅ Utilisateur connecté : " + username);
                        System.out.println("   Rôle : " + utilisateur.getRole());
                        System.out.println("   Se souvenir : " + (rememberMe != null && rememberMe.isSelected()));
                        setError("");
                        try {
                            loadDashboard(utilisateur);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erreur");
                            alert.setHeaderText("Impossible de charger le dashboard");
                            alert.setContentText(ex.getMessage());
                            alert.showAndWait();
                        }
                    } else {
                        setError("Email ou mot de passe incorrect.");
                        passwordField.setStyle("-fx-border-color: #C1272D; -fx-border-width: 2px; -fx-border-radius: 8px;");
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    loginButton.setDisable(false);
                    setError("Erreur de connexion à la base de données.");
                    e.printStackTrace();
                });
            }
        }).start();
    }

    // Méthode appelée depuis le FXML : lien 'S'inscrire'
    @FXML
    public void sInscrire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/inscription.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            
            // Garder les dimensions actuelles de la fenêtre
            double width = stage.getWidth();
            double height = stage.getHeight();
            
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.setTitle("Inscription - Coupe du Monde 2030");
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de charger la page d'inscription");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void setError(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
        }
    }

    // Charge le dashboard selon le rôle de l'utilisateur
    private void loadDashboard(Utilisateur utilisateur) throws IOException {
        String fxmlPath;
        String title;
        
        if ("ADMIN".equalsIgnoreCase(utilisateur.getRole())) {
            fxmlPath = "/fxml/admin-dashboard.fxml";
            title = "Dashboard Admin - Coupe du Monde 2030";
        } else {
            fxmlPath = "/fxml/user-dashboard.fxml";
            title = "Dashboard Utilisateur - Coupe du Monde 2030";
        }
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        
        // Passer les informations de l'utilisateur au contrôleur du dashboard
        if ("USER".equalsIgnoreCase(utilisateur.getRole())) {
            UserDashboardController controller = loader.getController();
            if (controller != null) {
                controller.setUsername(utilisateur.getNomComplet());
            }
        }
        
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.centerOnScreen();
    }
}
