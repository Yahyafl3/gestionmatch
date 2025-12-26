package com.example.gestionmatch.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.gestionmatch.dao.EquipeDAO;
import com.example.gestionmatch.dao.GroupeDAO;
import com.example.gestionmatch.dao.GroupeEquipeDAO;
import com.example.gestionmatch.model.Equipe;
import com.example.gestionmatch.model.Groupe;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class GroupeViewController implements Initializable {

    @FXML private GridPane groupesGrid;
    
    private final GroupeDAO groupeDAO = new GroupeDAO();
    private final EquipeDAO equipeDAO = new EquipeDAO();
    private final GroupeEquipeDAO groupeEquipeDAO = new GroupeEquipeDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chargerGroupes();
    }
    
    private void chargerGroupes() {
        groupesGrid.getChildren().clear();
        List<Groupe> groupes = groupeDAO.getAllGroupes();
        
        int col = 0;
        int row = 0;
        
        for (Groupe groupe : groupes) {
            VBox groupeCard = creerCarteGroupe(groupe);
            groupesGrid.add(groupeCard, col, row);
            
            col++;
            if (col >= 4) {
                col = 0;
                row++;
            }
        }
    }
    
    private VBox creerCarteGroupe(Groupe groupe) {
        VBox card = new VBox(15);
        card.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95); " +
                     "-fx-background-radius: 16; " +
                     "-fx-padding: 20; " +
                     "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 15, 0, 0, 5);");
        card.setPrefWidth(280);
        card.setMinHeight(350);
        
        // En-tête du groupe
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #2d5a3d; " +
                       "-fx-background-radius: 10; " +
                       "-fx-padding: 15;");
        
        Label nomGroupe = new Label(groupe.getNom());
        nomGroupe.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
        header.getChildren().add(nomGroupe);
        
        // Liste des équipes
        VBox equipesBox = new VBox(10);
        equipesBox.setStyle("-fx-padding: 10 0;");
        
        // Récupérer les équipes du groupe
        List<Equipe> equipes = getEquipesDuGroupe(groupe.getId());
        
        for (Equipe equipe : equipes) {
            HBox equipeRow = creerLigneEquipe(equipe, groupe);
            equipesBox.getChildren().add(equipeRow);
        }
        
        // Boutons d'action
        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER);
        
        Button btnAjouter = new Button("➕ Ajouter");
        btnAjouter.setStyle("-fx-background-color: #54BF93; -fx-text-fill: white; " +
                           "-fx-font-size: 12px; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand;");
        btnAjouter.setOnAction(e -> ajouterEquipeAuGroupe(groupe));
        
        Button btnModifier = new Button("✏️ Modifier");
        btnModifier.setStyle("-fx-background-color: #D4AF37; -fx-text-fill: white; " +
                            "-fx-font-size: 12px; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand;");
        btnModifier.setOnAction(e -> modifierGroupe(groupe));
        
        actions.getChildren().addAll(btnAjouter, btnModifier);
        
        card.getChildren().addAll(header, equipesBox, actions);
        
        return card;
    }
    
    private HBox creerLigneEquipe(Equipe equipe, Groupe groupe) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #f8faf9; " +
                    "-fx-background-radius: 8; " +
                    "-fx-padding: 10;");
        
        Label nomEquipe = new Label(equipe.getNom());
        nomEquipe.setStyle("-fx-text-fill: #1e293b; -fx-font-size: 13px; -fx-font-weight: 600;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Button btnRetirer = new Button("🗑️");
        btnRetirer.setStyle("-fx-background-color: #C1272D; -fx-text-fill: white; " +
                           "-fx-font-size: 11px; -fx-padding: 5 10; -fx-background-radius: 6; -fx-cursor: hand;");
        btnRetirer.setOnAction(e -> retirerEquipeDuGroupe(equipe, groupe));
        
        row.getChildren().addAll(nomEquipe, spacer, btnRetirer);
        
        return row;
    }
    
    private List<Equipe> getEquipesDuGroupe(int groupeId) {
        return groupeEquipeDAO.getEquipesDuGroupe(groupeId);
    }
    
    @FXML
    private void creerGroupe() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Créer un groupe");
        dialog.setHeaderText("Nouveau groupe");
        dialog.setContentText("Nom du groupe:");
        
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nom -> {
            Groupe groupe = new Groupe();
            groupe.setNom(nom);
            groupe.setDescription("Groupe de la Coupe du Monde 2030");
            
            if (groupeDAO.ajouterGroupe(groupe)) {
                showAlert("Succès", "Groupe créé avec succès", Alert.AlertType.INFORMATION);
                chargerGroupes();
            } else {
                showAlert("Erreur", "Erreur lors de la création du groupe", Alert.AlertType.ERROR);
            }
        });
    }
    
    private void ajouterEquipeAuGroupe(Groupe groupe) {
        // Vérifier le nombre d'équipes dans le groupe
        int nbEquipes = groupeEquipeDAO.compterEquipesDansGroupe(groupe.getId());
        if (nbEquipes >= 4) {
            showAlert("Limite atteinte", "Un groupe ne peut contenir que 4 équipes maximum", Alert.AlertType.WARNING);
            return;
        }
        
        List<Equipe> toutesEquipes = equipeDAO.getAllEquipes();
        
        // Filtrer les équipes déjà dans le groupe
        List<Equipe> equipesDisponibles = new ArrayList<>();
        for (Equipe equipe : toutesEquipes) {
            if (!groupeEquipeDAO.equipeEstDansGroupe(groupe.getId(), equipe.getId())) {
                equipesDisponibles.add(equipe);
            }
        }
        
        if (equipesDisponibles.isEmpty()) {
            showAlert("Aucune équipe disponible", "Toutes les équipes sont déjà assignées ou il n'y a pas d'équipes", Alert.AlertType.INFORMATION);
            return;
        }
        
        ChoiceDialog<Equipe> dialog = new ChoiceDialog<>(equipesDisponibles.get(0), equipesDisponibles);
        dialog.setTitle("Ajouter une équipe");
        dialog.setHeaderText("Ajouter une équipe au " + groupe.getNom());
        dialog.setContentText("Choisir une équipe:");
        
        Optional<Equipe> result = dialog.showAndWait();
        result.ifPresent(equipe -> {
            if (groupeEquipeDAO.ajouterEquipeAuGroupe(groupe.getId(), equipe.getId())) {
                showAlert("Succès", equipe.getNom() + " ajoutée au " + groupe.getNom(), Alert.AlertType.INFORMATION);
                chargerGroupes();
            } else {
                showAlert("Erreur", "Impossible d'ajouter l'équipe", Alert.AlertType.ERROR);
            }
        });
    }
    
    private void modifierGroupe(Groupe groupe) {
        TextInputDialog dialog = new TextInputDialog(groupe.getNom());
        dialog.setTitle("Modifier le groupe");
        dialog.setHeaderText("Modifier " + groupe.getNom());
        dialog.setContentText("Nouveau nom:");
        
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nom -> {
            groupe.setNom(nom);
            if (groupeDAO.modifierGroupe(groupe)) {
                showAlert("Succès", "Groupe modifié avec succès", Alert.AlertType.INFORMATION);
                chargerGroupes();
            } else {
                showAlert("Erreur", "Erreur lors de la modification", Alert.AlertType.ERROR);
            }
        });
    }
    
    private void retirerEquipeDuGroupe(Equipe equipe, Groupe groupe) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Retirer " + equipe.getNom() + " du " + groupe.getNom() + " ?");
        confirm.setContentText("Cette action peut être annulée en rajoutant l'équipe.");
        
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (groupeEquipeDAO.retirerEquipeDuGroupe(groupe.getId(), equipe.getId())) {
                showAlert("Succès", equipe.getNom() + " retirée du " + groupe.getNom(), Alert.AlertType.INFORMATION);
                chargerGroupes();
            } else {
                showAlert("Erreur", "Impossible de retirer l'équipe", Alert.AlertType.ERROR);
            }
        }
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
