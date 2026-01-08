-- ============================================
-- Préparation de la base de données
-- ============================================

USE gestionmatch;

-- Ajouter les colonnes manquantes dans la table joueurs
ALTER TABLE joueurs ADD COLUMN nationalite VARCHAR(100);
ALTER TABLE joueurs ADD COLUMN numero_maillot INT;
ALTER TABLE joueurs ADD COLUMN buts_marques INT DEFAULT 0;

-- Ajouter la colonne image_path dans la table equipes
ALTER TABLE equipes ADD COLUMN image_path VARCHAR(255);

-- Vérifier la structure
DESCRIBE joueurs;
DESCRIBE equipes;

SELECT '✅ Base de données préparée avec succès!' AS Status;
