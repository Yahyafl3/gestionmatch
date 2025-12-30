-- ============================================
-- DONNÉES DE TEST - COUPE DU MONDE 2030
-- ============================================

USE gestionmatch;

-- ============================================
-- ÉQUIPES NATIONALES (32 équipes)
-- ============================================

INSERT INTO equipes (nom, pays, entraineur, nombre_joueurs, groupe_lettre, groupe_id) VALUES 
-- Groupe A
('Maroc', 'Maroc', 'Walid Regragui', 26, 'A', 1),
('Brésil', 'Brésil', 'Fernando Diniz', 26, 'A', 1),
('Allemagne', 'Allemagne', 'Julian Nagelsmann', 26, 'A', 1),
('Australie', 'Australie', 'Graham Arnold', 26, 'A', 1),

-- Groupe B
('France', 'France', 'Didier Deschamps', 26, 'B', 2),
('Argentine', 'Argentine', 'Lionel Scaloni', 26, 'B', 2),
('Japon', 'Japon', 'Hajime Moriyasu', 26, 'B', 2),
('Danemark', 'Danemark', 'Kasper Hjulmand', 26, 'B', 2),

-- Groupe C
('Espagne', 'Espagne', 'Luis de la Fuente', 26, 'C', 3),
('Portugal', 'Portugal', 'Roberto Martínez', 26, 'C', 3),
('Mexique', 'Mexique', 'Jaime Lozano', 26, 'C', 3),
('Croatie', 'Croatie', 'Zlatko Dalić', 26, 'C', 3),

-- Groupe D
('Angleterre', 'Angleterre', 'Gareth Southgate', 26, 'D', 4),
('Pays-Bas', 'Pays-Bas', 'Ronald Koeman', 26, 'D', 4),
('Sénégal', 'Sénégal', 'Aliou Cissé', 26, 'D', 4),
('États-Unis', 'États-Unis', 'Gregg Berhalter', 26, 'D', 4);

-- ============================================
-- JOUEURS (Quelques joueurs par équipe)
-- ============================================

-- MAROC
INSERT INTO joueurs (nom, prenom, date_naissance, poste, numero, equipe_id) VALUES 
('Bounou', 'Yassine', '1991-04-05', 'Gardien', 1, 1),
('Hakimi', 'Achraf', '1998-11-04', 'Défenseur', 2, 1),
('Mazraoui', 'Noussair', '1997-11-14', 'Défenseur', 3, 1),
('Aguerd', 'Nayef', '1996-03-30', 'Défenseur', 5, 1),
('Saïss', 'Romain', '1990-03-26', 'Défenseur', 6, 1),
('Amrabat', 'Sofyan', '1996-08-21', 'Milieu', 4, 1),
('Ounahi', 'Azzedine', '2000-04-19', 'Milieu', 8, 1),
('Ziyech', 'Hakim', '1993-03-19', 'Attaquant', 7, 1),
('En-Nesyri', 'Youssef', '1997-06-01', 'Attaquant', 19, 1),
('Boufal', 'Sofiane', '1993-09-17', 'Attaquant', 17, 1);

-- BRÉSIL
INSERT INTO joueurs (nom, prenom, date_naissance, poste, numero, equipe_id) VALUES 
('Alisson', 'Becker', '1992-10-02', 'Gardien', 1, 2),
('Marquinhos', '', '1994-05-14', 'Défenseur', 4, 2),
('Militão', 'Éder', '1998-01-18', 'Défenseur', 3, 2),
('Casemiro', '', '1992-02-23', 'Milieu', 5, 2),
('Paquetá', 'Lucas', '1997-08-27', 'Milieu', 8, 2),
('Neymar', 'Jr', '1992-02-05', 'Attaquant', 10, 2),
('Vinícius', 'Júnior', '2000-07-12', 'Attaquant', 7, 2),
('Richarlison', '', '1997-05-10', 'Attaquant', 9, 2),
('Rodrygo', '', '2001-01-09', 'Attaquant', 11, 2),
('Raphinha', '', '1996-12-14', 'Attaquant', 19, 2);

-- FRANCE
INSERT INTO joueurs (nom, prenom, date_naissance, poste, numero, equipe_id) VALUES 
('Maignan', 'Mike', '1995-07-03', 'Gardien', 16, 5),
('Pavard', 'Benjamin', '1996-03-28', 'Défenseur', 2, 5),
('Varane', 'Raphaël', '1993-04-25', 'Défenseur', 4, 5),
('Hernández', 'Theo', '1997-10-06', 'Défenseur', 22, 5),
('Tchouaméni', 'Aurélien', '2000-01-27', 'Milieu', 8, 5),
('Griezmann', 'Antoine', '1991-03-21', 'Milieu', 7, 5),
('Mbappé', 'Kylian', '1998-12-20', 'Attaquant', 10, 5),
('Dembélé', 'Ousmane', '1997-05-15', 'Attaquant', 11, 5),
('Giroud', 'Olivier', '1986-09-30', 'Attaquant', 9, 5),
('Coman', 'Kingsley', '1996-06-13', 'Attaquant', 20, 5);

