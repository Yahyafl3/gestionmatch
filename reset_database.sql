-- Script pour réinitialiser la base de données
-- Cela supprimera toutes les données existantes

USE gestionmatch;

-- Désactiver les contraintes de clés étrangères temporairement
SET FOREIGN_KEY_CHECKS = 0;

-- Supprimer toutes les données des tables
TRUNCATE TABLE groupe_equipe;
TRUNCATE TABLE joueur;
TRUNCATE TABLE match_data;
TRUNCATE TABLE equipe;
TRUNCATE TABLE stade;
TRUNCATE TABLE groupe;

-- Ne pas supprimer les utilisateurs pour garder le compte admin
-- TRUNCATE TABLE utilisateur;

-- Réactiver les contraintes
SET FOREIGN_KEY_CHECKS = 1;

SELECT '✅ Base de données réinitialisée ! Relancez l\'application pour charger les nouvelles données.' AS Status;
