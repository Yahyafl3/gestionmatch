package com.example.gestionmatch.controller;

import com.example.gestionmatch.dao.EquipeDAO;
import com.example.gestionmatch.dao.GroupeDAO;
import com.example.gestionmatch.model.Equipe;
import com.example.gestionmatch.model.Groupe;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class FormGroupeController implements Initializable {

    @FXML private TextField nomField;
    @FXML private TextArea descriptionArea;
    @FXML private ComboBox<Equipe> equipe1Combo;
    @FXML private ComboBox<Equipe> equipe2Combo;
    @FXML private ComboBox<Equipe> equipe3Combo;
    @FXML private ComboBox<Equipe> equipe4Combo;

    private final GroupeDAO groupeDAO = new GroupeDAO();
    private final EquipeDAO equipeDAO = new EquipeDAO();
    private Groupe groupeModif;
    private Runnable onSaveCallback;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chargerEquipes();
        configurerStyleComboBox();
    }

    private void chargerEquipes() {
        equipe1Combo.getItems().addAll(equipeDAO.getAllEquipes());
        equipe2Combo.getItems().addAll(equipeDAO.getAllEquipes());
        equipe3Combo.getItems().addAll(equipeDAO.getAllEquipes());
        equipe4Combo.getItems().addAll(equipeDAO.getAllEquipes());
    }

    private void configurerStyleComboBox() {
        // Appliquer le style pour afficher le texte en blanc
        String comboBoxStyle = "-fx-background-color: #0f1429; -fx-text-fill: white;";
        
        equipe1Combo.setStyle(comboBoxStyle);
        equipe2Combo.setStyle(comboBoxStyle);
        equipe3Combo.setStyle(comboBoxStyle);
        equipe4Combo.setStyle(comboBoxStyle);
        
        // Forcer le texte en blanc pour les éléments sélectionnés
        equipe1Combo.setButtonCell(createWhiteTextCell());
        equipe2Combo.setButtonCell(createWhiteTextCell());
        equipe3Combo.setButtonCell(createWhiteTextCell());
        equipe4Combo.setButtonCell(createWhiteTextCell());
    }

    private javafx.scene.control.ListCell<Equipe> createWhiteTextCell() {
        return new javafx.scene.control.ListCell<Equipe>() {
            @Override
            protected void updateItem(Equipe item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                    setStyle("-fx-text-fill: white; -fx-background-color: #0f1429;");
                }
            }
        };
    }

    public void setGroupe(Groupe groupe) {
        this.groupeModif = groupe;
        if (groupe != null) {
            nomField.setText(groupe.getNom());
            descriptionArea.setText(groupe.getDescription());
            
            // Charger les équipes du groupe
            try {
                java.util.List<Equipe> equipesGroupe = equipeDAO.findByGroupe(groupe.getId());
                
                if (equipesGroupe.size() >= 1) equipe1Combo.setValue(equipesGroupe.get(0));
                if (equipesGroupe.size() >= 2) equipe2Combo.setValue(equipesGroupe.get(1));
                if (equipesGroupe.size() >= 3) equipe3Combo.setValue(equipesGroupe.get(2));
                if (equipesGroupe.size() >= 4) equipe4Combo.setValue(equipesGroupe.get(3));
                
                System.out.println("✅ " + equipesGroupe.size() + " équipes chargées pour le groupe " + groupe.getNom());
            } catch (Exception e) {
                System.err.println("❌ Erreur lors du chargement des équipes du groupe: " + e.getMessage());
            }
        }
    }

    public void setOnSaveCallback(Runnable callback) {
        this.onSaveCallback = callback;
    }

    @FXML
    private void sauvegarder() {
        String nom = nomField.getText().trim();
        String description = descriptionArea.getText().trim();
        Equipe eq1 = equipe1Combo.getValue();
        Equipe eq2 = equipe2Combo.getValue();
        Equipe eq3 = equipe3Combo.getValue();
        Equipe eq4 = equipe4Combo.getValue();

        if (nom.isEmpty()) {
            showAlert("Erreur", "Le nom est obligatoire", Alert.AlertType.ERROR);
            return;
        }

        // Vérifier que 4 équipes sont sélectionnées
        if (eq1 == null || eq2 == null || eq3 == null || eq4 == null) {
            showAlert("Erreur", "Vous devez sélectionner exactement 4 équipes", Alert.AlertType.ERROR);
            return;
        }

        // Vérifier qu'il n'y a pas de doublons
        Set<Integer> equipeIds = new HashSet<>();
        equipeIds.add(eq1.getId());
        equipeIds.add(eq2.getId());
        equipeIds.add(eq3.getId());
        equipeIds.add(eq4.getId());

        if (equipeIds.size() != 4) {
            showAlert("Erreur", "Les 4 équipes doivent être différentes", Alert.AlertType.ERROR);
            return;
        }

        boolean success;
        if (groupeModif == null) {
            // Créer le groupe
            Groupe nouveauGroupe = new Groupe(0, nom, description);
            success = groupeDAO.ajouterGroupe(nouveauGroupe);
            
            if (success && nouveauGroupe.getId() > 0) {
                // Assigner les équipes au groupe
                try {
                    equipeDAO.assignerGroupe(eq1.getId(), nouveauGroupe.getId());
                    equipeDAO.assignerGroupe(eq2.getId(), nouveauGroupe.getId());
                    equipeDAO.assignerGroupe(eq3.getId(), nouveauGroupe.getId());
                    equipeDAO.assignerGroupe(eq4.getId(), nouveauGroupe.getId());
                    
                    // Mettre à jour le champ 'equipes' dans la table groupe
                    java.util.List<String> equipesNoms = new java.util.ArrayList<>();
                    equipesNoms.add(eq1.getNom());
                    equipesNoms.add(eq2.getNom());
                    equipesNoms.add(eq3.getNom());
                    equipesNoms.add(eq4.getNom());
                    groupeDAO.updateEquipesGroupe(nouveauGroupe.getId(), equipesNoms);
                    
                    System.out.println("✅ Les 4 équipes ont été assignées au groupe " + nouveauGroupe.getNom());
                    showAlert("Succès", "Groupe créé avec 4 équipes assignées", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    System.err.println("❌ Erreur lors de l'assignation des équipes: " + e.getMessage());
                    showAlert("Attention", "Groupe créé mais erreur lors de l'assignation des équipes", Alert.AlertType.WARNING);
                }
            }
        } else {
            groupeModif.setNom(nom);
            groupeModif.setDescription(description);
            success = groupeDAO.modifierGroupe(groupeModif);
            
            if (success) {
                // Mettre à jour les équipes du groupe
                try {
                    equipeDAO.assignerGroupe(eq1.getId(), groupeModif.getId());
                    equipeDAO.assignerGroupe(eq2.getId(), groupeModif.getId());
                    equipeDAO.assignerGroupe(eq3.getId(), groupeModif.getId());
                    equipeDAO.assignerGroupe(eq4.getId(), groupeModif.getId());
                    
                    // Mettre à jour le champ 'equipes' dans la table groupe
                    java.util.List<String> equipesNoms = new java.util.ArrayList<>();
                    equipesNoms.add(eq1.getNom());
                    equipesNoms.add(eq2.getNom());
                    equipesNoms.add(eq3.getNom());
                    equipesNoms.add(eq4.getNom());
                    groupeDAO.updateEquipesGroupe(groupeModif.getId(), equipesNoms);
                    
                    System.out.println("✅ Les équipes du groupe ont été mises à jour");
                } catch (Exception e) {
                    System.err.println("❌ Erreur lors de la mise à jour des équipes: " + e.getMessage());
                }
            }
        }

        if (success) {
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
