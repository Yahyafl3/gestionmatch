-- ============================================
-- FORCER L'INSERTION DES DONNÉES COUPE DU MONDE 2030
-- ============================================

USE gestionmatch;

-- Désactiver les contraintes temporairement
SET FOREIGN_KEY_CHECKS = 0;

-- Supprimer les anciennes données (sauf utilisateurs)
TRUNCATE TABLE groupe_equipe;
TRUNCATE TABLE joueur;
TRUNCATE TABLE match_data;
TRUNCATE TABLE equipe;
TRUNCATE TABLE stade;
TRUNCATE TABLE groupe;

-- Réactiver les contraintes
SET FOREIGN_KEY_CHECKS = 1;

-- ============================================
-- INSERTION DES GROUPES
-- ============================================
INSERT INTO groupe (id, nom, description) VALUES 
(1, 'Groupe A', 'Premier groupe de la phase de groupes'),
(2, 'Groupe B', 'Deuxième groupe de la phase de groupes'),
(3, 'Groupe C', 'Troisième groupe de la phase de groupes'),
(4, 'Groupe D', 'Quatrième groupe de la phase de groupes');

-- ============================================
-- INSERTION DES ÉQUIPES NATIONALES
-- ============================================

-- Groupe A
INSERT INTO equipe (nom, pays, entraineur, nombre_joueurs, groupe_id, matchs_joues, victoires, nuls, defaites, buts_marques, buts_encaisses, points) VALUES 
('Maroc', 'Maroc', 'Walid Regragui', 26, 1, 0, 0, 0, 0, 0, 0, 0),
('Brésil', 'Brésil', 'Fernando Diniz', 26, 1, 0, 0, 0, 0, 0, 0, 0),
('Allemagne', 'Allemagne', 'Julian Nagelsmann', 26, 1, 0, 0, 0, 0, 0, 0, 0),
('Australie', 'Australie', 'Graham Arnold', 26, 1, 0, 0, 0, 0, 0, 0, 0);

-- Groupe B
INSERT INTO equipe (nom, pays, entraineur, nombre_joueurs, groupe_id, matchs_joues, victoires, nuls, defaites, buts_marques, buts_encaisses, points) VALUES 
('France', 'France', 'Didier Deschamps', 26, 2, 0, 0, 0, 0, 0, 0, 0),
('Argentine', 'Argentine', 'Lionel Scaloni', 26, 2, 0, 0, 0, 0, 0, 0, 0),
('Japon', 'Japon', 'Hajime Moriyasu', 26, 2, 0, 0, 0, 0, 0, 0, 0),
('Danemark', 'Danemark', 'Kasper Hjulmand', 26, 2, 0, 0, 0, 0, 0, 0, 0);

-- Groupe C
INSERT INTO equipe (nom, pays, entraineur, nombre_joueurs, groupe_id, matchs_joues, victoires, nuls, defaites, buts_marques, buts_encaisses, points) VALUES 
('Espagne', 'Espagne', 'Luis de la Fuente', 26, 3, 0, 0, 0, 0, 0, 0, 0),
('Portugal', 'Portugal', 'Roberto Martínez', 26, 3, 0, 0, 0, 0, 0, 0, 0),
('Mexique', 'Mexique', 'Jaime Lozano', 26, 3, 0, 0, 0, 0, 0, 0, 0),
('Croatie', 'Croatie', 'Zlatko Dalić', 26, 3, 0, 0, 0, 0, 0, 0, 0);

-- Groupe D
INSERT INTO equipe (nom, pays, entraineur, nombre_joueurs, groupe_id, matchs_joues, victoires, nuls, defaites, buts_marques, buts_encaisses, points) VALUES 
('Angleterre', 'Angleterre', 'Gareth Southgate', 26, 4, 0, 0, 0, 0, 0, 0, 0),
('Pays-Bas', 'Pays-Bas', 'Ronald Koeman', 26, 4, 0, 0, 0, 0, 0, 0, 0),
('Sénégal', 'Sénégal', 'Aliou Cissé', 26, 4, 0, 0, 0, 0, 0, 0, 0),
('États-Unis', 'États-Unis', 'Gregg Berhalter', 26, 4, 0, 0, 0, 0, 0, 0, 0);

-- ============================================
-- INSERTION DES JOUEURS
-- ============================================

