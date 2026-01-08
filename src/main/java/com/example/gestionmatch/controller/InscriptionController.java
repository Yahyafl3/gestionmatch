package com.example.gestionmatch.controller;

import java.io.IOException;

import com.example.gestionmatch.dao.UtilisateurDAO;
import com.example.gestionmatch.model.Utilisateur;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

@SuppressWarnings("unused")
public class InscriptionController {

    @FXML private TextField registerPrenom;
    @FXML private TextField registerNom;
    @FXML private TextField registerEmail;
    @FXML private PasswordField registerPassword;
    @FXML private PasswordField registerPasswordConfirm;
    @FXML private Button signupButton;

    // Carte UI pour l'effet 3D
    @FXML private VBox signupCard;

    // Animations réutilisables
    private ScaleTransition scaleAnim;
    private RotateTransition rotateAnim;
    private ParallelTransition currentTransition;

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @FXML
    public void initialize() {
        System.out.println("✅ InscriptionController initialisé");
    }

    // Handlers d'animation 3D pour la carte d'inscription
    @FXML
    private void onCardEnter(MouseEvent e) {
        try {
            if (currentTransition != null) currentTransition.stop();

            scaleAnim = new ScaleTransition(Duration.millis(220), signupCard);
            scaleAnim.setToX(1.03);
            scaleAnim.setToY(1.03);

            rotateAnim = new RotateTransition(Duration.millis(220), signupCard);
            rotateAnim.setAxis(Rotate.Y_AXIS);
            rotateAnim.setToAngle(4);

            currentTransition = new ParallelTransition(scaleAnim, rotateAnim);
            currentTransition.play();
        } catch (Exception ex) {
            // ignore
        }
    }

    @FXML
    private void onCardExit(MouseEvent e) {
        try {
            if (currentTransition != null) currentTransition.stop();

            ScaleTransition s = new ScaleTransition(Duration.millis(220), signupCard);
            s.setToX(1.0);
            s.setToY(1.0);

            RotateTransition r = new RotateTransition(Duration.millis(220), signupCard);
            r.setAxis(Rotate.Y_AXIS);
            r.setToAngle(0);

            currentTransition = new ParallelTransition(s, r);
            currentTransition.play();
        } catch (Exception ex) {
            // ignore
        }
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
            
            // Garder les dimensions actuelles de la fenêtre
            double width = stage.getWidth();
            double height = stage.getHeight();
            
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.setTitle("Connexion - Coupe du Monde 2030");
        } catch (IOException e) {
            System.err.println("Erreur lors de la navigation vers la connexion: " + e.getMessage());
        }
    }

    @FXML
    private void retourAccueil() {
        try {
            // Rediriger vers la page de connexion (accueil supprimé)
            System.out.println("🔄 Retour à la connexion...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) registerEmail.getScene().getWindow();
            Scene scene = new Scene(root, 1600, 1000);
            stage.setScene(scene);
            stage.setTitle("Connexion - FIFA 2030");
            System.out.println("✅ Retour à la connexion réussi");
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la navigation vers la connexion: " + e.getMessage());
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