package com.example.gestionmatch.model;

import java.time.LocalDate;

public class Joueur {

    private int id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String poste;
    private int numero;
    private int equipeId;

    // Constructeur vide
    public Joueur() {
    }

    // Constructeur complet
    public Joueur(int id, String nom, String prenom, LocalDate dateNaissance,
                  String poste, int numero, int equipeId) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.poste = poste;
        this.numero = numero;
        this.equipeId = equipeId;
    }

    // Constructeur sans ID
    public Joueur(String nom, String prenom, LocalDate dateNaissance,
                  String poste, int numero, int equipeId) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.poste = poste;
        this.numero = numero;
        this.equipeId = equipeId;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getPoste() { return poste; }
    public void setPoste(String poste) { this.poste = poste; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public int getEquipeId() { return equipeId; }
    public void setEquipeId(int equipeId) { this.equipeId = equipeId; }

    public String getNomComplet() {
        return prenom + " " + nom;
    }

    @Override
    public String toString() {
        return "Joueur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", poste='" + poste + '\'' +
                ", numero=" + numero +
                '}';
    }
}