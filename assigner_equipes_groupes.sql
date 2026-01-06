-- Script pour assigner les équipes aux groupes
-- Coupe du Monde 2030

-- D'abord, vérifier les groupes existants
SELECT * FROM groupe;

-- Assigner les équipes aux groupes (exemple)
-- Remplacez les noms d'équipes par ceux de votre base de données

-- GROUPE A
UPDATE equipe SET groupe_id = (SELECT id FROM groupe WHERE nom = 'A' LIMIT 1) 
WHERE nom IN ('France', 'Brésil', 'Allemagne', 'Maroc');

-- GROUPE B
UPDATE equipe SET groupe_id = (SELECT id FROM groupe WHERE nom = 'B' LIMIT 1) 
WHERE nom IN ('Argentine', 'Espagne', 'Portugal', 'Mexique');

-- GROUPE C
UPDATE equipe SET groupe_id = (SELECT id FROM groupe WHERE nom = 'C' LIMIT 1) 
WHERE nom IN ('Angleterre', 'Italie', 'Pays-Bas', 'Égypte');

-- GROUPE D
UPDATE equipe SET groupe_id = (SELECT id FROM groupe WHERE nom = 'D' LIMIT 1) 
WHERE nom IN ('Belgique', 'Uruguay', 'Croatie', 'Sénégal');

-- GROUPE E
UPDATE equipe SET groupe_id = (SELECT id FROM groupe WHERE nom = 'E' LIMIT 1) 
WHERE nom IN ('Japon', 'Colombie', 'Suisse', 'Algérie');

-- GROUPE F
UPDATE equipe SET groupe_id = (SELECT id FROM groupe WHERE nom = 'F' LIMIT 1) 
WHERE nom IN ('Danemark', 'Pérou', 'Suède', 'Tunisie');

-- GROUPE G
UPDATE equipe SET groupe_id = (SELECT id FROM groupe WHERE nom = 'G' LIMIT 1) 
WHERE nom IN ('Pologne', 'Chili', 'Corée du Sud', 'Nigeria');

-- GROUPE H
UPDATE equipe SET groupe_id = (SELECT id FROM groupe WHERE nom = 'H' LIMIT 1) 
WHERE nom IN ('États-Unis', 'Canada', 'Costa Rica', 'Ghana');

-- Vérifier les assignations
SELECT e.nom AS equipe, g.nom AS groupe 
FROM equipe e 
LEFT JOIN groupe g ON e.groupe_id = g.id 
ORDER BY g.nom, e.nom;

-- Compter les équipes par groupe
SELECT g.nom AS groupe, COUNT(e.id) AS nombre_equipes
FROM groupe g
LEFT JOIN equipe e ON g.id = e.groupe_id
GROUP BY g.id, g.nom
ORDER BY g.nom;
