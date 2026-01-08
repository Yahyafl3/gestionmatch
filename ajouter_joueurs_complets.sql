-- ============================================
-- Script pour ajouter les joueurs manquants
-- avec prénoms complets et nationalités
-- ============================================

USE gestionmatch;

-- Vérifier les équipes existantes
SELECT id, nom, pays FROM equipes ORDER BY nom;

-- Ajouter les joueurs manquants pour chaque équipe nationale
-- Format: (nom, prenom, numero_maillot, poste, nationalite, equipe_id, buts_marques)

-- MAROC (id à déterminer selon votre base)
INSERT INTO joueurs (nom, prenom, numero_maillot, poste, nationalite, equipe_id, buts_marques) VALUES
('Bounou', 'Yassine', 1, 'Gardien', 'Marocaine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%maroc%' LIMIT 1), 0),
('Hakimi', 'Achraf', 2, 'Défenseur', 'Marocaine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%maroc%' LIMIT 1), 0),
('Mazraoui', 'Noussair', 3, 'Défenseur', 'Marocaine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%maroc%' LIMIT 1), 0),
('Aguerd', 'Nayef', 5, 'Défenseur', 'Marocaine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%maroc%' LIMIT 1), 0),
('Saïss', 'Romain', 6, 'Défenseur', 'Marocaine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%maroc%' LIMIT 1), 0),
('Ziyech', 'Hakim', 7, 'Milieu', 'Marocaine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%maroc%' LIMIT 1), 0),
('Ounahi', 'Azzedine', 8, 'Milieu', 'Marocaine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%maroc%' LIMIT 1), 0),
('En-Nesyri', 'Youssef', 9, 'Attaquant', 'Marocaine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%maroc%' LIMIT 1), 0),
('Amrabat', 'Sofyan', 4, 'Milieu', 'Marocaine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%maroc%' LIMIT 1), 0),
('Boufal', 'Sofiane', 17, 'Attaquant', 'Marocaine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%maroc%' LIMIT 1), 0),
('Amallah', 'Selim', 15, 'Milieu', 'Marocaine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%maroc%' LIMIT 1), 0);

-- BRÉSIL
INSERT INTO joueurs (nom, prenom, numero_maillot, poste, nationalite, equipe_id, buts_marques) VALUES
('Alisson', 'Becker', 1, 'Gardien', 'Brésilienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%br%sil%' LIMIT 1), 0),
('Danilo', 'Luiz da Silva', 2, 'Défenseur', 'Brésilienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%br%sil%' LIMIT 1), 0),
('Thiago Silva', '', 3, 'Défenseur', 'Brésilienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%br%sil%' LIMIT 1), 0),
('Marquinhos', '', 4, 'Défenseur', 'Brésilienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%br%sil%' LIMIT 1), 0),
('Casemiro', '', 5, 'Milieu', 'Brésilienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%br%sil%' LIMIT 1), 0),
('Neymar', 'Jr', 10, 'Attaquant', 'Brésilienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%br%sil%' LIMIT 1), 0),
('Vinícius', 'Júnior', 7, 'Attaquant', 'Brésilienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%br%sil%' LIMIT 1), 0),
('Richarlison', 'de Andrade', 9, 'Attaquant', 'Brésilienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%br%sil%' LIMIT 1), 0),
('Raphinha', '', 11, 'Attaquant', 'Brésilienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%br%sil%' LIMIT 1), 0),
('Rodrygo', 'Goes', 21, 'Attaquant', 'Brésilienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%br%sil%' LIMIT 1), 0),
('Gabriel Jesus', '', 19, 'Attaquant', 'Brésilienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%br%sil%' LIMIT 1), 0);

