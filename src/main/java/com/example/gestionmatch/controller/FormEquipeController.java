package com.example.gestionmatch.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.example.gestionmatch.dao.EquipeDAO;
import com.example.gestionmatch.model.Equipe;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FormEquipeController {

    @FXML private TextField nomField;
    @FXML private TextField paysField;
    @FXML private TextField entraineurField;
    @FXML private TextField nombreJoueursField;
    @FXML private TextField matchsJouesField;
    @FXML private TextField victoiresField;
    @FXML private TextField nulsField;
    @FXML private TextField defaitesField;
    @FXML private TextField butsMarquesField;
    @FXML private TextField butsEncaissesField;
    @FXML private TextField pointsField;
    @FXML private TextField imagePathField;
    @FXML private ImageView imagePreview;

    private final EquipeDAO equipeDAO = new EquipeDAO();
    private Equipe equipeModif;
    private Runnable onSaveCallback;
    private File selectedImageFile;
    
    @FXML
    public void initialize() {
        // Initialisation du contrôleur
    }

    public void setEquipe(Equipe equipe) {
        this.equipeModif = equipe;
        if (equipe != null) {
            nomField.setText(equipe.getNom());
            paysField.setText(equipe.getPays());
            entraineurField.setText(equipe.getEntraineur());
            nombreJoueursField.setText(String.valueOf(equipe.getNombreJoueurs()));
            matchsJouesField.setText(String.valueOf(equipe.getMatchsJoues()));
            victoiresField.setText(String.valueOf(equipe.getVictoires()));
            nulsField.setText(String.valueOf(equipe.getNuls()));
            defaitesField.setText(String.valueOf(equipe.getDefaites()));
            butsMarquesField.setText(String.valueOf(equipe.getButsMarques()));
            butsEncaissesField.setText(String.valueOf(equipe.getButsEncaisses()));
            pointsField.setText(String.valueOf(equipe.getPoints()));
            
            // Charger l'image si elle existe
            if (equipe.getImagePath() != null && !equipe.getImagePath().isEmpty()) {
                imagePathField.setText(equipe.getImagePath());
                chargerApercu(equipe.getImagePath());
            }
        }
    }
    
    @FXML
    private void choisirImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        
        Stage stage = (Stage) nomField.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null) {
            selectedImageFile = file;
            imagePathField.setText(file.getName());
            
            // Afficher l'aperçu
            try {
                Image image = new Image(file.toURI().toString());
                imagePreview.setImage(image);
            } catch (Exception e) {
                System.err.println("Erreur chargement aperçu: " + e.getMessage());
            }
        }
    }
    
    private void chargerApercu(String imagePath) {
        try {
            String cheminComplet = "/images/equipes/" + imagePath;
            Image image = new Image(getClass().getResourceAsStream(cheminComplet));
            if (!image.isError()) {
                imagePreview.setImage(image);
            }
        } catch (Exception e) {
            System.err.println("Erreur chargement image: " + e.getMessage());
        }
    }

    public void setOnSaveCallback(Runnable callback) {
        this.onSaveCallback = callback;
    }

    @FXML
    private void sauvegarder() {
        String nom = nomField.getText().trim();
        String pays = paysField.getText().trim();
        String entraineur = entraineurField.getText().trim();

        if (nom.isEmpty() || pays.isEmpty()) {
            showAlert("Erreur", "Le nom et le pays sont obligatoires", Alert.AlertType.ERROR);
            return;
        }

        // Récupérer les valeurs numériques avec valeurs par défaut
        int nombreJoueurs = parseIntOrDefault(nombreJoueursField.getText(), 0);
        int matchsJoues = parseIntOrDefault(matchsJouesField.getText(), 0);
        int victoires = parseIntOrDefault(victoiresField.getText(), 0);
        int nuls = parseIntOrDefault(nulsField.getText(), 0);
        int defaites = parseIntOrDefault(defaitesField.getText(), 0);
        int butsMarques = parseIntOrDefault(butsMarquesField.getText(), 0);
        int butsEncaisses = parseIntOrDefault(butsEncaissesField.getText(), 0);
        int points = parseIntOrDefault(pointsField.getText(), 0);

        boolean success;
        String imagePath = null;
        
        if (equipeModif == null) {
            // Créer l'équipe sans l'image d'abord
            Equipe nouvelleEquipe = new Equipe(nom, pays, entraineur);
            nouvelleEquipe.setNombreJoueurs(nombreJoueurs);
            nouvelleEquipe.setMatchsJoues(matchsJoues);
            nouvelleEquipe.setVictoires(victoires);
            nouvelleEquipe.setNuls(nuls);
            nouvelleEquipe.setDefaites(defaites);
            nouvelleEquipe.setButsMarques(butsMarques);
            nouvelleEquipe.setButsEncaisses(butsEncaisses);
            nouvelleEquipe.setPoints(points);
            
            // Sauvegarder l'image SEULEMENT si on a sélectionné un fichier
            if (selectedImageFile != null) {
                imagePath = sauvegarderImage(selectedImageFile, nom);
                nouvelleEquipe.setImagePath(imagePath);
            }
            
            // Essayer d'ajouter l'équipe dans la BD
            success = equipeDAO.ajouterEquipe(nouvelleEquipe);
            
            // Si échec et qu'on a sauvegardé une image, la supprimer
            if (!success && imagePath != null) {
                supprimerImage(imagePath);
            }
        } else {
            equipeModif.setNom(nom);
            equipeModif.setPays(pays);
            equipeModif.setEntraineur(entraineur);
            equipeModif.setNombreJoueurs(nombreJoueurs);
            equipeModif.setMatchsJoues(matchsJoues);
            equipeModif.setVictoires(victoires);
            equipeModif.setNuls(nuls);
            equipeModif.setDefaites(defaites);
            equipeModif.setButsMarques(butsMarques);
            equipeModif.setButsEncaisses(butsEncaisses);
            equipeModif.setPoints(points);
            
            // Sauvegarder l'image si sélectionnée
            if (selectedImageFile != null) {
                imagePath = sauvegarderImage(selectedImageFile, nom);
                equipeModif.setImagePath(imagePath);
            }
            
            success = equipeDAO.modifierEquipe(equipeModif);
        }

        if (success) {
            showAlert("Succès", "Équipe sauvegardée avec succès", Alert.AlertType.INFORMATION);
            if (onSaveCallback != null) onSaveCallback.run();
            fermer();
        } else {
            showAlert("Erreur", "Erreur lors de la sauvegarde. L'équipe existe peut-être déjà.", Alert.AlertType.ERROR);
        }
    }

    private int parseIntOrDefault(String value, int defaultValue) {
        try {
            return value == null || value.trim().isEmpty() ? defaultValue : Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    private String sauvegarderImage(File imageFile, String nomEquipe) {
        try {
            // Normaliser le nom de l'équipe pour le nom de fichier
            String nomFichier = nomEquipe.toLowerCase()
                .replace(" ", "_")
                .replace("é", "e")
                .replace("è", "e")
                .replace("ê", "e")
                .replace("à", "a")
                .replace("ô", "o")
                .replace("ù", "u")
                .replace("ç", "c");
            
            // Obtenir l'extension du fichier
            String extension = "";
            String fileName = imageFile.getName();
            int dotIndex = fileName.lastIndexOf('.');
            if (dotIndex > 0) {
                extension = fileName.substring(dotIndex);
            }
            
            String nomFichierFinal = nomFichier + extension;
            
            // Sauvegarder dans src/main/resources/images/equipes (pour le développement)
            Path destinationSrc = Paths.get("src/main/resources/images/equipes");
            if (!Files.exists(destinationSrc)) {
                Files.createDirectories(destinationSrc);
            }
            Path fichierSrc = destinationSrc.resolve(nomFichierFinal);
            Files.copy(imageFile.toPath(), fichierSrc, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("✅ Image sauvegardée (src): " + nomFichierFinal);
            
            // Sauvegarder aussi dans target/classes/images/equipes (pour l'exécution)
            Path destinationTarget = Paths.get("target/classes/images/equipes");
            if (!Files.exists(destinationTarget)) {
                Files.createDirectories(destinationTarget);
            }
            Path fichierTarget = destinationTarget.resolve(nomFichierFinal);
            Files.copy(imageFile.toPath(), fichierTarget, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("✅ Image sauvegardée (target): " + nomFichierFinal);
            
            return nomFichierFinal;
            
        } catch (IOException e) {
            System.err.println("❌ Erreur sauvegarde image: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    private void supprimerImage(String imagePath) {
        try {
            // Supprimer de src/main/resources/images/equipes
            Path cheminImageSrc = Paths.get("src/main/resources/images/equipes", imagePath);
            if (Files.exists(cheminImageSrc)) {
                Files.delete(cheminImageSrc);
                System.out.println("🗑️ Image supprimée (src): " + imagePath);
            }
            
            // Supprimer de target/classes/images/equipes
            Path cheminImageTarget = Paths.get("target/classes/images/equipes", imagePath);
            if (Files.exists(cheminImageTarget)) {
                Files.delete(cheminImageTarget);
                System.out.println("🗑️ Image supprimée (target): " + imagePath);
            }
        } catch (IOException e) {
            System.err.println("❌ Erreur suppression image: " + e.getMessage());
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
