package com.example.gestionmatch.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.gestionmatch.dao.EquipeDAO;
import com.example.gestionmatch.dao.MatchDAO;
import com.example.gestionmatch.model.Equipe;
import com.example.gestionmatch.model.Match;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CalendarPageController {

    @FXML private Label monthYearLabel;
    @FXML private GridPane calendarGrid;
    @FXML private VBox matchListContainer;
    @FXML private Button btnPrevious;
    @FXML private Button btnNext;
    @FXML private ScrollPane scrollPane;

    private final MatchDAO matchDAO = new MatchDAO();
    private final EquipeDAO equipeDAO = new EquipeDAO();
    private YearMonth currentMonth;
    private LocalDate selectedDate;
    private List<Match> allMatches;

    @FXML
    public void initialize() {
        currentMonth = YearMonth.now();
        selectedDate = LocalDate.now();
        loadMatches();
        updateCalendar();
    }

    private void loadMatches() {
        allMatches = matchDAO.getAllMatchs();
        System.out.println("✅ " + allMatches.size() + " matchs chargés pour le calendrier");
    }

    @FXML
    private void previousMonth() {
        currentMonth = currentMonth.minusMonths(1);
        updateCalendar();
    }

    @FXML
    private void nextMonth() {
        currentMonth = currentMonth.plusMonths(1);
        updateCalendar();
    }

    private void updateCalendar() {
        // Update month/year label
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.FRENCH);
        monthYearLabel.setText(currentMonth.format(formatter));

        // Clear calendar grid (keep header row)
        calendarGrid.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

        // Get first day of month and number of days
        LocalDate firstDay = currentMonth.atDay(1);
        int daysInMonth = currentMonth.lengthOfMonth();
        int startDayOfWeek = firstDay.getDayOfWeek().getValue(); // 1=Monday, 7=Sunday

        // Group matches by date
        Map<LocalDate, List<Match>> matchesByDate = allMatches.stream()
            .filter(m -> m.getDateMatch() != null)
            .filter(m -> YearMonth.from(m.getDateMatch()).equals(currentMonth))
            .collect(Collectors.groupingBy(Match::getDateMatch));

        // Fill calendar
        int row = 1;
        int col = startDayOfWeek - 1; // Start from correct day

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = currentMonth.atDay(day);
            List<Match> dayMatches = matchesByDate.get(date);
            int matchCount = dayMatches != null ? dayMatches.size() : 0;

            VBox dayCell = createDayCell(day, date, matchCount);
            calendarGrid.add(dayCell, col, row);

            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }

        // Update match list for selected date
        updateMatchList(selectedDate);
    }

    private VBox createDayCell(int day, LocalDate date, int matchCount) {
        VBox cell = new VBox(6);
        cell.setAlignment(Pos.CENTER);
        cell.setPrefWidth(120);
        cell.setPrefHeight(110);

        // Determine cell style based on date
        String style;
        if (date.equals(LocalDate.now())) {
            // Today - red background
            style = "-fx-background-color: #C1272D; -fx-padding: 18; -fx-background-radius: 10; -fx-cursor: hand;";
        } else if (matchCount > 0) {
            // Has matches - colored based on status
            LocalDate today = LocalDate.now();
            if (date.isBefore(today)) {
                // Past - green
                style = "-fx-background-color: #f0fdf4; -fx-padding: 18; -fx-background-radius: 10; -fx-border-color: #10b981; -fx-border-width: 2; -fx-border-radius: 10; -fx-cursor: hand;";
            } else {
                // Future - blue
                style = "-fx-background-color: #eff6ff; -fx-padding: 18; -fx-background-radius: 10; -fx-border-color: #3b82f6; -fx-border-width: 2; -fx-border-radius: 10; -fx-cursor: hand;";
            }
        } else {
            // No matches - gray
            style = "-fx-background-color: #f8f9fa; -fx-padding: 18; -fx-background-radius: 10; -fx-cursor: hand; -fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-border-radius: 10;";
        }
        cell.setStyle(style);

        // Day number
        Label dayLabel = new Label(String.valueOf(day));
        if (date.equals(LocalDate.now())) {
            dayLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: 700; -fx-text-fill: white;");
        } else {
            dayLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: 700; -fx-text-fill: " + 
                (matchCount > 0 ? "#1a1a1a" : "#666666") + ";");
        }
        cell.getChildren().add(dayLabel);

        // Today indicator or match indicator
        if (date.equals(LocalDate.now())) {
            Label todayLabel = new Label("Aujourd'hui");
            todayLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: rgba(255,255,255,0.95); -fx-font-weight: 600;");
            cell.getChildren().add(todayLabel);
        } else if (matchCount > 0) {
            Label indicator = new Label("●");
            LocalDate today = LocalDate.now();
            String color = date.isBefore(today) ? "#10b981" : "#3b82f6";
            indicator.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 14px;");
            cell.getChildren().add(indicator);

            Label matchLabel = new Label(matchCount + " match" + (matchCount > 1 ? "s" : ""));
            matchLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #666666; -fx-font-weight: 600;");
            cell.getChildren().add(matchLabel);
        }

        // Click handler
        cell.setOnMouseClicked(e -> {
            selectedDate = date;
            updateMatchList(date);
        });

        return cell;
    }

    private void updateMatchList(LocalDate date) {
        matchListContainer.getChildren().clear();

        // Title
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH);
        Label title = new Label("Matchs du " + date.format(formatter));
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #1a1a1a;");
        matchListContainer.getChildren().add(title);

        // Separator
        javafx.scene.control.Separator separator = new javafx.scene.control.Separator();
        separator.setStyle("-fx-background-color: #e0e0e0;");
        matchListContainer.getChildren().add(separator);

        // Get matches for this date
        List<Match> dayMatches = allMatches.stream()
            .filter(m -> m.getDateMatch() != null && m.getDateMatch().equals(date))
            .collect(Collectors.toList());

        if (dayMatches.isEmpty()) {
            Label noMatch = new Label("Aucun match prévu ce jour");
            noMatch.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666; -fx-padding: 20;");
            matchListContainer.getChildren().add(noMatch);
        } else {
            for (Match match : dayMatches) {
                HBox matchBox = createMatchBox(match);
                matchListContainer.getChildren().add(matchBox);
            }
        }
    }

    private HBox createMatchBox(Match match) {
        HBox box = new HBox(30);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 24; -fx-background-radius: 10;");

        // Parse team names
        String nomMatch = match.getNom();
        String equipeA = match.getEquipeA();
        String equipeB = match.getEquipeB();

        // If equipeA/B are null, try to parse from match name
        if ((equipeA == null || equipeA.isEmpty()) && nomMatch != null && nomMatch.contains(" vs ")) {
            String[] teams = nomMatch.split(" vs ");
            if (teams.length == 2) {
                equipeA = teams[0].trim();
                equipeB = teams[1].trim();
            }
        }

        // Team A
        VBox teamABox = new VBox(6);
        teamABox.setAlignment(Pos.CENTER);
        HBox.setHgrow(teamABox, javafx.scene.layout.Priority.ALWAYS);

        if (equipeA != null && !equipeA.isEmpty()) {
            Equipe equipe = equipeDAO.getEquipeByNom(equipeA);
            if (equipe != null && equipe.getImagePath() != null && !equipe.getImagePath().isEmpty()) {
                try {
                    String imagePath = "/images/equipes/" + equipe.getImagePath();
                    java.io.InputStream stream = getClass().getResourceAsStream(imagePath);
                    if (stream != null) {
                        ImageView flag = new ImageView(new Image(stream));
                        flag.setFitWidth(40);
                        flag.setFitHeight(30);
                        flag.setPreserveRatio(true);
                        teamABox.getChildren().add(flag);
                    }
                } catch (Exception e) {
                    // Ignore
                }
            }

            Label nameA = new Label(equipeA);
            nameA.setStyle("-fx-font-weight: 700; -fx-font-size: 18px; -fx-text-fill: #1a1a1a;");
            teamABox.getChildren().add(nameA);

            if (equipe != null) {
                Label groupeA = new Label("Groupe " + (equipe.getGroupeLettre() != null ? equipe.getGroupeLettre() : "?"));
                groupeA.setStyle("-fx-font-size: 13px; -fx-text-fill: #666666; -fx-font-weight: 500;");
                teamABox.getChildren().add(groupeA);
            }
        }

        // VS section
        VBox vsBox = new VBox(6);
        vsBox.setAlignment(Pos.CENTER);

        Label vsLabel = new Label("VS");
        vsLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: 700; -fx-text-fill: #C1272D;");
        vsBox.getChildren().add(vsLabel);

        // Time (if available in score field or default)
        String time = match.getScore() != null && !match.getScore().equals("-") ? match.getScore() : "20:00";
        Label timeLabel = new Label(time);
        timeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666; -fx-font-weight: 600;");
        vsBox.getChildren().add(timeLabel);

        // Team B
        VBox teamBBox = new VBox(6);
        teamBBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(teamBBox, javafx.scene.layout.Priority.ALWAYS);

        if (equipeB != null && !equipeB.isEmpty()) {
            Equipe equipe = equipeDAO.getEquipeByNom(equipeB);
            if (equipe != null && equipe.getImagePath() != null && !equipe.getImagePath().isEmpty()) {
                try {
                    String imagePath = "/images/equipes/" + equipe.getImagePath();
                    java.io.InputStream stream = getClass().getResourceAsStream(imagePath);
                    if (stream != null) {
                        ImageView flag = new ImageView(new Image(stream));
                        flag.setFitWidth(40);
                        flag.setFitHeight(30);
                        flag.setPreserveRatio(true);
                        teamBBox.getChildren().add(flag);
                    }
                } catch (Exception e) {
                    // Ignore
                }
            }

            Label nameB = new Label(equipeB);
            nameB.setStyle("-fx-font-weight: 700; -fx-font-size: 18px; -fx-text-fill: #1a1a1a;");
            teamBBox.getChildren().add(nameB);

            if (equipe != null) {
                Label groupeB = new Label("Groupe " + (equipe.getGroupeLettre() != null ? equipe.getGroupeLettre() : "?"));
                groupeB.setStyle("-fx-font-size: 13px; -fx-text-fill: #666666; -fx-font-weight: 500;");
                teamBBox.getChildren().add(groupeB);
            }
        }

        // Stadium info
        VBox stadiumBox = new VBox(8);
        stadiumBox.setAlignment(Pos.CENTER_RIGHT);

        String stadium = match.getLieu() != null && !match.getLieu().isEmpty() ? match.getLieu() : "Stade à définir";
        Label stadiumLabel = new Label(stadium);
        stadiumLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #666666; -fx-font-weight: 500;");
        stadiumBox.getChildren().add(stadiumLabel);

        Button detailsBtn = new Button("Voir Détails");
        detailsBtn.setStyle("-fx-background-color: #C1272D; -fx-text-fill: white; -fx-padding: 10 24; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 14px; -fx-font-weight: 700;");
        stadiumBox.getChildren().add(detailsBtn);

        box.getChildren().addAll(teamABox, vsBox, teamBBox, stadiumBox);
        return box;
    }
}
