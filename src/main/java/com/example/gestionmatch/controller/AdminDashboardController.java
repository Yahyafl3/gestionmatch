package com.example.gestionmatch.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.gestionmatch.dao.EquipeDAO;
import com.example.gestionmatch.dao.GroupeDAO;
import com.example.gestionmatch.dao.JoueurDAO;
import com.example.gestionmatch.dao.MatchDAO;
import com.example.gestionmatch.dao.StadeDAO;
import com.example.gestionmatch.model.Equipe;
import com.example.gestionmatch.model.Groupe;
import com.example.gestionmatch.model.Joueur;
import com.example.gestionmatch.model.Match;
import com.example.gestionmatch.model.Stade;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminDashboardController implements Initializable {

    @FXML private BorderPane rootPane;
    @FXML private StackPane contentPane;
    @FXML private VBox dashboardView, joueursView, equipesView, stadesView, groupesView, matchsView;
    @FXML private VBox prochainsMatchsList;
    
    @FXML private Label statJoueurs, statEquipes, statMatchs, statStades, pageTitle, dateLabel, timeLabel, userEmail;
    @FXML private Label tauxRemplissage, matchsSemaine, equipesQualifiees;
    
    @FXML private Button btnDashboard, btnJoueurs, btnEquipes, btnStades, btnGroupes, btnMatchs;
    
    @FXML private TextField searchFieldJoueurs;
    
    @FXML private TableView<Joueur> tableJoueurs;
    @FXML private TableView<Equipe> tableEquipes;
    @FXML private TableView<Stade> tableStades;
    @FXML private TableView<Groupe> tableGroupes;
    @FXML private TableView<Match> tableMatchs;

    private final JoueurDAO joueurDAO = new JoueurDAO();
    private final EquipeDAO equipeDAO = new EquipeDAO();
    private final StadeDAO stadeDAO = new StadeDAO();
    private final GroupeDAO groupeDAO = new GroupeDAO();
    private final MatchDAO matchDAO = new MatchDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Charger l'image de fond
        chargerImageDeFond();
        
        initialiserTablesJoueurs();
        initialiserTablesEquipes();
        initialiserTablesStades();
        initialiserTablesGroupes();
        initialiserTablesMatchs();
        
        // Mettre à jour la date et l'heure
        updateDateTime();
        
        // Horloge en temps réel
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateDateTime()));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
        
        afficherDashboard();
        chargerStatistiques();
    }
    
    private void chargerImageDeFond() {
        try {
            // Charger l'image zelij.jpg sans transparence
            Image backgroundImage = new Image(getClass().getResourceAsStream("/images/zelij.jpg"));
            
            // Créer le BackgroundImage avec les propriétés (image claire, sans transparence)
            BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true)
            );
            
            // Appliquer le fond au BorderPane avec opacité complète
            rootPane.setBackground(new Background(bgImage));
            rootPane.setOpacity(1.0); // Opacité complète, pas de transparence
            
            System.out.println("Image de fond zelij.jpg chargée avec succès (sans transparence) !");
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image de fond : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateDateTime() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        dateLabel.setText(now.format(dateFormatter));
        timeLabel.setText(now.format(timeFormatter));
    }

    @FXML
    private void afficherDashboard() {
        cacherToutesVues();
        dashboardView.setVisible(true);
        pageTitle.setText("Dashboard");
        activerBoutonMenu(btnDashboard);
        chargerStatistiques();
    }

    @FXML
    private void afficherJoueurs() {
        cacherToutesVues();
        joueursView.setVisible(true);
        pageTitle.setText("Gestion des Joueurs");
        activerBoutonMenu(btnJoueurs);
        chargerJoueurs();
    }

    @FXML
    private void afficherEquipes() {
        cacherToutesVues();
        equipesView.setVisible(true);
        pageTitle.setText("Gestion des Équipes");
        activerBoutonMenu(btnEquipes);
        chargerEquipes();
    }

    @FXML
    private void afficherStades() {
        cacherToutesVues();
        stadesView.setVisible(true);
        pageTitle.setText("Gestion des Stades");
        activerBoutonMenu(btnStades);
        chargerStades();
    }

    @FXML
    private void afficherGroupes() {
        try {
            cacherToutesVues();
            
            // Charger la nouvelle vue des groupes
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/fxml/groupe-view.fxml")
            );
            javafx.scene.Parent groupeView = loader.load();
            
            // Remplacer le contenu
            contentPane.getChildren().clear();
            contentPane.getChildren().add(groupeView);
            
            pageTitle.setText("Phase de Groupes - Coupe du Monde 2030");
            activerBoutonMenu(btnGroupes);
        } catch (Exception e) {
            System.err.println("❌ Erreur chargement vue groupes : " + e.getMessage());
            e.printStackTrace();
            
            // Fallback vers l'ancienne vue
            groupesView.setVisible(true);
            pageTitle.setText("Gestion des Groupes");
            activerBoutonMenu(btnGroupes);
            chargerGroupes();
        }
    }

    @FXML
    private void afficherMatchs() {
        cacherToutesVues();
        matchsView.setVisible(true);
        pageTitle.setText("Gestion des Matchs");
        activerBoutonMenu(btnMatchs);
        chargerMatchs();
    }

    private void cacherToutesVues() {
        dashboardView.setVisible(false);
        joueursView.setVisible(false);
        equipesView.setVisible(false);
        stadesView.setVisible(false);
        groupesView.setVisible(false);
        matchsView.setVisible(false);
    }

    private void activerBoutonMenu(Button boutonActif) {
        // Réinitialiser tous les boutons
        btnDashboard.getStyleClass().clear();
        btnJoueurs.getStyleClass().clear();
        btnEquipes.getStyleClass().clear();
        btnStades.getStyleClass().clear();
        btnGroupes.getStyleClass().clear();
        btnMatchs.getStyleClass().clear();
        
        btnDashboard.getStyleClass().add("menu-btn");
        btnJoueurs.getStyleClass().add("menu-btn");
        btnEquipes.getStyleClass().add("menu-btn");
        btnStades.getStyleClass().add("menu-btn");
        btnGroupes.getStyleClass().add("menu-btn");
        btnMatchs.getStyleClass().add("menu-btn");
        
        // Activer le bouton sélectionné
        boutonActif.getStyleClass().clear();
        boutonActif.getStyleClass().add("menu-btn-active");
    }

    private void chargerStatistiques() {
        statJoueurs.setText(String.valueOf(joueurDAO.getAllJoueurs().size()));
        statEquipes.setText(String.valueOf(equipeDAO.getAllEquipes().size()));
        statMatchs.setText(String.valueOf(matchDAO.getAllMatchs().size()));
        statStades.setText(String.valueOf(stadeDAO.getAllStades().size()));
        
        // Charger les prochains matchs
        chargerProchainsMatchs();
        
        // Charger les analytics
        chargerAnalytics();
    }
    
    private void chargerProchainsMatchs() {
        if (prochainsMatchsList == null) return;
        
        prochainsMatchsList.getChildren().clear();
        
        // Récupérer les 3 prochains matchs
        java.util.List<Match> tousLesMatchs = matchDAO.getAllMatchs();
        java.time.LocalDate aujourdhui = java.time.LocalDate.now();
        
        // Filtrer et trier les matchs futurs
        java.util.List<Match> prochains = tousLesMatchs.stream()
            .filter(m -> m.getDateMatch() != null && !m.getDateMatch().isBefore(aujourdhui))
            .sorted((m1, m2) -> m1.getDateMatch().compareTo(m2.getDateMatch()))
            .limit(3)
            .collect(java.util.stream.Collectors.toList());
        
        // Couleurs pour les heures
        String[] couleurs = {"#667eea", "#4facfe", "#f093fb"};
        String[] heures = {"20:00", "18:00", "21:00"}; // Heures par défaut
        
        for (int i = 0; i < prochains.size(); i++) {
            Match match = prochains.get(i);
            String couleur = couleurs[i % couleurs.length];
            String heure = heures[i % heures.length];
            
            try {
                // Formater la date
                String dateFormatee = match.getDateMatch().format(
                    java.time.format.DateTimeFormatter.ofPattern("dd MMMM yyyy", java.util.Locale.FRENCH));
                
                // Créer l'élément de match
                HBox matchBox = new HBox(15);
                matchBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                matchBox.setStyle("-fx-background-color: linear-gradient(to right, #f8fafc 0%, #f1f5f9 100%); " +
                                "-fx-padding: 18; -fx-background-radius: 12;");
                
                VBox infoBox = new VBox(4);
                HBox.setHgrow(infoBox, javafx.scene.layout.Priority.ALWAYS);
                
                Label nomLabel = new Label(match.getNom());
                nomLabel.setStyle("-fx-text-fill: #1e293b; -fx-font-size: 15px; -fx-font-weight: bold;");
                
                String lieu = match.getLieu() != null ? match.getLieu() : "Stade à définir";
                Label lieuLabel = new Label(lieu + " - " + dateFormatee);
                lieuLabel.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;");
                
                infoBox.getChildren().addAll(nomLabel, lieuLabel);
                
                Label heureLabel = new Label(heure);
                heureLabel.setStyle("-fx-text-fill: " + couleur + "; -fx-font-size: 18px; -fx-font-weight: bold; " +
                                  "-fx-background-color: rgba(" + hexToRgb(couleur) + ", 0.1); " +
                                  "-fx-padding: 8 15; -fx-background-radius: 8;");
                
                matchBox.getChildren().addAll(infoBox, heureLabel);
                prochainsMatchsList.getChildren().add(matchBox);
                
            } catch (Exception e) {
                System.err.println("Erreur affichage match: " + e.getMessage());
            }
        }
        
        // Si aucun match trouvé
        if (prochains.isEmpty()) {
            Label aucunMatch = new Label("Aucun match à venir");
            aucunMatch.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px; -fx-padding: 20;");
            prochainsMatchsList.getChildren().add(aucunMatch);
        }
    }
    
    private String hexToRgb(String hex) {
        hex = hex.replace("#", "");
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        return r + ", " + g + ", " + b;
    }
    
    private void chargerAnalytics() {
        // Taux de remplissage (nombre d'équipes / 32 * 100)
        int nbEquipes = equipeDAO.getAllEquipes().size();
        int tauxPourcent = (int) Math.round((nbEquipes / 32.0) * 100);
        if (tauxRemplissage != null) {
            tauxRemplissage.setText(tauxPourcent + "%");
        }
        
        // Matchs cette semaine
        java.util.List<Match> tousLesMatchs = matchDAO.getAllMatchs();
        java.time.LocalDate aujourdhui = java.time.LocalDate.now();
        java.time.LocalDate finSemaine = aujourdhui.plusDays(7);
        
        long matchsCetteSemaine = tousLesMatchs.stream()
            .filter(m -> {
                if (m.getDateMatch() == null) return false;
                return !m.getDateMatch().isBefore(aujourdhui) && !m.getDateMatch().isAfter(finSemaine);
            })
            .count();
        
        if (matchsSemaine != null) {
            matchsSemaine.setText(String.valueOf(matchsCetteSemaine));
        }
        
        // Équipes qualifiées (toutes les équipes dans la base)
        if (equipesQualifiees != null) {
            equipesQualifiees.setText(String.valueOf(nbEquipes));
        }
    }

    @SuppressWarnings("unchecked")
    private void initialiserTablesJoueurs() {
        TableColumn<Joueur, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Joueur, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(150);

        TableColumn<Joueur, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colPrenom.setPrefWidth(150);

        TableColumn<Joueur, Integer> colNumero = new TableColumn<>("Numéro");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colNumero.setPrefWidth(80);

        TableColumn<Joueur, String> colPoste = new TableColumn<>("Poste");
        colPoste.setCellValueFactory(new PropertyValueFactory<>("poste"));
        colPoste.setPrefWidth(120);

        tableJoueurs.getColumns().clear();
        tableJoueurs.getColumns().addAll(colId, colNom, colPrenom, colNumero, colPoste);
    }

    private void chargerJoueurs() {
        tableJoueurs.setItems(FXCollections.observableArrayList(joueurDAO.getAllJoueurs()));
    }

    @FXML
    private void ajouterJoueur() {
        ouvrirFormulaireJoueur(null);
    }

    @FXML
    private void modifierJoueur() {
        Joueur selected = tableJoueurs.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner un joueur", Alert.AlertType.WARNING);
            return;
        }
        ouvrirFormulaireJoueur(selected);
    }

    @FXML
    private void supprimerJoueur() {
        Joueur selected = tableJoueurs.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner un joueur", Alert.AlertType.WARNING);
            return;
        }

        Optional<ButtonType> result = showConfirmation("Confirmation", 
            "Voulez-vous vraiment supprimer ce joueur ?");
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (joueurDAO.supprimerJoueur(selected.getId())) {
                showAlert("Succès", "Joueur supprimé", Alert.AlertType.INFORMATION);
                chargerJoueurs();
                chargerStatistiques();
            } else {
                showAlert("Erreur", "Erreur lors de la suppression", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void rechercherJoueur() {
        String nomRecherche = searchFieldJoueurs.getText().trim();
        
        if (nomRecherche.isEmpty()) {
            showAlert("Attention", "Veuillez entrer un nom à rechercher", Alert.AlertType.WARNING);
            return;
        }
        
        java.util.List<Joueur> resultats = joueurDAO.rechercherParNom(nomRecherche);
        
        if (resultats.isEmpty()) {
            showAlert("Information", "Aucun joueur trouvé avec le nom : " + nomRecherche, Alert.AlertType.INFORMATION);
            tableJoueurs.setItems(FXCollections.observableArrayList());
        } else {
            tableJoueurs.setItems(FXCollections.observableArrayList(resultats));
            showAlert("Succès", resultats.size() + " joueur(s) trouvé(s)", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void reinitialiserRechercheJoueurs() {
        searchFieldJoueurs.clear();
        chargerJoueurs();
    }

    private void ouvrirFormulaireJoueur(Joueur joueur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/form-joueur.fxml"));
            VBox root = loader.load();
            
            FormJoueurController controller = loader.getController();
            if (joueur != null) controller.setJoueur(joueur);
            controller.setOnSaveCallback(() -> {
                chargerJoueurs();
                chargerStatistiques();
            });
            
            Stage stage = new Stage();
            stage.setTitle(joueur == null ? "Ajouter un joueur" : "Modifier un joueur");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("❌ Erreur : " + e.getMessage());
            showAlert("Erreur", "Impossible d'ouvrir le formulaire", Alert.AlertType.ERROR);
        }
    }

    @SuppressWarnings("unchecked")
    private void initialiserTablesEquipes() {
        TableColumn<Equipe, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Equipe, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(180);

        TableColumn<Equipe, String> colPays = new TableColumn<>("Pays");
        colPays.setCellValueFactory(new PropertyValueFactory<>("pays"));
        colPays.setPrefWidth(120);

        TableColumn<Equipe, String> colEntraineur = new TableColumn<>("Entraîneur");
        colEntraineur.setCellValueFactory(new PropertyValueFactory<>("entraineur"));
        colEntraineur.setPrefWidth(150);

        TableColumn<Equipe, Integer> colPoints = new TableColumn<>("Points");
        colPoints.setCellValueFactory(new PropertyValueFactory<>("points"));
        colPoints.setPrefWidth(80);

        tableEquipes.getColumns().clear();
        tableEquipes.getColumns().addAll(colId, colNom, colPays, colEntraineur, colPoints);
    }

    private void chargerEquipes() {
        tableEquipes.setItems(FXCollections.observableArrayList(equipeDAO.getAllEquipes()));
    }

    @FXML
    private void ajouterEquipe() {
        ouvrirFormulaireEquipe(null);
    }

    @FXML
    private void modifierEquipe() {
        Equipe selected = tableEquipes.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner une équipe", Alert.AlertType.WARNING);
            return;
        }
        ouvrirFormulaireEquipe(selected);
    }

    @FXML
    private void supprimerEquipe() {
        Equipe selected = tableEquipes.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner une équipe", Alert.AlertType.WARNING);
            return;
        }

        Optional<ButtonType> result = showConfirmation("Confirmation", 
            "Voulez-vous vraiment supprimer cette équipe ?");
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (equipeDAO.supprimerEquipe(selected.getId())) {
                showAlert("Succès", "Équipe supprimée", Alert.AlertType.INFORMATION);
                chargerEquipes();
                chargerStatistiques();
            } else {
                showAlert("Erreur", "Erreur lors de la suppression", Alert.AlertType.ERROR);
            }
        }
    }

    private void ouvrirFormulaireEquipe(Equipe equipe) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/form-equipe.fxml"));
            javafx.scene.Parent root = loader.load();
            
            FormEquipeController controller = loader.getController();
            if (equipe != null) controller.setEquipe(equipe);
            controller.setOnSaveCallback(() -> {
                chargerEquipes();
                chargerStatistiques();
            });
            
            Stage stage = new Stage();
            stage.setTitle(equipe == null ? "Ajouter une équipe" : "Modifier une équipe");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("❌ Erreur : " + e.getMessage());
            showAlert("Erreur", "Impossible d'ouvrir le formulaire", Alert.AlertType.ERROR);
        }
    }

    @SuppressWarnings("unchecked")
    private void initialiserTablesStades() {
        TableColumn<Stade, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Stade, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(250);

        TableColumn<Stade, String> colVille = new TableColumn<>("Ville");
        colVille.setCellValueFactory(new PropertyValueFactory<>("ville"));
        colVille.setPrefWidth(150);

        TableColumn<Stade, Integer> colCapacite = new TableColumn<>("Capacité");
        colCapacite.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        colCapacite.setPrefWidth(120);

        tableStades.getColumns().clear();
        tableStades.getColumns().addAll(colId, colNom, colVille, colCapacite);
    }

    private void chargerStades() {
        tableStades.setItems(FXCollections.observableArrayList(stadeDAO.getAllStades()));
    }

    @FXML
    private void ajouterStade() {
        ouvrirFormulaireStade(null);
    }

    @FXML
    private void modifierStade() {
        Stade selected = tableStades.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner un stade", Alert.AlertType.WARNING);
            return;
        }
        ouvrirFormulaireStade(selected);
    }

    @FXML
    private void supprimerStade() {
        Stade selected = tableStades.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner un stade", Alert.AlertType.WARNING);
            return;
        }

        Optional<ButtonType> result = showConfirmation("Confirmation", 
            "Voulez-vous vraiment supprimer ce stade ?");
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (stadeDAO.supprimerStade(selected.getId())) {
                showAlert("Succès", "Stade supprimé", Alert.AlertType.INFORMATION);
                chargerStades();
                chargerStatistiques();
            } else {
                showAlert("Erreur", "Erreur lors de la suppression", Alert.AlertType.ERROR);
            }
        }
    }

    private void ouvrirFormulaireStade(Stade stade) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/form-stade.fxml"));
            VBox root = loader.load();
            
            FormStadeController controller = loader.getController();
            if (stade != null) controller.setStade(stade);
            controller.setOnSaveCallback(() -> {
                chargerStades();
                chargerStatistiques();
            });
            
            Stage stage = new Stage();
            stage.setTitle(stade == null ? "Ajouter un stade" : "Modifier un stade");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("❌ Erreur : " + e.getMessage());
            showAlert("Erreur", "Impossible d'ouvrir le formulaire", Alert.AlertType.ERROR);
        }
    }

    @SuppressWarnings("unchecked")
    private void initialiserTablesGroupes() {
        TableColumn<Groupe, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Groupe, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(150);

        TableColumn<Groupe, String> colDescription = new TableColumn<>("Description");
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDescription.setPrefWidth(300);

        tableGroupes.getColumns().clear();
        tableGroupes.getColumns().addAll(colId, colNom, colDescription);
    }

    private void chargerGroupes() {
        tableGroupes.setItems(FXCollections.observableArrayList(groupeDAO.getAllGroupes()));
    }

    @FXML
    private void ajouterGroupe() {
        ouvrirFormulaireGroupe(null);
    }

    @FXML
    private void modifierGroupe() {
        Groupe selected = tableGroupes.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner un groupe", Alert.AlertType.WARNING);
            return;
        }
        ouvrirFormulaireGroupe(selected);
    }

    @FXML
    private void supprimerGroupe() {
        Groupe selected = tableGroupes.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner un groupe", Alert.AlertType.WARNING);
            return;
        }

        Optional<ButtonType> result = showConfirmation("Confirmation", 
            "Voulez-vous vraiment supprimer ce groupe ?");
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (groupeDAO.supprimerGroupe(selected.getId())) {
                showAlert("Succès", "Groupe supprimé", Alert.AlertType.INFORMATION);
                chargerGroupes();
            } else {
                showAlert("Erreur", "Erreur lors de la suppression", Alert.AlertType.ERROR);
            }
        }
    }

    private void ouvrirFormulaireGroupe(Groupe groupe) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/form-groupe.fxml"));
            VBox root = loader.load();
            
            FormGroupeController controller = loader.getController();
            if (groupe != null) controller.setGroupe(groupe);
            controller.setOnSaveCallback(this::chargerGroupes);
            
            Stage stage = new Stage();
            stage.setTitle(groupe == null ? "Ajouter un groupe" : "Modifier un groupe");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("❌ Erreur : " + e.getMessage());
            showAlert("Erreur", "Impossible d'ouvrir le formulaire", Alert.AlertType.ERROR);
        }
    }

    @SuppressWarnings("unchecked")
    private void initialiserTablesMatchs() {
        TableColumn<Match, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Match, String> colNom = new TableColumn<>("Match");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(250);

        TableColumn<Match, String> colLieu = new TableColumn<>("Lieu");
        colLieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        colLieu.setPrefWidth(180);

        TableColumn<Match, String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateMatch"));
        colDate.setPrefWidth(120);

        tableMatchs.getColumns().clear();
        tableMatchs.getColumns().addAll(colId, colNom, colLieu, colDate);
    }

    private void chargerMatchs() {
        tableMatchs.setItems(FXCollections.observableArrayList(matchDAO.getAllMatchs()));
    }

    @FXML
    private void ajouterMatch() {
        ouvrirFormulaireMatch(null);
    }

    @FXML
    private void modifierMatch() {
        Match selected = tableMatchs.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner un match", Alert.AlertType.WARNING);
            return;
        }
        ouvrirFormulaireMatch(selected);
    }

    @FXML
    private void supprimerMatch() {
        Match selected = tableMatchs.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner un match", Alert.AlertType.WARNING);
            return;
        }

        Optional<ButtonType> result = showConfirmation("Confirmation", 
            "Voulez-vous vraiment supprimer ce match ?");
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (matchDAO.supprimerMatch(selected.getId())) {
                showAlert("Succès", "Match supprimé", Alert.AlertType.INFORMATION);
                chargerMatchs();
                chargerStatistiques();
            } else {
                showAlert("Erreur", "Erreur lors de la suppression", Alert.AlertType.ERROR);
            }
        }
    }

    private void ouvrirFormulaireMatch(Match match) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/form-match.fxml"));
            VBox root = loader.load();
            
            FormMatchController controller = loader.getController();
            if (match != null) controller.setMatch(match);
            controller.setOnSaveCallback(() -> {
                chargerMatchs();
                chargerStatistiques();
            });
            
            Stage stage = new Stage();
            stage.setTitle(match == null ? "Ajouter un match" : "Modifier un match");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("❌ Erreur : " + e.getMessage());
            showAlert("Erreur", "Impossible d'ouvrir le formulaire", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void deconnecter() {
        try {
            // Garder les dimensions actuelles AVANT de changer la scène
            Stage stage = (Stage) contentPane.getScene().getWindow();
            double width = stage.getWidth();
            double height = stage.getHeight();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            javafx.scene.Parent root = loader.load();

            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.setTitle("Connexion - Coupe du Monde 2030");
        } catch (IOException e) {
            System.err.println("❌ Erreur : " + e.getMessage());
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Optional<ButtonType> showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }
}
