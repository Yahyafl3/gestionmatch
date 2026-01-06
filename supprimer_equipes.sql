-- Supprimer toutes les équipes existantes
USE gestionmatch;

-- Désactiver les contraintes de clés étrangères temporairement
SET FOREIGN_KEY_CHECKS = 0;

-- Supprimer toutes les équipes
DELETE FROM equipe;

-- Réinitialiser l'auto-increment
ALTER TABLE equipe AUTO_INCREMENT = 1;

-- Réactiver les contraintes
SET FOREIGN_KEY_CHECKS = 1;

SELECT '✅ Toutes les équipes ont été supprimées!' AS Status;
