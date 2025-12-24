package com.example.gestionmatch.controller;

import com.example.gestionmatch.dao.*;
import com.example.gestionmatch.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    @FXML private StackPane contentPane;
    @FXML private VBox dashboardView, joueursView, equipesView, stadesView, groupesView, matchsView;
    
    @FXML private Label statJoueurs, statEquipes, statMatchs, statStades, lastUpdateLabel;
    @FXML private ListView<String> activitiesList;
    
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
        initialiserTablesJoueurs();
        initialiserTablesEquipes();
        initialiserTablesStades();
        initialiserTablesGroupes();
        initialiserTablesMatchs();
        
        afficherDashboard();
        chargerStatistiques();
    }

    @FXML
    private void afficherDashboard() {
        cacherToutesVues();
        dashboardView.setVisible(true);
        chargerStatistiques();
    }

    @FXML
    private void afficherJoueurs() {
        cacherToutesVues();
        joueursView.setVisible(true);
        chargerJoueurs();
    }

    @FXML
    private void afficherEquipes() {
        cacherToutesVues();
        equipesView.setVisible(true);
        chargerEquipes();
    }

    @FXML
    private void afficherStades() {
        cacherToutesVues();
        stadesView.setVisible(true);
        chargerStades();
    }

    @FXML
    private void afficherGroupes() {
        cacherToutesVues();
        groupesView.setVisible(true);
        chargerGroupes();
    }

    @FXML
    private void afficherMatchs() {
        cacherToutesVues();
        matchsView.setVisible(true);
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

    private void chargerStatistiques() {
        statJoueurs.setText(String.valueOf(joueurDAO.getAllJoueurs().size()));
        statEquipes.setText(String.valueOf(equipeDAO.getAllEquipes().size()));
        statMatchs.setText(String.valueOf(matchDAO.getAllMatchs().size()));
        statStades.setText(String.valueOf(stadeDAO.getAllStades().size()));
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        lastUpdateLabel.setText(LocalDateTime.now().format(formatter));
        
        activitiesList.getItems().clear();
        activitiesList.getItems().addAll(
            "Systeme initialise",
            "Base de donnees connectee",
            statEquipes.getText() + " equipes chargees",
            statJoueurs.getText() + " joueurs charges"
        );
    }

    @SuppressWarnings("unchecked")
    private void initialiserTablesJoueurs() {
        TableColumn<Joueur, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Joueur, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(120);

        TableColumn<Joueur, String> colPrenom = new TableColumn<>("Prenom");
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colPrenom.setPrefWidth(120);

        TableColumn<Joueur, Integer> colNumero = new TableColumn<>("Numero");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colNumero.setPrefWidth(50);

        TableColumn<Joueur, String> colPoste = new TableColumn<>("Poste");
        colPoste.setCellValueFactory(new PropertyValueFactory<>("poste"));
        colPoste.setPrefWidth(100);

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
            showAlert("Attention", "Veuillez selectionner un joueur", Alert.AlertType.WARNING);
            return;
        }
        ouvrirFormulaireJoueur(selected);
    }

    @FXML
    private void supprimerJoueur() {
        Joueur selected = tableJoueurs.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez selectionner un joueur", Alert.AlertType.WARNING);
            return;
        }

        Optional<ButtonType> result = showConfirmation("Confirmation", 
            "Voulez-vous vraiment supprimer ce joueur ?");
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (joueurDAO.supprimerJoueur(selected.getId())) {
                showAlert("Succes", "Joueur supprime", Alert.AlertType.INFORMATION);
                chargerJoueurs();
            } else {
                showAlert("Erreur", "Erreur lors de la suppression", Alert.AlertType.ERROR);
            }
        }
    }

    private void ouvrirFormulaireJoueur(Joueur joueur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/form-joueur.fxml"));
            VBox root = loader.load();
            
            FormJoueurController controller = loader.getController();
            if (joueur != null) controller.setJoueur(joueur);
            controller.setOnSaveCallback(this::chargerJoueurs);
            
            Stage stage = new Stage();
            stage.setTitle(joueur == null ? "Ajouter un joueur" : "Modifier un joueur");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de l'ouverture du formulaire joueur : " + e.getMessage());
            showAlert("Erreur", "Impossible d'ouvrir le formulaire", Alert.AlertType.ERROR);
        }
    }

    @SuppressWarnings("unchecked")
    private void initialiserTablesEquipes() {
        TableColumn<Equipe, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(40);

        TableColumn<Equipe, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(120);

        TableColumn<Equipe, String> colPays = new TableColumn<>("Pays");
        colPays.setCellValueFactory(new PropertyValueFactory<>("pays"));
        colPays.setPrefWidth(100);

        TableColumn<Equipe, String> colEntraineur = new TableColumn<>("Entraineur");
        colEntraineur.setCellValueFactory(new PropertyValueFactory<>("entraineur"));
        colEntraineur.setPrefWidth(120);

        TableColumn<Equipe, Integer> colNbJoueurs = new TableColumn<>("Joueurs");
        colNbJoueurs.setCellValueFactory(new PropertyValueFactory<>("nombreJoueurs"));
        colNbJoueurs.setPrefWidth(60);

        TableColumn<Equipe, Integer> colMatchs = new TableColumn<>("MJ");
        colMatchs.setCellValueFactory(new PropertyValueFactory<>("matchsJoues"));
        colMatchs.setPrefWidth(40);

        TableColumn<Equipe, Integer> colVictoires = new TableColumn<>("V");
        colVictoires.setCellValueFactory(new PropertyValueFactory<>("victoires"));
        colVictoires.setPrefWidth(35);

        TableColumn<Equipe, Integer> colNuls = new TableColumn<>("N");
        colNuls.setCellValueFactory(new PropertyValueFactory<>("nuls"));
        colNuls.setPrefWidth(35);

        TableColumn<Equipe, Integer> colDefaites = new TableColumn<>("D");
        colDefaites.setCellValueFactory(new PropertyValueFactory<>("defaites"));
        colDefaites.setPrefWidth(35);

        TableColumn<Equipe, Integer> colBM = new TableColumn<>("BM");
        colBM.setCellValueFactory(new PropertyValueFactory<>("butsMarques"));
        colBM.setPrefWidth(40);

        TableColumn<Equipe, Integer> colBE = new TableColumn<>("BE");
        colBE.setCellValueFactory(new PropertyValueFactory<>("butsEncaisses"));
        colBE.setPrefWidth(40);

        TableColumn<Equipe, Integer> colPoints = new TableColumn<>("Pts");
        colPoints.setCellValueFactory(new PropertyValueFactory<>("points"));
        colPoints.setPrefWidth(40);

        tableEquipes.getColumns().clear();
        tableEquipes.getColumns().addAll(colId, colNom, colPays, colEntraineur, colNbJoueurs, 
                                         colMatchs, colVictoires, colNuls, colDefaites, 
                                         colBM, colBE, colPoints);
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
            showAlert("Attention", "Veuillez selectionner une equipe", Alert.AlertType.WARNING);
            return;
        }
        ouvrirFormulaireEquipe(selected);
    }

    @FXML
    private void supprimerEquipe() {
        Equipe selected = tableEquipes.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez selectionner une equipe", Alert.AlertType.WARNING);
            return;
        }

        Optional<ButtonType> result = showConfirmation("Confirmation", 
            "Voulez-vous vraiment supprimer cette equipe ?");
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (equipeDAO.supprimerEquipe(selected.getId())) {
                showAlert("Succes", "Equipe supprimee", Alert.AlertType.INFORMATION);
                chargerEquipes();
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
            controller.setOnSaveCallback(this::chargerEquipes);
            
            Stage stage = new Stage();
            stage.setTitle(equipe == null ? "Ajouter une equipe" : "Modifier une equipe");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de l'ouverture du formulaire équipe : " + e.getMessage());
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
        colNom.setPrefWidth(200);

        TableColumn<Stade, String> colVille = new TableColumn<>("Ville");
        colVille.setCellValueFactory(new PropertyValueFactory<>("ville"));
        colVille.setPrefWidth(120);

        TableColumn<Stade, Integer> colCapacite = new TableColumn<>("Capacite");
        colCapacite.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        colCapacite.setPrefWidth(100);

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
            showAlert("Attention", "Veuillez selectionner un stade", Alert.AlertType.WARNING);
            return;
        }
        ouvrirFormulaireStade(selected);
    }

    @FXML
    private void supprimerStade() {
        Stade selected = tableStades.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez selectionner un stade", Alert.AlertType.WARNING);
            return;
        }

        Optional<ButtonType> result = showConfirmation("Confirmation", 
            "Voulez-vous vraiment supprimer ce stade ?");
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (stadeDAO.supprimerStade(selected.getId())) {
                showAlert("Succes", "Stade supprime", Alert.AlertType.INFORMATION);
                chargerStades();
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
            controller.setOnSaveCallback(this::chargerStades);
            
            Stage stage = new Stage();
            stage.setTitle(stade == null ? "Ajouter un stade" : "Modifier un stade");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de l'ouverture du formulaire stade : " + e.getMessage());
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
        colNom.setPrefWidth(120);

        TableColumn<Groupe, String> colDescription = new TableColumn<>("Description");
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDescription.setPrefWidth(200);

        TableColumn<Groupe, String> colEquipes = new TableColumn<>("Équipes");
        colEquipes.setCellValueFactory(new PropertyValueFactory<>("equipesString"));
        colEquipes.setPrefWidth(250);

        tableGroupes.getColumns().clear();
        tableGroupes.getColumns().addAll(colId, colNom, colDescription, colEquipes);
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
            showAlert("Attention", "Veuillez selectionner un groupe", Alert.AlertType.WARNING);
            return;
        }
        ouvrirFormulaireGroupe(selected);
    }

    @FXML
    private void supprimerGroupe() {
        Groupe selected = tableGroupes.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez selectionner un groupe", Alert.AlertType.WARNING);
            return;
        }

        Optional<ButtonType> result = showConfirmation("Confirmation", 
            "Voulez-vous vraiment supprimer ce groupe ?");
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (groupeDAO.supprimerGroupe(selected.getId())) {
                showAlert("Succes", "Groupe supprime", Alert.AlertType.INFORMATION);
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
            System.err.println("❌ Erreur lors de l'ouverture du formulaire groupe : " + e.getMessage());
            showAlert("Erreur", "Impossible d'ouvrir le formulaire", Alert.AlertType.ERROR);
        }
    }

    @SuppressWarnings("unchecked")
    private void initialiserTablesMatchs() {
        TableColumn<Match, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Match, String> colEquipe1 = new TableColumn<>("Nom");
        colEquipe1.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colEquipe1.setPrefWidth(200);

        TableColumn<Match, String> colEquipe2 = new TableColumn<>("Lieu");
        colEquipe2.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        colEquipe2.setPrefWidth(150);

        TableColumn<Match, String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateMatch"));
        colDate.setPrefWidth(100);

        TableColumn<Match, String> colScore = new TableColumn<>("Score");
        colScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        colScore.setPrefWidth(80);

        tableMatchs.getColumns().clear();
        tableMatchs.getColumns().addAll(colId, colEquipe1, colEquipe2, colDate, colScore);
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
            showAlert("Attention", "Veuillez selectionner un match", Alert.AlertType.WARNING);
            return;
        }
        ouvrirFormulaireMatch(selected);
    }

    @FXML
    private void supprimerMatch() {
        Match selected = tableMatchs.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez selectionner un match", Alert.AlertType.WARNING);
            return;
        }

        Optional<ButtonType> result = showConfirmation("Confirmation", 
            "Voulez-vous vraiment supprimer ce match ?");
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (matchDAO.supprimerMatch(selected.getId())) {
                showAlert("Succes", "Match supprime", Alert.AlertType.INFORMATION);
                chargerMatchs();
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
            controller.setOnSaveCallback(this::chargerMatchs);
            
            Stage stage = new Stage();
            stage.setTitle(match == null ? "Ajouter un match" : "Modifier un match");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de l'ouverture du formulaire match : " + e.getMessage());
            showAlert("Erreur", "Impossible d'ouvrir le formulaire", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void deconnecter() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/accueil.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de la déconnexion : " + e.getMessage());
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
