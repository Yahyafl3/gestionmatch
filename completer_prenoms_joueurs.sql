-- Compléter les prénoms manquants des joueurs
-- Base de données: gestionmatch

USE gestionmatch;

-- Mise à jour des prénoms manquants
UPDATE joueur SET prenom = 'Thiago' WHERE nom = 'Alcântara' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Vinícius' WHERE nom = 'Júnior' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Thibaut' WHERE nom = 'Courtois' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Karim' WHERE nom = 'Benzema' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Luka' WHERE nom = 'Modrić' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Toni' WHERE nom = 'Kroos' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Sergio' WHERE nom = 'Ramos' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Raphaël' WHERE nom = 'Varane' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Dani' WHERE nom = 'Carvajal' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Ferland' WHERE nom = 'Mendy' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Federico' WHERE nom = 'Valverde' AND (prenom IS NULL OR prenom = '');

-- Joueurs brésiliens
UPDATE joueur SET prenom = 'Alisson' WHERE nom = 'Becker' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Marquinhos' WHERE nom = 'Corrêa' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Thiago' WHERE nom = 'Silva' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Casemiro' WHERE nom = 'Santos' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Philippe' WHERE nom = 'Coutinho' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Gabriel' WHERE nom = 'Jesus' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Roberto' WHERE nom = 'Firmino' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Richarlison' WHERE nom = 'Andrade' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Rodrygo' WHERE nom = 'Goes' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Raphinha' WHERE nom = 'Dias' AND (prenom IS NULL OR prenom = '');

-- Joueurs portugais
UPDATE joueur SET prenom = 'Rui' WHERE nom = 'Patrício' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Pepe' WHERE nom = 'Ferreira' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Rúben' WHERE nom = 'Dias' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'João' WHERE nom = 'Cancelo' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Bruno' WHERE nom = 'Fernandes' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Bernardo' WHERE nom = 'Silva' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'João' WHERE nom = 'Félix' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Diogo' WHERE nom = 'Jota' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Rafael' WHERE nom = 'Leão' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Gonçalo' WHERE nom = 'Ramos' AND (prenom IS NULL OR prenom = '');

-- Joueurs marocains
UPDATE joueur SET prenom = 'Yassine' WHERE nom = 'Bounou' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Achraf' WHERE nom = 'Hakimi' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Noussair' WHERE nom = 'Mazraoui' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Romain' WHERE nom = 'Saïss' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Nayef' WHERE nom = 'Aguerd' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Sofyan' WHERE nom = 'Amrabat' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Azzedine' WHERE nom = 'Ounahi' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Hakim' WHERE nom = 'Ziyech' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Youssef' WHERE nom = 'En-Nesyri' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Sofiane' WHERE nom = 'Boufal' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Zakaria' WHERE nom = 'Aboukhlal' AND (prenom IS NULL OR prenom = '');

-- Joueurs argentins
UPDATE joueur SET prenom = 'Emiliano' WHERE nom = 'Martínez' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Nicolás' WHERE nom = 'Otamendi' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Cristian' WHERE nom = 'Romero' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Nahuel' WHERE nom = 'Molina' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Marcos' WHERE nom = 'Acuña' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Rodrigo' WHERE nom = 'De Paul' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Leandro' WHERE nom = 'Paredes' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Enzo' WHERE nom = 'Fernández' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Ángel' WHERE nom = 'Di María' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Julián' WHERE nom = 'Álvarez' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Lautaro' WHERE nom = 'Martínez' AND (prenom IS NULL OR prenom = '');

-- Joueurs français
UPDATE joueur SET prenom = 'Hugo' WHERE nom = 'Lloris' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Raphaël' WHERE nom = 'Varane' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Presnel' WHERE nom = 'Kimpembe' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Benjamin' WHERE nom = 'Pavard' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Lucas' WHERE nom = 'Hernández' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'N\'Golo' WHERE nom = 'Kanté' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Paul' WHERE nom = 'Pogba' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Antoine' WHERE nom = 'Griezmann' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Ousmane' WHERE nom = 'Dembélé' AND (prenom IS NULL OR prenom = '');
UPDATE joueur SET prenom = 'Olivier' WHERE nom = 'Giroud' AND (prenom IS NULL OR prenom = '');

-- Vérification
SELECT nom, prenom, numero, poste, nationalite 
FROM joueur 
WHERE prenom IS NULL OR prenom = '' OR LENGTH(prenom) < 3
ORDER BY nom;

SELECT COUNT(*) as total_joueurs, 
       SUM(CASE WHEN prenom IS NOT NULL AND prenom != '' THEN 1 ELSE 0 END) as avec_prenom,
       SUM(CASE WHEN prenom IS NULL OR prenom = '' THEN 1 ELSE 0 END) as sans_prenom
FROM joueur;
