package com.example.gestionmatch.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.gestionmatch.dao.EquipeDAO;
import com.example.gestionmatch.dao.MatchDAO;
import com.example.gestionmatch.dao.StadeDAO;
import com.example.gestionmatch.model.Equipe;
import com.example.gestionmatch.model.Match;
import com.example.gestionmatch.model.Stade;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class FormMatchController implements Initializable {

    // Champs pour le formulaire
    @FXML private ComboBox<Equipe> equipe1Combo;
    @FXML private ComboBox<Equipe> equipe2Combo;
    @FXML private ComboBox<Stade> stadeCombo;
    @FXML private DatePicker datePicker;
    @FXML private TextField score1Field;
    @FXML private TextField score2Field;

    // Champs pour la vue desktop
    @FXML private TableView<Match> tableMatchs;
    @FXML private TextField searchField;
    @FXML private ComboBox<?> filterStatut;
    @FXML private Label labelNombreMatchs;
    @FXML private Label labelSucces;
    @FXML private Label labelErreur;
    @FXML private Label statusLabel;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;
    @FXML private MenuItem menuModifier;
    @FXML private MenuItem menuSupprimer;

    private final MatchDAO matchDAO = new MatchDAO();
    private final EquipeDAO equipeDAO = new EquipeDAO();
    private final StadeDAO stadeDAO = new StadeDAO();
    private Match matchModif;
    private Runnable onSaveCallback;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (equipe1Combo != null && equipe2Combo != null) {
            chargerEquipes();
        }
        if (stadeCombo != null) {
            chargerStades();
        }
        if (score1Field != null && score2Field != null) {
            score1Field.setText("0");
            score2Field.setText("0");
        }
        if (tableMatchs != null) {
            chargerMatchs();
        }
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

    @FXML
    private void ajouterMatch() {
        System.out.println("➕ Ajouter match");
        ouvrirFormulaire(null);
    }

    @FXML
    private void modifierMatch() {
        System.out.println("✏️ Modifier match");
        if (tableMatchs != null && tableMatchs.getSelectionModel().getSelectedItem() != null) {
            Match matchSelectionne = tableMatchs.getSelectionModel().getSelectedItem();
            ouvrirFormulaire(matchSelectionne);
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un match à modifier", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void supprimerMatch() {
        System.out.println("🗑️ Supprimer match");
        if (tableMatchs != null && tableMatchs.getSelectionModel().getSelectedItem() != null) {
            Match matchSelectionne = tableMatchs.getSelectionModel().getSelectedItem();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation");
            confirm.setHeaderText("Supprimer le match " + matchSelectionne.getNom() + " ?");
            confirm.setContentText("Cette action est irréversible.");
            
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (matchDAO.supprimerMatch(matchSelectionne.getId())) {
                    showAlert("Succès", "Match supprimé avec succès", Alert.AlertType.INFORMATION);
                    actualiser();
                } else {
                    showAlert("Erreur", "Erreur lors de la suppression", Alert.AlertType.ERROR);
                }
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un match à supprimer", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void actualiser() {
        System.out.println("🔄 Actualiser");
        if (tableMatchs != null) {
            chargerMatchs();
            showAlert("Succès", "Liste actualisée", Alert.AlertType.INFORMATION);
        }
    }

    private void ouvrirFormulaire(Match match) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/form-match.fxml"));
            VBox root = loader.load();
            
            FormMatchController controller = loader.getController();
            if (match != null) controller.setMatch(match);
            controller.setOnSaveCallback(this::actualiser);
            
            Stage stage = new Stage();
            stage.setTitle(match == null ? "Ajouter un match" : "Modifier un match");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir le formulaire", Alert.AlertType.ERROR);
        }
    }

    private void chargerMatchs() {
        if (tableMatchs != null) {
            List<Match> matchs = matchDAO.getAllMatchs();
            tableMatchs.getItems().clear();
            tableMatchs.getItems().addAll(matchs);
            if (labelNombreMatchs != null) {
                labelNombreMatchs.setText(matchs.size() + " match(s)");
            }
        }
    }
}
