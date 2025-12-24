package com.example.gestionmatch.controller;

import com.example.gestionmatch.dao.StadeDAO;
import com.example.gestionmatch.model.Stade;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormStadeController {

    @FXML private TextField nomField;
    @FXML private TextField villeField;
    @FXML private TextField paysField;
    @FXML private TextField capaciteField;

    private final StadeDAO stadeDAO = new StadeDAO();
    private Stade stadeModif;
    private Runnable onSaveCallback;

    public void setStade(Stade stade) {
        this.stadeModif = stade;
        if (stade != null) {
            nomField.setText(stade.getNom());
            villeField.setText(stade.getVille());
            paysField.setText(stade.getPays());
            capaciteField.setText(String.valueOf(stade.getCapacite()));
        }
    }

    public void setOnSaveCallback(Runnable callback) {
        this.onSaveCallback = callback;
    }

    @FXML
    private void sauvegarder() {
        String nom = nomField.getText().trim();
        String ville = villeField.getText().trim();
        String pays = paysField.getText().trim();
        String capaciteStr = capaciteField.getText().trim();

        if (nom.isEmpty() || ville.isEmpty() || pays.isEmpty() || capaciteStr.isEmpty()) {
            showAlert("Erreur", "Tous les champs sont obligatoires", Alert.AlertType.ERROR);
            return;
        }

        int capacite;
        try {
            capacite = Integer.parseInt(capaciteStr);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "La capacité doit être un nombre", Alert.AlertType.ERROR);
            return;
        }

        boolean success;
        if (stadeModif == null) {
            Stade nouveauStade = new Stade(0, nom, ville, pays, capacite);
            success = stadeDAO.ajouterStade(nouveauStade);
        } else {
            stadeModif.setNom(nom);
            stadeModif.setVille(ville);
            stadeModif.setPays(pays);
            stadeModif.setCapacite(capacite);
            success = stadeDAO.modifierStade(stadeModif);
        }

        if (success) {
            showAlert("Succès", "Stade sauvegardé avec succès", Alert.AlertType.INFORMATION);
            if (onSaveCallback != null) onSaveCallback.run();
            fermer();
        } else {
            showAlert("Erreur", "Erreur lors de la sauvegarde", Alert.AlertType.ERROR);
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
