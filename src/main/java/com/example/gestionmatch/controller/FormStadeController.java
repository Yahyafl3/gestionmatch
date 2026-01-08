package com.example.gestionmatch.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.gestionmatch.dao.StadeDAO;
import com.example.gestionmatch.model.Stade;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class FormStadeController implements Initializable {

    // Champs pour le formulaire
    @FXML private TextField nomField;
    @FXML private TextField villeField;
    @FXML private TextField paysField;
    @FXML private TextField capaciteField;

    // Champs pour la vue desktop
    @FXML private TableView<Stade> tableStades;
    @FXML private TextField searchField;
    @FXML private Label labelNombreStades;
    @FXML private Label labelSucces;
    @FXML private Label labelErreur;
    @FXML private Label statusLabel;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;
    @FXML private MenuItem menuModifier;
    @FXML private MenuItem menuSupprimer;

    private final StadeDAO stadeDAO = new StadeDAO();
    private Stade stadeModif;
    private Runnable onSaveCallback;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (tableStades != null) {
            chargerStades();
        }
    }

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

    @FXML
    private void ajouterStade() {
        System.out.println("➕ Ajouter stade");
        ouvrirFormulaire(null);
    }

    @FXML
    private void modifierStade() {
        System.out.println("✏️ Modifier stade");
        if (tableStades != null && tableStades.getSelectionModel().getSelectedItem() != null) {
            Stade stadeSelectionne = tableStades.getSelectionModel().getSelectedItem();
            ouvrirFormulaire(stadeSelectionne);
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un stade à modifier", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void supprimerStade() {
        System.out.println("🗑️ Supprimer stade");
        if (tableStades != null && tableStades.getSelectionModel().getSelectedItem() != null) {
            Stade stadeSelectionne = tableStades.getSelectionModel().getSelectedItem();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation");
            confirm.setHeaderText("Supprimer le stade " + stadeSelectionne.getNom() + " ?");
            confirm.setContentText("Cette action est irréversible.");
            
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (stadeDAO.supprimerStade(stadeSelectionne.getId())) {
                    showAlert("Succès", "Stade supprimé avec succès", Alert.AlertType.INFORMATION);
                    actualiser();
                } else {
                    showAlert("Erreur", "Erreur lors de la suppression", Alert.AlertType.ERROR);
                }
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un stade à supprimer", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void actualiser() {
        System.out.println("🔄 Actualiser");
        if (tableStades != null) {
            chargerStades();
            showAlert("Succès", "Liste actualisée", Alert.AlertType.INFORMATION);
        }
    }

    private void ouvrirFormulaire(Stade stade) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/form-stade.fxml"));
            ScrollPane root = loader.load();
            
            FormStadeController controller = loader.getController();
            if (stade != null) controller.setStade(stade);
            controller.setOnSaveCallback(this::actualiser);
            
            Stage stage = new Stage();
            stage.setTitle(stade == null ? "Ajouter un stade" : "Modifier un stade");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir le formulaire", Alert.AlertType.ERROR);
        }
    }

    private void chargerStades() {
        if (tableStades != null) {
            List<Stade> stades = stadeDAO.getAllStades();
            tableStades.getItems().clear();
            tableStades.getItems().addAll(stades);
            if (labelNombreStades != null) {
                labelNombreStades.setText(stades.size() + " stade(s)");
            }
        }
    }
}
