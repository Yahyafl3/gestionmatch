-- ========================================
-- SCRIPT D'INSERTION DE DONNÉES DE TEST
-- Coupe du Monde 2030
-- ========================================

-- Insertion des 4 équipes
INSERT INTO equipe (nom, pays, entraineur, points) VALUES
('Maroc', 'Maroc', 'Walid Regragui', 0),
('France', 'France', 'Didier Deschamps', 0),
('Brésil', 'Brésil', 'Fernando Diniz', 0),
('Argentine', 'Argentine', 'Lionel Scaloni', 0);

-- Récupérer les IDs des équipes
SET @maroc_id = (SELECT id FROM equipe WHERE nom = 'Maroc');
SET @france_id = (SELECT id FROM equipe WHERE nom = 'France');
SET @bresil_id = (SELECT id FROM equipe WHERE nom = 'Brésil');
SET @argentine_id = (SELECT id FROM equipe WHERE nom = 'Argentine');

-- Insertion des joueurs du Maroc
INSERT INTO joueur (nom, prenom, numero, poste, equipe_id) VALUES
('Bounou', 'Yassine', 1, 'Gardien', @maroc_id),
('Hakimi', 'Achraf', 2, 'Défenseur', @maroc_id),
('Mazraoui', 'Noussair', 3, 'Défenseur', @maroc_id),
('Amrabat', 'Sofyan', 4, 'Milieu', @maroc_id),
('Ounahi', 'Azzedine', 8, 'Milieu', @maroc_id),
('Ziyech', 'Hakim', 7, 'Attaquant', @maroc_id),
('En-Nesyri', 'Youssef', 19, 'Attaquant', @maroc_id),
('Boufal', 'Sofiane', 17, 'Attaquant', @maroc_id),
('Saiss', 'Romain', 6, 'Défenseur', @maroc_id),
('Aguerd', 'Nayef', 5, 'Défenseur', @maroc_id),
('Amallah', 'Selim', 15, 'Milieu', @maroc_id);

-- Insertion des joueurs de la France
INSERT INTO joueur (nom, prenom, numero, poste, equipe_id) VALUES
('Lloris', 'Hugo', 1, 'Gardien', @france_id),
('Pavard', 'Benjamin', 2, 'Défenseur', @france_id),
('Varane', 'Raphaël', 4, 'Défenseur', @france_id),
('Koundé', 'Jules', 5, 'Défenseur', @france_id),
('Tchouaméni', 'Aurélien', 8, 'Milieu', @france_id),
('Griezmann', 'Antoine', 7, 'Milieu', @france_id),
('Mbappé', 'Kylian', 10, 'Attaquant', @france_id),
('Giroud', 'Olivier', 9, 'Attaquant', @france_id),
('Dembélé', 'Ousmane', 11, 'Attaquant', @france_id),
('Rabiot', 'Adrien', 14, 'Milieu', @france_id),
('Hernandez', 'Theo', 22, 'Défenseur', @france_id);

-- Insertion des joueurs du Brésil
INSERT INTO joueur (nom, prenom, numero, poste, equipe_id) VALUES
('Alisson', 'Becker', 1, 'Gardien', @bresil_id),
('Danilo', 'Luiz', 2, 'Défenseur', @bresil_id),
('Thiago Silva', '', 3, 'Défenseur', @bresil_id),
('Marquinhos', '', 4, 'Défenseur', @bresil_id),
('Casemiro', '', 5, 'Milieu', @bresil_id),
('Neymar', 'Jr', 10, 'Attaquant', @bresil_id),
('Vinicius', 'Junior', 7, 'Attaquant', @bresil_id),
('Richarlison', '', 9, 'Attaquant', @bresil_id),
('Raphinha', '', 11, 'Attaquant', @bresil_id),
('Paquetá', 'Lucas', 8, 'Milieu', @bresil_id),
('Militão', 'Éder', 14, 'Défenseur', @bresil_id);

-- Insertion des joueurs de l'Argentine
INSERT INTO joueur (nom, prenom, numero, poste, equipe_id) VALUES
('Martínez', 'Emiliano', 23, 'Gardien', @argentine_id),
('Molina', 'Nahuel', 26, 'Défenseur', @argentine_id),
('Romero', 'Cristian', 13, 'Défenseur', @argentine_id),
('Otamendi', 'Nicolás', 19, 'Défenseur', @argentine_id),
('De Paul', 'Rodrigo', 7, 'Milieu', @argentine_id),
('Fernández', 'Enzo', 24, 'Milieu', @argentine_id),
('Messi', 'Lionel', 10, 'Attaquant', @argentine_id),
('Di María', 'Ángel', 11, 'Attaquant', @argentine_id),
('Álvarez', 'Julián', 9, 'Attaquant', @argentine_id),
('Mac Allister', 'Alexis', 20, 'Milieu', @argentine_id),
('Acuña', 'Marcos', 8, 'Défenseur', @argentine_id);

-- Insertion des 2 stades
INSERT INTO stade (nom, ville, capacite) VALUES
('Stade Mohammed V', 'Casablanca', 67000),
('Stade Hassan II', 'Rabat', 115000);

-- Récupérer les IDs des stades
SET @stade1_id = (SELECT id FROM stade WHERE nom = 'Stade Mohammed V');
SET @stade2_id = (SELECT id FROM stade WHERE nom = 'Stade Hassan II');

-- Insertion des 2 matchs
INSERT INTO match_data (nom, score, lieu, date_match) VALUES
('Maroc vs France', '0-0', 'Stade Mohammed V, Casablanca', '2030-06-15'),
('Brésil vs Argentine', '0-0', 'Stade Hassan II, Rabat', '2030-06-16');

-- Afficher un résumé
SELECT 'Données insérées avec succès !' as Message;
SELECT COUNT(*) as 'Nombre d\'équipes' FROM equipe;
SELECT COUNT(*) as 'Nombre de joueurs' FROM joueur;
SELECT COUNT(*) as 'Nombre de stades' FROM stade;
SELECT COUNT(*) as 'Nombre de matchs' FROM match_data;
