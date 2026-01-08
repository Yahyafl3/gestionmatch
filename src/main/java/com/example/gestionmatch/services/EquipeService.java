package com.example.gestionmatch.services;

import java.sql.SQLException;
import java.util.List;

import com.example.gestionmatch.dao.EquipeDAO;
import com.example.gestionmatch.exceptions.EquipeDejaExistanteException;
import com.example.gestionmatch.exceptions.EquipeNonTrouveeException;
import com.example.gestionmatch.model.Equipe;

public class EquipeService {

    private EquipeDAO equipeDAO;

    public EquipeService() {
        this.equipeDAO = new EquipeDAO();
    }

    public void creer(Equipe equipe) throws EquipeDejaExistanteException, SQLException {
        if (equipeDAO.existe(equipe.getNom())) {
            throw new EquipeDejaExistanteException("L'équipe '" + equipe.getNom() + "' existe déjà");
        }

        validerEquipe(equipe);
        equipeDAO.insert(equipe);
        System.out.println("✅ Équipe créée : " + equipe.getNom());
    }

    public Equipe trouverParId(int id) throws EquipeNonTrouveeException, SQLException {
        Equipe equipe = equipeDAO.findById(id);

        if (equipe == null) {
            throw new EquipeNonTrouveeException("Équipe non trouvée avec l'ID : " + id);
        }

        return equipe;
    }

    public Equipe trouverParNom(String nom) throws EquipeNonTrouveeException, SQLException {
        Equipe equipe = equipeDAO.findByNom(nom);

        if (equipe == null) {
            throw new EquipeNonTrouveeException("Équipe non trouvée : " + nom);
        }

        return equipe;
    }

    public List<Equipe> trouverParPays(String pays) throws SQLException {
        return equipeDAO.findByPays(pays);
    }

    public List<Equipe> trouverParGroupe(int groupeId) throws SQLException {
        return equipeDAO.findByGroupe(groupeId);
    }

    public List<Equipe> listerTous() throws SQLException {
        return equipeDAO.findAll();
    }

    public void modifier(Equipe equipe) throws SQLException {
        validerEquipe(equipe);
        equipeDAO.update(equipe);
        System.out.println("✅ Équipe modifiée : " + equipe.getNom());
    }

    public void supprimer(int id) throws SQLException {
        equipeDAO.delete(id);
        System.out.println("✅ Équipe supprimée");
    }

    public void assignerGroupe(int equipeId, int groupeId) throws SQLException {
        equipeDAO.assignerGroupe(equipeId, groupeId);
        System.out.println("✅ Équipe assignée au groupe");
    }

    public void retirerDuGroupe(int equipeId) throws SQLException {
        equipeDAO.retirerDuGroupe(equipeId);
        System.out.println("✅ Équipe retirée du groupe");
    }

    public void mettreAJourStatistiques(Equipe equipe) throws SQLException {
        equipeDAO.updateStatistiques(equipe);
        System.out.println("✅ Statistiques mises à jour");
    }

    private void validerEquipe(Equipe equipe) {
        if (equipe.getNom() == null || equipe.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'équipe est obligatoire");
        }

        if (equipe.getPays() == null || equipe.getPays().trim().isEmpty()) {
            throw new IllegalArgumentException("Le pays est obligatoire");
        }

        if (equipe.getEntraineur() == null || equipe.getEntraineur().trim().isEmpty()) {
            throw new IllegalArgumentException("L'entraîneur est obligatoire");
        }

        if (equipe.getNombreJoueurs() < 0 || equipe.getNombreJoueurs() > 26) {
            throw new IllegalArgumentException("Le nombre de joueurs doit être entre 0 et 26");
        }
    }
}