-- Script pour vérifier les drapeaux dans les groupes
USE gestionmatch;

-- 1. Afficher toutes les équipes avec leurs images
SELECT id, nom, pays, image_path 
FROM equipe 
ORDER BY nom;

-- 2. Afficher les groupes et leurs équipes
SELECT 
    g.nom as groupe,
    e.nom as equipe,
    e.image_path as drapeau
FROM groupe g
LEFT JOIN groupe_equipe ge ON g.id = ge.groupe_id
LEFT JOIN equipe e ON ge.equipe_id = e.id
ORDER BY g.nom, e.nom;

-- 3. Compter les équipes par groupe
SELECT 
    g.nom as groupe,
    COUNT(ge.equipe_id) as nb_equipes
FROM groupe g
LEFT JOIN groupe_equipe ge ON g.id = ge.groupe_id
GROUP BY g.id, g.nom
ORDER BY g.nom;

-- 4. Vérifier les équipes sans image
SELECT id, nom, pays, image_path
FROM equipe
WHERE image_path IS NULL OR image_path = ''
ORDER BY nom;
