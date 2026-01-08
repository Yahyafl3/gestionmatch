package com.example.gestionmatch.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.example.gestionmatch.dao.EquipeDAO;
import com.example.gestionmatch.dao.MatchDAO;
import com.example.gestionmatch.model.Equipe;
import com.example.gestionmatch.model.Match;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;

public class CalendarPageController {

    @FXML private ScrollPane scrollPane;
    @FXML private HBox groupsContainer;
    @FXML private VBox matchScheduleContainer;
    @FXML private VBox knockoutContainer;

    private final MatchDAO matchDAO = new MatchDAO();
    private final EquipeDAO equipeDAO = new EquipeDAO();
    private List<Match> allMatches;
    private List<Equipe> allEquipes;

    // Group colors matching World Cup style
    private final Map<String, String> groupColors = Map.of(
        "A", "#8B1C1C",
        "B", "#C1272D",
        "C", "#00A8E8",
        "D", "#D4AF37",
        "E", "#E91E63",
        "F", "#4CAF50",
        "G", "#00BCD4",
        "H", "#FF9800"
    );

    @FXML
    public void initialize() {
        loadData();
        buildGroupsSection();
        buildMatchSchedule();
        buildKnockoutStage();
    }

    private void loadData() {
        allMatches = matchDAO.getAllMatchs();
        allEquipes = equipeDAO.getAllEquipes();
        System.out.println("✅ " + allMatches.size() + " matchs et " + allEquipes.size() + " équipes chargés");
    }

    private void buildGroupsSection() {
        groupsContainer.getChildren().clear();
        
        // Get all groups (A-H)
        Map<String, List<Equipe>> equipesByGroup = allEquipes.stream()
            .filter(e -> e.getGroupeLettre() != null && !e.getGroupeLettre().isEmpty())
            .collect(Collectors.groupingBy(Equipe::getGroupeLettre));

        // Sort groups alphabetically
        List<String> sortedGroups = new ArrayList<>(equipesByGroup.keySet());
        Collections.sort(sortedGroups);

        for (String groupe : sortedGroups) {
            VBox groupBox = createGroupBox(groupe, equipesByGroup.get(groupe));
            groupsContainer.getChildren().add(groupBox);
        }
    }

