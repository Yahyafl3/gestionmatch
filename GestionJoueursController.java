package com.example.gestionmatch.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import com.example.gestionmatch.model.Joueur;
import com.example.gestionmatch.dao.JoueurDAO;

/**
 * EXEMPLE DE CONTRÔLEUR POUR LES PAGES DE GESTION
 * 
 * Ce fichier montre comment implémenter un contrôleur pour les nouvelles
 * pages de gestion avec tableaux, recherche et filtres.
 * 
 * Adaptez ce code pour chaque entité (Matchs, Équipes, Groupes, Stades)
 */
public class GestionJoueursController {

    // ========== COMPOSANTS FXML ==========
    
    // Statistiques
    @FXML private Label totalJoueursLabel;
    @FXML private Label gardienLabel;
    @FXML private Label defenseurLabel;
    @FXML private Label milieuLabel;
    @FXML private Label attaquantLabel;
    
    // Recherche et filtres
    @FXML private TextField searchField;
    @FXML private ComboBox<String> filtreEquipeCombo;
    @FXML private ComboBox<String> filtrePosteCombo;
    
    // Table
    @FXML private TableView<Joueur> joueursTable;
    @FXML private TableColumn<Joueur, Integer> colId;
    @FXML private TableColumn<Joueur, Integer> colNumero;
    @FXML private TableColumn<Joueur, String> colNom;
    @FXML private TableColumn<Joueur, String> colPrenom;
    @FXML private TableColumn<Joueur, String> colPoste;
    @FXML private TableColumn<Joueur, String> colEquipe;
    @FXML private TableColumn<Joueur, Void> colActions;
    
    // Footer
    @FXML private Label infoLabel;
    
    // ========== DONNÉES ==========
    
    private ObservableList<Joueur> tousLesJoueurs = FXCollections.observableArrayList();
    private ObservableList<Joueur> joueursAffiches = FXCollections.observableArrayList();
    private JoueurDAO joueurDAO;
    
    // ========== INITIALISATION ==========
    
    @FXML
    public void initialize() {
        joueurDAO = new JoueurDAO();
        
        // 1. Configurer les colonnes du tableau
        setupTableColumns();
        
        // 2. Charger les données
        chargerDonnees();
        
        // 3. Configurer la recherche
        setupSearch();
        
        // 4. Configurer les filtres
        setupFiltres();
        
        // 5. Mettre à jour les statistiques
        updateStatistics();
    }
    
    // ========== CONFIGURATION DU TABLEAU ==========
    
