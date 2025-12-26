package com.example.gestionmatch.controller;

import java.io.IOException;

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

        // Simuler traitement asynchrone (appel d'API / vérification)
        loginButton.setDisable(true);
        loginButton.setText("Connexion...");

        new Thread(() -> {
            try {
                Thread.sleep(800); // simulation
                boolean authSuccess = mockAuthenticate(username, password);
                Platform.runLater(() -> {
                    loginButton.setDisable(false);
                    loginButton.setText("Se connecter");
                    if (authSuccess) {
                        // Succès — navigation vers la vue principale
                        System.out.println("Utilisateur connecté : " + username + " (Se souvenir: " + (rememberMe != null && rememberMe.isSelected()) + ")");
                        setError("");
                        try {
                            loadMainView();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erreur");
                            alert.setHeaderText("Impossible de charger la vue principale");
                            alert.setContentText(ex.getMessage());
                            alert.showAndWait();
                        }
                    } else {
                        setError("Nom d'utilisateur ou mot de passe incorrect.");
                        passwordField.setStyle("-fx-border-color: #C1272D; -fx-border-width: 2px; -fx-border-radius: 8px;");
                    }
                });
            } catch (InterruptedException e) {
                Platform.runLater(() -> {
                    loginButton.setDisable(false);
                    loginButton.setText("Se connecter");
                    setError("Erreur interne. Réessayez.");
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

    // Mock d'authentification : accepte user=admin@fifa2030.com / pwd=Admin123 pour test
    private boolean mockAuthenticate(String username, String password) {
        return "admin@fifa2030.com".equals(username) && "Admin123".equals(password);
    }

    // Charge le dashboard admin après connexion
    private void loadMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin-dashboard.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Dashboard Admin - Coupe du Monde 2030");
    }
}
