-- Mettre à jour les nationalités des joueurs selon leurs équipes
USE gestionmatch;

-- Mettre à jour depuis les équipes
UPDATE joueur j
INNER JOIN equipe e ON j.equipe_id = e.id
SET j.nationalite = e.pays
WHERE j.nationalite IS NULL OR j.nationalite = '';

-- Vérification
SELECT nom, prenom, nationalite, numero FROM joueur LIMIT 10;