    private void setupTableColumns() {
        // Colonnes simples
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colPoste.setCellValueFactory(new PropertyValueFactory<>("poste"));
        
        // Colonne équipe (relation)
        colEquipe.setCellValueFactory(cellData -> {
            if (cellData.getValue().getEquipe() != null) {
                return new javafx.beans.property.SimpleStringProperty(
                    cellData.getValue().getEquipe().getNom()
                );
            }
            return new javafx.beans.property.SimpleStringProperty("N/A");
        });
        
        // Colonne actions avec boutons
        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button btnModifier = new Button("✏️ Modifier");
            private final Button btnSupprimer = new Button("🗑️ Supprimer");
            private final HBox container = new HBox(8, btnModifier, btnSupprimer);
            
            {
                // Style des boutons
                btnModifier.getStyleClass().add("btn-warning");
                btnModifier.setPrefWidth(90);
                btnModifier.setPrefHeight(32);
                
                btnSupprimer.getStyleClass().add("btn-danger");
                btnSupprimer.setPrefWidth(90);
                btnSupprimer.setPrefHeight(32);
                
                container.setAlignment(Pos.CENTER);
                
                // Actions des boutons
                btnModifier.setOnAction(event -> {
                    Joueur joueur = getTableView().getItems().get(getIndex());
                    modifierJoueur(joueur);
                });
                
                btnSupprimer.setOnAction(event -> {
                    Joueur joueur = getTableView().getItems().get(getIndex());
                    supprimerJoueur(joueur);
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : container);
            }
        });
    }
    
    // ========== CHARGEMENT DES DONNÉES ==========
    
    private void chargerDonnees() {
        try {
            tousLesJoueurs.clear();
            tousLesJoueurs.addAll(joueurDAO.findAll());
            
            joueursAffiches.clear();
            joueursAffiches.addAll(tousLesJoueurs);
            
            joueursTable.setItems(joueursAffiches);
            
            updateStatistics();
            updateInfoLabel();
            
        } catch (Exception e) {
            showError("Erreur lors du chargement des données", e.getMessage());
        }
    }
    
    // ========== RECHERCHE ==========
    
    private void setupSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrerJoueurs();
        });
    }
    
    private void filtrerJoueurs() {
        String searchText = searchField.getText().toLowerCase();
        String equipeFiltre = filtreEquipeCombo.getValue();
        String posteFiltre = filtrePosteCombo.getValue();
        
        joueursAffiches.clear();
        
        for (Joueur joueur : tousLesJoueurs) {
            boolean matchSearch = searchText.isEmpty() ||
                joueur.getNom().toLowerCase().contains(searchText) ||
                joueur.getPrenom().toLowerCase().contains(searchText) ||
                String.valueOf(joueur.getNumero()).contains(searchText);
            
            boolean matchEquipe = equipeFiltre == null || equipeFiltre.equals("Toutes les équipes") ||
                (joueur.getEquipe() != null && joueur.getEquipe().getNom().equals(equipeFiltre));
            
            boolean matchPoste = posteFiltre == null || posteFiltre.equals("Tous les postes") ||
                joueur.getPoste().equals(posteFiltre);
            
            if (matchSearch && matchEquipe && matchPoste) {
                joueursAffiches.add(joueur);
            }
        }
        
        updateInfoLabel();
    }
    
    // ========== FILTRES ==========
    
    private void setupFiltres() {
        // Remplir le filtre équipes
        ObservableList<String> equipes = FXCollections.observableArrayList("Toutes les équipes");
        // Ajouter les équipes depuis la base de données
        // equipes.addAll(equipeDAO.findAll().stream().map(Equipe::getNom).collect(Collectors.toList()));
        filtreEquipeCombo.setItems(equipes);
        filtreEquipeCombo.setValue("Toutes les équipes");
        filtreEquipeCombo.setOnAction(e -> filtrerJoueurs());
        
        // Remplir le filtre postes
        ObservableList<String> postes = FXCollections.observableArrayList(
            "Tous les postes", "Gardien", "Défenseur", "Milieu", "Attaquant"
        );
        filtrePosteCombo.setItems(postes);
        filtrePosteCombo.setValue("Tous les postes");
        filtrePosteCombo.setOnAction(e -> filtrerJoueurs());
    }
    
    @FXML
    private void reinitialiserFiltres() {
        searchField.clear();
        filtreEquipeCombo.setValue("Toutes les équipes");
        filtrePosteCombo.setValue("Tous les postes");
        filtrerJoueurs();
    }
    
    // ========== STATISTIQUES ==========
    
    private void updateStatistics() {
        int total = tousLesJoueurs.size();
        int gardiens = (int) tousLesJoueurs.stream().filter(j -> "Gardien".equals(j.getPoste())).count();
        int defenseurs = (int) tousLesJoueurs.stream().filter(j -> "Défenseur".equals(j.getPoste())).count();
        int milieux = (int) tousLesJoueurs.stream().filter(j -> "Milieu".equals(j.getPoste())).count();
        int attaquants = (int) tousLesJoueurs.stream().filter(j -> "Attaquant".equals(j.getPoste())).count();
        
        totalJoueursLabel.setText(String.valueOf(total));
        gardienLabel.setText(String.valueOf(gardiens));
        defenseurLabel.setText(String.valueOf(defenseurs));
        milieuLabel.setText(String.valueOf(milieux));
        attaquantLabel.setText(String.valueOf(attaquants));
    }
    
    private void updateInfoLabel() {
        int count = joueursAffiches.size();
        infoLabel.setText(count + " joueur(s) affiché(s)");
    }
    
    // ========== ACTIONS ==========
    
    @FXML
    private void ajouterJoueur() {
        try {
            // Ouvrir le formulaire d'ajout
            // FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/form-joueur.fxml"));
            // Parent root = loader.load();
            // Stage stage = new Stage();
            // stage.setScene(new Scene(root));
            // stage.setTitle("Nouveau Joueur");
            // stage.showAndWait();
            
            // Recharger les données après fermeture
            chargerDonnees();
            
        } catch (Exception e) {
            showError("Erreur", "Impossible d'ouvrir le formulaire");
        }
    }
    
    private void modifierJoueur(Joueur joueur) {
        try {
            // Ouvrir le formulaire de modification avec les données du joueur
            // FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/form-joueur.fxml"));
            // Parent root = loader.load();
            // FormJoueurController controller = loader.getController();
            // controller.setJoueur(joueur);
            // Stage stage = new Stage();
            // stage.setScene(new Scene(root));
            // stage.setTitle("Modifier Joueur");
            // stage.showAndWait();
            
            // Recharger les données
            chargerDonnees();
            
        } catch (Exception e) {
            showError("Erreur", "Impossible de modifier le joueur");
        }
    }
    
    private void supprimerJoueur(Joueur joueur) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer le joueur");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer " + 
                           joueur.getPrenom() + " " + joueur.getNom() + " ?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    joueurDAO.delete(joueur.getId());
                    chargerDonnees();
                    showSuccess("Succès", "Joueur supprimé avec succès");
                } catch (Exception e) {
                    showError("Erreur", "Impossible de supprimer le joueur");
                }
            }
        });
    }
    
    @FXML
    private void actualiser() {
        chargerDonnees();
        showInfo("Actualisation", "Données actualisées avec succès");
    }
    
    @FXML
    private void exporterDonnees() {
        try {
            // Implémenter l'export CSV/Excel
            // FileChooser fileChooser = new FileChooser();
            // fileChooser.setTitle("Exporter les données");
            // fileChooser.getExtensionFilters().add(
            //     new FileChooser.ExtensionFilter("CSV Files", "*.csv")
            // );
            // File file = fileChooser.showSaveDialog(joueursTable.getScene().getWindow());
            
            showSuccess("Export", "Données exportées avec succès");
            
        } catch (Exception e) {
            showError("Erreur", "Impossible d'exporter les données");
        }
    }
    
    // ========== UTILITAIRES ==========
    
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
