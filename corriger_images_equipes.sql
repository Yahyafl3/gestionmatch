-- Script pour corriger les chemins d'images des équipes
-- Exécuter ce script pour synchroniser les noms d'images avec les fichiers disponibles

USE gestionmatch;

-- Afficher les équipes actuelles et leurs images
SELECT id, nom, pays, image_path FROM equipe;

-- Corriger les noms d'images pour correspondre aux fichiers disponibles
-- Format: nom_pays.png (tout en minuscules, sans espaces)

UPDATE equipe SET image_path = 'algerie.png' WHERE LOWER(nom) = 'algerie' OR LOWER(pays) = 'algerie';
UPDATE equipe SET image_path = 'maroc.png' WHERE LOWER(nom) = 'maroc' OR LOWER(pays) = 'maroc';
UPDATE equipe SET image_path = 'tunisie.png' WHERE LOWER(nom) = 'tunisie' OR LOWER(pays) = 'tunisie';
UPDATE equipe SET image_path = 'egypte.png' WHERE LOWER(nom) = 'egypte' OR LOWER(pays) = 'egypte';
UPDATE equipe SET image_path = 'senegal.png' WHERE LOWER(nom) = 'senegal' OR LOWER(pays) = 'senegal';
UPDATE equipe SET image_path = 'nigeria.png' WHERE LOWER(nom) = 'nigeria' OR LOWER(pays) = 'nigeria';
UPDATE equipe SET image_path = 'cameroun.png' WHERE LOWER(nom) = 'cameroun' OR LOWER(pays) = 'cameroun';
UPDATE equipe SET image_path = 'ghana.png' WHERE LOWER(nom) = 'ghana' OR LOWER(pays) = 'ghana';
UPDATE equipe SET image_path = 'cote_d_ivoire.png' WHERE LOWER(nom) LIKE '%ivoire%' OR LOWER(pays) LIKE '%ivoire%';
UPDATE equipe SET image_path = 'afrique_du_sud.png' WHERE LOWER(nom) LIKE '%afrique%sud%' OR LOWER(pays) LIKE '%afrique%sud%';

UPDATE equipe SET image_path = 'france.png' WHERE LOWER(nom) = 'france' OR LOWER(pays) = 'france';
UPDATE equipe SET image_path = 'allemagne.png' WHERE LOWER(nom) = 'allemagne' OR LOWER(pays) = 'allemagne';
UPDATE equipe SET image_path = 'espagne.png' WHERE LOWER(nom) = 'espagne' OR LOWER(pays) = 'espagne';
UPDATE equipe SET image_path = 'italie.png' WHERE LOWER(nom) = 'italie' OR LOWER(pays) = 'italie';
UPDATE equipe SET image_path = 'angleterre.png' WHERE LOWER(nom) = 'angleterre' OR LOWER(pays) = 'angleterre';
UPDATE equipe SET image_path = 'portugal.png' WHERE LOWER(nom) = 'portugal' OR LOWER(pays) = 'portugal';
UPDATE equipe SET image_path = 'pays_bas.png' WHERE LOWER(nom) LIKE '%pays%bas%' OR LOWER(pays) LIKE '%pays%bas%';
UPDATE equipe SET image_path = 'belgique.png' WHERE LOWER(nom) = 'belgique' OR LOWER(pays) = 'belgique';
UPDATE equipe SET image_path = 'croatie.png' WHERE LOWER(nom) = 'croatie' OR LOWER(pays) = 'croatie';
UPDATE equipe SET image_path = 'danemark.png' WHERE LOWER(nom) = 'danemark' OR LOWER(pays) = 'danemark';

UPDATE equipe SET image_path = 'bresil.png' WHERE LOWER(nom) = 'bresil' OR LOWER(pays) = 'bresil';
UPDATE equipe SET image_path = 'argentine.png' WHERE LOWER(nom) = 'argentine' OR LOWER(pays) = 'argentine';
UPDATE equipe SET image_path = 'uruguay.png' WHERE LOWER(nom) = 'uruguay' OR LOWER(pays) = 'uruguay';
UPDATE equipe SET image_path = 'colombie.png' WHERE LOWER(nom) = 'colombie' OR LOWER(pays) = 'colombie';
UPDATE equipe SET image_path = 'chili.png' WHERE LOWER(nom) = 'chili' OR LOWER(pays) = 'chili';
UPDATE equipe SET image_path = 'mexique.png' WHERE LOWER(nom) = 'mexique' OR LOWER(pays) = 'mexique';
UPDATE equipe SET image_path = 'etats_unis.png' WHERE LOWER(nom) LIKE '%etats%unis%' OR LOWER(pays) LIKE '%etats%unis%';
UPDATE equipe SET image_path = 'canada.png' WHERE LOWER(nom) = 'canada' OR LOWER(pays) = 'canada';

UPDATE equipe SET image_path = 'japon.png' WHERE LOWER(nom) = 'japon' OR LOWER(pays) = 'japon';
UPDATE equipe SET image_path = 'coree_du_sud.png' WHERE LOWER(nom) LIKE '%coree%' OR LOWER(pays) LIKE '%coree%';
UPDATE equipe SET image_path = 'australie.png' WHERE LOWER(nom) = 'australie' OR LOWER(pays) = 'australie';
UPDATE equipe SET image_path = 'arabie_saoudite.png' WHERE LOWER(nom) LIKE '%arabie%' OR LOWER(pays) LIKE '%arabie%';
UPDATE equipe SET image_path = 'iran.png' WHERE LOWER(nom) = 'iran' OR LOWER(pays) = 'iran';
UPDATE equipe SET image_path = 'qatar.png' WHERE LOWER(nom) = 'qatar' OR LOWER(pays) = 'qatar';
UPDATE equipe SET image_path = 'emirats_arabes_unis.png' WHERE LOWER(nom) LIKE '%emirats%' OR LOWER(pays) LIKE '%emirats%';

UPDATE equipe SET image_path = 'nouvelle_zelande.png' WHERE LOWER(nom) LIKE '%zelande%' OR LOWER(pays) LIKE '%zelande%';
UPDATE equipe SET image_path = 'costa_rica.png' WHERE LOWER(nom) LIKE '%costa%' OR LOWER(pays) LIKE '%costa%';
UPDATE equipe SET image_path = 'equateur.png' WHERE LOWER(nom) = 'equateur' OR LOWER(pays) = 'equateur';
UPDATE equipe SET image_path = 'perou.png' WHERE LOWER(nom) = 'perou' OR LOWER(pays) = 'perou';

-- Afficher le résultat
SELECT id, nom, pays, image_path FROM equipe ORDER BY nom;

-- Compter les équipes avec et sans image
SELECT 
    COUNT(*) as total_equipes,
    SUM(CASE WHEN image_path IS NOT NULL AND image_path != '' THEN 1 ELSE 0 END) as avec_image,
    SUM(CASE WHEN image_path IS NULL OR image_path = '' THEN 1 ELSE 0 END) as sans_image
FROM equipe;
