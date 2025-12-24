package com.example.gestionmatch.model;

import java.time.LocalDate;

public class Match {

    private int id;
    private String nom;
    private String score;
    private LocalDate dateMatch;
    private String lieu;
    private String equipeA;
    private String equipeB;

    // Constructeur vide
    public Match() {
    }

    // Constructeur complet
    public Match(int id, String nom, String score, LocalDate dateMatch) {
        this.id = id;
        this.nom = nom;
        this.score = score;
        this.dateMatch = dateMatch;
    }

    // Constructeur sans ID (pour insertion)
    public Match(String nom, String score, LocalDate dateMatch) {
        this.nom = nom;
        this.score = score;
        this.dateMatch = dateMatch;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getScore() { return score; }
    public void setScore(String score) { this.score = score; }

    public LocalDate getDateMatch() { return dateMatch; }
    public void setDateMatch(LocalDate dateMatch) { this.dateMatch = dateMatch; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public String getEquipeA() { return equipeA; }
    public void setEquipeA(String equipeA) { this.equipeA = equipeA; }

    public String getEquipeB() { return equipeB; }
    public void setEquipeB(String equipeB) { this.equipeB = equipeB; }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", score='" + score + '\'' +
                ", dateMatch=" + dateMatch +
                '}';
    }
}