    private VBox createGroupBox(String groupe, List<Equipe> equipes) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.TOP_CENTER);
        box.setStyle("-fx-background-color: white; -fx-padding: 8; -fx-background-radius: 6; " +
                     "-fx-border-color: " + groupColors.getOrDefault(groupe, "#666666") + "; " +
                     "-fx-border-width: 2; -fx-border-radius: 6; -fx-min-width: 120; -fx-max-width: 120;");

        // Group header
        Label groupLabel = new Label("Groupe " + groupe);
        groupLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 700; -fx-text-fill: white; " +
                           "-fx-background-color: " + groupColors.getOrDefault(groupe, "#666666") + "; " +
                           "-fx-padding: 6 12; -fx-background-radius: 4;");
        box.getChildren().add(groupLabel);

        // Teams list
        for (Equipe equipe : equipes) {
            HBox teamRow = new HBox(5);
            teamRow.setAlignment(Pos.CENTER_LEFT);
            teamRow.setStyle("-fx-padding: 3; -fx-background-color: #f8f9fa; -fx-background-radius: 3;");

            // Flag
            if (equipe.getImagePath() != null && !equipe.getImagePath().isEmpty()) {
                try {
                    String imagePath = "/images/equipes/" + equipe.getImagePath();
                    java.io.InputStream stream = getClass().getResourceAsStream(imagePath);
                    if (stream != null) {
                        ImageView flag = new ImageView(new Image(stream));
                        flag.setFitWidth(20);
                        flag.setFitHeight(15);
                        flag.setPreserveRatio(true);
                        teamRow.getChildren().add(flag);
                    }
                } catch (Exception e) {
                    // Ignore
                }
            }

            // Team name
            Label teamName = new Label(equipe.getNom());
            teamName.setStyle("-fx-font-size: 10px; -fx-font-weight: 600; -fx-text-fill: #1a1a1a;");
            teamName.setMaxWidth(85);
            teamName.setWrapText(false);
            teamRow.getChildren().add(teamName);

            box.getChildren().add(teamRow);
        }

        return box;
    }

    private void buildMatchSchedule() {
        matchScheduleContainer.getChildren().clear();

        // Group matches by date
        Map<LocalDate, List<Match>> matchesByDate = allMatches.stream()
            .filter(m -> m.getDateMatch() != null)
            .collect(Collectors.groupingBy(Match::getDateMatch));

        // Sort dates
        List<LocalDate> sortedDates = new ArrayList<>(matchesByDate.keySet());
        Collections.sort(sortedDates);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM", Locale.FRENCH);

        // Create a grid-like layout for matches
        VBox scheduleGrid = new VBox(12);
        scheduleGrid.setStyle("-fx-background-color: #8B1C1C; -fx-padding: 20; -fx-background-radius: 8;");

        for (LocalDate date : sortedDates) {
            VBox dateColumn = new VBox(10);
            dateColumn.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-padding: 12; -fx-background-radius: 6;");

            // Date header
            Label dateLabel = new Label(date.format(dateFormatter));
            dateLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: 700; -fx-text-fill: white; " +
                             "-fx-background-color: rgba(0,0,0,0.3); -fx-padding: 8 16; -fx-background-radius: 4;");
            dateColumn.getChildren().add(dateLabel);

            // Matches for this date in a horizontal flow
            HBox matchesRow = new HBox(12);
            matchesRow.setAlignment(Pos.CENTER_LEFT);

            for (Match match : matchesByDate.get(date)) {
                VBox matchBox = createScheduleMatchBox(match);
                matchesRow.getChildren().add(matchBox);
            }

            dateColumn.getChildren().add(matchesRow);
            scheduleGrid.getChildren().add(dateColumn);
        }

        matchScheduleContainer.getChildren().add(scheduleGrid);
    }

    private VBox createScheduleMatchBox(Match match) {
        VBox box = new VBox(8);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: white; -fx-padding: 16; -fx-background-radius: 6; -fx-min-width: 200; -fx-min-height: 140;");

        // Parse teams
        String equipeA = match.getEquipeA();
        String equipeB = match.getEquipeB();

        if ((equipeA == null || equipeA.isEmpty()) && match.getNom() != null && match.getNom().contains(" vs ")) {
            String[] teams = match.getNom().split(" vs ");
            if (teams.length == 2) {
                equipeA = teams[0].trim();
                equipeB = teams[1].trim();
            }
        }

        // Get group color for this match
        Equipe eq = equipeDAO.getEquipeByNom(equipeA);
        String groupeLetter = (eq != null && eq.getGroupeLettre() != null) ? eq.getGroupeLettre() : "A";
        String matchColor = groupColors.getOrDefault(groupeLetter, "#666666");

        // Team A with flag
        HBox teamABox = createCompactTeamRow(equipeA, matchColor);
        box.getChildren().add(teamABox);

        // VS
        Label vsLabel = new Label("VS");
        vsLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 700; -fx-text-fill: #C1272D;");
        box.getChildren().add(vsLabel);

        // Team B with flag
        HBox teamBBox = createCompactTeamRow(equipeB, matchColor);
        box.getChildren().add(teamBBox);

        // Time
        String time = match.getScore() != null && !match.getScore().equals("-") ? match.getScore() : "20:00";
        Label timeLabel = new Label(time);
        timeLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #1a1a1a; -fx-font-weight: 700;");
        box.getChildren().add(timeLabel);

        // Stadium
        if (match.getLieu() != null && !match.getLieu().isEmpty()) {
            String stadium = match.getLieu().length() > 20 ? match.getLieu().substring(0, 20) + "..." : match.getLieu();
            Label stadiumLabel = new Label(stadium);
            stadiumLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #666666; -fx-font-weight: 600;");
            box.getChildren().add(stadiumLabel);
        }

        return box;
    }

    private HBox createCompactTeamRow(String teamName, String color) {
        HBox row = new HBox(6);
        row.setAlignment(Pos.CENTER);
        row.setStyle("-fx-background-color: " + color + "; -fx-padding: 6 12; -fx-background-radius: 4;");

        if (teamName != null && !teamName.isEmpty()) {
            Equipe equipe = equipeDAO.getEquipeByNom(teamName);
            
            // Flag
            if (equipe != null && equipe.getImagePath() != null && !equipe.getImagePath().isEmpty()) {
                try {
                    String imagePath = "/images/equipes/" + equipe.getImagePath();
                    java.io.InputStream stream = getClass().getResourceAsStream(imagePath);
                    if (stream != null) {
                        ImageView flag = new ImageView(new Image(stream));
                        flag.setFitWidth(24);
                        flag.setFitHeight(18);
                        flag.setPreserveRatio(true);
                        row.getChildren().add(flag);
                    }
                } catch (Exception e) {
                    // Ignore
                }
            }

            // Team name
            String displayName = teamName.length() > 12 ? teamName.substring(0, 12) : teamName;
            Label name = new Label(displayName);
            name.setStyle("-fx-font-size: 12px; -fx-font-weight: 700; -fx-text-fill: white;");
            row.getChildren().add(name);
        }

        return row;
    }

    private void buildKnockoutStage() {
        knockoutContainer.getChildren().clear();
        knockoutContainer.setAlignment(Pos.CENTER);

        // Create bracket layout
        HBox bracketLayout = new HBox(40);
        bracketLayout.setAlignment(Pos.CENTER);
        bracketLayout.setStyle("-fx-padding: 30;");

        // Round of 16 (Left side)
        VBox round16Left = createKnockoutRound("Huitièmes de finale", 4, true);
        
        // Quarter finals (Left side)
        VBox quarterLeft = createKnockoutRound("Quarts de finale", 2, true);
        
        // Semi finals
        VBox semiFinals = createKnockoutRound("Demi-finales", 2, false);
        
        // Final
        VBox finalMatch = createFinalSection();
        
        // Quarter finals (Right side)
        VBox quarterRight = createKnockoutRound("Quarts de finale", 2, true);
        
        // Round of 16 (Right side)
        VBox round16Right = createKnockoutRound("Huitièmes de finale", 4, true);

        bracketLayout.getChildren().addAll(round16Left, quarterLeft, semiFinals, finalMatch, quarterRight, round16Right);
        knockoutContainer.getChildren().add(bracketLayout);
    }

    private VBox createKnockoutRound(String roundName, int matchCount, boolean showLabel) {
        VBox round = new VBox(15);
        round.setAlignment(Pos.CENTER);

        if (showLabel) {
            Label label = new Label(roundName);
            label.setStyle("-fx-font-size: 12px; -fx-font-weight: 700; -fx-text-fill: #FFD700; -fx-padding: 5;");
            round.getChildren().add(label);
        }

        for (int i = 0; i < matchCount; i++) {
            VBox matchBox = createKnockoutMatchBox("1er Groupe ?", "2ème Groupe ?");
            round.getChildren().add(matchBox);
        }

        return round;
    }

    private VBox createKnockoutMatchBox(String team1, String team2) {
        VBox box = new VBox(3);
        box.setStyle("-fx-background-color: white; -fx-padding: 8; -fx-background-radius: 4; -fx-min-width: 140;");

        // Team 1
        Label t1 = new Label(team1);
        t1.setStyle("-fx-font-size: 10px; -fx-font-weight: 600; -fx-text-fill: #1a1a1a; " +
                   "-fx-background-color: #f0f0f0; -fx-padding: 4 8; -fx-background-radius: 2;");
        box.getChildren().add(t1);

        // VS
        Label vs = new Label("vs");
        vs.setStyle("-fx-font-size: 9px; -fx-text-fill: #999999; -fx-alignment: center;");
        box.getChildren().add(vs);

        // Team 2
        Label t2 = new Label(team2);
        t2.setStyle("-fx-font-size: 10px; -fx-font-weight: 600; -fx-text-fill: #1a1a1a; " +
                   "-fx-background-color: #f0f0f0; -fx-padding: 4 8; -fx-background-radius: 2;");
        box.getChildren().add(t2);

        return box;
    }

    private VBox createFinalSection() {
        VBox section = new VBox(15);
        section.setAlignment(Pos.CENTER);
        section.setStyle("-fx-padding: 20;");

        // Trophy icon (using text)
        Label trophy = new Label("🏆");
        trophy.setStyle("-fx-font-size: 40px;");
        section.getChildren().add(trophy);

        // Final label
        Label finalLabel = new Label("FINALE");
        finalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 700; -fx-text-fill: #FFD700; " +
                          "-fx-background-color: rgba(0,0,0,0.3); -fx-padding: 8 20; -fx-background-radius: 6;");
        section.getChildren().add(finalLabel);

        // Final match
        VBox finalMatch = createKnockoutMatchBox("Vainqueur DF1", "Vainqueur DF2");
        section.getChildren().add(finalMatch);

        // Date placeholder
        Label dateLabel = new Label("Dimanche 21 décembre");
        dateLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: rgba(255,255,255,0.8); -fx-font-weight: 600;");
        section.getChildren().add(dateLabel);

        return section;
    }
}
