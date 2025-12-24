package com.example.gestionmatch.controller;

import com.example.gestionmatch.dao.EquipeDAO;
import com.example.gestionmatch.model.Equipe;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormEquipeController {

    @FXML private TextField nomField;
    @FXML private TextField paysField;
    @FXML private TextField entraineurField;
    @FXML private TextField nombreJoueursField;
    @FXML private TextField matchsJouesField;
    @FXML private TextField victoiresField;
    @FXML private TextField nulsField;
    @FXML private TextField defaitesField;
    @FXML private TextField butsMarquesField;
    @FXML private TextField butsEncaissesField;
    @FXML private TextField pointsField;

    private final EquipeDAO equipeDAO = new EquipeDAO();
    private Equipe equipeModif;
    private Runnable onSaveCallback;

    public void setEquipe(Equipe equipe) {
        this.equipeModif = equipe;
        if (equipe != null) {
            nomField.setText(equipe.getNom());
            paysField.setText(equipe.getPays());
            entraineurField.setText(equipe.getEntraineur());
            nombreJoueursField.setText(String.valueOf(equipe.getNombreJoueurs()));
            matchsJouesField.setText(String.valueOf(equipe.getMatchsJoues()));
            victoiresField.setText(String.valueOf(equipe.getVictoires()));
            nulsField.setText(String.valueOf(equipe.getNuls()));
            defaitesField.setText(String.valueOf(equipe.getDefaites()));
            butsMarquesField.setText(String.valueOf(equipe.getButsMarques()));
            butsEncaissesField.setText(String.valueOf(equipe.getButsEncaisses()));
            pointsField.setText(String.valueOf(equipe.getPoints()));
        }
    }

    public void setOnSaveCallback(Runnable callback) {
        this.onSaveCallback = callback;
    }

    @FXML
    private void sauvegarder() {
        String nom = nomField.getText().trim();
        String pays = paysField.getText().trim();
        String entraineur = entraineurField.getText().trim();

        if (nom.isEmpty() || pays.isEmpty()) {
            showAlert("Erreur", "Le nom et le pays sont obligatoires", Alert.AlertType.ERROR);
            return;
        }

        // Récupérer les valeurs numériques avec valeurs par défaut
        int nombreJoueurs = parseIntOrDefault(nombreJoueursField.getText(), 0);
        int matchsJoues = parseIntOrDefault(matchsJouesField.getText(), 0);
        int victoires = parseIntOrDefault(victoiresField.getText(), 0);
        int nuls = parseIntOrDefault(nulsField.getText(), 0);
        int defaites = parseIntOrDefault(defaitesField.getText(), 0);
        int butsMarques = parseIntOrDefault(butsMarquesField.getText(), 0);
        int butsEncaisses = parseIntOrDefault(butsEncaissesField.getText(), 0);
        int points = parseIntOrDefault(pointsField.getText(), 0);

        boolean success;
        if (equipeModif == null) {
            Equipe nouvelleEquipe = new Equipe(nom, pays, entraineur);
            nouvelleEquipe.setNombreJoueurs(nombreJoueurs);
            nouvelleEquipe.setMatchsJoues(matchsJoues);
            nouvelleEquipe.setVictoires(victoires);
            nouvelleEquipe.setNuls(nuls);
            nouvelleEquipe.setDefaites(defaites);
            nouvelleEquipe.setButsMarques(butsMarques);
            nouvelleEquipe.setButsEncaisses(butsEncaisses);
            nouvelleEquipe.setPoints(points);
            success = equipeDAO.ajouterEquipe(nouvelleEquipe);
        } else {
            equipeModif.setNom(nom);
            equipeModif.setPays(pays);
            equipeModif.setEntraineur(entraineur);
            equipeModif.setNombreJoueurs(nombreJoueurs);
            equipeModif.setMatchsJoues(matchsJoues);
            equipeModif.setVictoires(victoires);
            equipeModif.setNuls(nuls);
            equipeModif.setDefaites(defaites);
            equipeModif.setButsMarques(butsMarques);
            equipeModif.setButsEncaisses(butsEncaisses);
            equipeModif.setPoints(points);
            success = equipeDAO.modifierEquipe(equipeModif);
        }

        if (success) {
            showAlert("Succès", "Équipe sauvegardée avec succès", Alert.AlertType.INFORMATION);
            if (onSaveCallback != null) onSaveCallback.run();
            fermer();
        } else {
            showAlert("Erreur", "Erreur lors de la sauvegarde", Alert.AlertType.ERROR);
        }
    }

    private int parseIntOrDefault(String value, int defaultValue) {
        try {
            return value == null || value.trim().isEmpty() ? defaultValue : Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    @FXML
    private void annuler() {
        fermer();
    }

    private void fermer() {
        Stage stage = (Stage) nomField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
