package com.example.gestionmatch.controller;

import com.example.gestionmatch.dao.MatchDAO;
import com.example.gestionmatch.database.DatabaseConnection;
import com.example.gestionmatch.model.Match;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class MainController {

    @FXML private Label statusLabel;
    @FXML private TextField nomField;
    @FXML private TextField scoreField;
    @FXML private DatePicker datePicker;
    @FXML private TextArea resultArea;

    private final MatchDAO matchDAO = new MatchDAO();

    @FXML
    private void testConnection() {
        if (DatabaseConnection.testConnection()) {
            statusLabel.setText("✅ Connexion réussie !");
            statusLabel.setStyle("-fx-text-fill: green;");
        } else {
            statusLabel.setText("❌ Connexion échouée !");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void ajouterMatch() {
        if (nomField.getText().isEmpty() || scoreField.getText().isEmpty() || datePicker.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Remplissez tous les champs !");
            return;
        }

        Match match = new Match(
                nomField.getText(),
                scoreField.getText(),
                datePicker.getValue()
        );

        if (matchDAO.ajouter(match)) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Match ajouté !");
            clearFields();
            chargerMatches();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout !");
        }
    }

    @FXML
    private void chargerMatches() {
        List<Match> matches = matchDAO.getTous();
        StringBuilder sb = new StringBuilder();
        sb.append("📋 Liste des matches :\n");
        sb.append("─────────────────────────────────\n");

        for (Match m : matches) {
            sb.append(String.format("%s | Score: %s | Date: %s\n",
                    m.getNom(), m.getScore(), m.getDateMatch()));
        }

        resultArea.setText(sb.toString());
    }

    @FXML
    private void rechercherMatch() {
        List<Match> matches = matchDAO.rechercherParNom(nomField.getText());
        StringBuilder sb = new StringBuilder();

        for (Match m : matches) {
            sb.append(m.getNom()).append(" | ").append(m.getScore()).append("\n");
        }

        resultArea.setText(sb.toString());
    }

    @FXML
    private void supprimerTout() {
        if (matchDAO.supprimerTous()) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Tous les matches supprimés !");
            chargerMatches();
        }
    }

    private void clearFields() {
        nomField.clear();
        scoreField.clear();
        datePicker.setValue(null);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        testConnection();
    }
}