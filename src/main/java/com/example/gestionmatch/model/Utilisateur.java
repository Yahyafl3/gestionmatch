package com.example.gestionmatch.model;

import java.time.LocalDateTime;

public class Utilisateur {

    private int id;
    private String username;
    private String email;
    private String motDePasse;
    private String nom;
    private String prenom;
    private String role;
    private TypeUtilisateur type;
    private LocalDateTime dateCreation;
    private boolean actif;

    public Utilisateur() {
    }

    public Utilisateur(String email, String motDePasse, String nom, String prenom) {
        this.email = email;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.role = "USER";
        this.type = TypeUtilisateur.USER;
        this.actif = true;
    }

    public Utilisateur(String username, String motDePasse, String email, String nom, TypeUtilisateur type) {
        this.username = username;
        this.motDePasse = motDePasse;
        this.email = email;
        this.nom = nom;
        this.type = type;
        this.role = type.name();
        this.actif = true;
    }

    public Utilisateur(int id, String email, String nom, String prenom, String role, boolean actif) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.type = TypeUtilisateur.valueOf(role);
        this.actif = actif;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public TypeUtilisateur getType() { return type; }
    public void setType(TypeUtilisateur type) { this.type = type; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }

    public String getNomComplet() {
        return prenom + " " + nom;
    }

    public String getNomUtilisateur() {
        return username != null ? username : email;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