-- Joueurs du MAROC (equipe_id = 1)
INSERT INTO joueur (nom, prenom, numero, poste, equipe_id) VALUES 
('Bounou', 'Yassine', 1, 'Gardien', 1),
('Hakimi', 'Achraf', 2, 'Défenseur', 1),
('Mazraoui', 'Noussair', 3, 'Défenseur', 1),
('Amrabat', 'Sofyan', 4, 'Milieu', 1),
('Aguerd', 'Nayef', 5, 'Défenseur', 1),
('Saïss', 'Romain', 6, 'Défenseur', 1),
('Ziyech', 'Hakim', 7, 'Attaquant', 1),
('Ounahi', 'Azzedine', 8, 'Milieu', 1),
('En-Nesyri', 'Youssef', 19, 'Attaquant', 1),
('Boufal', 'Sofiane', 17, 'Attaquant', 1);

-- Joueurs du BRÉSIL (equipe_id = 2)
INSERT INTO joueur (nom, prenom, numero, poste, equipe_id) VALUES 
('Alisson', 'Becker', 1, 'Gardien', 2),
('Marquinhos', '', 4, 'Défenseur', 2),
('Militão', 'Éder', 3, 'Défenseur', 2),
('Casemiro', '', 5, 'Milieu', 2),
('Paquetá', 'Lucas', 8, 'Milieu', 2),
('Neymar', 'Jr', 10, 'Attaquant', 2),
('Vinícius', 'Júnior', 7, 'Attaquant', 2),
('Richarlison', '', 9, 'Attaquant', 2),
('Rodrygo', '', 11, 'Attaquant', 2),
('Raphinha', '', 19, 'Attaquant', 2);

-- Joueurs de la FRANCE (equipe_id = 5)
INSERT INTO joueur (nom, prenom, numero, poste, equipe_id) VALUES 
('Maignan', 'Mike', 16, 'Gardien', 5),
('Pavard', 'Benjamin', 2, 'Défenseur', 5),
('Varane', 'Raphaël', 4, 'Défenseur', 5),
('Hernández', 'Theo', 22, 'Défenseur', 5),
('Tchouaméni', 'Aurélien', 8, 'Milieu', 5),
('Griezmann', 'Antoine', 7, 'Milieu', 5),
('Mbappé', 'Kylian', 10, 'Attaquant', 5),
('Dembélé', 'Ousmane', 11, 'Attaquant', 5),
('Giroud', 'Olivier', 9, 'Attaquant', 5),
('Coman', 'Kingsley', 20, 'Attaquant', 5);

-- Joueurs de l'ARGENTINE (equipe_id = 6)
INSERT INTO joueur (nom, prenom, numero, poste, equipe_id) VALUES 
('Martínez', 'Emiliano', 23, 'Gardien', 6),
('Romero', 'Cristian', 13, 'Défenseur', 6),
('Otamendi', 'Nicolás', 19, 'Défenseur', 6),
('Acuña', 'Marcos', 8, 'Défenseur', 6),
('De Paul', 'Rodrigo', 7, 'Milieu', 6),
('Mac Allister', 'Alexis', 20, 'Milieu', 6),
('Messi', 'Lionel', 10, 'Attaquant', 6),
('Di María', 'Ángel', 11, 'Attaquant', 6),
('Álvarez', 'Julián', 9, 'Attaquant', 6),
('Lautaro', 'Martínez', 22, 'Attaquant', 6);

-- Joueurs de l'ESPAGNE (equipe_id = 9)
INSERT INTO joueur (nom, prenom, numero, poste, equipe_id) VALUES 
('Unai', 'Simón', 23, 'Gardien', 9),
('Carvajal', 'Dani', 2, 'Défenseur', 9),
('Laporte', 'Aymeric', 24, 'Défenseur', 9),
('Alba', 'Jordi', 18, 'Défenseur', 9),
('Rodri', '', 16, 'Milieu', 9),
('Pedri', '', 8, 'Milieu', 9),
('Gavi', '', 9, 'Milieu', 9),
('Morata', 'Álvaro', 7, 'Attaquant', 9),
('Ferran', 'Torres', 11, 'Attaquant', 9),
('Oyarzabal', 'Mikel', 21, 'Attaquant', 9);

