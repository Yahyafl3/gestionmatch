package com.example.gestionmatch.model;

public class Stade {
    private int id;
    private String nom;
    private String ville;
    private String pays;
    private int capacite;

    public Stade() {}

    public Stade(int id, String nom, String ville, String pays, int capacite) {
        this.id = id;
        this.nom = nom;
        this.ville = ville;
        this.pays = pays;
        this.capacite = capacite;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getPays() { return pays; }
    public void setPays(String pays) { this.pays = pays; }

    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }

    @Override
    public String toString() {
        return nom + " - " + ville + ", " + pays + " (" + capacite + " places)";
    }
}
