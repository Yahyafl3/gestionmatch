package com.example.gestionmatch.controller;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.example.gestionmatch.dao.EquipeDAO;
import com.example.gestionmatch.dao.GroupeDAO;
import com.example.gestionmatch.dao.JoueurDAO;
import com.example.gestionmatch.dao.MatchDAO;
import com.example.gestionmatch.dao.StadeDAO;
import com.example.gestionmatch.model.Equipe;
import com.example.gestionmatch.model.Groupe;
import com.example.gestionmatch.model.Match;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StatisticsPageController implements Initializable {

    @FXML private BarChart<String, Number> butsChart;
    @FXML private PieChart resultsChart;
    @FXML private GridPane top5Grid;
    @FXML private GridPane groupesStatsGrid;
    
    @FXML private Label totalMatchsLabel;
    @FXML private Label totalButsLabel;
    @FXML private Label statEquipesCard;
    @FXML private Label statJoueursCard;
    @FXML private Label statStadesCard;
    @FXML private Label statGroupesCard;
    
    @FXML private ProgressBar progressEquipes;
    @FXML private ProgressBar progressJoueurs;
    @FXML private ProgressBar progressStades;
    @FXML private ProgressBar progressGroupes;

    private final EquipeDAO equipeDAO = new EquipeDAO();
    private final JoueurDAO joueurDAO = new JoueurDAO();
    private final MatchDAO matchDAO = new MatchDAO();
    private final StadeDAO stadeDAO = new StadeDAO();
    private final GroupeDAO groupeDAO = new GroupeDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chargerStatistiques();
    }

    private void chargerStatistiques() {
        chargerCartesRapides();
        chargerGraphiqueButs();
        chargerGraphiqueResultats();
        chargerTop5Equipes();
        chargerStatistiquesGroupes();
    }
    
    private void chargerCartesRapides() {
        try {
            int nbEquipes = equipeDAO.getAllEquipes().size();
            int nbJoueurs = joueurDAO.getAllJoueurs().size();
            int nbStades = stadeDAO.getAllStades().size();
            int nbGroupes = groupeDAO.getAllGroupes().size();
            int nbMatchs = matchDAO.getAllMatchs().size();
            
            int totalButs = equipeDAO.getAllEquipes().stream()
                .mapToInt(Equipe::getButsMarques)
                .sum();
            
            statEquipesCard.setText(String.valueOf(nbEquipes));
            statJoueursCard.setText(String.valueOf(nbJoueurs));
            statStadesCard.setText(String.valueOf(nbStades));
            statGroupesCard.setText(String.valueOf(nbGroupes));
            
            totalMatchsLabel.setText(nbMatchs + " Matchs");
            totalButsLabel.setText(totalButs + " Buts");
            
            // Progress bars (sur 32 équipes max, 800 joueurs max, 20 stades max, 8 groupes max)
            progressEquipes.setProgress(nbEquipes / 32.0);
            progressJoueurs.setProgress(Math.min(nbJoueurs / 800.0, 1.0));
            progressStades.setProgress(Math.min(nbStades / 20.0, 1.0));
            progressGroupes.setProgress(nbGroupes / 8.0);
            
        } catch (Exception e) {
            System.err.println("Erreur chargement cartes rapides: " + e.getMessage());
        }
    }

    private void chargerGraphiqueButs() {
        try {
            List<Equipe> equipes = equipeDAO.getAllEquipes();
            
            // Trier par buts marqués (top 8)
            equipes.sort((e1, e2) -> Integer.compare(e2.getButsMarques(), e1.getButsMarques()));
            
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Buts");
            
            int count = 0;
            for (Equipe equipe : equipes) {
                if (count >= 8) break;
                series.getData().add(new XYChart.Data<>(equipe.getPays(), equipe.getButsMarques()));
                count++;
            }
            
            butsChart.getData().clear();
            butsChart.getData().add(series);
            butsChart.setLegendVisible(false);
            butsChart.setAnimated(true);
            
        } catch (Exception e) {
            System.err.println("Erreur chargement graphique buts: " + e.getMessage());
        }
    }

    private void chargerGraphiqueResultats() {
        try {
            List<Match> matchs = matchDAO.getAllMatchs();
            
            int victoires = 0;
            int nuls = 0;
            int defaites = 0;
            
            for (Match match : matchs) {
                String score = match.getScore();
                if (score != null && !score.isEmpty() && !score.equals("-")) {
                    String[] parts = score.split("-");
                    if (parts.length == 2) {
                        try {
                            int scoreA = Integer.parseInt(parts[0].trim());
                            int scoreB = Integer.parseInt(parts[1].trim());
                            
                            if (scoreA > scoreB) {
                                victoires++;
                            } else if (scoreA == scoreB) {
                                nuls++;
                            } else {
                                defaites++;
                            }
                        } catch (NumberFormatException e) {
                            // Ignorer les scores invalides
                        }
                    }
                }
            }
            
            resultsChart.getData().clear();
            
            if (victoires > 0) {
                PieChart.Data dataVictoires = new PieChart.Data("Victoires (" + victoires + ")", victoires);
                resultsChart.getData().add(dataVictoires);
            }
            
            if (nuls > 0) {
                PieChart.Data dataNuls = new PieChart.Data("Nuls (" + nuls + ")", nuls);
                resultsChart.getData().add(dataNuls);
            }
            
            if (defaites > 0) {
                PieChart.Data dataDefaites = new PieChart.Data("Défaites (" + defaites + ")", defaites);
                resultsChart.getData().add(dataDefaites);
            }
            
            if (victoires == 0 && nuls == 0 && defaites == 0) {
                PieChart.Data dataAucun = new PieChart.Data("Aucun résultat", 1);
                resultsChart.getData().add(dataAucun);
            }
            
            resultsChart.setLegendVisible(true);
            resultsChart.setAnimated(true);
            
        } catch (Exception e) {
            System.err.println("Erreur chargement graphique résultats: " + e.getMessage());
        }
    }
    
    private void chargerTop5Equipes() {
        try {
            List<Equipe> equipes = equipeDAO.getAllEquipes();
            equipes.sort((e1, e2) -> Integer.compare(e2.getPoints(), e1.getPoints()));
            
            top5Grid.getChildren().clear();
            
            int row = 0;
            int col = 0;
            int count = 0;
            
            for (Equipe equipe : equipes) {
                if (count >= 5) break;
                
                HBox equipeBox = new HBox(15);
                equipeBox.setAlignment(Pos.CENTER_LEFT);
                equipeBox.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 18; -fx-background-radius: 10;");
                
                // Rang
                Label rang = new Label("#" + (count + 1));
                rang.setStyle("-fx-font-size: 24px; -fx-font-weight: 700; -fx-text-fill: " + 
                    (count == 0 ? "#C1272D" : "#64748b") + "; -fx-min-width: 50;");
                
                // Drapeau
                if (equipe.getImagePath() != null && !equipe.getImagePath().isEmpty()) {
                    try {
                        String imagePath = "/images/equipes/" + equipe.getImagePath();
                        java.io.InputStream stream = getClass().getResourceAsStream(imagePath);
                        if (stream != null) {
                            ImageView drapeau = new ImageView(new Image(stream));
                            drapeau.setFitWidth(40);
                            drapeau.setFitHeight(30);
                            drapeau.setPreserveRatio(true);
                            equipeBox.getChildren().add(rang);
                            equipeBox.getChildren().add(drapeau);
                        }
                    } catch (Exception e) {
                        equipeBox.getChildren().add(rang);
                    }
                } else {
                    equipeBox.getChildren().add(rang);
                }
                
                // Infos équipe
                VBox infos = new VBox(4);
                infos.setAlignment(Pos.CENTER_LEFT);
                HBox.setHgrow(infos, javafx.scene.layout.Priority.ALWAYS);
                
                Label nom = new Label(equipe.getPays());
                nom.setStyle("-fx-font-size: 15px; -fx-font-weight: 700; -fx-text-fill: #1a1a1a;");
                
                Label stats = new Label(equipe.getVictoires() + "V - " + equipe.getNuls() + "N - " + equipe.getDefaites() + "D");
                stats.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748b;");
                
                infos.getChildren().addAll(nom, stats);
                equipeBox.getChildren().add(infos);
                
                // Points
                VBox pointsBox = new VBox(2);
                pointsBox.setAlignment(Pos.CENTER);
                
                Label points = new Label(String.valueOf(equipe.getPoints()));
                points.setStyle("-fx-font-size: 28px; -fx-font-weight: 700; -fx-text-fill: #C1272D;");
                
                Label ptsLabel = new Label("pts");
                ptsLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #64748b;");
                
                pointsBox.getChildren().addAll(points, ptsLabel);
                equipeBox.getChildren().add(pointsBox);
                
                top5Grid.add(equipeBox, col, row);
                
                col++;
                if (col > 1) {
                    col = 0;
                    row++;
                }
                count++;
            }
            
        } catch (Exception e) {
            System.err.println("Erreur chargement top 5: " + e.getMessage());
        }
    }
    
    private void chargerStatistiquesGroupes() {
        try {
            List<Groupe> groupes = groupeDAO.getAllGroupes();
            List<Equipe> equipes = equipeDAO.getAllEquipes();
            
            groupesStatsGrid.getChildren().clear();
            
            int col = 0;
            int row = 0;
            
            for (Groupe groupe : groupes) {
                VBox groupeBox = new VBox(12);
                groupeBox.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 20; -fx-background-radius: 10;");
                
                // Nom du groupe
                Label nomGroupe = new Label("Groupe " + groupe.getNom());
                nomGroupe.setStyle("-fx-font-size: 16px; -fx-font-weight: 700; -fx-text-fill: #1a1a1a;");
                
                // Séparateur
                javafx.scene.control.Separator sep = new javafx.scene.control.Separator();
                sep.setStyle("-fx-background-color: #e5e7eb;");
                
                // Statistiques
                List<Equipe> equipesGroupe = equipes.stream()
                    .filter(e -> e.getGroupeLettre() != null && e.getGroupeLettre().equals(groupe.getNom()))
                    .collect(Collectors.toList());
                
                int totalMatchs = equipesGroupe.stream().mapToInt(Equipe::getMatchsJoues).sum();
                int totalButs = equipesGroupe.stream().mapToInt(Equipe::getButsMarques).sum();
                
                VBox stats = new VBox(8);
                
                HBox matchsBox = createStatRow("Matchs joués:", String.valueOf(totalMatchs));
                HBox butsBox = createStatRow("Buts marqués:", String.valueOf(totalButs));
                HBox equipesBox = createStatRow("Équipes:", String.valueOf(equipesGroupe.size()));
                
                stats.getChildren().addAll(matchsBox, butsBox, equipesBox);
                
                groupeBox.getChildren().addAll(nomGroupe, sep, stats);
                
                groupesStatsGrid.add(groupeBox, col, row);
                
                col++;
                if (col > 3) {
                    col = 0;
                    row++;
                }
            }
            
        } catch (Exception e) {
            System.err.println("Erreur chargement stats groupes: " + e.getMessage());
        }
    }
    
    private HBox createStatRow(String label, String value) {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_LEFT);
        
        Label labelNode = new Label(label);
        labelNode.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");
        
        javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        
        Label valueNode = new Label(value);
        valueNode.setStyle("-fx-font-size: 14px; -fx-font-weight: 700; -fx-text-fill: #1a1a1a;");
        
        box.getChildren().addAll(labelNode, spacer, valueNode);
        return box;
    }
}