-- Joueurs du PORTUGAL (equipe_id = 10)
INSERT INTO joueur (nom, prenom, numero, poste, equipe_id) VALUES 
('Costa', 'Diogo', 22, 'Gardien', 10),
('Cancelo', 'João', 20, 'Défenseur', 10),
('Dias', 'Rúben', 3, 'Défenseur', 10),
('Pepe', '', 4, 'Défenseur', 10),
('Neves', 'Rúben', 18, 'Milieu', 10),
('Fernandes', 'Bruno', 8, 'Milieu', 10),
('Bernardo', 'Silva', 10, 'Milieu', 10),
('Ronaldo', 'Cristiano', 7, 'Attaquant', 10),
('Félix', 'João', 11, 'Attaquant', 10),
('Leão', 'Rafael', 17, 'Attaquant', 10);

-- ============================================
-- INSERTION DES STADES OFFICIELS 2030
-- ============================================

-- Stades du Maroc
INSERT INTO stade (nom, ville, pays, capacite) VALUES 
('Stade Hassan II', 'Casablanca', 'Maroc', 115000),
('Stade Mohammed V', 'Casablanca', 'Maroc', 67000),
('Stade Prince Moulay Abdellah', 'Rabat', 'Maroc', 52000),
('Stade de Marrakech', 'Marrakech', 'Maroc', 45000),
('Stade de Fès', 'Fès', 'Maroc', 45000),
('Stade Ibn Batouta', 'Tanger', 'Maroc', 65000);

-- Stades d'Espagne
INSERT INTO stade (nom, ville, pays, capacite) VALUES 
('Camp Nou', 'Barcelone', 'Espagne', 99354),
('Santiago Bernabéu', 'Madrid', 'Espagne', 81044),
('Metropolitano', 'Madrid', 'Espagne', 68456),
('Ramón Sánchez-Pizjuán', 'Séville', 'Espagne', 43883);

-- Stades du Portugal
INSERT INTO stade (nom, ville, pays, capacite) VALUES 
('Estádio da Luz', 'Lisbonne', 'Portugal', 64642),
('Estádio do Dragão', 'Porto', 'Portugal', 50033),
('José Alvalade', 'Lisbonne', 'Portugal', 50095);

-- ============================================
-- INSERTION DES MATCHS DE LA PHASE DE GROUPES
-- ============================================

INSERT INTO match_data (nom, date_match, score) VALUES 
-- Groupe A
('Maroc vs Brésil', '2030-06-11', ''),
('Allemagne vs Australie', '2030-06-11', ''),
('Maroc vs Allemagne', '2030-06-15', ''),
('Brésil vs Australie', '2030-06-15', ''),

-- Groupe B
('France vs Argentine', '2030-06-12', ''),
('Japon vs Danemark', '2030-06-12', ''),
('France vs Japon', '2030-06-16', ''),
('Argentine vs Danemark', '2030-06-16', ''),

-- Groupe C
('Espagne vs Portugal', '2030-06-13', ''),
('Mexique vs Croatie', '2030-06-13', ''),
('Espagne vs Mexique', '2030-06-17', ''),
('Portugal vs Croatie', '2030-06-17', ''),

-- Groupe D
('Angleterre vs Pays-Bas', '2030-06-14', ''),
('Sénégal vs États-Unis', '2030-06-14', ''),
('Angleterre vs Sénégal', '2030-06-18', ''),
('Pays-Bas vs États-Unis', '2030-06-18', '');

-- ============================================
-- AFFICHAGE DES RÉSULTATS
-- ============================================

SELECT '✅ DONNÉES INSÉRÉES AVEC SUCCÈS !' AS '';
SELECT '' AS '';
SELECT '📊 RÉSUMÉ :' AS '';
SELECT COUNT(*) AS 'Équipes' FROM equipe;
SELECT COUNT(*) AS 'Joueurs' FROM joueur;
SELECT COUNT(*) AS 'Stades' FROM stade;
SELECT COUNT(*) AS 'Matchs' FROM match_data;
SELECT COUNT(*) AS 'Groupes' FROM groupe;

SELECT '' AS '';
SELECT '🏆 ÉQUIPES PAR GROUPE :' AS '';
SELECT g.nom AS 'Groupe', COUNT(e.id) AS 'Nombre équipes'
FROM groupe g
LEFT JOIN equipe e ON e.groupe_id = g.id
GROUP BY g.id, g.nom
ORDER BY g.id;
