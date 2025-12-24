package com.example.gestionmatch.controller;

import com.example.gestionmatch.dao.EquipeDAO;
import com.example.gestionmatch.dao.JoueurDAO;
import com.example.gestionmatch.model.Equipe;
import com.example.gestionmatch.model.Joueur;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FormJoueurController implements Initializable {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField numeroField;
    @FXML private ComboBox<String> posteCombo;
    @FXML private ComboBox<Equipe> equipeCombo;

    private final JoueurDAO joueurDAO = new JoueurDAO();
    private final EquipeDAO equipeDAO = new EquipeDAO();
    private Joueur joueurModif;
    private Runnable onSaveCallback;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        posteCombo.getItems().addAll("Gardien", "Défenseur", "Milieu", "Attaquant");
        chargerEquipes();
    }

    private void chargerEquipes() {
        equipeCombo.getItems().clear();
        equipeCombo.getItems().addAll(equipeDAO.getAllEquipes());
    }

    public void setJoueur(Joueur joueur) {
        this.joueurModif = joueur;
        if (joueur != null) {
            nomField.setText(joueur.getNom());
            prenomField.setText(joueur.getPrenom());
            numeroField.setText(String.valueOf(joueur.getNumero()));
            posteCombo.setValue(joueur.getPoste());
            
            for (Equipe e : equipeCombo.getItems()) {
                if (e.getId() == joueur.getEquipeId()) {
                    equipeCombo.setValue(e);
                    break;
                }
            }
        }
    }

    public void setOnSaveCallback(Runnable callback) {
        this.onSaveCallback = callback;
    }

    @FXML
    private void sauvegarder() {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String numeroStr = numeroField.getText().trim();
        String poste = posteCombo.getValue();
        Equipe equipe = equipeCombo.getValue();

        if (nom.isEmpty() || prenom.isEmpty() || numeroStr.isEmpty() || poste == null || equipe == null) {
            showAlert("Erreur", "Tous les champs sont obligatoires", Alert.AlertType.ERROR);
            return;
        }

        int numero;
        try {
            numero = Integer.parseInt(numeroStr);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le numéro doit être un nombre", Alert.AlertType.ERROR);
            return;
        }

        boolean success;
        if (joueurModif == null) {
            Joueur nouveauJoueur = new Joueur(nom, prenom, null, poste, numero, equipe.getId());
            success = joueurDAO.ajouterJoueur(nouveauJoueur);
        } else {
            joueurModif.setNom(nom);
            joueurModif.setPrenom(prenom);
            joueurModif.setNumero(numero);
            joueurModif.setPoste(poste);
            joueurModif.setEquipeId(equipe.getId());
            success = joueurDAO.modifierJoueur(joueurModif);
        }

        if (success) {
            showAlert("Succès", "Joueur sauvegardé avec succès", Alert.AlertType.INFORMATION);
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