-- PORTUGAL
INSERT INTO joueurs (nom, prenom, numero_maillot, poste, nationalite, equipe_id, buts_marques) VALUES
('Costa', 'Diogo', 1, 'Gardien', 'Portugaise', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%portugal%' LIMIT 1), 0),
('Cancelo', 'João', 2, 'Défenseur', 'Portugaise', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%portugal%' LIMIT 1), 0),
('Pepe', '', 3, 'Défenseur', 'Portugaise', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%portugal%' LIMIT 1), 0),
('Dias', 'Rúben', 4, 'Défenseur', 'Portugaise', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%portugal%' LIMIT 1), 0),
('Ronaldo', 'Cristiano', 7, 'Attaquant', 'Portugaise', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%portugal%' LIMIT 1), 0),
('Fernandes', 'Bruno', 8, 'Milieu', 'Portugaise', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%portugal%' LIMIT 1), 0),
('Silva', 'Bernardo', 10, 'Milieu', 'Portugaise', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%portugal%' LIMIT 1), 0),
('Félix', 'João', 11, 'Attaquant', 'Portugaise', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%portugal%' LIMIT 1), 0),
('Leão', 'Rafael', 17, 'Attaquant', 'Portugaise', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%portugal%' LIMIT 1), 0),
('Ramos', 'Gonçalo', 26, 'Attaquant', 'Portugaise', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%portugal%' LIMIT 1), 0),
('Neves', 'Rúben', 18, 'Milieu', 'Portugaise', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%portugal%' LIMIT 1), 0);

-- ALGÉRIE
INSERT INTO joueurs (nom, prenom, numero_maillot, poste, nationalite, equipe_id, buts_marques) VALUES
('M\'Bolhi', 'Raïs', 1, 'Gardien', 'Algérienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%alg%rie%' LIMIT 1), 0),
('Mandi', 'Aïssa', 2, 'Défenseur', 'Algérienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%alg%rie%' LIMIT 1), 0),
('Bennacer', 'Ismaël', 8, 'Milieu', 'Algérienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%alg%rie%' LIMIT 1), 0),
('Mahrez', 'Riyad', 7, 'Attaquant', 'Algérienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%alg%rie%' LIMIT 1), 0),
('Slimani', 'Islam', 13, 'Attaquant', 'Algérienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%alg%rie%' LIMIT 1), 0),
('Feghouli', 'Sofiane', 10, 'Milieu', 'Algérienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%alg%rie%' LIMIT 1), 0),
('Bounedjah', 'Baghdad', 9, 'Attaquant', 'Algérienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%alg%rie%' LIMIT 1), 0),
('Bensebaini', 'Ramy', 3, 'Défenseur', 'Algérienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%alg%rie%' LIMIT 1), 0),
('Zerrouki', 'Ramiz', 6, 'Milieu', 'Algérienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%alg%rie%' LIMIT 1), 0),
('Belaïli', 'Youcef', 11, 'Attaquant', 'Algérienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%alg%rie%' LIMIT 1), 0),
('Atal', 'Youcef', 20, 'Défenseur', 'Algérienne', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%alg%rie%' LIMIT 1), 0);

-- FRANCE
INSERT INTO joueurs (nom, prenom, numero_maillot, poste, nationalite, equipe_id, buts_marques) VALUES
('Lloris', 'Hugo', 1, 'Gardien', 'Française', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%france%' LIMIT 1), 0),
('Pavard', 'Benjamin', 2, 'Défenseur', 'Française', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%france%' LIMIT 1), 0),
('Varane', 'Raphaël', 4, 'Défenseur', 'Française', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%france%' LIMIT 1), 0),
('Koundé', 'Jules', 5, 'Défenseur', 'Française', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%france%' LIMIT 1), 0),
('Pogba', 'Paul', 6, 'Milieu', 'Française', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%france%' LIMIT 1), 0),
('Griezmann', 'Antoine', 7, 'Attaquant', 'Française', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%france%' LIMIT 1), 0),
('Mbappé', 'Kylian', 10, 'Attaquant', 'Française', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%france%' LIMIT 1), 0),
('Dembélé', 'Ousmane', 11, 'Attaquant', 'Française', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%france%' LIMIT 1), 0),
('Kanté', 'N\'Golo', 13, 'Milieu', 'Française', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%france%' LIMIT 1), 0),
('Benzema', 'Karim', 19, 'Attaquant', 'Française', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%france%' LIMIT 1), 0),
('Hernández', 'Theo', 22, 'Défenseur', 'Française', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%france%' LIMIT 1), 0);

-- ESPAGNE
INSERT INTO joueurs (nom, prenom, numero_maillot, poste, nationalite, equipe_id, buts_marques) VALUES
('Simón', 'Unai', 1, 'Gardien', 'Espagnole', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%espagne%' LIMIT 1), 0),
('Carvajal', 'Daniel', 2, 'Défenseur', 'Espagnole', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%espagne%' LIMIT 1), 0),
('García', 'Eric', 3, 'Défenseur', 'Espagnole', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%espagne%' LIMIT 1), 0),
('Laporte', 'Aymeric', 4, 'Défenseur', 'Espagnole', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%espagne%' LIMIT 1), 0),
('Busquets', 'Sergio', 5, 'Milieu', 'Espagnole', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%espagne%' LIMIT 1), 0),
('Gavi', '', 9, 'Milieu', 'Espagnole', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%espagne%' LIMIT 1), 0),
('Pedri', '', 10, 'Milieu', 'Espagnole', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%espagne%' LIMIT 1), 0),
('Torres', 'Ferran', 11, 'Attaquant', 'Espagnole', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%espagne%' LIMIT 1), 0),
('Morata', 'Álvaro', 7, 'Attaquant', 'Espagnole', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%espagne%' LIMIT 1), 0),
('Olmo', 'Dani', 21, 'Milieu', 'Espagnole', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%espagne%' LIMIT 1), 0),
('Rodri', '', 16, 'Milieu', 'Espagnole', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%espagne%' LIMIT 1), 0);

-- ALLEMAGNE
INSERT INTO joueurs (nom, prenom, numero_maillot, poste, nationalite, equipe_id, buts_marques) VALUES
('Neuer', 'Manuel', 1, 'Gardien', 'Allemande', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%allemagne%' LIMIT 1), 0),
('Rüdiger', 'Antonio', 2, 'Défenseur', 'Allemande', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%allemagne%' LIMIT 1), 0),
('Raum', 'David', 3, 'Défenseur', 'Allemande', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%allemagne%' LIMIT 1), 0),
('Gündogan', 'İlkay', 8, 'Milieu', 'Allemande', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%allemagne%' LIMIT 1), 0),
('Havertz', 'Kai', 7, 'Attaquant', 'Allemande', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%allemagne%' LIMIT 1), 0),
('Müller', 'Thomas', 13, 'Attaquant', 'Allemande', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%allemagne%' LIMIT 1), 0),
('Gnabry', 'Serge', 10, 'Attaquant', 'Allemande', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%allemagne%' LIMIT 1), 0),
('Sané', 'Leroy', 19, 'Attaquant', 'Allemande', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%allemagne%' LIMIT 1), 0),
('Kimmich', 'Joshua', 6, 'Milieu', 'Allemande', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%allemagne%' LIMIT 1), 0),
('Goretzka', 'Leon', 18, 'Milieu', 'Allemande', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%allemagne%' LIMIT 1), 0),
('Füllkrug', 'Niclas', 9, 'Attaquant', 'Allemande', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%allemagne%' LIMIT 1), 0);

-- ARGENTINE
INSERT INTO joueurs (nom, prenom, numero_maillot, poste, nationalite, equipe_id, buts_marques) VALUES
('Martínez', 'Emiliano', 1, 'Gardien', 'Argentine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%argentine%' LIMIT 1), 0),
('Molina', 'Nahuel', 26, 'Défenseur', 'Argentine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%argentine%' LIMIT 1), 0),
('Romero', 'Cristian', 13, 'Défenseur', 'Argentine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%argentine%' LIMIT 1), 0),
('Otamendi', 'Nicolás', 19, 'Défenseur', 'Argentine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%argentine%' LIMIT 1), 0),
('Tagliafico', 'Nicolás', 3, 'Défenseur', 'Argentine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%argentine%' LIMIT 1), 0),
('De Paul', 'Rodrigo', 7, 'Milieu', 'Argentine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%argentine%' LIMIT 1), 0),
('Fernández', 'Enzo', 24, 'Milieu', 'Argentine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%argentine%' LIMIT 1), 0),
('Mac Allister', 'Alexis', 20, 'Milieu', 'Argentine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%argentine%' LIMIT 1), 0),
('Messi', 'Lionel', 10, 'Attaquant', 'Argentine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%argentine%' LIMIT 1), 0),
('Di María', 'Ángel', 11, 'Attaquant', 'Argentine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%argentine%' LIMIT 1), 0),
('Álvarez', 'Julián', 9, 'Attaquant', 'Argentine', (SELECT id FROM equipes WHERE LOWER(nom) LIKE '%argentine%' LIMIT 1), 0);

-- Vérification des joueurs ajoutés
SELECT 
    j.nom,
    j.prenom,
    j.numero_maillot,
    j.poste,
    j.nationalite,
    e.nom as equipe
FROM joueurs j
LEFT JOIN equipes e ON j.equipe_id = e.id
ORDER BY e.nom, j.numero_maillot;

-- Statistiques
SELECT 
    e.nom as equipe,
    COUNT(j.id) as nombre_joueurs
FROM equipes e
LEFT JOIN joueurs j ON e.id = j.equipe_id
GROUP BY e.id, e.nom
ORDER BY e.nom;
