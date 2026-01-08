-- ============================================
-- Script pour charger tous les logos des équipes
-- ============================================

USE gestionmatch;

-- Mise à jour des chemins d'images pour toutes les équipes
-- Format: nom_pays.png (en minuscules, sans espaces)

UPDATE equipes SET image_path = 'allemagne.png' WHERE LOWER(pays) LIKE '%allemagne%' OR LOWER(nom) LIKE '%allemagne%';
UPDATE equipes SET image_path = 'angleterre.png' WHERE LOWER(pays) LIKE '%angleterre%' OR LOWER(nom) LIKE '%angleterre%';
UPDATE equipes SET image_path = 'argentine.png' WHERE LOWER(pays) LIKE '%argentine%' OR LOWER(nom) LIKE '%argentine%';
UPDATE equipes SET image_path = 'australie.png' WHERE LOWER(pays) LIKE '%australie%' OR LOWER(nom) LIKE '%australie%';
UPDATE equipes SET image_path = 'bresil.png' WHERE LOWER(pays) LIKE '%br%sil%' OR LOWER(nom) LIKE '%br%sil%';
UPDATE equipes SET image_path = 'croatie.png' WHERE LOWER(pays) LIKE '%croatie%' OR LOWER(nom) LIKE '%croatie%';
UPDATE equipes SET image_path = 'danemark.png' WHERE LOWER(pays) LIKE '%danemark%' OR LOWER(nom) LIKE '%danemark%';
UPDATE equipes SET image_path = 'espagne.png' WHERE LOWER(pays) LIKE '%espagne%' OR LOWER(nom) LIKE '%espagne%';
UPDATE equipes SET image_path = 'etats-unis.png' WHERE LOWER(pays) LIKE '%tats%unis%' OR LOWER(nom) LIKE '%tats%unis%' OR LOWER(pays) LIKE '%usa%';
UPDATE equipes SET image_path = 'france.png' WHERE LOWER(pays) LIKE '%france%' OR LOWER(nom) LIKE '%france%';
UPDATE equipes SET image_path = 'japon.png' WHERE LOWER(pays) LIKE '%japon%' OR LOWER(nom) LIKE '%japon%';
UPDATE equipes SET image_path = 'maroc.png' WHERE LOWER(pays) LIKE '%maroc%' OR LOWER(nom) LIKE '%maroc%';
UPDATE equipes SET image_path = 'mexique.png' WHERE LOWER(pays) LIKE '%mexique%' OR LOWER(nom) LIKE '%mexique%';
UPDATE equipes SET image_path = 'pays-bas.png' WHERE LOWER(pays) LIKE '%pays%bas%' OR LOWER(nom) LIKE '%pays%bas%' OR LOWER(pays) LIKE '%hollande%';
UPDATE equipes SET image_path = 'portugal.png' WHERE LOWER(pays) LIKE '%portugal%' OR LOWER(nom) LIKE '%portugal%';
UPDATE equipes SET image_path = 'senegal.png' WHERE LOWER(pays) LIKE '%s%n%gal%' OR LOWER(nom) LIKE '%s%n%gal%';
UPDATE equipes SET image_path = 'algerie.png' WHERE LOWER(pays) LIKE '%alg%rie%' OR LOWER(nom) LIKE '%alg%rie%';
UPDATE equipes SET image_path = 'belgique.png' WHERE LOWER(pays) LIKE '%belgique%' OR LOWER(nom) LIKE '%belgique%';
UPDATE equipes SET image_path = 'cameroun.png' WHERE LOWER(pays) LIKE '%cameroun%' OR LOWER(nom) LIKE '%cameroun%';
UPDATE equipes SET image_path = 'canada.png' WHERE LOWER(pays) LIKE '%canada%' OR LOWER(nom) LIKE '%canada%';
UPDATE equipes SET image_path = 'coree-sud.png' WHERE LOWER(pays) LIKE '%cor%e%' OR LOWER(nom) LIKE '%cor%e%';
UPDATE equipes SET image_path = 'costa-rica.png' WHERE LOWER(pays) LIKE '%costa%rica%' OR LOWER(nom) LIKE '%costa%rica%';
UPDATE equipes SET image_path = 'equateur.png' WHERE LOWER(pays) LIKE '%quateur%' OR LOWER(nom) LIKE '%quateur%';
UPDATE equipes SET image_path = 'ghana.png' WHERE LOWER(pays) LIKE '%ghana%' OR LOWER(nom) LIKE '%ghana%';
UPDATE equipes SET image_path = 'iran.png' WHERE LOWER(pays) LIKE '%iran%' OR LOWER(nom) LIKE '%iran%';
UPDATE equipes SET image_path = 'italie.png' WHERE LOWER(pays) LIKE '%italie%' OR LOWER(nom) LIKE '%italie%';
UPDATE equipes SET image_path = 'nigeria.png' WHERE LOWER(pays) LIKE '%nigeria%' OR LOWER(nom) LIKE '%nigeria%';
UPDATE equipes SET image_path = 'pologne.png' WHERE LOWER(pays) LIKE '%pologne%' OR LOWER(nom) LIKE '%pologne%';
UPDATE equipes SET image_path = 'qatar.png' WHERE LOWER(pays) LIKE '%qatar%' OR LOWER(nom) LIKE '%qatar%';
UPDATE equipes SET image_path = 'serbie.png' WHERE LOWER(pays) LIKE '%serbie%' OR LOWER(nom) LIKE '%serbie%';
UPDATE equipes SET image_path = 'suisse.png' WHERE LOWER(pays) LIKE '%suisse%' OR LOWER(nom) LIKE '%suisse%';
UPDATE equipes SET image_path = 'tunisie.png' WHERE LOWER(pays) LIKE '%tunisie%' OR LOWER(nom) LIKE '%tunisie%';
UPDATE equipes SET image_path = 'uruguay.png' WHERE LOWER(pays) LIKE '%uruguay%' OR LOWER(nom) LIKE '%uruguay%';
UPDATE equipes SET image_path = 'pays-de-galles.png' WHERE LOWER(pays) LIKE '%galles%' OR LOWER(nom) LIKE '%galles%' OR LOWER(pays) LIKE '%wales%';
UPDATE equipes SET image_path = 'arabie-saoudite.png' WHERE LOWER(pays) LIKE '%arabie%' OR LOWER(nom) LIKE '%arabie%';

-- Vérification des équipes mises à jour
SELECT 
    id,
    nom,
    pays,
    image_path,
    CASE 
        WHEN image_path IS NOT NULL AND image_path != '' THEN '✓ Logo assigné'
        ELSE '✗ Pas de logo'
    END AS statut
FROM equipes
ORDER BY nom;

-- Statistiques
SELECT 
    COUNT(*) as total_equipes,
    SUM(CASE WHEN image_path IS NOT NULL AND image_path != '' THEN 1 ELSE 0 END) as avec_logo,
    SUM(CASE WHEN image_path IS NULL OR image_path = '' THEN 1 ELSE 0 END) as sans_logo
FROM equipes;
