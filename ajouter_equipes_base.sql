-- ============================================
-- Ajout des équipes de base
-- ============================================

USE gestionmatch;

-- Ajouter les équipes principales
INSERT INTO equipes (nom, pays, entraineur, nombre_joueurs) VALUES
('Maroc', 'Maroc', 'Walid Regragui', 26),
('Brésil', 'Brésil', 'Fernando Diniz', 26),
('Portugal', 'Portugal', 'Fernando Santos', 26),
('Algérie', 'Algérie', 'Djamel Belmadi', 26),
('France', 'France', 'Didier Deschamps', 26),
('Espagne', 'Espagne', 'Luis de la Fuente', 26),
('Allemagne', 'Allemagne', 'Julian Nagelsmann', 26),
('Argentine', 'Argentine', 'Lionel Scaloni', 26),
('Angleterre', 'Angleterre', 'Gareth Southgate', 26),
('Australie', 'Australie', 'Graham Arnold', 26),
('Croatie', 'Croatie', 'Zlatko Dalić', 26),
('Danemark', 'Danemark', 'Kasper Hjulmand', 26),
('États-Unis', 'États-Unis', 'Gregg Berhalter', 26),
('Japon', 'Japon', 'Hajime Moriyasu', 26);

-- Vérifier les équipes ajoutées
SELECT id, nom, pays, entraineur FROM equipes ORDER BY nom;

SELECT '✅ Équipes ajoutées avec succès!' AS Status;