-- ARGENTINE
INSERT INTO joueurs (nom, prenom, date_naissance, poste, numero, equipe_id) VALUES 
('Martínez', 'Emiliano', '1992-09-02', 'Gardien', 23, 6),
('Romero', 'Cristian', '1998-04-27', 'Défenseur', 13, 6),
('Otamendi', 'Nicolás', '1988-02-12', 'Défenseur', 19, 6),
('Acuña', 'Marcos', '1991-10-28', 'Défenseur', 8, 6),
('De Paul', 'Rodrigo', '1994-05-24', 'Milieu', 7, 6),
('Mac Allister', 'Alexis', '1998-12-24', 'Milieu', 20, 6),
('Messi', 'Lionel', '1987-06-24', 'Attaquant', 10, 6),
('Di María', 'Ángel', '1988-02-14', 'Attaquant', 11, 6),
('Álvarez', 'Julián', '2000-01-31', 'Attaquant', 9, 6),
('Lautaro', 'Martínez', '1997-08-22', 'Attaquant', 22, 6);

-- ESPAGNE
INSERT INTO joueurs (nom, prenom, date_naissance, poste, numero, equipe_id) VALUES 
('Unai', 'Simón', '1997-06-11', 'Gardien', 23, 9),
('Carvajal', 'Dani', '1992-01-11', 'Défenseur', 2, 9),
('Laporte', 'Aymeric', '1994-05-27', 'Défenseur', 24, 9),
('Alba', 'Jordi', '1989-03-21', 'Défenseur', 18, 9),
('Rodri', '', '1996-06-22', 'Milieu', 16, 9),
('Pedri', '', '2002-11-25', 'Milieu', 8, 9),
('Gavi', '', '2004-08-05', 'Milieu', 9, 9),
('Morata', 'Álvaro', '1992-10-23', 'Attaquant', 7, 9),
('Ferran', 'Torres', '2000-02-29', 'Attaquant', 11, 9),
('Oyarzabal', 'Mikel', '1997-04-21', 'Attaquant', 21, 9);

-- PORTUGAL
INSERT INTO joueurs (nom, prenom, date_naissance, poste, numero, equipe_id) VALUES 
('Costa', 'Diogo', '1999-09-19', 'Gardien', 22, 10),
('Cancelo', 'João', '1994-05-27', 'Défenseur', 20, 10),
('Dias', 'Rúben', '1997-05-14', 'Défenseur', 3, 10),
('Pepe', '', '1983-02-26', 'Défenseur', 4, 10),
('Neves', 'Rúben', '1997-03-13', 'Milieu', 18, 10),
('Fernandes', 'Bruno', '1994-09-08', 'Milieu', 8, 10),
('Bernardo', 'Silva', '1994-08-10', 'Milieu', 10, 10),
('Ronaldo', 'Cristiano', '1985-02-05', 'Attaquant', 7, 10),
('Félix', 'João', '1999-11-10', 'Attaquant', 11, 10),
('Leão', 'Rafael', '1999-06-10', 'Attaquant', 17, 10);

-- ANGLETERRE
INSERT INTO joueurs (nom, prenom, date_naissance, poste, numero, equipe_id) VALUES 
('Pickford', 'Jordan', '1994-03-07', 'Gardien', 1, 13),
('Walker', 'Kyle', '1990-05-28', 'Défenseur', 2, 13),
('Stones', 'John', '1994-05-28', 'Défenseur', 5, 13),
('Maguire', 'Harry', '1993-03-05', 'Défenseur', 6, 13),
('Rice', 'Declan', '1999-01-14', 'Milieu', 4, 13),
('Bellingham', 'Jude', '2003-06-29', 'Milieu', 22, 13),
('Foden', 'Phil', '2000-05-28', 'Milieu', 11, 13),
('Kane', 'Harry', '1993-07-28', 'Attaquant', 9, 13),
('Saka', 'Bukayo', '2001-09-05', 'Attaquant', 7, 13),
('Sterling', 'Raheem', '1994-12-08', 'Attaquant', 10, 13);

-- ALLEMAGNE
INSERT INTO joueurs (nom, prenom, date_naissance, poste, numero, equipe_id) VALUES 
('Neuer', 'Manuel', '1986-03-27', 'Gardien', 1, 3),
('Kimmich', 'Joshua', '1995-02-08', 'Défenseur', 6, 3),
('Rüdiger', 'Antonio', '1993-03-03', 'Défenseur', 2, 3),
('Süle', 'Niklas', '1995-09-03', 'Défenseur', 15, 3),
('Gündogan', 'İlkay', '1990-10-24', 'Milieu', 21, 3),
('Kroos', 'Toni', '1990-01-04', 'Milieu', 8, 3),
('Musiala', 'Jamal', '2003-02-26', 'Milieu', 10, 3),
('Havertz', 'Kai', '1999-06-11', 'Attaquant', 7, 3),
('Gnabry', 'Serge', '1995-07-14', 'Attaquant', 19, 3),
('Müller', 'Thomas', '1989-09-13', 'Attaquant', 25, 3);

