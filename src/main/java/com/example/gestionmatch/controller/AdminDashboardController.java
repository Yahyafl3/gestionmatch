package com.example.gestionmatch.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.gestionmatch.dao.EquipeDAO;
import com.example.gestionmatch.dao.GroupeDAO;
import com.example.gestionmatch.dao.GroupeEquipeDAO;
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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

@SuppressWarnings("unused")
public class AdminDashboardController implements Initializable {

    @FXML private BorderPane rootPane;
    @FXML private StackPane contentPane;
    @FXML private VBox dashboardView, joueursView, equipesView, stadesView, groupesView, matchsView;
    
    @FXML private Label statJoueurs, statEquipes, statMatchs, statStades, statGroupes, statButs, pageTitle, dateLabel, timeLabel;
    @FXML private Label statTaux, statMatchsSemaine, statEquipesQualif;
    @FXML private VBox top5Equipes, prochainsMatchsList;
    @FXML private ProgressBar progressTaux;
    @FXML private VBox barGroupes;
    
    @FXML private Button btnDashboard, btnJoueurs, btnEquipes, btnStades, btnGroupes, btnMatchs;
    @FXML private Button btnStatistiques, btnCalendrier, btnComparaison, btnNotifications;
    
    @FXML private TextField searchFieldJoueurs;
    @FXML private Label notificationBadge;
    
    @FXML private TableView<Joueur> tableJoueurs;
    @FXML private TableView<Equipe> tableEquipes;
    @FXML private TableView<Stade> tableStades;
    @FXML private TableView<Groupe> tableGroupes;
    @FXML private TableView<Match> tableMatchs;
    @FXML private GridPane groupesGrid;

    private final JoueurDAO joueurDAO = new JoueurDAO();
    private final EquipeDAO equipeDAO = new EquipeDAO();
    private final StadeDAO stadeDAO = new StadeDAO();
    private final GroupeDAO groupeDAO = new GroupeDAO();
    private final MatchDAO matchDAO = new MatchDAO();
    private final GroupeEquipeDAO groupeEquipeDAO = new GroupeEquipeDAO();

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
        cacherToutesVues();
        groupesView.setVisible(true);
        pageTitle.setText("Gestion des Groupes");
        activerBoutonMenu(btnGroupes);
        chargerGroupes();
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
        // S'assurer que toutes les vues originales sont dans le contentPane
        if (!contentPane.getChildren().contains(dashboardView)) {
            contentPane.getChildren().clear();
            contentPane.getChildren().addAll(dashboardView, joueursView, equipesView, stadesView, groupesView, matchsView);
        }
        
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
        int nbJoueurs = joueurDAO.getAllJoueurs().size();
        int nbEquipes = equipeDAO.getAllEquipes().size();
        int nbMatchs = matchDAO.getAllMatchs().size();
        int nbStades = stadeDAO.getAllStades().size();
        int nbGroupes = groupeDAO.getAllGroupes().size();
        
        statJoueurs.setText(String.valueOf(nbJoueurs));
        statEquipes.setText(String.valueOf(nbEquipes));
        statMatchs.setText(String.valueOf(nbMatchs));
        statStades.setText(String.valueOf(nbStades));
        statGroupes.setText(String.valueOf(nbGroupes));
        
        // Calculer le total des buts
        int totalButs = equipeDAO.getAllEquipes().stream()
            .mapToInt(Equipe::getButsMarques)
            .sum();
        statButs.setText(String.valueOf(totalButs));
        
        // Mini-cartes statistiques avec graphiques
        if (statTaux != null) {
            double taux = nbEquipes > 0 ? (nbEquipes * 100.0 / 32.0) : 0;
            statTaux.setText(String.format("%.0f%%", taux));
            
            // Mettre à jour la ProgressBar
            if (progressTaux != null) {
                progressTaux.setProgress(taux / 100.0);
            }
        }
        
