-- Compléter les prénoms manquants
USE gestionmatch;

-- Joueurs brésiliens
UPDATE joueur SET prenom = 'Marcos' WHERE nom = 'Marquinhos' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Carlos' WHERE nom = 'Casemiro' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Neymar' WHERE nom = 'Neymar' AND prenom = 'Jr';
UPDATE joueur SET prenom = 'Richarlison' WHERE nom = 'Richarlison' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Rodrygo' WHERE nom = 'Rodrygo' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Raphinha' WHERE nom = 'Raphinha' AND (prenom IS NULL OR prenom = '');

-- Vérification
SELECT 'Joueurs mis à jour:' as info;
SELECT nom, prenom, numero, poste FROM joueur WHERE nom IN ('Marquinhos', 'Casemiro', 'Neymar', 'Richarlison', 'Rodrygo', 'Raphinha');

SELECT 'Total joueurs sans prénom:' as info;
SELECT COUNT(*) as sans_prenom FROM joueur WHERE prenom IS NULL OR prenom = '';
