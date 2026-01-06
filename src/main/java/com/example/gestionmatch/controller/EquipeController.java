package com.example.gestionmatch.controller;

import java.util.List;

import com.example.gestionmatch.model.Equipe;
import com.example.gestionmatch.services.EquipeService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class EquipeController {

    @FXML private TableView<Equipe> tableEquipes;
    @FXML private TableColumn<Equipe, String> colNom;
    @FXML private TableColumn<Equipe, String> colPays;
    @FXML private TableColumn<Equipe, String> colEntraineur;
    @FXML private TableColumn<Equipe, Integer> colNombreJoueurs;
    @FXML private TableColumn<Equipe, Integer> colMatchsJoues;
    @FXML private TableColumn<Equipe, Integer> colVictoires;
    @FXML private TableColumn<Equipe, Integer> colNuls;
    @FXML private TableColumn<Equipe, Integer> colDefaites;
    @FXML private TableColumn<Equipe, Integer> colButsMarques;
    @FXML private TableColumn<Equipe, Integer> colButsEncaisses;
    @FXML private TableColumn<Equipe, Integer> colPoints;

    @FXML private Label labelErreur;
    @FXML private Label labelSucces;
    @FXML private Label labelNombreEquipes;

    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;

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

        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
    }

    private void initialiserTableau() {
        System.out.println("🔧 Initialisation du tableau avec drapeaux...");
        
        // Colonne Nom avec drapeau
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setCellFactory(column -> new TableCell<Equipe, String>() {
            private final ImageView imageView = new ImageView();
            private final Label label = new Label();
            private final HBox hbox = new HBox(10);

            {
                imageView.setFitWidth(32);
                imageView.setFitHeight(24);
                imageView.setPreserveRatio(true);
                hbox.getChildren().addAll(imageView, label);
                hbox.setAlignment(Pos.CENTER_LEFT);
            }

            @Override
            protected void updateItem(String nom, boolean empty) {
                super.updateItem(nom, empty);

                if (empty || nom == null) {
                    setGraphic(null);
                } else {
                    Equipe equipe = getTableView().getItems().get(getIndex());
                    label.setText(nom);

                    // Charger l'image du drapeau
                    if (equipe.getImagePath() != null && !equipe.getImagePath().isEmpty()) {
                        try {
                            String cheminImage = "/images/equipes/" + equipe.getImagePath();
                            System.out.println("🔍 Chargement: " + cheminImage);
                            Image image = new Image(getClass().getResourceAsStream(cheminImage), 32, 24, true, true);
                            if (!image.isError()) {
                                imageView.setImage(image);
                                System.out.println("✅ Image OK");
                            } else {
                                imageView.setImage(null);
                                System.out.println("❌ Image erreur");
                            }
                        } catch (Exception e) {
                            imageView.setImage(null);
                            System.out.println("❌ Exception: " + e.getMessage());
                        }
                    } else {
                        imageView.setImage(null);
                    }

                    setGraphic(hbox);
                }
            }
        });
        
        // Autres colonnes
        colPays.setCellValueFactory(new PropertyValueFactory<>("pays"));
        colEntraineur.setCellValueFactory(new PropertyValueFactory<>("entraineur"));
        colNombreJoueurs.setCellValueFactory(new PropertyValueFactory<>("nombreJoueurs"));
        colMatchsJoues.setCellValueFactory(new PropertyValueFactory<>("matchsJoues"));
        colVictoires.setCellValueFactory(new PropertyValueFactory<>("victoires"));
        colNuls.setCellValueFactory(new PropertyValueFactory<>("nuls"));
        colDefaites.setCellValueFactory(new PropertyValueFactory<>("defaites"));
        colButsMarques.setCellValueFactory(new PropertyValueFactory<>("butsMarques"));
        colButsEncaisses.setCellValueFactory(new PropertyValueFactory<>("butsEncaisses"));
        colPoints.setCellValueFactory(new PropertyValueFactory<>("points"));

        tableEquipes.setItems(equipesObservable);
    }

    private void configurerSelection() {
        tableEquipes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        equipeSelectionnee = newValue;
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
            System.out.println("📋 " + equipes.size() + " équipe(s) chargée(s)");
        } catch (Exception e) {
            afficherErreur("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void ajouterEquipe() {
        System.out.println("➕ Ajout d'une équipe");
        // TODO: Ouvrir le formulaire
    }

    @FXML
    public void modifierEquipe() {
        System.out.println("✏️ Modification d'une équipe");
        // TODO: Ouvrir le formulaire
    }

    @FXML
    public void supprimerEquipe() {
        try {
            if (equipeSelectionnee != null) {
                equipeService.supprimer(equipeSelectionnee.getId());
                afficherSucces("✅ Équipe supprimée");
                chargerEquipes();
            }
        } catch (Exception e) {
            afficherErreur("Erreur: " + e.getMessage());
        }
    }

    @FXML
    public void actualiser() {
        chargerEquipes();
        afficherSucces("✅ Liste actualisée");
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
