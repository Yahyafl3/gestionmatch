package com.example.gestionmatch.services;

import com.example.gestionmatch.model.Utilisateur;
import com.example.gestionmatch.model.TypeUtilisateur;
import com.example.gestionmatch.dao.UtilisateurDAO;
import com.example.gestionmatch.exceptions.UsernameDejaExistantException;
import com.example.gestionmatch.exceptions.EmailDejaUtiliseException;

public class UtilisateurService {

    private UtilisateurDAO UtilisateurDAO;

    public UtilisateurService() {
        this.UtilisateurDAO = new UtilisateurDAO();
    }


    public void creer(Utilisateur utilisateur) throws Exception {


        if (UtilisateurDAO.existeUsername(utilisateur.getNomUtilisateur())) {
            throw new UsernameDejaExistantException(
                    "Le nom d'utilisateur '" + utilisateur.getNomUtilisateur() + "' existe déjà"
            );
        }



        if (UtilisateurDAO.existeEmail(utilisateur.getEmail())) {
            throw new EmailDejaUtiliseException(
                    "L'email '" + utilisateur.getEmail() + "' est déjà utilisé"
            );
        }


        UtilisateurDAO.insert(utilisateur);

        System.out.println("✅ Utilisateur créé avec succès : " + utilisateur.getNomUtilisateur());
    }


    public Utilisateur authentifier(String identifiant, String motDePasse) throws Exception {

        // Chercher l'utilisateur par username ou email
        Utilisateur utilisateur = UtilisateurDAO.findByUsernameOrEmail(identifiant);

        if (utilisateur == null) {
            return null;
        }

        // Vérifier le mot de passe
        if (AuthentificationService.verifierMotDePasse(motDePasse, utilisateur.getMotDePasse())) {
            return utilisateur;
        }

        return null;
    }
}