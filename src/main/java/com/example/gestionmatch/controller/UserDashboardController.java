package com.example.gestionmatch.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.example.gestionmatch.dao.EquipeDAO;
import com.example.gestionmatch.dao.FavorisDAO;
import com.example.gestionmatch.dao.GroupeDAO;
import com.example.gestionmatch.dao.JoueurDAO;
import com.example.gestionmatch.dao.MatchDAO;
import com.example.gestionmatch.dao.StadeDAO;
import com.example.gestionmatch.dao.UtilisateurDAO;
import com.example.gestionmatch.model.Equipe;
import com.example.gestionmatch.model.Groupe;
import com.example.gestionmatch.model.Joueur;
import com.example.gestionmatch.model.Match;
import com.example.gestionmatch.model.Stade;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Contrôleur pour le tableau de bord utilisateur avec toutes les fonctionnalités
 */
@SuppressWarnings("unused")
public class UserDashboardController {

    // DAOs
    private final JoueurDAO joueurDAO = new JoueurDAO();
    private final EquipeDAO equipeDAO = new EquipeDAO();
    private final StadeDAO stadeDAO = new StadeDAO();
    private final GroupeDAO groupeDAO = new GroupeDAO();
    private final MatchDAO matchDAO = new MatchDAO();
    private final FavorisDAO favorisDAO = new FavorisDAO();
    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    // UI Components
    @FXML private BorderPane rootPane;
    @FXML private StackPane contentPane;
    @FXML private Label pageTitle;
    @FXML private Label dateLabel;
    @FXML private Label timeLabel;
    
    // Vues
    @FXML private VBox accueilView;
    @FXML private VBox equipesView;
    @FXML private VBox matchsView;
    @FXML private VBox groupesView;
    @FXML private VBox joueursView;
    @FXML private VBox stadesView;
    @FXML private VBox favorisView;
    @FXML private VBox profilView;
    
    // Statistiques
    @FXML private Label statEquipes;
    @FXML private Label statMatchs;
    @FXML private Label statStades;
    @FXML private Label statJoueurs;
    
    // Tables
    @FXML private TableView<Joueur> tableJoueurs;
    @FXML private TableView<Equipe> tableEquipes;
    @FXML private TableView<Stade> tableStades;
    @FXML private TableView<Match> tableMatchs;
    @FXML private TableView<Equipe> tableFavoris;
    
    // Champs de recherche
    @FXML private TextField searchJoueur;
    @FXML private TextField searchEquipe;
    @FXML private TextField searchStade;
    
    // Filtres
    @FXML private ComboBox<String> filterEquipeJoueur;
    @FXML private ComboBox<String> filterPosteJoueur;
    @FXML private ComboBox<String> filterGroupeEquipe;
    @FXML private ComboBox<String> filterPaysStade;
    @FXML private ComboBox<String> filterEquipeMatch;
    @FXML private ComboBox<String> filterGroupeMatch;
    @FXML private ComboBox<String> filterStadeMatch;
    
    // Profil
    @FXML private Label profilEmail;
    @FXML private Label profilNom;
    
    // Boutons menu
    @FXML private Button btnAccueil;
    @FXML private Button btnEquipes;
    @FXML private Button btnMatchs;
    @FXML private Button btnGroupes;
    @FXML private Button btnJoueurs;
    @FXML private Button btnStades;
    @FXML private Button btnFavoris;
    @FXML private Button btnProfil;
    @FXML private Button btnStatistiques;
    @FXML private Button btnCalendrier;
    @FXML private Button btnComparaison;
    @FXML private Button btnNotifications;
    
    @FXML private Label notificationBadge;

    // Données
    private final ObservableList<Joueur> tousLesJoueurs = FXCollections.observableArrayList();
    private final ObservableList<Equipe> toutesLesEquipes = FXCollections.observableArrayList();
    private final ObservableList<Stade> tousLesStades = FXCollections.observableArrayList();
    private final ObservableList<Match> tousLesMatchs = FXCollections.observableArrayList();
    private FilteredList<Joueur> joueursFilteres;
    private FilteredList<Equipe> equipesFilteres;
    private FilteredList<Stade> stadesFilteres;
    private FilteredList<Match> matchsFilteres;
    
    private String username;
    private String userEmail;
    private int utilisateurId = 0; // ID de l'utilisateur connecté

    /**
     * Initialise le contrôleur
     */
    @FXML
    public void initialize() {
        updateDateTime();
        initializeTables();
        chargerStatistiques();
        afficherAccueil(null);
    }

    /**
     * Initialise toutes les tables
     */
    private void initializeTables() {
        initTableJoueurs();
        initTableEquipes();
        initTableStades();
        initTableMatchs();
        initTableFavoris();
    }

    /**
     * Initialise la table des joueurs
     */
    @SuppressWarnings("unchecked")
    private void initTableJoueurs() {
        if (tableJoueurs != null) {
            TableColumn<Joueur, String> colNom = new TableColumn<>("Nom");
            colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            colNom.setPrefWidth(120);
            colNom.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            TableColumn<Joueur, String> colPrenom = new TableColumn<>("Prénom");
            colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            colPrenom.setPrefWidth(120);
            colPrenom.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            TableColumn<Joueur, String> colPoste = new TableColumn<>("Poste");
            colPoste.setCellValueFactory(new PropertyValueFactory<>("poste"));
            colPoste.setPrefWidth(100);
            colPoste.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            TableColumn<Joueur, Integer> colNumero = new TableColumn<>("N°");
            colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
            colNumero.setPrefWidth(50);
            colNumero.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: CENTER;");

            TableColumn<Joueur, String> colNationalite = new TableColumn<>("Nationalité");
            colNationalite.setCellValueFactory(new PropertyValueFactory<>("nationalite"));
            colNationalite.setPrefWidth(120);
            colNationalite.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            TableColumn<Joueur, String> colDateNaissance = new TableColumn<>("Date de Naissance");
            colDateNaissance.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
            colDateNaissance.setPrefWidth(140);
            colDateNaissance.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            tableJoueurs.getColumns().addAll(colNom, colPrenom, colPoste, colNumero, colNationalite, colDateNaissance);
        }
    }

