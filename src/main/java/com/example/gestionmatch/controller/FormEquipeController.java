package com.example.gestionmatch.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.gestionmatch.dao.EquipeDAO;
import com.example.gestionmatch.dao.JoueurDAO;
import com.example.gestionmatch.model.Equipe;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class FormEquipeController {

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
    @FXML private ImageView imagePreview;
    @FXML private Label drapeauInfoLabel;
    @FXML private Label fichierSelectionneLabel;

    private final EquipeDAO equipeDAO = new EquipeDAO();
    private final JoueurDAO joueurDAO = new JoueurDAO();
    private Equipe equipeModif;
    private Runnable onSaveCallback;
    private java.io.File fichierDrapeauSelectionne;
    
    // Mapping pays -> nom de fichier drapeau
    private final Map<String, String> paysVersDrapeau = new HashMap<>();
    
    @FXML
    public void initialize() {
        // Initialiser le mapping pays -> drapeau
        initializerPaysMapping();
        
        // Listener pour charger le drapeau automatiquement quand l'utilisateur tape
        paysField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.trim().isEmpty()) {
                chargerDrapeauAutomatique(newValue.trim());
            }
        });
    }
    
    private void initializerPaysMapping() {
        paysVersDrapeau.put("Algérie", "algerie.png");
        paysVersDrapeau.put("Allemagne", "allemagne.png");
        paysVersDrapeau.put("Angleterre", "angleterre.png");
        paysVersDrapeau.put("Arabie Saoudite", "arabie-saoudite.png");
        paysVersDrapeau.put("Argentine", "argentine.png");
        paysVersDrapeau.put("Australie", "australie.png");
        paysVersDrapeau.put("Belgique", "belgique.png");
        paysVersDrapeau.put("Brésil", "bresil.png");
        paysVersDrapeau.put("Cameroun", "cameroun.png");
        paysVersDrapeau.put("Canada", "canada.png");
        paysVersDrapeau.put("Corée du Sud", "coree-sud.png");
        paysVersDrapeau.put("Costa Rica", "costa-rica.png");
        paysVersDrapeau.put("Croatie", "croatie.png");
        paysVersDrapeau.put("Danemark", "danemark.png");
        paysVersDrapeau.put("Équateur", "equateur.png");
        paysVersDrapeau.put("Espagne", "espagne.png");
        paysVersDrapeau.put("États-Unis", "etats-unis.png");
        paysVersDrapeau.put("France", "france.png");
        paysVersDrapeau.put("Ghana", "ghana.png");
        paysVersDrapeau.put("Iran", "iran.png");
        paysVersDrapeau.put("Italie", "italie.png");
        paysVersDrapeau.put("Japon", "japon.png");
        paysVersDrapeau.put("Maroc", "maroc.png");
        paysVersDrapeau.put("Mexique", "mexique.png");
        paysVersDrapeau.put("Nigeria", "nigeria.png");
        paysVersDrapeau.put("Pays-Bas", "pays-bas.png");
        paysVersDrapeau.put("Pérou", "perou.png");
        paysVersDrapeau.put("Pologne", "pologne.png");
        paysVersDrapeau.put("Portugal", "portugal.png");
        paysVersDrapeau.put("Qatar", "qatar.png");
        paysVersDrapeau.put("Sénégal", "senegal.png");
        paysVersDrapeau.put("Serbie", "serbie.png");
        paysVersDrapeau.put("Suisse", "suisse.png");
        paysVersDrapeau.put("Tunisie", "tunisie.png");
        paysVersDrapeau.put("Uruguay", "uruguay.png");
    }
    
    private void chargerDrapeauAutomatique(String pays) {
        String nomFichierDrapeau = paysVersDrapeau.get(pays);
        if (nomFichierDrapeau != null) {
            try {
                String cheminComplet = "/images/equipes/" + nomFichierDrapeau;
                java.io.InputStream stream = getClass().getResourceAsStream(cheminComplet);
                if (stream != null) {
                    Image image = new Image(stream);
                    if (!image.isError()) {
                        imagePreview.setImage(image);
                        drapeauInfoLabel.setText("✅ Drapeau chargé: " + nomFichierDrapeau);
                        drapeauInfoLabel.setStyle("-fx-text-fill: #10b981; -fx-font-size: 13px; -fx-font-weight: 600;");
                        System.out.println("✅ Drapeau chargé: " + nomFichierDrapeau);
                    } else {
                        drapeauInfoLabel.setText("❌ Erreur de chargement du drapeau");
                        drapeauInfoLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 13px;");
                    }
                } else {
                    drapeauInfoLabel.setText("⚠️ Drapeau non trouvé pour: " + pays);
                    drapeauInfoLabel.setStyle("-fx-text-fill: #f59e0b; -fx-font-size: 13px;");
                    imagePreview.setImage(null);
                }
            } catch (Exception e) {
                System.err.println("Erreur chargement drapeau: " + e.getMessage());
                drapeauInfoLabel.setText("❌ Erreur: " + e.getMessage());
                drapeauInfoLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 13px;");
            }
        } else {
            drapeauInfoLabel.setText("ℹ️ Tapez le nom exact du pays (ex: Maroc, France, Brésil)");
            drapeauInfoLabel.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px;");
            imagePreview.setImage(null);
        }
    }

    public void setEquipe(Equipe equipe) {
        this.equipeModif = equipe;
        if (equipe != null) {
            paysField.setText(equipe.getPays());
            entraineurField.setText(equipe.getEntraineur());
            
            // Compter le nombre réel de joueurs dans l'équipe
            int nombreJoueursReel = joueurDAO.compterJoueursParEquipe(equipe.getId());
            nombreJoueursField.setText(String.valueOf(nombreJoueursReel));
            
            matchsJouesField.setText(String.valueOf(equipe.getMatchsJoues()));
            victoiresField.setText(String.valueOf(equipe.getVictoires()));
            nulsField.setText(String.valueOf(equipe.getNuls()));
            defaitesField.setText(String.valueOf(equipe.getDefaites()));
            butsMarquesField.setText(String.valueOf(equipe.getButsMarques()));
            butsEncaissesField.setText(String.valueOf(equipe.getButsEncaisses()));
            pointsField.setText(String.valueOf(equipe.getPoints()));
            
            // Charger le drapeau si disponible
            if (equipe.getPays() != null && !equipe.getPays().isEmpty()) {
                chargerDrapeauAutomatique(equipe.getPays());
            }
        } else {
            // Nouvelle équipe : 0 joueurs
            nombreJoueursField.setText("0");
        }
    }

    public void setOnSaveCallback(Runnable callback) {
        this.onSaveCallback = callback;
    }

    @FXML
    private void sauvegarder() {
        String pays = paysField.getText().trim();
        String entraineur = entraineurField.getText().trim();

        if (pays.isEmpty()) {
            showAlert("Erreur", "Le pays est obligatoire", Alert.AlertType.ERROR);
            return;
        }

        // Le nom de l'équipe est le même que le pays
        String nom = pays;

        // Récupérer les valeurs numériques avec valeurs par défaut
        int nombreJoueurs = parseIntOrDefault(nombreJoueursField.getText(), 0);
        
        // VALIDATION: Vérifier que le nombre de joueurs ne dépasse pas 26
        if (nombreJoueurs > 26) {
            showAlert("Erreur", 
                "❌ Le nombre de joueurs ne peut pas dépasser 26.\n\n" +
                "⚠️ Une équipe nationale peut avoir maximum 26 joueurs.\n\n" +
                "Veuillez entrer un nombre entre 0 et 26.", 
                Alert.AlertType.ERROR);
            return;
        }
        
        int matchsJoues = parseIntOrDefault(matchsJouesField.getText(), 0);
        int victoires = parseIntOrDefault(victoiresField.getText(), 0);
        int nuls = parseIntOrDefault(nulsField.getText(), 0);
        int defaites = parseIntOrDefault(defaitesField.getText(), 0);
        int butsMarques = parseIntOrDefault(butsMarquesField.getText(), 0);
        int butsEncaisses = parseIntOrDefault(butsEncaissesField.getText(), 0);
        int points = parseIntOrDefault(pointsField.getText(), 0);

        boolean success;
        String imagePath = null;
        
        // Si un fichier a été sélectionné manuellement, le copier
        if (fichierDrapeauSelectionne != null) {
            try {
                // Créer le nom de fichier basé sur le pays
                String extension = fichierDrapeauSelectionne.getName().substring(
                    fichierDrapeauSelectionne.getName().lastIndexOf("."));
                String nomFichier = pays.toLowerCase().replace(" ", "-").replace("é", "e")
                    .replace("è", "e").replace("ê", "e").replace("à", "a") + extension;
                
                // Copier le fichier dans resources/images/equipes/
                java.nio.file.Path source = fichierDrapeauSelectionne.toPath();
                java.nio.file.Path destination = java.nio.file.Paths.get(
                    "src/main/resources/images/equipes/" + nomFichier);
                
                // Créer le dossier si nécessaire
                java.nio.file.Files.createDirectories(destination.getParent());
                
                // Copier le fichier
                java.nio.file.Files.copy(source, destination, 
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                
                imagePath = nomFichier;
                System.out.println("✅ Drapeau copié: " + nomFichier);
                
            } catch (Exception e) {
                System.err.println("❌ Erreur copie drapeau: " + e.getMessage());
                showAlert("Avertissement", "Le drapeau n'a pas pu être copié: " + e.getMessage(), 
                    Alert.AlertType.WARNING);
            }
        } else {
            // Utiliser le drapeau automatique
            imagePath = paysVersDrapeau.get(pays);
        }
        
        if (equipeModif == null) {
            // Créer nouvelle équipe
            Equipe nouvelleEquipe = new Equipe(nom, pays, entraineur);
            nouvelleEquipe.setNombreJoueurs(nombreJoueurs);
            nouvelleEquipe.setMatchsJoues(matchsJoues);
            nouvelleEquipe.setVictoires(victoires);
            nouvelleEquipe.setNuls(nuls);
            nouvelleEquipe.setDefaites(defaites);
            nouvelleEquipe.setButsMarques(butsMarques);
            nouvelleEquipe.setButsEncaisses(butsEncaisses);
            nouvelleEquipe.setPoints(points);
            nouvelleEquipe.setImagePath(imagePath);
            
            success = equipeDAO.ajouterEquipe(nouvelleEquipe);
        } else {
            // Modifier équipe existante
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
            equipeModif.setImagePath(imagePath);
            
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

    @FXML
    private void parcourirDrapeau() {
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Sélectionner un drapeau");
        fileChooser.getExtensionFilters().addAll(
            new javafx.stage.FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"),
            new javafx.stage.FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
        );
        
        // Ouvrir le dossier Images par défaut
        String userHome = System.getProperty("user.home");
        java.io.File initialDir = new java.io.File(userHome, "Pictures");
        if (!initialDir.exists()) {
            initialDir = new java.io.File(userHome);
        }
        fileChooser.setInitialDirectory(initialDir);
        
        Stage stage = (Stage) paysField.getScene().getWindow();
        java.io.File fichier = fileChooser.showOpenDialog(stage);
        
        if (fichier != null) {
            fichierDrapeauSelectionne = fichier;
            fichierSelectionneLabel.setText("✅ " + fichier.getName());
            fichierSelectionneLabel.setStyle("-fx-text-fill: #10b981; -fx-font-size: 12px; -fx-font-weight: 600;");
            
            // Afficher l'aperçu
            try {
                Image image = new Image(fichier.toURI().toString());
                imagePreview.setImage(image);
                drapeauInfoLabel.setText("✅ Drapeau personnalisé chargé");
                drapeauInfoLabel.setStyle("-fx-text-fill: #10b981; -fx-font-size: 13px; -fx-font-weight: 600;");
            } catch (Exception e) {
                showAlert("Erreur", "Impossible de charger l'image: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void annuler() {
        fermer();
    }

    private void fermer() {
        Stage stage = (Stage) paysField.getScene().getWindow();
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
