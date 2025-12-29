package com.example.gestionmatch.controller;

import java.util.List;
import java.util.Optional;

import com.example.gestionmatch.model.Equipe;
import com.example.gestionmatch.services.EquipeService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EquipeController {

    @FXML private TextField champNom;
    @FXML private TextField champPays;
    @FXML private TextField champEntraineur;
    @FXML private TextField champNombreJoueurs;
    @FXML private TextField champRecherche;

    @FXML private TableView<Equipe> tableEquipes;
    @FXML private TableColumn<Equipe, String> colNom;
    @FXML private TableColumn<Equipe, String> colPays;
    @FXML private TableColumn<Equipe, String> colEntraineur;

    @FXML private Label labelErreur;
    @FXML private Label labelSucces;
    @FXML private Label labelNombreEquipes;
    @FXML private TextArea textAreaDetails;

    @FXML private Button btnAjouter;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;
    @FXML private Button btnReinitialiser;

    private EquipeService equipeService;
    private ObservableList<Equipe> equipesObservable;
    private Equipe equipeSelectionnee;

    @FXML
    public void initialize() {
        System.out.println("✅ EquipeController initialisé");

        equipeService = new EquipeService();
        equipesObservable = FXCollections.observableArrayList();

        initialiserTableau();
        chargerEquipes();
        configurerSelection();

        labelErreur.setVisible(false);
        labelSucces.setVisible(false);

        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
    }

    private void initialiserTableau() {
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPays.setCellValueFactory(new PropertyValueFactory<>("pays"));
        colEntraineur.setCellValueFactory(new PropertyValueFactory<>("entraineur"));

        tableEquipes.setItems(equipesObservable);
    }

    private void configurerSelection() {
        tableEquipes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        equipeSelectionnee = newValue;
                        remplirFormulaire(newValue);
                        afficherDetails(newValue);
                        btnModifier.setDisable(false);
                        btnSupprimer.setDisable(false);
                    } else {
                        btnModifier.setDisable(true);
                        btnSupprimer.setDisable(true);
                    }
                }
        );
    }

    private void chargerEquipes() {
        try {
            equipesObservable.clear();
            List<Equipe> equipes = equipeService.listerTous();
            equipesObservable.addAll(equipes);

            labelNombreEquipes.setText(equipes.size() + " équipe(s)");

        } catch (Exception e) {
            afficherErreur("Erreur lors du chargement : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void ajouterEquipe() {
        try {
            if (!validerFormulaire()) {
                return;
            }

            Equipe equipe = new Equipe();
            equipe.setNom(champNom.getText().trim());
            equipe.setPays(champPays.getText().trim());
            equipe.setEntraineur(champEntraineur.getText().trim());

            String nbJoueursText = champNombreJoueurs.getText().trim();
            if (!nbJoueursText.isEmpty()) {
                equipe.setNombreJoueurs(Integer.parseInt(nbJoueursText));
            }

            equipeService.creer(equipe);

            afficherSucces("✅ Équipe ajoutée : " + equipe.getNom());
            chargerEquipes();
            viderFormulaire();

        } catch (Exception e) {
            afficherErreur("❌ " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void modifierEquipe() {
        try {
            if (equipeSelectionnee == null) {
                afficherErreur("⚠️ Veuillez sélectionner une équipe");
                return;
            }

            if (!validerFormulaire()) {
                return;
            }

            equipeSelectionnee.setNom(champNom.getText().trim());
            equipeSelectionnee.setPays(champPays.getText().trim());
            equipeSelectionnee.setEntraineur(champEntraineur.getText().trim());

            String nbJoueursText = champNombreJoueurs.getText().trim();
            if (!nbJoueursText.isEmpty()) {
                equipeSelectionnee.setNombreJoueurs(Integer.parseInt(nbJoueursText));
            }

            equipeService.modifier(equipeSelectionnee);

            afficherSucces("✅ Équipe modifiée : " + equipeSelectionnee.getNom());
            chargerEquipes();
            viderFormulaire();

        } catch (Exception e) {
            afficherErreur("❌ " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void supprimerEquipe() {
        try {
            if (equipeSelectionnee == null) {
                afficherErreur("⚠️ Veuillez sélectionner une équipe");
                return;
            }

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Supprimer l'équipe");
            confirmation.setContentText("Voulez-vous vraiment supprimer " + equipeSelectionnee.getNom() + " ?");

            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                equipeService.supprimer(equipeSelectionnee.getId());

                afficherSucces("✅ Équipe supprimée");
                chargerEquipes();
                viderFormulaire();
            }

        } catch (Exception e) {
            afficherErreur("❌ " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    @SuppressWarnings("UseSpecificCatch")
    public void rechercherEquipe() {
        try {
            String recherche = champRecherche.getText().trim();

            if (recherche.isEmpty()) {
                chargerEquipes();
                return;
            }

            equipesObservable.clear();

            List<Equipe> toutesEquipes = equipeService.listerTous();

            for (Equipe equipe : toutesEquipes) {
                if (equipe.getNom().toLowerCase().contains(recherche.toLowerCase()) ||
                        equipe.getPays().toLowerCase().contains(recherche.toLowerCase())) {
                    equipesObservable.add(equipe);
                }
            }

            labelNombreEquipes.setText(equipesObservable.size() + " résultat(s)");

        } catch (Exception e) {
            afficherErreur("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void actualiser() {
        chargerEquipes();
        viderFormulaire();
        afficherSucces("✅ Liste actualisée");
    }

    @FXML
    public void reinitialiserStatistiques() {
        try {
            if (equipeSelectionnee == null) {
                afficherErreur("⚠️ Veuillez sélectionner une équipe");
                return;
            }

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Réinitialiser les statistiques");
            confirmation.setContentText("Voulez-vous vraiment réinitialiser les stats de " +
                    equipeSelectionnee.getNom() + " ?");

            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                equipeSelectionnee.reinitialiserStatistiques();
                equipeService.modifier(equipeSelectionnee);

                afficherSucces("✅ Statistiques réinitialisées");
                chargerEquipes();
            }

        } catch (Exception e) {
            afficherErreur("❌ " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void exporterEquipes() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("📋 LISTE DES ÉQUIPES\n");
            sb.append("=".repeat(60)).append("\n\n");

            List<Equipe> equipes = equipeService.listerTous();

            for (Equipe equipe : equipes) {
                sb.append(equipe.afficherStatistiques()).append("\n\n");
            }

            textAreaDetails.setText(sb.toString());

            afficherSucces("✅ Export réalisé (" + equipes.size() + " équipes)");

        } catch (Exception e) {
            afficherErreur("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void remplirFormulaire(Equipe equipe) {
        champNom.setText(equipe.getNom());
        champPays.setText(equipe.getPays());
        champEntraineur.setText(equipe.getEntraineur());
        champNombreJoueurs.setText(String.valueOf(equipe.getNombreJoueurs()));
    }

    private void afficherDetails(Equipe equipe) {
        textAreaDetails.setText(equipe.afficherStatistiques());
    }

    private void viderFormulaire() {
        champNom.clear();
        champPays.clear();
        champEntraineur.clear();
        champNombreJoueurs.clear();
        champRecherche.clear();
        textAreaDetails.clear();

        tableEquipes.getSelectionModel().clearSelection();
        equipeSelectionnee = null;

        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
    }

    private boolean validerFormulaire() {
        if (champNom.getText().trim().isEmpty()) {
            afficherErreur("⚠️ Le nom de l'équipe est obligatoire");
            champNom.requestFocus();
            return false;
        }

        if (champPays.getText().trim().isEmpty()) {
            afficherErreur("⚠️ Le pays est obligatoire");
            champPays.requestFocus();
            return false;
        }

        if (champEntraineur.getText().trim().isEmpty()) {
            afficherErreur("⚠️ L'entraîneur est obligatoire");
            champEntraineur.requestFocus();
            return false;
        }

        String nbJoueursText = champNombreJoueurs.getText().trim();
        if (!nbJoueursText.isEmpty()) {
            try {
                int nbJoueurs = Integer.parseInt(nbJoueursText);
                if (nbJoueurs < 0 || nbJoueurs > 30) {
                    afficherErreur("⚠️ Le nombre de joueurs doit être entre 0 et 30");
                    champNombreJoueurs.requestFocus();
                    return false;
                }
            } catch (NumberFormatException e) {
                afficherErreur("⚠️ Nombre de joueurs invalide");
                champNombreJoueurs.requestFocus();
                return false;
            }
        }

        return true;
    }

    private void afficherErreur(String message) {
        labelErreur.setText(message);
        labelErreur.setVisible(true);
        labelSucces.setVisible(false);
    }

    private void afficherSucces(String message) {
        labelSucces.setText(message);
        labelSucces.setVisible(true);
        labelErreur.setVisible(false);
    }
}