-- ============================================
-- MATCHS DE LA PHASE DE GROUPES
-- ============================================

INSERT INTO match_data (nom, date_match, score) VALUES 
-- Groupe A
('Maroc vs Brésil', '2030-06-11', '2-1'),
('Allemagne vs Australie', '2030-06-11', '3-0'),
('Maroc vs Allemagne', '2030-06-15', '1-1'),
('Brésil vs Australie', '2030-06-15', '4-0'),
('Brésil vs Allemagne', '2030-06-19', '2-2'),
('Maroc vs Australie', '2030-06-19', '3-0'),

-- Groupe B
('France vs Argentine', '2030-06-12', '2-3'),
('Japon vs Danemark', '2030-06-12', '1-1'),
('France vs Japon', '2030-06-16', '3-1'),
('Argentine vs Danemark', '2030-06-16', '2-0'),
('Argentine vs Japon', '2030-06-20', '3-2'),
('France vs Danemark', '2030-06-20', '2-1'),

-- Groupe C
('Espagne vs Portugal', '2030-06-13', '1-2'),
('Mexique vs Croatie', '2030-06-13', '0-1'),
('Espagne vs Mexique', '2030-06-17', '4-0'),
('Portugal vs Croatie', '2030-06-17', '2-1'),
('Portugal vs Mexique', '2030-06-21', '3-0'),
('Espagne vs Croatie', '2030-06-21', '2-2'),

-- Groupe D
('Angleterre vs Pays-Bas', '2030-06-14', '2-1'),
('Sénégal vs États-Unis', '2030-06-14', '1-0'),
('Angleterre vs Sénégal', '2030-06-18', '3-0'),
('Pays-Bas vs États-Unis', '2030-06-18', '2-2'),
('Pays-Bas vs Sénégal', '2030-06-22', '1-1'),
('Angleterre vs États-Unis', '2030-06-22', '4-1');

-- ============================================
-- MISE À JOUR DES STATISTIQUES DES ÉQUIPES
-- ============================================

-- Groupe A
UPDATE equipes SET matchs_joues=3, victoires=3, points=9, buts_marques=6, buts_encaisses=2 WHERE nom='Maroc';
UPDATE equipes SET matchs_joues=3, victoires=1, nuls=2, points=5, buts_marques=7, buts_encaisses=5 WHERE nom='Brésil';
UPDATE equipes SET matchs_joues=3, victoires=1, nuls=2, points=5, buts_marques=6, buts_encaisses=5 WHERE nom='Allemagne';
UPDATE equipes SET matchs_joues=3, defaites=3, points=0, buts_marques=0, buts_encaisses=7 WHERE nom='Australie';

-- Groupe B
UPDATE equipes SET matchs_joues=3, victoires=3, points=9, buts_marques=8, buts_encaisses=5 WHERE nom='Argentine';
UPDATE equipes SET matchs_joues=3, victoires=2, defaites=1, points=6, buts_marques=7, buts_encaisses=5 WHERE nom='France';
UPDATE equipes SET matchs_joues=3, nuls=1, defaites=2, points=1, buts_marques=4, buts_encaisses=7 WHERE nom='Japon';
UPDATE equipes SET matchs_joues=3, nuls=1, defaites=2, points=1, buts_marques=2, buts_encaisses=4 WHERE nom='Danemark';

-- Groupe C
UPDATE equipes SET matchs_joues=3, victoires=2, defaites=1, points=6, buts_marques=5, buts_encaisses=3 WHERE nom='Portugal';
UPDATE equipes SET matchs_joues=3, victoires=1, nuls=2, points=5, buts_marques=7, buts_encaisses=4 WHERE nom='Espagne';
UPDATE equipes SET matchs_joues=3, victoires=2, defaites=1, points=6, buts_marques=4, buts_encaisses=4 WHERE nom='Croatie';
UPDATE equipes SET matchs_joues=3, defaites=3, points=0, buts_marques=0, buts_encaisses=7 WHERE nom='Mexique';

-- Groupe D
UPDATE equipes SET matchs_joues=3, victoires=3, points=9, buts_marques=9, buts_encaisses=2 WHERE nom='Angleterre';
UPDATE equipes SET matchs_joues=3, victoires=1, nuls=2, points=5, buts_marques=4, buts_encaisses=4 WHERE nom='Pays-Bas';
UPDATE equipes SET matchs_joues=3, victoires=1, nuls=1, defaites=1, points=4, buts_marques=2, buts_encaisses=4 WHERE nom='Sénégal';
UPDATE equipes SET matchs_joues=3, nuls=1, defaites=2, points=1, buts_marques=3, buts_encaisses=8 WHERE nom='États-Unis';

-- ============================================
-- AFFICHAGE DES RÉSULTATS
-- ============================================

SELECT '✅ Données de test insérées avec succès !' AS Status;
SELECT COUNT(*) AS 'Nombre d\'équipes' FROM equipes;
SELECT COUNT(*) AS 'Nombre de joueurs' FROM joueurs;
SELECT COUNT(*) AS 'Nombre de stades' FROM stades;
SELECT COUNT(*) AS 'Nombre de matchs' FROM match_data;
