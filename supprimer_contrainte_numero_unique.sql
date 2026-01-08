-- Script pour supprimer la contrainte UNIQUE sur le numéro de joueur
-- Cela permettra d'avoir plusieurs joueurs avec le même numéro dans une équipe

USE gestion_match;

-- Vérifier les contraintes existantes
SELECT 
    CONSTRAINT_NAME, 
    TABLE_NAME, 
    COLUMN_NAME
FROM 
    INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE 
    TABLE_SCHEMA = 'gestion_match' 
    AND TABLE_NAME = 'joueur'
    AND COLUMN_NAME = 'numero';

-- Si une contrainte UNIQUE existe, la supprimer
-- (Remplacer 'nom_contrainte' par le nom réel si trouvé ci-dessus)
-- ALTER TABLE joueur DROP INDEX nom_contrainte;

-- Vérifier aussi pour la table joueurs (pluriel)
SELECT 
    CONSTRAINT_NAME, 
    TABLE_NAME, 
    COLUMN_NAME
FROM 
    INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE 
    TABLE_SCHEMA = 'gestion_match' 
    AND TABLE_NAME = 'joueurs'
    AND COLUMN_NAME = 'numero';

-- Si une contrainte UNIQUE existe sur joueurs, la supprimer
-- ALTER TABLE joueurs DROP INDEX nom_contrainte;

-- Afficher la structure actuelle de la table
SHOW CREATE TABLE joueur;
