-- Ajouter la colonne nationalite à la table joueur
USE gestionmatch;

-- Vérifier si la colonne existe déjà
SELECT COUNT(*) as colonne_existe
FROM information_schema.COLUMNS 
WHERE TABLE_SCHEMA = 'gestionmatch' 
  AND TABLE_NAME = 'joueur' 
  AND COLUMN_NAME = 'nationalite';

-- Ajouter la colonne si elle n'existe pas
ALTER TABLE joueur 
ADD COLUMN IF NOT EXISTS nationalite VARCHAR(100) DEFAULT NULL;

-- Mettre à jour les nationalités selon les équipes
UPDATE joueur j
INNER JOIN equipe e ON j.equipe_id = e.id
SET j.nationalite = e.pays
WHERE j.nationalite IS NULL OR j.nationalite = '';

-- Vérification
SELECT 'Joueurs avec nationalité:' as info;
SELECT nom, prenom, nationalite, numero, poste 
FROM joueur 
LIMIT 10;

SELECT 'Total par nationalité:' as info;
SELECT nationalite, COUNT(*) as nombre
FROM joueur
WHERE nationalite IS NOT NULL
GROUP BY nationalite
ORDER BY nombre DESC;