    /**
     * Initialise la table des équipes
     */
    @SuppressWarnings("unchecked")
    private void initTableEquipes() {
        if (tableEquipes != null) {
            // Colonne Équipe avec drapeau
            TableColumn<Equipe, String> colEquipe = new TableColumn<>("Équipe");
            colEquipe.setPrefWidth(250);
            colEquipe.setCellFactory(param -> new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        Equipe equipe = getTableRow().getItem();
                        HBox box = new HBox(10);
                        box.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                        
                        // Utiliser l'image du drapeau
                        javafx.scene.Node drapeau = getDrapeauImage(equipe.getPays(), 32, 24);
                        
                        Label nom = new Label(equipe.getNom());
                        nom.setStyle("-fx-font-size: 14px; -fx-font-weight: 600;");
                        
                        box.getChildren().addAll(drapeau, nom);
                        setGraphic(box);
                        setText(null);
                    }
                }
            });

            TableColumn<Equipe, String> colEntraineur = new TableColumn<>("Entraîneur");
            colEntraineur.setCellValueFactory(new PropertyValueFactory<>("entraineur"));
            colEntraineur.setPrefWidth(180);

            TableColumn<Equipe, Integer> colPoints = new TableColumn<>("Points");
            colPoints.setCellValueFactory(new PropertyValueFactory<>("points"));
            colPoints.setPrefWidth(80);
            colPoints.setStyle("-fx-alignment: CENTER;");

            tableEquipes.getColumns().addAll(colEquipe, colEntraineur, colPoints);
        }
    }

    /**
     * Initialise la table des stades
     */
    @SuppressWarnings("unchecked")
    private void initTableStades() {
        if (tableStades != null) {
            TableColumn<Stade, String> colNom = new TableColumn<>("Stade");
            colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            colNom.setPrefWidth(250);

            TableColumn<Stade, String> colVille = new TableColumn<>("Ville");
            colVille.setCellValueFactory(new PropertyValueFactory<>("ville"));
            colVille.setPrefWidth(150);

            TableColumn<Stade, String> colPays = new TableColumn<>("Pays");
            colPays.setCellValueFactory(new PropertyValueFactory<>("pays"));
            colPays.setPrefWidth(150);

            TableColumn<Stade, Integer> colCapacite = new TableColumn<>("Capacité");
            colCapacite.setCellValueFactory(new PropertyValueFactory<>("capacite"));
            colCapacite.setPrefWidth(100);

            tableStades.getColumns().addAll(colNom, colVille, colPays, colCapacite);
        }
    }

    /**
     * Initialise la table des matchs
     */
    @SuppressWarnings("unchecked")
    private void initTableMatchs() {
        if (tableMatchs != null) {
            // Colonne Match avec drapeaux
            TableColumn<Match, String> colMatch = new TableColumn<>("Match");
            colMatch.setPrefWidth(350);
            colMatch.setCellFactory(param -> new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        Match match = getTableRow().getItem();
                        String nomMatch = match.getNom();
                        
                        HBox box = new HBox(10);
                        box.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                        
                        // Extraire les noms des équipes (format: "Équipe A vs Équipe B")
                        if (nomMatch != null && nomMatch.contains(" vs ")) {
                            String[] equipes = nomMatch.split(" vs ");
                            if (equipes.length == 2) {
                                String equipeA = equipes[0].trim();
                                String equipeB = equipes[1].trim();
                                
                                // Obtenir les pays des équipes
                                String paysA = getPaysParNomEquipe(equipeA);
                                String paysB = getPaysParNomEquipe(equipeB);
                                
                                // Drapeau équipe A (image)
                                javafx.scene.Node drapeauA = getDrapeauImage(paysA, 28, 20);
                                
                                Label nomA = new Label(equipeA);
                                nomA.setStyle("-fx-font-size: 13px; -fx-font-weight: 600;");
                                
                                Label vs = new Label("vs");
                                vs.setStyle("-fx-font-size: 12px; -fx-text-fill: #999; -fx-font-weight: bold;");
                                
                                // Drapeau équipe B (image)
                                javafx.scene.Node drapeauB = getDrapeauImage(paysB, 28, 20);
                                
                                Label nomB = new Label(equipeB);
                                nomB.setStyle("-fx-font-size: 13px; -fx-font-weight: 600;");
                                
                                box.getChildren().addAll(drapeauA, nomA, vs, drapeauB, nomB);
                            } else {
                                Label label = new Label(nomMatch);
                                box.getChildren().add(label);
                            }
                        } else {
                            Label label = new Label(nomMatch);
                            box.getChildren().add(label);
                        }
                        
                        setGraphic(box);
                        setText(null);
                    }
                }
            });

            TableColumn<Match, String> colScore = new TableColumn<>("Score");
            colScore.setCellValueFactory(new PropertyValueFactory<>("score"));
            colScore.setPrefWidth(100);
            colScore.setStyle("-fx-alignment: CENTER;");

            TableColumn<Match, String> colDate = new TableColumn<>("Date");
            colDate.setCellValueFactory(new PropertyValueFactory<>("dateMatch"));
            colDate.setPrefWidth(150);
            colDate.setStyle("-fx-alignment: CENTER;");

            tableMatchs.getColumns().addAll(colMatch, colScore, colDate);
        }
    }

    /**
     * Initialise la table des favoris
     */
    @SuppressWarnings("unchecked")
    private void initTableFavoris() {
        if (tableFavoris != null) {
            // Colonne Équipe avec drapeau
            TableColumn<Equipe, String> colEquipe = new TableColumn<>("Équipe");
            colEquipe.setPrefWidth(300);
            colEquipe.setCellFactory(param -> new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        Equipe equipe = getTableRow().getItem();
                        HBox box = new HBox(10);
                        box.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                        
                        // Utiliser l'image du drapeau
                        javafx.scene.Node drapeau = getDrapeauImage(equipe.getPays(), 32, 24);
                        
                        Label nom = new Label(equipe.getNom());
                        nom.setStyle("-fx-font-size: 14px; -fx-font-weight: 600;");
                        
                        box.getChildren().addAll(drapeau, nom);
                        setGraphic(box);
                        setText(null);
                    }
                }
            });

            TableColumn<Equipe, Void> colActions = new TableColumn<>("Actions");
            colActions.setPrefWidth(150);
            colActions.setCellFactory(param -> new TableCell<>() {
                private final Button btnRetirer = new Button("❌ Retirer");
                {
                    btnRetirer.setOnAction(event -> {
                        Equipe equipe = getTableView().getItems().get(getIndex());
                        retirerDesFavoris(equipe);
                    });
                }
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : btnRetirer);
                }
            });

            tableFavoris.getColumns().addAll(colEquipe, colActions);
        }
    }

    /**
     * Charge les statistiques
     */
    private void chargerStatistiques() {
        try {
            List<Joueur> joueurs = joueurDAO.getAllJoueurs();
            List<Equipe> equipes = equipeDAO.getAllEquipes();
            List<Stade> stades = stadeDAO.getAllStades();
            List<Match> matchs = matchDAO.getAllMatchs();

            if (statJoueurs != null) statJoueurs.setText(String.valueOf(joueurs.size()));
            if (statEquipes != null) statEquipes.setText(String.valueOf(equipes.size()));
            if (statStades != null) statStades.setText(String.valueOf(stades.size()));
            if (statMatchs != null) statMatchs.setText(String.valueOf(matchs.size()));
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des statistiques: " + e.getMessage());
        }
    }

    /**
     * Met à jour la date et l'heure
     */
    private void updateDateTime() {
        LocalDateTime now = LocalDateTime.now();
        if (dateLabel != null) {
            dateLabel.setText(now.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        }
        if (timeLabel != null) {
            timeLabel.setText(now.format(DateTimeFormatter.ofPattern("HH:mm")));
        }
    }

    /**
     * Définit les informations utilisateur
     */
    public void setUsername(String username) {
        this.username = username;
        if (profilNom != null) {
            profilNom.setText(username);
        }
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
        if (profilEmail != null) {
            profilEmail.setText(email);
        }
    }
    
    public void setUtilisateurId(int id) {
        this.utilisateurId = id;
        System.out.println("✅ ID utilisateur défini: " + id);
    }

    // ========================================
    // NAVIGATION
    // ========================================

    @FXML
    private void afficherAccueil(javafx.event.ActionEvent event) {
        cacherToutesLesVues();
        if (accueilView != null) accueilView.setVisible(true);
        if (pageTitle != null) pageTitle.setText("Accueil");
        setActiveButton(btnAccueil);
        chargerStatistiques();
    }

    @FXML
    private void afficherJoueurs(javafx.event.ActionEvent event) {
        cacherToutesLesVues();
        if (joueursView != null) joueursView.setVisible(true);
        if (pageTitle != null) pageTitle.setText("Joueurs");
        setActiveButton(btnJoueurs);
        chargerJoueurs();
    }

    @FXML
    private void afficherEquipes(javafx.event.ActionEvent event) {
        cacherToutesLesVues();
        if (equipesView != null) equipesView.setVisible(true);
        if (pageTitle != null) pageTitle.setText("Équipes");
        setActiveButton(btnEquipes);
        chargerEquipes();
    }

    @FXML
    private void afficherStades(javafx.event.ActionEvent event) {
        cacherToutesLesVues();
        if (stadesView != null) stadesView.setVisible(true);
        if (pageTitle != null) pageTitle.setText("Stades");
        setActiveButton(btnStades);
        chargerStades();
    }

    @FXML
    private void afficherGroupes(javafx.event.ActionEvent event) {
        cacherToutesLesVues();
        if (groupesView != null) groupesView.setVisible(true);
        if (pageTitle != null) pageTitle.setText("Groupes");
        setActiveButton(btnGroupes);
        chargerGroupes();
    }

    @FXML
    private void afficherMatchs(javafx.event.ActionEvent event) {
        cacherToutesLesVues();
        if (matchsView != null) matchsView.setVisible(true);
        if (pageTitle != null) pageTitle.setText("Matchs");
        setActiveButton(btnMatchs);
        chargerMatchs();
    }

    @FXML
    private void afficherFavoris(javafx.event.ActionEvent event) {
        cacherToutesLesVues();
        if (favorisView != null) favorisView.setVisible(true);
        if (pageTitle != null) pageTitle.setText("Mes Favoris");
        setActiveButton(btnFavoris);
        chargerFavoris();
    }

    @FXML
    private void afficherProfil(javafx.event.ActionEvent event) {
        cacherToutesLesVues();
        if (profilView != null) profilView.setVisible(true);
        if (pageTitle != null) pageTitle.setText("Mon Profil");
        setActiveButton(btnProfil);
    }

    private void cacherToutesLesVues() {
        if (accueilView != null) accueilView.setVisible(false);
        if (equipesView != null) equipesView.setVisible(false);
        if (matchsView != null) matchsView.setVisible(false);
        if (groupesView != null) groupesView.setVisible(false);
        if (joueursView != null) joueursView.setVisible(false);
        if (stadesView != null) stadesView.setVisible(false);
        if (favorisView != null) favorisView.setVisible(false);
        if (profilView != null) profilView.setVisible(false);
    }

    private void setActiveButton(Button activeBtn) {
        String defaultStyle = "-fx-background-color: #000000; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 190; -fx-pref-height: 45; -fx-background-radius: 12; -fx-cursor: hand; -fx-alignment: CENTER_LEFT; -fx-padding: 0 0 0 15; -fx-font-weight: 600; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 4);";
        String activeStyle = "-fx-background-color: #C1272D; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 190; -fx-pref-height: 45; -fx-background-radius: 12; -fx-cursor: hand; -fx-alignment: CENTER_LEFT; -fx-padding: 0 0 0 15; -fx-font-weight: 600; -fx-effect: dropshadow(gaussian, rgba(193, 39, 45, 0.5), 10, 0, 0, 4);";
        
        if (btnAccueil != null) btnAccueil.setStyle(defaultStyle);
        if (btnEquipes != null) btnEquipes.setStyle(defaultStyle);
        if (btnMatchs != null) btnMatchs.setStyle(defaultStyle);
        if (btnGroupes != null) btnGroupes.setStyle(defaultStyle);
        if (btnJoueurs != null) btnJoueurs.setStyle(defaultStyle);
        if (btnStades != null) btnStades.setStyle(defaultStyle);
        if (btnFavoris != null) btnFavoris.setStyle(defaultStyle);
        if (btnProfil != null) btnProfil.setStyle(defaultStyle);
        if (btnStatistiques != null) btnStatistiques.setStyle(defaultStyle);
        if (btnCalendrier != null) btnCalendrier.setStyle(defaultStyle);
        if (btnComparaison != null) btnComparaison.setStyle(defaultStyle);
        
        if (activeBtn != null) activeBtn.setStyle(activeStyle);
    }
    
    // ========================================
    // NOUVELLES PAGES
    // ========================================
    
    @FXML
    private void afficherStatistiques(javafx.event.ActionEvent event) {
        chargerPageDynamique("/fxml/statistics-page.fxml", "Statistiques");
        setActiveButton(btnStatistiques);
    }
    
    @FXML
    private void afficherCalendrier(javafx.event.ActionEvent event) {
        chargerPageDynamique("/fxml/calendar-page.fxml", "Calendrier");
        setActiveButton(btnCalendrier);
    }
    
    @FXML
    private void afficherComparaison(javafx.event.ActionEvent event) {
        chargerPageDynamique("/fxml/team-comparison.fxml", "Comparaison");
        setActiveButton(btnComparaison);
    }
    
    @FXML
    private void afficherNotifications(javafx.event.ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notifications");
        alert.setHeaderText("Centre de Notifications");
        alert.setContentText("🔔 2 nouvelles notifications:\n\n" +
                           "⚽ Match à venir: Maroc vs Brésil demain\n" +
                           "📊 Nouvelles statistiques disponibles");
        alert.showAndWait();
        
        // Réinitialiser le badge
        if (notificationBadge != null) {
            notificationBadge.setText("0");
            notificationBadge.setVisible(false);
        }
    }
    
    private void chargerPageDynamique(String fxmlPath, String titre) {
        try {
            cacherToutesLesVues();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            javafx.scene.Parent page = loader.load();
            
            // Remplacer le contenu
            contentPane.getChildren().clear();
            contentPane.getChildren().add(page);
            
            if (pageTitle != null) {
                pageTitle.setText(titre);
            }
            
            System.out.println("✅ Page chargée: " + titre);
        } catch (IOException e) {
            System.err.println("❌ Erreur chargement page: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger la page: " + titre);
        }
    }

    // Suite dans le prochain message...
    
    @FXML
    private void deconnecter(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("🏆 Coupe du Monde 2030 - Connexion");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // ========================================
    // CHARGEMENT DES DONNÉES
    // ========================================
    
    private void chargerJoueurs() {
        try {
            List<Joueur> joueurs = joueurDAO.getAllJoueurs();
            tousLesJoueurs.setAll(joueurs);
            
            joueursFilteres = new FilteredList<>(tousLesJoueurs, p -> true);
            tableJoueurs.setItems(joueursFilteres);
            
            // Recherche en temps réel
            if (searchJoueur != null) {
                searchJoueur.textProperty().addListener((obs, oldVal, newVal) -> {
                    appliquerFiltresJoueurs();
                });
            }
            
            // Charger les équipes pour le filtre
            if (filterEquipeJoueur != null) {
                List<Equipe> equipes = equipeDAO.getAllEquipes();
                filterEquipeJoueur.getItems().clear();
                filterEquipeJoueur.getItems().add("Toutes");
                equipes.forEach(e -> filterEquipeJoueur.getItems().add(e.getNom()));
                
                // Listener pour le filtre par équipe
                filterEquipeJoueur.setOnAction(e -> appliquerFiltresJoueurs());
            }
            
            // Charger les postes
            if (filterPosteJoueur != null) {
                filterPosteJoueur.getItems().clear();
                filterPosteJoueur.getItems().addAll("Tous", "Gardien", "Défenseur", "Milieu", "Attaquant");
                
                // Listener pour le filtre par poste
                filterPosteJoueur.setOnAction(e -> appliquerFiltresJoueurs());
            }
            
            System.out.println("✅ " + joueurs.size() + " joueurs chargés");
        } catch (Exception e) {
            System.err.println("❌ Erreur chargement joueurs: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les joueurs");
        }
    }
    
    private void chargerEquipes() {
        try {
            List<Equipe> equipes = equipeDAO.getAllEquipes();
            toutesLesEquipes.setAll(equipes);
            
            equipesFilteres = new FilteredList<>(toutesLesEquipes, p -> true);
            tableEquipes.setItems(equipesFilteres);
            
            // Recherche en temps réel
            if (searchEquipe != null) {
                searchEquipe.textProperty().addListener((obs, oldVal, newVal) -> {
                    equipesFilteres.setPredicate(equipe -> {
                        if (newVal == null || newVal.isEmpty()) return true;
                        String lowerCase = newVal.toLowerCase();
                        return equipe.getNom().toLowerCase().contains(lowerCase) ||
                               equipe.getPays().toLowerCase().contains(lowerCase);
                    });
                });
            }
            
            // Charger les groupes dans le ComboBox
            if (filterGroupeEquipe != null) {
                List<Groupe> groupes = groupeDAO.getAllGroupes();
                filterGroupeEquipe.getItems().clear();
                filterGroupeEquipe.getItems().add("Tous");
                groupes.forEach(g -> filterGroupeEquipe.getItems().add(g.getNom()));
                
                // Filtre par groupe
                filterGroupeEquipe.setOnAction(e -> {
                    String selected = filterGroupeEquipe.getValue();
                    if (selected == null || selected.equals("Tous")) {
                        equipesFilteres.setPredicate(p -> true);
                    } else {
                        equipesFilteres.setPredicate(equipe -> {
                            // Filtrer par nom de groupe
                            return equipe.getGroupeId() != null;
                        });
                    }
                });
            }
            
            System.out.println("✅ " + equipes.size() + " équipes chargées");
        } catch (Exception e) {
            System.err.println("❌ Erreur chargement équipes: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les équipes");
        }
    }
    
    private void chargerStades() {
        try {
            List<Stade> stades = stadeDAO.getAllStades();
            tousLesStades.setAll(stades);
            
            stadesFilteres = new FilteredList<>(tousLesStades, p -> true);
            tableStades.setItems(stadesFilteres);
            
            // Recherche en temps réel
            if (searchStade != null) {
                searchStade.textProperty().addListener((obs, oldVal, newVal) -> {
                    stadesFilteres.setPredicate(stade -> {
                        if (newVal == null || newVal.isEmpty()) return true;
                        String lowerCase = newVal.toLowerCase();
                        return stade.getNom().toLowerCase().contains(lowerCase) ||
                               stade.getVille().toLowerCase().contains(lowerCase) ||
                               stade.getPays().toLowerCase().contains(lowerCase);
                    });
                });
            }
            
            // Charger les pays pour le filtre
            if (filterPaysStade != null) {
                filterPaysStade.getItems().clear();
                filterPaysStade.getItems().add("Tous");
                stades.stream()
                      .map(Stade::getPays)
                      .distinct()
                      .sorted()
                      .forEach(pays -> filterPaysStade.getItems().add(pays));
                
                // Filtre par pays
                filterPaysStade.setOnAction(e -> {
                    String selected = filterPaysStade.getValue();
                    if (selected == null || selected.equals("Tous")) {
                        stadesFilteres.setPredicate(p -> true);
                    } else {
                        stadesFilteres.setPredicate(stade -> 
                            stade.getPays().equals(selected)
                        );
                    }
                });
            }
            
            System.out.println("✅ " + stades.size() + " stades chargés");
        } catch (Exception e) {
            System.err.println("❌ Erreur chargement stades: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les stades");
        }
    }
    
    private void chargerGroupes() {
        try {
            // Vider la vue actuelle
            if (groupesView == null) return;
            groupesView.getChildren().clear();
            
            // Créer une grille pour les groupes (2 colonnes)
            javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
            grid.setHgap(20);
            grid.setVgap(20);
            grid.setPadding(new javafx.geometry.Insets(10));
            
            List<Groupe> groupes = groupeDAO.getAllGroupes();
            List<Equipe> toutesEquipes = equipeDAO.getAllEquipes();
            
            int row = 0, col = 0;
            for (Groupe groupe : groupes) {
                // Créer une carte pour chaque groupe
                VBox carteGroupe = creerCarteGroupe(groupe, toutesEquipes);
                grid.add(carteGroupe, col, row);
                
                col++;
                if (col >= 2) {
                    col = 0;
                    row++;
                }
            }
            
            groupesView.getChildren().add(grid);
            System.out.println("✅ " + groupes.size() + " groupes chargés");
        } catch (Exception e) {
            System.err.println("❌ Erreur chargement groupes: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les groupes");
        }
    }
    
    /**
     * Crée une carte visuelle pour un groupe
     */
    private VBox creerCarteGroupe(Groupe groupe, List<Equipe> toutesEquipes) {
        VBox carte = new VBox(10);
        carte.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 10; " +
            "-fx-padding: 15; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 3); " +
            "-fx-pref-width: 480; " +
            "-fx-min-height: 350;"  // Augmenté de 280 à 350 pour afficher 4 équipes
        );
        
        // En-tête du groupe
        HBox header = new HBox();
        header.setAlignment(javafx.geometry.Pos.CENTER);
        header.setStyle(
            "-fx-background-color: #C1272D; " +
            "-fx-background-radius: 8; " +
            "-fx-padding: 12;"
        );
        
        Label nomGroupe = new Label("GROUPE " + groupe.getNom());
        nomGroupe.setStyle(
            "-fx-font-size: 20px; " +
            "-fx-font-weight: bold; " +
            "-fx-text-fill: white;"
        );
        header.getChildren().add(nomGroupe);
        carte.getChildren().add(header);
        
        // Filtrer les équipes de ce groupe
        List<Equipe> equipesGroupe = toutesEquipes.stream()
            .filter(e -> e.getGroupeId() != null && e.getGroupeId() == groupe.getId())
            .sorted((e1, e2) -> {
                int comparePoints = Integer.compare(e2.getPoints(), e1.getPoints());
                if (comparePoints != 0) return comparePoints;
                int diff1 = e1.getButsMarques() - e1.getButsEncaisses();
                int diff2 = e2.getButsMarques() - e2.getButsEncaisses();
                return Integer.compare(diff2, diff1);
            })
            .collect(Collectors.toList());
        
        // Si aucune équipe dans le groupe
        if (equipesGroupe.isEmpty()) {
            VBox emptyBox = new VBox(15);
            emptyBox.setAlignment(javafx.geometry.Pos.CENTER);
            emptyBox.setStyle("-fx-padding: 30;");
            
            Label emptyIcon = new Label("📋");
            emptyIcon.setStyle("-fx-font-size: 40px;");
            
            Label emptyText = new Label("Aucune équipe assignée");
            emptyText.setStyle(
                "-fx-font-size: 13px; " +
                "-fx-text-fill: #666;"
            );
            
            emptyBox.getChildren().addAll(emptyIcon, emptyText);
            carte.getChildren().add(emptyBox);
            return carte;
        }
        
        // Tableau des équipes
        VBox tableauEquipes = new VBox(5);
        tableauEquipes.setStyle("-fx-padding: 5 0 0 0;");
        
        // En-tête du tableau
        HBox headerTable = new HBox();
        headerTable.setStyle(
            "-fx-background-color: #000000; " +
            "-fx-padding: 8; " +
            "-fx-background-radius: 5;"
        );
        
        Label colPos = new Label("#");
        colPos.setStyle("-fx-font-weight: bold; -fx-pref-width: 35; -fx-text-fill: white; -fx-font-size: 11px;");
        
        Label colEquipe = new Label("ÉQUIPE");
        colEquipe.setStyle("-fx-font-weight: bold; -fx-pref-width: 240; -fx-text-fill: white; -fx-font-size: 11px;");
        
        Label colPts = new Label("PTS");
        colPts.setStyle("-fx-font-weight: bold; -fx-pref-width: 45; -fx-text-fill: white; -fx-font-size: 11px; -fx-alignment: center;");
        
        Label colMJ = new Label("MJ");
        colMJ.setStyle("-fx-font-weight: bold; -fx-pref-width: 40; -fx-text-fill: white; -fx-font-size: 11px; -fx-alignment: center;");
        
        Label colDiff = new Label("+/-");
        colDiff.setStyle("-fx-font-weight: bold; -fx-pref-width: 50; -fx-text-fill: white; -fx-font-size: 11px; -fx-alignment: center;");
        
        headerTable.getChildren().addAll(colPos, colEquipe, colPts, colMJ, colDiff);
        tableauEquipes.getChildren().add(headerTable);
        
        // Lignes des équipes
        int position = 1;
        for (Equipe equipe : equipesGroupe) {
            HBox ligneEquipe = new HBox();
            
            // Style de base
            String bgColor = position <= 2 ? "rgba(193, 39, 45, 0.08)" : "transparent";
            ligneEquipe.setStyle(
                "-fx-padding: 8; " +
                "-fx-background-color: " + bgColor + "; " +
                "-fx-background-radius: 5; " +
                "-fx-cursor: hand;"
            );
            
            // Effet hover
            final String baseStyle = ligneEquipe.getStyle();
            final int pos = position;
            ligneEquipe.setOnMouseEntered(e -> {
                String hoverBg = pos <= 2 ? "rgba(193, 39, 45, 0.15)" : "#f5f5f5";
                ligneEquipe.setStyle(
                    "-fx-padding: 8; " +
                    "-fx-background-color: " + hoverBg + "; " +
                    "-fx-background-radius: 5; " +
                    "-fx-cursor: hand; " +
                    "-fx-border-color: #C1272D; " +
                    "-fx-border-width: 1; " +
                    "-fx-border-radius: 5;"
                );
            });
            ligneEquipe.setOnMouseExited(e -> ligneEquipe.setStyle(baseStyle));
            
            // Position
            Label lblPos = new Label(String.valueOf(position));
            lblPos.setStyle(
                "-fx-font-weight: bold; " +
                "-fx-pref-width: 35; " +
                "-fx-font-size: 14px; " +
                "-fx-text-fill: " + (position <= 2 ? "#C1272D" : "#333") + ";"
            );
            
            // Drapeau + Nom équipe
            HBox equipeBox = new HBox(8);
            equipeBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            equipeBox.setPrefWidth(240);
            
            // Utiliser l'image du drapeau
            javafx.scene.Node drapeau = getDrapeauImage(equipe.getPays(), 30, 22);
            
            Label nomEquipe = new Label(equipe.getNom());
            nomEquipe.setStyle(
                "-fx-font-size: 13px; " +
                "-fx-font-weight: 600; " +
                "-fx-text-fill: #000000;"
            );
            
            equipeBox.getChildren().addAll(drapeau, nomEquipe);
            
            // Points
            Label lblPts = new Label(String.valueOf(equipe.getPoints()));
            lblPts.setStyle(
                "-fx-font-weight: bold; " +
                "-fx-pref-width: 45; " +
                "-fx-alignment: center; " +
                "-fx-text-fill: #C1272D; " +
                "-fx-font-size: 16px;"
            );
            
            // Matchs joués
            Label lblMJ = new Label(String.valueOf(equipe.getMatchsJoues()));
            lblMJ.setStyle(
                "-fx-pref-width: 40; " +
                "-fx-alignment: center; " +
                "-fx-font-size: 13px; " +
                "-fx-text-fill: #333;"
            );
            
            // Différence de buts
            int diff = equipe.getButsMarques() - equipe.getButsEncaisses();
            Label lblDiff = new Label((diff > 0 ? "+" : "") + diff);
            lblDiff.setStyle(
                "-fx-pref-width: 50; " +
                "-fx-alignment: center; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: 600; " +
                "-fx-text-fill: " + (diff > 0 ? "#C1272D" : diff < 0 ? "#000000" : "#666") + ";"
            );
            
            ligneEquipe.getChildren().addAll(lblPos, equipeBox, lblPts, lblMJ, lblDiff);
            tableauEquipes.getChildren().add(ligneEquipe);
            
            position++;
        }
        
        carte.getChildren().add(tableauEquipes);
        
        return carte;
    }
    
    /**
     * Retourne l'emoji du drapeau pour un pays (fallback si image non disponible)
     */
    private String getDrapeauEmoji(String pays) {
        if (pays == null) return "🏴";
        
        switch (pays.toLowerCase()) {
            // Afrique
            case "maroc": return "🇲🇦";
            case "algérie": case "algerie": return "🇩🇿";
            case "tunisie": return "🇹🇳";
            case "égypte": case "egypte": return "🇪🇬";
            case "sénégal": case "senegal": return "🇸🇳";
            case "nigeria": return "🇳🇬";
            case "cameroun": return "🇨🇲";
            case "ghana": return "🇬🇭";
            case "côte d'ivoire": case "cote d'ivoire": return "🇨🇮";
            
            // Europe
            case "france": return "🇫🇷";
            case "espagne": return "🇪🇸";
            case "portugal": return "🇵🇹";
            case "allemagne": return "🇩🇪";
            case "italie": return "🇮🇹";
            case "angleterre": return "🏴󠁧󠁢󠁥󠁮󠁧󠁿";
            case "pays-bas": case "pays bas": return "🇳🇱";
            case "belgique": return "🇧🇪";
            case "croatie": return "🇭🇷";
            case "suisse": return "🇨🇭";
            case "danemark": return "🇩🇰";
            case "suède": case "suede": return "🇸🇪";
            case "pologne": return "🇵🇱";
            
            // Amérique du Sud
            case "brésil": case "bresil": return "🇧🇷";
            case "argentine": return "🇦🇷";
            case "uruguay": return "🇺🇾";
            case "colombie": return "🇨🇴";
            case "chili": return "🇨🇱";
            case "pérou": case "perou": return "🇵🇪";
            case "équateur": case "equateur": return "🇪🇨";
            
            // Amérique du Nord
            case "mexique": return "🇲🇽";
            case "états-unis": case "etats-unis": case "usa": return "🇺🇸";
            case "canada": return "🇨🇦";
            case "costa rica": return "🇨🇷";
            
            // Asie
            case "japon": return "🇯🇵";
            case "corée du sud": case "coree du sud": return "🇰🇷";
            case "iran": return "🇮🇷";
            case "arabie saoudite": return "🇸🇦";
            case "australie": return "🇦🇺";
            case "qatar": return "🇶🇦";
            
            default: return "🏴";
        }
    }
    
    /**
     * Crée une ImageView avec le drapeau du pays
     */
    private javafx.scene.Node getDrapeauImage(String pays, double width, double height) {
        if (pays == null) {
            Label fallback = new Label("🏴");
            fallback.setStyle("-fx-font-size: " + height + "px;");
            return fallback;
        }
        
        // Normaliser le nom du pays pour le nom de fichier
        String nomFichier = getNomFichierDrapeau(pays);
        String cheminImage = "/images/flags/" + nomFichier + ".png";
        
        try {
            // Essayer de charger l'image
            javafx.scene.image.Image image = new javafx.scene.image.Image(
                getClass().getResourceAsStream(cheminImage),
                width, height, true, true
            );
            
            if (!image.isError()) {
                javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
                imageView.setFitWidth(width);
                imageView.setFitHeight(height);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
                
                // Ajouter un effet d'ombre pour un meilleur rendu
                imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 0, 1);");
                
                return imageView;
            }
        } catch (Exception e) {
            System.err.println("⚠️ Image drapeau non trouvée pour " + pays + " (" + cheminImage + "), utilisation emoji");
        }
        
        // Fallback vers emoji si image non disponible
        Label fallback = new Label(getDrapeauEmoji(pays));
        fallback.setStyle("-fx-font-size: " + height + "px;");
        return fallback;
    }
    
    /**
     * Retourne le nom de fichier normalisé pour un pays
     */
    private String getNomFichierDrapeau(String pays) {
        if (pays == null) return "default";
        
        String normalized = pays.toLowerCase()
            .replace("é", "e")
            .replace("è", "e")
            .replace("ê", "e")
            .replace("à", "a")
            .replace("â", "a")
            .replace("ô", "o")
            .replace("ù", "u")
            .replace("û", "u")
            .replace("ç", "c")
            .replace("î", "i")
            .replace("ï", "i")
            .replace(" ", "_")
            .replace("-", "_")
            .replace("'", "_");
        
        return normalized;
    }
    
    /**
     * Obtient le pays d'une équipe à partir de son nom
     */
    private String getPaysParNomEquipe(String nomEquipe) {
        if (nomEquipe == null) return null;
        
        try {
            List<Equipe> equipes = equipeDAO.getAllEquipes();
            for (Equipe equipe : equipes) {
                if (equipe.getNom() != null && equipe.getNom().equalsIgnoreCase(nomEquipe.trim())) {
                    return equipe.getPays();
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur recherche pays: " + e.getMessage());
        }
        
        return null;
    }
    
    private void chargerMatchs() {
        try {
            List<Match> matchs = matchDAO.getAllMatchs();
            tousLesMatchs.setAll(matchs);
            
            matchsFilteres = new FilteredList<>(tousLesMatchs, p -> true);
            tableMatchs.setItems(matchsFilteres);
            
            // Charger les équipes pour le filtre
            if (filterEquipeMatch != null) {
                List<Equipe> equipes = equipeDAO.getAllEquipes();
                filterEquipeMatch.getItems().clear();
                filterEquipeMatch.getItems().add("Toutes");
                equipes.forEach(e -> filterEquipeMatch.getItems().add(e.getNom()));
                
                // Listener pour le filtre par équipe
                filterEquipeMatch.setOnAction(e -> appliquerFiltresMatchs());
            }
            
            // Charger les groupes pour le filtre
            if (filterGroupeMatch != null) {
                List<Groupe> groupes = groupeDAO.getAllGroupes();
                filterGroupeMatch.getItems().clear();
                filterGroupeMatch.getItems().add("Tous");
                groupes.forEach(g -> filterGroupeMatch.getItems().add(g.getNom()));
                
                // Listener pour le filtre par groupe
                filterGroupeMatch.setOnAction(e -> appliquerFiltresMatchs());
            }
            
            // Charger les stades pour le filtre
            if (filterStadeMatch != null) {
                List<Stade> stades = stadeDAO.getAllStades();
                filterStadeMatch.getItems().clear();
                filterStadeMatch.getItems().add("Tous");
                stades.forEach(s -> filterStadeMatch.getItems().add(s.getNom()));
                
                // Listener pour le filtre par stade
                filterStadeMatch.setOnAction(e -> appliquerFiltresMatchs());
            }
            
            System.out.println("✅ " + matchs.size() + " matchs chargés");
        } catch (Exception e) {
            System.err.println("❌ Erreur chargement matchs: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les matchs");
        }
    }
    
    private void chargerFavoris() {
        try {
            if (utilisateurId <= 0) {
                System.err.println("⚠️ ID utilisateur non défini");
                showAlert(Alert.AlertType.WARNING, "Attention", "Impossible de charger les favoris");
                return;
            }
            
            List<Equipe> favoris = favorisDAO.getFavoris(utilisateurId);
            ObservableList<Equipe> data = FXCollections.observableArrayList(favoris);
            tableFavoris.setItems(data);
            
            System.out.println("✅ " + favoris.size() + " favoris chargés pour l'utilisateur " + utilisateurId);
        } catch (Exception e) {
            System.err.println("❌ Erreur chargement favoris: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les favoris");
        }
    }
    
    /**
     * Applique les filtres combinés pour les joueurs
     */
    private void appliquerFiltresJoueurs() {
        if (joueursFilteres == null) return;
        
        String equipeSelectionnee = filterEquipeJoueur != null ? filterEquipeJoueur.getValue() : null;
        String posteSelectionne = filterPosteJoueur != null ? filterPosteJoueur.getValue() : null;
        String recherche = searchJoueur != null ? searchJoueur.getText() : "";
        
        joueursFilteres.setPredicate(joueur -> {
            // Filtre par recherche
            boolean matchRecherche = true;
            if (recherche != null && !recherche.isEmpty()) {
                String lowerCase = recherche.toLowerCase();
                matchRecherche = joueur.getNom().toLowerCase().contains(lowerCase) ||
                                joueur.getPrenom().toLowerCase().contains(lowerCase);
            }
            
            // Filtre par équipe
            boolean matchEquipe = true;
            if (equipeSelectionnee != null && !equipeSelectionnee.equals("Toutes")) {
                try {
                    List<Equipe> equipes = equipeDAO.getAllEquipes();
                    Equipe equipe = equipes.stream()
                        .filter(e -> e.getNom().equals(equipeSelectionnee))
                        .findFirst()
                        .orElse(null);
                    if (equipe != null) {
                        matchEquipe = joueur.getEquipeId() == equipe.getId();
                    }
                } catch (Exception e) {
                    System.err.println("Erreur filtre équipe: " + e.getMessage());
                }
            }
            
            // Filtre par poste
            boolean matchPoste = true;
            if (posteSelectionne != null && !posteSelectionne.equals("Tous")) {
                matchPoste = joueur.getPoste() != null && 
                            joueur.getPoste().equalsIgnoreCase(posteSelectionne);
            }
            
            return matchRecherche && matchEquipe && matchPoste;
        });
    }
    
    /**
     * Applique les filtres combinés pour les matchs
     */
    private void appliquerFiltresMatchs() {
        if (matchsFilteres == null) return;
        
        String equipeSelectionnee = filterEquipeMatch != null ? filterEquipeMatch.getValue() : null;
        String groupeSelectionne = filterGroupeMatch != null ? filterGroupeMatch.getValue() : null;
        String stadeSelectionne = filterStadeMatch != null ? filterStadeMatch.getValue() : null;
        
        matchsFilteres.setPredicate(match -> {
            String nomMatch = match.getNom();
            if (nomMatch == null) return false;
            
            // Filtre par équipe
            boolean matchEquipe = true;
            if (equipeSelectionnee != null && !equipeSelectionnee.equals("Toutes")) {
                matchEquipe = nomMatch.toLowerCase().contains(equipeSelectionnee.toLowerCase());
            }
            
            // Filtre par groupe (vérifier si les équipes du match appartiennent au groupe)
            boolean matchGroupe = true;
            if (groupeSelectionne != null && !groupeSelectionne.equals("Tous")) {
                try {
                    // Extraire les noms des équipes du match
                    if (nomMatch.contains(" vs ")) {
                        String[] equipes = nomMatch.split(" vs ");
                        if (equipes.length == 2) {
                            String equipeA = equipes[0].trim();
                            String equipeB = equipes[1].trim();
                            
                            // Vérifier si au moins une des équipes appartient au groupe
                            List<Equipe> toutesEquipes = equipeDAO.getAllEquipes();
                            List<Groupe> groupes = groupeDAO.getAllGroupes();
                            
                            // Trouver l'ID du groupe sélectionné
                            Integer groupeId = null;
                            for (Groupe g : groupes) {
                                if (g.getNom().equals(groupeSelectionne)) {
                                    groupeId = g.getId();
                                    break;
                                }
                            }
                            
                            if (groupeId != null) {
                                boolean equipeADansGroupe = false;
                                boolean equipeBDansGroupe = false;
                                
                                for (Equipe e : toutesEquipes) {
                                    if (e.getNom().equalsIgnoreCase(equipeA) && 
                                        e.getGroupeId() != null && e.getGroupeId() == groupeId) {
                                        equipeADansGroupe = true;
                                    }
                                    if (e.getNom().equalsIgnoreCase(equipeB) && 
                                        e.getGroupeId() != null && e.getGroupeId() == groupeId) {
                                        equipeBDansGroupe = true;
                                    }
                                }
                                
                                matchGroupe = equipeADansGroupe || equipeBDansGroupe;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Erreur filtre groupe: " + e.getMessage());
                }
            }
            
            // Filtre par stade (le nom du stade peut être dans le nom du match ou dans lieu)
            boolean matchStade = true;
            if (stadeSelectionne != null && !stadeSelectionne.equals("Tous")) {
                // Vérifier dans le champ lieu
                String lieu = match.getLieu();
                if (lieu != null && !lieu.isEmpty()) {
                    matchStade = lieu.toLowerCase().contains(stadeSelectionne.toLowerCase());
                } else {
                    // Si pas de lieu, vérifier dans le nom du match
                    matchStade = nomMatch.toLowerCase().contains(stadeSelectionne.toLowerCase());
                }
            }
            
            return matchEquipe && matchGroupe && matchStade;
        });
    }
    
    // ========================================
    // FILTRES ET ACTIONS
    // ========================================
    
    @FXML 
    private void resetFiltersJoueurs(javafx.event.ActionEvent event) {
        if (searchJoueur != null) searchJoueur.clear();
        if (filterEquipeJoueur != null) filterEquipeJoueur.setValue(null);
        if (filterPosteJoueur != null) filterPosteJoueur.setValue(null);
        if (joueursFilteres != null) joueursFilteres.setPredicate(p -> true);
    }
    
    @FXML 
    private void resetFiltersEquipes(javafx.event.ActionEvent event) {
        if (searchEquipe != null) searchEquipe.clear();
        if (filterGroupeEquipe != null) filterGroupeEquipe.setValue(null);
        if (equipesFilteres != null) equipesFilteres.setPredicate(p -> true);
    }
    
    @FXML 
    private void resetFiltersStades(javafx.event.ActionEvent event) {
        if (searchStade != null) searchStade.clear();
        if (filterPaysStade != null) filterPaysStade.setValue(null);
        if (stadesFilteres != null) stadesFilteres.setPredicate(p -> true);
    }
    
    @FXML 
    private void resetFiltersMatchs(javafx.event.ActionEvent event) {
        if (filterEquipeMatch != null) filterEquipeMatch.setValue(null);
        if (filterGroupeMatch != null) filterGroupeMatch.setValue(null);
        if (filterStadeMatch != null) filterStadeMatch.setValue(null);
        if (matchsFilteres != null) matchsFilteres.setPredicate(p -> true);
    }
    
    @FXML 
    private void voirStatistiquesEquipe(javafx.event.ActionEvent event) {
        Equipe selected = tableEquipes.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une équipe");
            return;
        }
        
        // Afficher les statistiques détaillées
        String stats = String.format(
            "📊 Statistiques de %s\n\n" +
            "🏆 Points: %d\n" +
            "✅ Victoires: %d\n" +
            "⚖️ Nuls: %d\n" +
            "❌ Défaites: %d\n" +
            "⚽ Buts marqués: %d\n" +
            "🥅 Buts encaissés: %d\n" +
            "📈 Différence: %d",
            selected.getNom(),
            selected.getPoints(),
            selected.getVictoires(),
            selected.getNuls(),
            selected.getDefaites(),
            selected.getButsMarques(),
            selected.getButsEncaisses(),
            selected.getButsMarques() - selected.getButsEncaisses()
        );
        
        showAlert(Alert.AlertType.INFORMATION, "Statistiques", stats);
    }
    
    @FXML 
    private void exporterEquipesPDF(javafx.event.ActionEvent event) {
        try {
            // Créer un fichier PDF simple
            javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
            fileChooser.setTitle("Enregistrer le PDF");
            fileChooser.setInitialFileName("equipes_coupe_du_monde_2030.pdf");
            fileChooser.getExtensionFilters().add(
                new javafx.stage.FileChooser.ExtensionFilter("PDF", "*.pdf")
            );
            
            java.io.File file = fileChooser.showSaveDialog(rootPane.getScene().getWindow());
            
            if (file != null) {
                // Créer un fichier texte simple pour l'instant
                try (java.io.PrintWriter writer = new java.io.PrintWriter(file)) {
                    writer.println("=".repeat(80));
                    writer.println("COUPE DU MONDE 2030 - LISTE DES ÉQUIPES");
                    writer.println("=".repeat(80));
                    writer.println();
                    writer.println(String.format("%-30s %-20s %-20s %s", "ÉQUIPE", "PAYS", "ENTRAÎNEUR", "POINTS"));
                    writer.println("-".repeat(80));
                    
                    for (Equipe equipe : toutesLesEquipes) {
                        writer.println(String.format("%-30s %-20s %-20s %d", 
                            equipe.getNom(),
                            equipe.getPays(),
                            equipe.getEntraineur() != null ? equipe.getEntraineur() : "N/A",
                            equipe.getPoints()
                        ));
                    }
                    
                    writer.println();
                    writer.println("=".repeat(80));
                    writer.println("Total: " + toutesLesEquipes.size() + " équipes");
                    writer.println("=".repeat(80));
                }
                
                showAlert(Alert.AlertType.INFORMATION, "Succès", 
                         "Liste exportée avec succès !\n" + file.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur export: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", 
                     "Impossible d'exporter la liste: " + e.getMessage());
        }
    }
    
    @FXML 
    private void filtrerMatchsAvenir(javafx.event.ActionEvent event) {
        if (matchsFilteres != null) {
            matchsFilteres.setPredicate(match -> {
                // Filtrer les matchs sans score (à venir)
                return match.getScore() == null || match.getScore().isEmpty() || match.getScore().equals("-");
            });
        }
    }
    
    @FXML 
    private void filtrerMatchsTermines(javafx.event.ActionEvent event) {
        if (matchsFilteres != null) {
            matchsFilteres.setPredicate(match -> {
                // Filtrer les matchs avec score (terminés)
                return match.getScore() != null && !match.getScore().isEmpty() && !match.getScore().equals("-");
            });
        }
    }
    
    @FXML 
    private void changerMotDePasse(javafx.event.ActionEvent event) {
        if (utilisateurId == 0) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez vous reconnecter");
            return;
        }
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Changer le mot de passe");
        dialog.setHeaderText("Modifier votre mot de passe");
        
        // Boutons
        ButtonType btnValider = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnValider, ButtonType.CANCEL);
        
        // Formulaire
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));
        
        PasswordField txtAncien = new PasswordField();
        txtAncien.setPromptText("Ancien mot de passe");
        txtAncien.setStyle("-fx-pref-width: 250; -fx-pref-height: 35;");
        
        PasswordField txtNouveau = new PasswordField();
        txtNouveau.setPromptText("Nouveau mot de passe");
        txtNouveau.setStyle("-fx-pref-width: 250; -fx-pref-height: 35;");
        
        PasswordField txtConfirm = new PasswordField();
        txtConfirm.setPromptText("Confirmer le mot de passe");
        txtConfirm.setStyle("-fx-pref-width: 250; -fx-pref-height: 35;");
        
        Label lblAncien = new Label("Ancien mot de passe :");
        lblAncien.setStyle("-fx-font-weight: bold;");
        
        Label lblNouveau = new Label("Nouveau mot de passe :");
        lblNouveau.setStyle("-fx-font-weight: bold;");
        
        Label lblConfirm = new Label("Confirmer :");
        lblConfirm.setStyle("-fx-font-weight: bold;");
        
        grid.add(lblAncien, 0, 0);
        grid.add(txtAncien, 1, 0);
        grid.add(lblNouveau, 0, 1);
        grid.add(txtNouveau, 1, 1);
        grid.add(lblConfirm, 0, 2);
        grid.add(txtConfirm, 1, 2);
        
        dialog.getDialogPane().setContent(grid);
        
        // Style du dialog
        dialog.getDialogPane().setStyle("-fx-background-color: white;");
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == btnValider) {
                String ancien = txtAncien.getText();
                String nouveau = txtNouveau.getText();
                String confirm = txtConfirm.getText();
                
                // Validations
                if (ancien.isEmpty() || nouveau.isEmpty() || confirm.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs sont obligatoires");
                    return;
                }
                
                if (!nouveau.equals(confirm)) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Les mots de passe ne correspondent pas");
                    return;
                }
                
                if (nouveau.length() < 6) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Le mot de passe doit contenir au moins 6 caractères");
                    return;
                }
                
                // Changer le mot de passe
                if (utilisateurDAO.changerMotDePasse(utilisateurId, ancien, nouveau)) {
                    showAlert(Alert.AlertType.INFORMATION, "Succès", 
                             "✅ Votre mot de passe a été changé avec succès !");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", 
                             "❌ Ancien mot de passe incorrect ou erreur lors du changement");
                }
            }
        });
    }
    
    @FXML
    private void ajouterEquipe(javafx.event.ActionEvent event) {
        Dialog<Equipe> dialog = new Dialog<>();
        dialog.setTitle("Ajouter une équipe");
        dialog.setHeaderText("Nouvelle équipe");
        
        // Boutons
        ButtonType btnAjouter = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnAjouter, ButtonType.CANCEL);
        
        // Formulaire
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));
        
        TextField txtNom = new TextField();
        txtNom.setPromptText("Nom de l'équipe");
        
        TextField txtPays = new TextField();
        txtPays.setPromptText("Pays");
        
        TextField txtEntraineur = new TextField();
        txtEntraineur.setPromptText("Entraîneur");
        
        ComboBox<String> cbGroupe = new ComboBox<>();
        try {
            List<Groupe> groupes = groupeDAO.getAllGroupes();
            groupes.forEach(g -> cbGroupe.getItems().add(g.getNom()));
        } catch (Exception e) {
            System.err.println("Erreur chargement groupes: " + e.getMessage());
        }
        cbGroupe.setPromptText("Sélectionner un groupe");
        
        grid.add(new Label("Nom:"), 0, 0);
        grid.add(txtNom, 1, 0);
        grid.add(new Label("Pays:"), 0, 1);
        grid.add(txtPays, 1, 1);
        grid.add(new Label("Entraîneur:"), 0, 2);
        grid.add(txtEntraineur, 1, 2);
        grid.add(new Label("Groupe:"), 0, 3);
        grid.add(cbGroupe, 1, 3);
        
        dialog.getDialogPane().setContent(grid);
        
        // Convertir le résultat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnAjouter) {
                Equipe equipe = new Equipe();
                equipe.setNom(txtNom.getText());
                equipe.setPays(txtPays.getText());
                equipe.setEntraineur(txtEntraineur.getText());
                
                // Trouver l'ID du groupe
                String groupeNom = cbGroupe.getValue();
                if (groupeNom != null) {
                    try {
                        List<Groupe> groupes = groupeDAO.getAllGroupes();
                        for (Groupe g : groupes) {
                            if (g.getNom().equals(groupeNom)) {
                                equipe.setGroupeId(g.getId());
                                break;
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Erreur: " + e.getMessage());
                    }
                }
                
                return equipe;
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(equipe -> {
            if (equipe.getNom() != null && !equipe.getNom().isEmpty()) {
                if (equipeDAO.ajouterEquipe(equipe)) {
                    showAlert(Alert.AlertType.INFORMATION, "Succès", 
                             "Équipe " + equipe.getNom() + " ajoutée avec succès !");
                    chargerEquipes(); // Recharger la liste
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", 
                             "Impossible d'ajouter l'équipe");
                }
            }
        });
    }
    
    @FXML
    private void modifierEquipe(javafx.event.ActionEvent event) {
        Equipe selected = tableEquipes.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", 
                     "Veuillez sélectionner une équipe à modifier");
            return;
        }
        
        Dialog<Equipe> dialog = new Dialog<>();
        dialog.setTitle("Modifier une équipe");
        dialog.setHeaderText("Modifier: " + selected.getNom());
        
        // Boutons
        ButtonType btnModifier = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnModifier, ButtonType.CANCEL);
        
        // Formulaire
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));
        
        TextField txtNom = new TextField(selected.getNom());
        TextField txtPays = new TextField(selected.getPays());
        TextField txtEntraineur = new TextField(selected.getEntraineur());
        
        ComboBox<String> cbGroupe = new ComboBox<>();
        try {
            List<Groupe> groupes = groupeDAO.getAllGroupes();
            groupes.forEach(g -> cbGroupe.getItems().add(g.getNom()));
            
            // Sélectionner le groupe actuel
            if (selected.getGroupeId() != null) {
                for (Groupe g : groupes) {
                    if (g.getId() == selected.getGroupeId()) {
                        cbGroupe.setValue(g.getNom());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur chargement groupes: " + e.getMessage());
        }
        
        grid.add(new Label("Nom:"), 0, 0);
        grid.add(txtNom, 1, 0);
        grid.add(new Label("Pays:"), 0, 1);
        grid.add(txtPays, 1, 1);
        grid.add(new Label("Entraîneur:"), 0, 2);
        grid.add(txtEntraineur, 1, 2);
        grid.add(new Label("Groupe:"), 0, 3);
        grid.add(cbGroupe, 1, 3);
        
        dialog.getDialogPane().setContent(grid);
        
        // Convertir le résultat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnModifier) {
                selected.setNom(txtNom.getText());
                selected.setPays(txtPays.getText());
                selected.setEntraineur(txtEntraineur.getText());
                
                // Trouver l'ID du groupe
                String groupeNom = cbGroupe.getValue();
                if (groupeNom != null) {
                    try {
                        List<Groupe> groupes = groupeDAO.getAllGroupes();
                        for (Groupe g : groupes) {
                            if (g.getNom().equals(groupeNom)) {
                                selected.setGroupeId(g.getId());
                                break;
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Erreur: " + e.getMessage());
                    }
                }
                
                return selected;
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(equipe -> {
            if (equipeDAO.modifierEquipe(equipe)) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", 
                         "Équipe " + equipe.getNom() + " modifiée avec succès !");
                chargerEquipes(); // Recharger la liste
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", 
                         "Impossible de modifier l'équipe");
            }
        });
    }
    
    private void ajouterAuxFavoris(Equipe equipe) {
        if (utilisateurId == 0) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez vous reconnecter");
            return;
        }
        
        if (favorisDAO.ajouterFavori(utilisateurId, equipe.getId())) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", 
                     "⭐ " + equipe.getNom() + " ajoutée aux favoris !");
        } else {
            showAlert(Alert.AlertType.WARNING, "Information", 
                     "Cette équipe est déjà dans vos favoris");
        }
    }
    
    private void retirerDesFavoris(Equipe equipe) {
        if (utilisateurId == 0) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez vous reconnecter");
            return;
        }
        
        if (favorisDAO.retirerFavori(utilisateurId, equipe.getId())) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", 
                     "❌ " + equipe.getNom() + " retirée des favoris");
            chargerFavoris(); // Recharger la liste
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", 
                     "Impossible de retirer cette équipe des favoris");
        }
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
