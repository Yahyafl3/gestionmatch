-- Vérifier les équipes dans la base de données
USE gestionmatch;

SELECT 
    id,
    nom,
    pays,
    entraineur,
    image_path,
    matchs_joues,
    victoires,
    nuls,
    defaites,
    buts_marques,
    buts_encaisses,
    points
FROM equipe
ORDER BY nom;
