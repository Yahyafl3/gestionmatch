package com.example.gestionmatch.controller;

import com.example.gestionmatch.dao.EquipeDAO;
import com.example.gestionmatch.dao.MatchDAO;
import com.example.gestionmatch.dao.StadeDAO;
import com.example.gestionmatch.model.Equipe;
import com.example.gestionmatch.model.Match;
import com.example.gestionmatch.model.Stade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FormMatchController implements Initializable {

    @FXML private ComboBox<Equipe> equipe1Combo;
    @FXML private ComboBox<Equipe> equipe2Combo;
    @FXML private ComboBox<Stade> stadeCombo;
    @FXML private DatePicker datePicker;
    @FXML private TextField score1Field;
    @FXML private TextField score2Field;

    private final MatchDAO matchDAO = new MatchDAO();
    private final EquipeDAO equipeDAO = new EquipeDAO();
    private final StadeDAO stadeDAO = new StadeDAO();
    private Match matchModif;
    private Runnable onSaveCallback;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chargerEquipes();
        chargerStades();
        score1Field.setText("0");
        score2Field.setText("0");
    }

    private void chargerEquipes() {
        equipe1Combo.getItems().addAll(equipeDAO.getAllEquipes());
        equipe2Combo.getItems().addAll(equipeDAO.getAllEquipes());
    }

    private void chargerStades() {
        stadeCombo.getItems().addAll(stadeDAO.getAllStades());
    }

    public void setMatch(Match match) {
        this.matchModif = match;
        if (match != null) {
            // Adapter selon le modèle Match existant
            datePicker.setValue(match.getDateMatch());
            if (match.getScore() != null && match.getScore().contains("-")) {
                String[] scores = match.getScore().split("-");
                score1Field.setText(scores[0].trim());
                score2Field.setText(scores.length > 1 ? scores[1].trim() : "0");
            }
        }
    }

    public void setOnSaveCallback(Runnable callback) {
        this.onSaveCallback = callback;
    }

    @FXML
    private void sauvegarder() {
        Equipe equipe1 = equipe1Combo.getValue();
        Equipe equipe2 = equipe2Combo.getValue();
        Stade stade = stadeCombo.getValue();
        LocalDate date = datePicker.getValue();

        if (equipe1 == null || equipe2 == null || stade == null || date == null) {
            showAlert("Erreur", "Tous les champs sont obligatoires", Alert.AlertType.ERROR);
            return;
        }

        if (equipe1.getId() == equipe2.getId()) {
            showAlert("Erreur", "Les deux equipes doivent etre differentes", Alert.AlertType.ERROR);
            return;
        }

        int score1, score2;
        try {
            score1 = Integer.parseInt(score1Field.getText().trim());
            score2 = Integer.parseInt(score2Field.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Les scores doivent etre des nombres", Alert.AlertType.ERROR);
            return;
        }

        String nomMatch = equipe1.getNom() + " vs " + equipe2.getNom();
        String scoreStr = score1 + " - " + score2;

        boolean success;
        if (matchModif == null) {
            Match nouveauMatch = new Match(nomMatch, scoreStr, date);
            nouveauMatch.setLieu(stade.getNom());
            nouveauMatch.setEquipeA(equipe1.getNom());
            nouveauMatch.setEquipeB(equipe2.getNom());
            success = matchDAO.ajouterMatch(nouveauMatch);
        } else {
            matchModif.setNom(nomMatch);
            matchModif.setScore(scoreStr);
            matchModif.setDateMatch(date);
            matchModif.setLieu(stade.getNom());
            matchModif.setEquipeA(equipe1.getNom());
            matchModif.setEquipeB(equipe2.getNom());
            success = matchDAO.modifierMatch(matchModif);
        }

        if (success) {
            showAlert("Succes", "Match sauvegarde avec succes", Alert.AlertType.INFORMATION);
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
        Stage stage = (Stage) datePicker.getScene().getWindow();
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
