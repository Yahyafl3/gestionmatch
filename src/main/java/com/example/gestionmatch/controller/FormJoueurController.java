package com.example.gestionmatch.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.gestionmatch.dao.EquipeDAO;
import com.example.gestionmatch.dao.JoueurDAO;
import com.example.gestionmatch.model.Equipe;
import com.example.gestionmatch.model.Joueur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class FormJoueurController implements Initializable {

    // Champs pour le formulaire
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField numeroField;
    @FXML private ComboBox<String> posteCombo;
    @FXML private ComboBox<Equipe> equipeCombo;

    // Champs pour la vue desktop
    @FXML private TableView<Joueur> tableJoueurs;
    @FXML private TextField searchField;
    @FXML private Label labelNombreJoueurs;
    @FXML private Label labelSucces;
    @FXML private Label labelErreur;
    @FXML private Label statusLabel;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;
    @FXML private MenuItem menuModifier;
    @FXML private MenuItem menuSupprimer;

    private final JoueurDAO joueurDAO = new JoueurDAO();
    private final EquipeDAO equipeDAO = new EquipeDAO();
    private Joueur joueurModif;
    private Runnable onSaveCallback;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (posteCombo != null) {
            posteCombo.getItems().addAll("Gardien", "Défenseur", "Milieu", "Attaquant");
        }
        if (equipeCombo != null) {
            chargerEquipes();
        }
        if (tableJoueurs != null) {
            chargerJoueurs();
        }
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

    @FXML
    private void ajouterJoueur() {
        System.out.println("➕ Ajouter joueur");
        ouvrirFormulaire(null);
    }

    @FXML
    private void modifierJoueur() {
        System.out.println("✏️ Modifier joueur");
        if (tableJoueurs != null && tableJoueurs.getSelectionModel().getSelectedItem() != null) {
            Joueur joueurSelectionne = tableJoueurs.getSelectionModel().getSelectedItem();
            ouvrirFormulaire(joueurSelectionne);
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un joueur à modifier", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void supprimerJoueur() {
        System.out.println("🗑️ Supprimer joueur");
        if (tableJoueurs != null && tableJoueurs.getSelectionModel().getSelectedItem() != null) {
            Joueur joueurSelectionne = tableJoueurs.getSelectionModel().getSelectedItem();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation");
            confirm.setHeaderText("Supprimer " + joueurSelectionne.getNom() + " " + joueurSelectionne.getPrenom() + " ?");
            confirm.setContentText("Cette action est irréversible.");
            
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (joueurDAO.supprimerJoueur(joueurSelectionne.getId())) {
                    showAlert("Succès", "Joueur supprimé avec succès", Alert.AlertType.INFORMATION);
                    actualiser();
                } else {
                    showAlert("Erreur", "Erreur lors de la suppression", Alert.AlertType.ERROR);
                }
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un joueur à supprimer", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void actualiser() {
        System.out.println("🔄 Actualiser");
        if (tableJoueurs != null) {
            chargerJoueurs();
            showAlert("Succès", "Liste actualisée", Alert.AlertType.INFORMATION);
        }
    }

    private void ouvrirFormulaire(Joueur joueur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/form-joueur.fxml"));
            ScrollPane root = loader.load();
            
            FormJoueurController controller = loader.getController();
            if (joueur != null) controller.setJoueur(joueur);
            controller.setOnSaveCallback(this::actualiser);
            
            Stage stage = new Stage();
            stage.setTitle(joueur == null ? "Ajouter un joueur" : "Modifier un joueur");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir le formulaire", Alert.AlertType.ERROR);
        }
    }

    private void chargerJoueurs() {
        if (tableJoueurs != null) {
            List<Joueur> joueurs = joueurDAO.getAllJoueurs();
            tableJoueurs.getItems().clear();
            tableJoueurs.getItems().addAll(joueurs);
            if (labelNombreJoueurs != null) {
                labelNombreJoueurs.setText(joueurs.size() + " joueur(s)");
            }
        }
    }
}
