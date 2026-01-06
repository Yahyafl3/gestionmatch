-- Ajouter la colonne image_path à la table equipe
USE gestionmatch;

-- Vérifier si la table s'appelle 'equipe' ou 'equipes'
-- et ajouter la colonne image_path

ALTER TABLE equipe ADD COLUMN IF NOT EXISTS image_path VARCHAR(255);

SELECT '✅ Colonne image_path ajoutée avec succès!' AS Status;
