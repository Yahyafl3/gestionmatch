#!/bin/bash

echo "🔍 Vérification des données dans la base..."
echo ""

mysql -u root gestionmatch << EOF
SELECT '📊 STATISTIQUES' as '';
SELECT COUNT(*) as 'Équipes' FROM equipe;
SELECT COUNT(*) as 'Joueurs' FROM joueur;
SELECT COUNT(*) as 'Stades' FROM stade;
SELECT COUNT(*) as 'Matchs' FROM match_data;
SELECT COUNT(*) as 'Groupes' FROM groupe;

SELECT '' as '';
SELECT '🏆 ÉQUIPES' as '';
SELECT nom, pays, entraineur FROM equipe LIMIT 10;

SELECT '' as '';
SELECT '👤 JOUEURS' as '';
SELECT nom, prenom, numero, poste FROM joueur LIMIT 10;

SELECT '' as '';
SELECT '🏟️ STADES' as '';
SELECT nom, ville, pays, capacite FROM stade LIMIT 10;
EOF
