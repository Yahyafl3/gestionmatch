package com.example.gestionmatch.model;

import java.util.ArrayList;
import java.util.List;

public class Groupe {
    private int id;
    private String nom;
    private String description;
    private List<String> equipesNoms;

    public Groupe() {
        this.equipesNoms = new ArrayList<>();
    }

    public Groupe(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.equipesNoms = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getEquipesNoms() { return equipesNoms; }
    public void setEquipesNoms(List<String> equipesNoms) { this.equipesNoms = equipesNoms; }

    public String getEquipesString() {
        if (equipesNoms == null || equipesNoms.isEmpty()) {
            return "Aucune équipe";
        }
        return String.join(", ", equipesNoms);
    }
}
