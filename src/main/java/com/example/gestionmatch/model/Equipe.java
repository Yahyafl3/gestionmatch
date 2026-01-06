package com.example.gestionmatch.model;

import java.time.LocalDateTime;

public class Equipe {

    private int id;
    private String nom;
    private String pays;
    private String entraineur;
    private int nombreJoueurs;
    private LocalDateTime dateCreation;
    private String imagePath; // Chemin vers l'image de l'équipe

    private Integer groupeId;
    private String groupeLettre;

    private int matchsJoues;
    private int victoires;
    private int nuls;
    private int defaites;
    private int butsMarques;
    private int butsEncaisses;
    private int points;

    public Equipe() {
        this.nombreJoueurs = 0;
        this.matchsJoues = 0;
        this.victoires = 0;
        this.nuls = 0;
        this.defaites = 0;
        this.butsMarques = 0;
        this.butsEncaisses = 0;
        this.points = 0;
    }

    public Equipe(String nom, String pays, String entraineur) {
        this();
        this.nom = nom;
        this.pays = pays;
        this.entraineur = entraineur;
    }

    public Equipe(int id, String nom, String pays, String entraineur, int nombreJoueurs) {
        this();
        this.id = id;
        this.nom = nom;
        this.pays = pays;
        this.entraineur = entraineur;
        this.nombreJoueurs = nombreJoueurs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getEntraineur() {
        return entraineur;
    }

    public void setEntraineur(String entraineur) {
        this.entraineur = entraineur;
    }

    public int getNombreJoueurs() {
        return nombreJoueurs;
    }

    public void setNombreJoueurs(int nombreJoueurs) {
        this.nombreJoueurs = nombreJoueurs;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(Integer groupeId) {
        this.groupeId = groupeId;
    }

    public String getGroupeLettre() {
        return groupeLettre;
    }

    public void setGroupeLettre(String groupeLettre) {
        this.groupeLettre = groupeLettre;
    }

    public int getMatchsJoues() {
        return matchsJoues;
    }

    public void setMatchsJoues(int matchsJoues) {
        this.matchsJoues = matchsJoues;
    }

    public int getVictoires() {
        return victoires;
    }

    public void setVictoires(int victoires) {
        this.victoires = victoires;
    }

    public int getNuls() {
        return nuls;
    }

    public void setNuls(int nuls) {
        this.nuls = nuls;
    }

    public int getDefaites() {
        return defaites;
    }

    public void setDefaites(int defaites) {
        this.defaites = defaites;
    }

    public int getButsMarques() {
        return butsMarques;
    }

    public void setButsMarques(int butsMarques) {
        this.butsMarques = butsMarques;
    }

    public int getButsEncaisses() {
        return butsEncaisses;
    }

    public void setButsEncaisses(int butsEncaisses) {
        this.butsEncaisses = butsEncaisses;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getDifferenceButs() {
        return butsMarques - butsEncaisses;
    }


    public void ajouterVictoire(int butsMarques, int butsEncaisses) {
        this.matchsJoues++;
        this.victoires++;
        this.butsMarques += butsMarques;
        this.butsEncaisses += butsEncaisses;
        this.points += 3;
    }

    public void ajouterNul(int butsMarques, int butsEncaisses) {
        this.matchsJoues++;
        this.nuls++;
        this.butsMarques += butsMarques;
        this.butsEncaisses += butsEncaisses;
        this.points += 1;
    }

    public void ajouterDefaite(int butsMarques, int butsEncaisses) {
        this.matchsJoues++;
        this.defaites++;
        this.butsMarques += butsMarques;
        this.butsEncaisses += butsEncaisses;
    }

    public void reinitialiserStatistiques() {
        this.matchsJoues = 0;
        this.victoires = 0;
        this.nuls = 0;
        this.defaites = 0;
        this.butsMarques = 0;
        this.butsEncaisses = 0;
        this.points = 0;
    }

    public String afficherStatistiques() {
        return String.format(
                "⚽ %s (%s)\n" +
                        "📊 Statistiques:\n" +
                        "   Matchs: %d | V: %d | N: %d | D: %d\n" +
                        "   Buts: %d-%d (Diff: %+d)\n" +
                        "   Points: %d",
                nom, pays, matchsJoues, victoires, nuls, defaites,
                butsMarques, butsEncaisses, getDifferenceButs(), points
        );
    }

    public String getIdentifiantComplet() {
        if (groupeLettre != null) {
            return String.format("%s (%s) - Groupe %s", nom, pays, groupeLettre);
        }
        return String.format("%s (%s)", nom, pays);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipe equipe = (Equipe) o;
        return id == equipe.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return nom + " (" + pays + ")";
    }
}