        // Mettre à jour la barre des groupes
        if (barGroupes != null && nbGroupes > 0) {
            double widthPerGroup = 20.0; // 160px total / 8 groupes = 20px par groupe
            barGroupes.setPrefWidth(nbGroupes * widthPerGroup);
        }
        
        // Charger les graphiques
        chargerTop5Equipes();
        chargerProchainsMatchs();
    }
    
    
    private void chargerTop5Equipes() {
        if (top5Equipes == null) return;
        
        top5Equipes.getChildren().clear();
        
        // Récupérer les équipes triées par points
        java.util.List<Equipe> equipes = equipeDAO.getAllEquipes();
        equipes.sort((e1, e2) -> Integer.compare(e2.getPoints(), e1.getPoints()));
        
        for (Equipe equipe : equipes.stream().limit(5).collect(java.util.stream.Collectors.toList())) {
            HBox equipeRow = new HBox(14);
            equipeRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            equipeRow.getStyleClass().add("player-card");
            
            // Drapeau avec ombre
            if (equipe.getImagePath() != null && !equipe.getImagePath().isEmpty()) {
                try {
                    String cheminImage = "/images/equipes/" + equipe.getImagePath();
                    java.io.InputStream stream = getClass().getResourceAsStream(cheminImage);
                    
                    if (stream != null) {
                        javafx.scene.image.Image image = new javafx.scene.image.Image(stream);
                        if (!image.isError()) {
                            ImageView flag = new ImageView(image);
                            flag.setFitWidth(36);
                            flag.setFitHeight(27);
                            flag.setPreserveRatio(true);
                            flag.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 6, 0, 0, 3);");
                            equipeRow.getChildren().add(flag);
                        }
                    }
                } catch (Exception e) {
                    // Ignorer
                }
            }
            
            // Nom équipe
            Label nomLabel = new Label(equipe.getNom());
            nomLabel.setStyle("-fx-text-fill: #0f172a; -fx-font-size: 14px; -fx-font-weight: 600;");
            HBox.setHgrow(nomLabel, javafx.scene.layout.Priority.ALWAYS);
            
            // Points avec badge vert
            Label pointsLabel = new Label(equipe.getPoints() + " pts");
            pointsLabel.getStyleClass().add("badge-green");
            pointsLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 700;");
            
            equipeRow.getChildren().addAll(nomLabel, pointsLabel);
            top5Equipes.getChildren().add(equipeRow);
        }
        
        if (equipes.isEmpty()) {
            Label message = new Label("Aucune équipe enregistrée");
            message.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 13px; -fx-padding: 25;");
            top5Equipes.getChildren().add(message);
        }
    }
    
    private void chargerProchainsMatchs() {
        if (prochainsMatchsList == null) return;
        
        prochainsMatchsList.getChildren().clear();
        
        // Récupérer les 3 prochains matchs
        java.util.List<Match> tousLesMatchs = matchDAO.getAllMatchs();
        java.time.LocalDate aujourdhui = java.time.LocalDate.now();
        
        java.util.List<Match> prochains = tousLesMatchs.stream()
            .filter(m -> m.getDateMatch() != null && !m.getDateMatch().isBefore(aujourdhui))
            .sorted((m1, m2) -> m1.getDateMatch().compareTo(m2.getDateMatch()))
            .limit(3)
            .collect(java.util.stream.Collectors.toList());
        
        for (Match match : prochains) {
            try {
                String dateFormatee = match.getDateMatch().format(
                    java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy", java.util.Locale.FRENCH));
                
                VBox matchBox = new VBox(12);
                matchBox.getStyleClass().add("match-card");
                
                // HBox pour les équipes et VS
                HBox equipesBox = new HBox(18);
                equipesBox.setAlignment(javafx.geometry.Pos.CENTER);
                
                // Parser le nom du match pour extraire les équipes (ex: "Maroc vs Brésil")
                String nomMatch = match.getNom();
                String[] equipes = null;
                
                if (nomMatch != null && (nomMatch.contains(" vs ") || nomMatch.contains(" VS "))) {
                    equipes = nomMatch.split(" vs | VS ");
                }
                
                // Si on a des équipes dans equipeA et equipeB, les utiliser
                if (match.getEquipeA() != null && !match.getEquipeA().isEmpty()) {
                    equipes = new String[]{match.getEquipeA(), match.getEquipeB()};
                }
                
                if (equipes != null && equipes.length >= 2) {
                    // Équipe A avec drapeau
                    String nomEquipeA = equipes[0].trim();
                    Equipe equipeA = equipeDAO.getEquipeByNom(nomEquipeA);
                    
                    if (equipeA != null && equipeA.getImagePath() != null && !equipeA.getImagePath().isEmpty()) {
                        try {
                            String cheminImage = "/images/equipes/" + equipeA.getImagePath();
                            java.io.InputStream stream = getClass().getResourceAsStream(cheminImage);
                            if (stream != null) {
                                ImageView drapeauA = new ImageView(new Image(stream));
                                drapeauA.setFitWidth(45);
                                drapeauA.setFitHeight(34);
                                drapeauA.setPreserveRatio(true);
                                drapeauA.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 5, 0, 0, 2);");
                                equipesBox.getChildren().add(drapeauA);
                            } else {
                                Label nomA = new Label(nomEquipeA);
                                nomA.setStyle("-fx-text-fill: #1e293b; -fx-font-size: 12px; -fx-font-weight: 600;");
                                equipesBox.getChildren().add(nomA);
                            }
                        } catch (Exception e) {
                            Label nomA = new Label(nomEquipeA);
                            nomA.setStyle("-fx-text-fill: #1e293b; -fx-font-size: 12px; -fx-font-weight: 600;");
                            equipesBox.getChildren().add(nomA);
                        }
                    } else {
                        Label nomA = new Label(nomEquipeA);
                        nomA.setStyle("-fx-text-fill: #1e293b; -fx-font-size: 12px; -fx-font-weight: 600;");
                        equipesBox.getChildren().add(nomA);
                    }
                    
                    // VS avec style amélioré
                    Label vsLabel = new Label("VS");
                    vsLabel.setStyle("-fx-text-fill: #C1272D; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #fee2e2; -fx-padding: 8 14; -fx-background-radius: 10;");
                    equipesBox.getChildren().add(vsLabel);
                    
                    // Équipe B avec drapeau
                    String nomEquipeB = equipes[1].trim();
                    Equipe equipeB = equipeDAO.getEquipeByNom(nomEquipeB);
                    
                    if (equipeB != null && equipeB.getImagePath() != null && !equipeB.getImagePath().isEmpty()) {
                        try {
                            String cheminImage = "/images/equipes/" + equipeB.getImagePath();
                            java.io.InputStream stream = getClass().getResourceAsStream(cheminImage);
                            if (stream != null) {
                                ImageView drapeauB = new ImageView(new Image(stream));
                                drapeauB.setFitWidth(50);
                                drapeauB.setFitHeight(38);
                                drapeauB.setPreserveRatio(true);
                                drapeauB.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 3);");
                                equipesBox.getChildren().add(drapeauB);
                            } else {
                                Label nomB = new Label(nomEquipeB);
                                nomB.setStyle("-fx-text-fill: #1e293b; -fx-font-size: 12px; -fx-font-weight: 600;");
                                equipesBox.getChildren().add(nomB);
                            }
                        } catch (Exception e) {
                            Label nomB = new Label(nomEquipeB);
                            nomB.setStyle("-fx-text-fill: #1e293b; -fx-font-size: 12px; -fx-font-weight: 600;");
                            equipesBox.getChildren().add(nomB);
                        }
                    } else {
                        Label nomB = new Label(nomEquipeB);
                        nomB.setStyle("-fx-text-fill: #1e293b; -fx-font-size: 12px; -fx-font-weight: 600;");
                        equipesBox.getChildren().add(nomB);
                    }
                } else {
                    // Si on ne peut pas parser, afficher le nom du match
                    Label nomLabel = new Label(nomMatch);
                    nomLabel.setStyle("-fx-text-fill: #1e293b; -fx-font-size: 13px; -fx-font-weight: 600;");
                    equipesBox.getChildren().add(nomLabel);
                }
                
                // Lieu avec style amélioré
                String lieu = match.getLieu();
                if (lieu == null || lieu.trim().isEmpty()) {
                    lieu = "Aucun stade";
                }
                Label lieuLabel = new Label("📍 " + lieu);
                lieuLabel.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px; -fx-alignment: center; -fx-font-weight: 500;");
                lieuLabel.setMaxWidth(Double.MAX_VALUE);
                lieuLabel.setAlignment(javafx.geometry.Pos.CENTER);
                
                // Date avec badge rouge moderne amélioré
                HBox dateContainer = new HBox();
                dateContainer.setAlignment(javafx.geometry.Pos.CENTER);
                Label dateLabel = new Label(dateFormatee);
                dateLabel.getStyleClass().add("badge-red");
                dateLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 700;");
                dateContainer.getChildren().add(dateLabel);
                
                matchBox.getChildren().addAll(equipesBox, lieuLabel, dateContainer);
                prochainsMatchsList.getChildren().add(matchBox);
                
            } catch (Exception e) {
                System.err.println("Erreur affichage match: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        if (prochains.isEmpty()) {
            Label aucunMatch = new Label("Aucun match à venir");
            aucunMatch.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 13px; -fx-padding: 25; -fx-alignment: center;");
            prochainsMatchsList.getChildren().add(aucunMatch);
        }
    }
    
    @SuppressWarnings("unchecked")
    private void initialiserTablesJoueurs() {
        TableColumn<Joueur, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(130);
        colNom.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        TableColumn<Joueur, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colPrenom.setPrefWidth(130);
        colPrenom.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        TableColumn<Joueur, Integer> colNumero = new TableColumn<>("N°");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colNumero.setPrefWidth(50);
        colNumero.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: CENTER;");

        TableColumn<Joueur, String> colPoste = new TableColumn<>("Poste");
        colPoste.setCellValueFactory(new PropertyValueFactory<>("poste"));
        colPoste.setPrefWidth(110);
        colPoste.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        TableColumn<Joueur, String> colNationalite = new TableColumn<>("Nationalité");
        colNationalite.setCellValueFactory(new PropertyValueFactory<>("nationalite"));
        colNationalite.setPrefWidth(130);
        colNationalite.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        tableJoueurs.getColumns().clear();
        tableJoueurs.getColumns().addAll(colNom, colPrenom, colNumero, colPoste, colNationalite);
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
            ScrollPane root = loader.load();
            
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
        // Colonne Nom avec drapeau
        TableColumn<Equipe, String> colNom = new TableColumn<>("Équipe");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(250);
        colNom.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        colNom.setCellFactory(column -> new TableCell<Equipe, String>() {
            private final ImageView imageView = new ImageView();
            private final Label label = new Label();
            private final HBox hbox = new HBox(10);

            {
                imageView.setFitWidth(32);
                imageView.setFitHeight(24);
                imageView.setPreserveRatio(true);
                hbox.getChildren().addAll(imageView, label);
                hbox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
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
                        javafx.scene.image.Image image = null;
                        
                        try {
                            // Méthode 1: Essayer depuis le classpath (target/classes - prioritaire)
                            String cheminImage = "/images/equipes/" + equipe.getImagePath();
                            java.io.InputStream stream = getClass().getResourceAsStream(cheminImage);
                            if (stream != null) {
                                image = new javafx.scene.image.Image(stream, 32, 24, true, true);
                                if (!image.isError()) {
                                    imageView.setImage(image);
                                    setGraphic(hbox);
                                    return;
                                }
                            }
                        } catch (Exception e) {
                            // Ignorer et essayer la méthode suivante
                        }
                        
                        try {
                            // Méthode 2: Essayer depuis le système de fichiers (fallback)
                            java.io.File imageFile = new java.io.File("target/classes/images/equipes/" + equipe.getImagePath());
                            if (imageFile.exists()) {
                                image = new javafx.scene.image.Image(imageFile.toURI().toString(), 32, 24, true, true);
                                if (!image.isError()) {
                                    imageView.setImage(image);
                                    setGraphic(hbox);
                                    return;
                                }
                            }
                        } catch (Exception e) {
                            // Ignorer
                        }
                        
                        // Si aucune méthode n'a fonctionné, pas d'image
                        imageView.setImage(null);
                    } else {
                        imageView.setImage(null);
                    }

                    setGraphic(hbox);
                }
            }
        });

        TableColumn<Equipe, String> colPays = new TableColumn<>("Pays");
        colPays.setCellValueFactory(new PropertyValueFactory<>("pays"));
        colPays.setPrefWidth(150);
        colPays.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        TableColumn<Equipe, String> colEntraineur = new TableColumn<>("Entraîneur");
        colEntraineur.setCellValueFactory(new PropertyValueFactory<>("entraineur"));
        colEntraineur.setPrefWidth(180);
        colEntraineur.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        TableColumn<Equipe, Integer> colNombreJoueurs = new TableColumn<>("Joueurs");
        colNombreJoueurs.setCellValueFactory(new PropertyValueFactory<>("nombreJoueurs"));
        colNombreJoueurs.setPrefWidth(90);
        colNombreJoueurs.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: CENTER;");

        TableColumn<Equipe, Integer> colMatchsJoues = new TableColumn<>("MJ");
        colMatchsJoues.setCellValueFactory(new PropertyValueFactory<>("matchsJoues"));
        colMatchsJoues.setPrefWidth(60);
        colMatchsJoues.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: CENTER;");

        TableColumn<Equipe, Integer> colVictoires = new TableColumn<>("V");
        colVictoires.setCellValueFactory(new PropertyValueFactory<>("victoires"));
        colVictoires.setPrefWidth(50);
        colVictoires.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: CENTER;");

        TableColumn<Equipe, Integer> colNuls = new TableColumn<>("N");
        colNuls.setCellValueFactory(new PropertyValueFactory<>("nuls"));
        colNuls.setPrefWidth(50);
        colNuls.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: CENTER;");

        TableColumn<Equipe, Integer> colDefaites = new TableColumn<>("D");
        colDefaites.setCellValueFactory(new PropertyValueFactory<>("defaites"));
        colDefaites.setPrefWidth(50);
        colDefaites.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: CENTER;");

        TableColumn<Equipe, Integer> colButsMarques = new TableColumn<>("BP");
        colButsMarques.setCellValueFactory(new PropertyValueFactory<>("butsMarques"));
        colButsMarques.setPrefWidth(60);
        colButsMarques.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: CENTER;");

        TableColumn<Equipe, Integer> colButsEncaisses = new TableColumn<>("BC");
        colButsEncaisses.setCellValueFactory(new PropertyValueFactory<>("butsEncaisses"));
        colButsEncaisses.setPrefWidth(60);
        colButsEncaisses.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: CENTER;");

        tableEquipes.getColumns().clear();
        tableEquipes.getColumns().addAll(colNom, colPays, colEntraineur, colNombreJoueurs,
                colMatchsJoues, colVictoires, colNuls, colDefaites, 
                colButsMarques, colButsEncaisses);
        
        // Appliquer le style en gras pour toutes les cellules
        tableEquipes.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
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
                // Rafraîchir le tableau pour afficher les nouvelles images
                tableEquipes.refresh();
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
        tableStades.getColumns().addAll(colNom, colVille, colCapacite);
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
            ScrollPane root = loader.load();
            
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
        // Méthode conservée pour compatibilité mais non utilisée
    }

    private void chargerGroupes() {
        if (groupesGrid == null) {
            return;
        }
        
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
        card.setStyle("-fx-background-color: white; " +
                     "-fx-background-radius: 16; " +
                     "-fx-padding: 20; " +
                     "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 8); " +
                     "-fx-border-color: #e5e7eb; " +
                     "-fx-border-width: 2; " +
                     "-fx-border-radius: 16;");
        card.setPrefWidth(280);
        card.setMinHeight(400);
        
        // En-tête rouge
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #C1272D; " +
                       "-fx-background-radius: 12; " +
                       "-fx-padding: 18;");
        
        Label nomGroupe = new Label(groupe.getNom());
        nomGroupe.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
        header.getChildren().add(nomGroupe);
        
        // Zone scrollable pour les équipes
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(220);
        scrollPane.setMaxHeight(220);
        
        VBox equipesBox = new VBox(12);
        equipesBox.setStyle("-fx-padding: 5;");
        
        // Charger les équipes du groupe
        List<Equipe> equipes = groupeEquipeDAO.getEquipesDuGroupe(groupe.getId());
        
        if (equipes.isEmpty()) {
            Label aucuneEquipe = new Label("Aucune équipe");
            aucuneEquipe.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 14px; " +
                                 "-fx-font-style: italic; -fx-padding: 20; -fx-alignment: center;");
            equipesBox.getChildren().add(aucuneEquipe);
        } else {
            for (Equipe equipe : equipes) {
                HBox equipeRow = creerLigneEquipe(equipe, groupe);
                equipesBox.getChildren().add(equipeRow);
            }
        }
        
        scrollPane.setContent(equipesBox);
        
        // Compteur
        Label compteur = new Label(equipes.size() + " / 4 équipes");
        compteur.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px; -fx-font-weight: 600;");
        
        // Boutons d'action
        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER);
        
        Button btnAjouter = new Button("➕ Ajouter");
        btnAjouter.setStyle("-fx-background-color: #C1272D; -fx-text-fill: white; " +
                           "-fx-font-size: 13px; -fx-padding: 12 20; -fx-background-radius: 8; " +
                           "-fx-cursor: hand; -fx-font-weight: 600;");
        btnAjouter.setOnAction(e -> ajouterEquipeAuGroupe(groupe));
        
        Button btnModifier = new Button("✏️");
        btnModifier.setStyle("-fx-background-color: #1e293b; -fx-text-fill: white; " +
                            "-fx-font-size: 13px; -fx-padding: 12 16; -fx-background-radius: 8; " +
                            "-fx-cursor: hand;");
        btnModifier.setOnAction(e -> modifierGroupe(groupe));
        
        Button btnSupprimer = new Button("🗑️");
        btnSupprimer.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; " +
                             "-fx-font-size: 13px; -fx-padding: 12 16; -fx-background-radius: 8; " +
                             "-fx-cursor: hand;");
        btnSupprimer.setOnAction(e -> supprimerGroupe(groupe));
        
        actions.getChildren().addAll(btnAjouter, btnModifier, btnSupprimer);
        
        card.getChildren().addAll(header, scrollPane, compteur, actions);
        
        return card;
    }
    
    private HBox creerLigneEquipe(Equipe equipe, Groupe groupe) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #f8f9fa; " +
                    "-fx-background-radius: 8; " +
                    "-fx-padding: 10;");
        
        // Drapeau AVANT le nom - Méthode robuste avec vérification du stream
        if (equipe.getImagePath() != null && !equipe.getImagePath().isEmpty()) {
            try {
                // Construire le chemin de l'image
                String cheminImage = "/images/equipes/" + equipe.getImagePath();
                
                // Charger le stream AVANT de créer l'image
                java.io.InputStream stream = getClass().getResourceAsStream(cheminImage);
                
                // Vérifier si le stream existe
                if (stream != null) {
                    javafx.scene.image.Image image = new javafx.scene.image.Image(stream);
                    
                    // Vérifier si l'image est chargée correctement
                    if (!image.isError() && image.getWidth() > 0) {
                        ImageView flag = new ImageView(image);
                        flag.setFitWidth(32);
                        flag.setFitHeight(24);
                        flag.setPreserveRatio(true);
                        flag.setSmooth(true);
                        flag.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0, 0, 1);");
                        row.getChildren().add(flag);
                    } else {
                        // Si erreur de chargement, afficher emoji
                        Label emoji = new Label("🏴");
                        emoji.setStyle("-fx-font-size: 20px;");
                        row.getChildren().add(emoji);
                    }
                } else {
                    // Stream null = fichier introuvable, afficher emoji
                    Label emoji = new Label("🏴");
                    emoji.setStyle("-fx-font-size: 20px;");
                    row.getChildren().add(emoji);
                }
            } catch (Exception e) {
                // En cas d'erreur, afficher emoji
                Label emoji = new Label("🏴");
                emoji.setStyle("-fx-font-size: 20px;");
                row.getChildren().add(emoji);
            }
        } else {
            // Pas d'image définie, afficher emoji
            Label emoji = new Label("🏴");
            emoji.setStyle("-fx-font-size: 20px;");
            row.getChildren().add(emoji);
        }
        
        // Nom de l'équipe
        Label nomEquipe = new Label(equipe.getNom());
        nomEquipe.setStyle("-fx-text-fill: #1e293b; -fx-font-size: 14px; -fx-font-weight: 600;");
        row.getChildren().add(nomEquipe);
        
        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        row.getChildren().add(spacer);
        
        // Bouton retirer
        Button btnRetirer = new Button("✖");
        btnRetirer.setStyle("-fx-background-color: #C1272D; -fx-text-fill: white; " +
                           "-fx-font-size: 11px; -fx-padding: 6 10; -fx-background-radius: 6; " +
                           "-fx-cursor: hand; -fx-font-weight: bold;");
        btnRetirer.setOnAction(e -> retirerEquipeDuGroupe(equipe, groupe));
        row.getChildren().add(btnRetirer);
        
        return row;
    }

    @FXML
    private void creerGroupe() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Créer un groupe");
        dialog.setHeaderText("Nouveau groupe");
        dialog.setContentText("Nom du groupe (ex: Groupe A):");
        
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nom -> {
            if (nom.trim().isEmpty()) {
                showAlert("Erreur", "Le nom du groupe ne peut pas être vide", Alert.AlertType.ERROR);
                return;
            }
            
            Groupe groupe = new Groupe();
            groupe.setNom(nom.trim());
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
        int nbEquipes = groupeEquipeDAO.compterEquipesDansGroupe(groupe.getId());
        if (nbEquipes >= 4) {
            showAlert("Limite atteinte", "Un groupe ne peut contenir que 4 équipes maximum", Alert.AlertType.WARNING);
            return;
        }
        
        List<Equipe> toutesEquipes = equipeDAO.getAllEquipes();
        List<Equipe> equipesDisponibles = new ArrayList<>();
        
        for (Equipe equipe : toutesEquipes) {
            if (!groupeEquipeDAO.equipeEstDansGroupe(groupe.getId(), equipe.getId())) {
                equipesDisponibles.add(equipe);
            }
        }
        
        if (equipesDisponibles.isEmpty()) {
            showAlert("Aucune équipe disponible", "Toutes les équipes sont déjà assignées", Alert.AlertType.INFORMATION);
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
            if (nom.trim().isEmpty()) {
                showAlert("Erreur", "Le nom du groupe ne peut pas être vide", Alert.AlertType.ERROR);
                return;
            }
            
            groupe.setNom(nom.trim());
            if (groupeDAO.modifierGroupe(groupe)) {
                showAlert("Succès", "Groupe modifié avec succès", Alert.AlertType.INFORMATION);
                chargerGroupes();
            } else {
                showAlert("Erreur", "Erreur lors de la modification", Alert.AlertType.ERROR);
            }
        });
    }
    
    private void supprimerGroupe(Groupe groupe) {
        int nbEquipes = groupeEquipeDAO.compterEquipesDansGroupe(groupe.getId());
        if (nbEquipes > 0) {
            showAlert("Impossible de supprimer", 
                     "Le groupe contient " + nbEquipes + " équipe(s). Retirez d'abord toutes les équipes.", 
                     Alert.AlertType.WARNING);
            return;
        }
        
        Optional<ButtonType> result = showConfirmation("Confirmation", 
            "Voulez-vous vraiment supprimer le " + groupe.getNom() + " ?");
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (groupeDAO.supprimerGroupe(groupe.getId())) {
                showAlert("Succès", "Groupe supprimé", Alert.AlertType.INFORMATION);
                chargerGroupes();
            } else {
                showAlert("Erreur", "Erreur lors de la suppression", Alert.AlertType.ERROR);
            }
        }
    }
    
    private void retirerEquipeDuGroupe(Equipe equipe, Groupe groupe) {
        Optional<ButtonType> result = showConfirmation("Confirmation", 
            "Retirer " + equipe.getNom() + " du " + groupe.getNom() + " ?");
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (groupeEquipeDAO.retirerEquipeDuGroupe(groupe.getId(), equipe.getId())) {
                showAlert("Succès", equipe.getNom() + " retirée du " + groupe.getNom(), Alert.AlertType.INFORMATION);
                chargerGroupes();
            } else {
                showAlert("Erreur", "Impossible de retirer l'équipe", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void ajouterGroupe() {
        creerGroupe();
    }

    @FXML
    private void modifierGroupe() {
        showAlert("Information", "Cliquez sur le bouton ✏️ d'une carte de groupe pour le modifier", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void supprimerGroupe() {
        showAlert("Information", "Cliquez sur le bouton 🗑️ d'une carte de groupe pour le supprimer", Alert.AlertType.INFORMATION);
    }

    @SuppressWarnings("unchecked")
    private void initialiserTablesMatchs() {
        TableColumn<Match, String> colNom = new TableColumn<>("Match");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(250);

        TableColumn<Match, java.time.LocalDate> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateMatch"));
        colDate.setPrefWidth(120);

        TableColumn<Match, String> colLieu = new TableColumn<>("Stade");
        colLieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        colLieu.setPrefWidth(180);

        tableMatchs.getColumns().clear();
        tableMatchs.getColumns().addAll(colNom, colDate, colLieu);
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
    
    // ========================================
    // NOUVELLES PAGES
    // ========================================
    
    @FXML
    private void afficherStatistiques() {
        chargerPageDynamique("/fxml/statistics-page.fxml", "Statistiques Avancées");
        activerBoutonMenu(btnStatistiques);
    }
    
    @FXML
    private void afficherCalendrier() {
        chargerPageDynamique("/fxml/calendar-page.fxml", "Calendrier des Matchs");
        activerBoutonMenu(btnCalendrier);
    }
    
    @FXML
    private void afficherComparaison() {
        chargerPageDynamique("/fxml/team-comparison.fxml", "Comparaison d'Équipes");
        activerBoutonMenu(btnComparaison);
    }
    
    @FXML
    private void afficherNotifications() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notifications");
        alert.setHeaderText("Centre de Notifications");
        alert.setContentText("🔔 3 nouvelles notifications:\n\n" +
                           "⚽ Match à venir: Maroc vs Brésil demain\n" +
                           "👤 5 nouveaux joueurs ajoutés\n" +
                           "📊 Statistiques mises à jour");
        alert.showAndWait();
        
        // Réinitialiser le badge
        if (notificationBadge != null) {
            notificationBadge.setText("0");
            notificationBadge.setVisible(false);
        }
    }
    
    private void chargerPageDynamique(String fxmlPath, String titre) {
        try {
            cacherToutesVues();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            javafx.scene.Parent page = loader.load();
            
            // Remplacer le contenu
            contentPane.getChildren().clear();
            contentPane.getChildren().add(page);
            
            pageTitle.setText(titre);
            
            System.out.println("✅ Page chargée: " + titre);
        } catch (IOException e) {
            System.err.println("❌ Erreur chargement page: " + e.getMessage());
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la page: " + titre, Alert.AlertType.ERROR);
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
