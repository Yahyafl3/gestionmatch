#!/bin/bash

echo "========================================"
echo "Complétion des Prénoms (MAMP)"
echo "========================================"

# Configuration MySQL MAMP
MYSQL_PATH="/Applications/MAMP/Library/bin/mysql80/bin/mysql"
DB_NAME="gestionmatch"
DB_USER="root"
DB_PASS="root"
MYSQL_SOCKET="/Applications/MAMP/tmp/mysql/mysql.sock"

echo ""
echo "Complétion des prénoms manquants..."
if $MYSQL_PATH -u$DB_USER -p$DB_PASS --socket=$MYSQL_SOCKET $DB_NAME < completer_prenoms_joueurs.sql 2>/dev/null; then
    echo "✅ Prénoms complétés avec succès"
    
    echo ""
    echo "Vérification:"
    $MYSQL_PATH -u$DB_USER -p$DB_PASS --socket=$MYSQL_SOCKET $DB_NAME -e "
    SELECT 
        COUNT(*) as total_joueurs, 
        SUM(CASE WHEN prenom IS NOT NULL AND prenom != '' THEN 1 ELSE 0 END) as avec_prenom,
        SUM(CASE WHEN prenom IS NULL OR prenom = '' THEN 1 ELSE 0 END) as sans_prenom
    FROM joueur;" 2>/dev/null
    
    echo ""
    echo "Exemples de joueurs:"
    $MYSQL_PATH -u$DB_USER -p$DB_PASS --socket=$MYSQL_SOCKET $DB_NAME -e "
    SELECT nom, prenom, numero, poste 
    FROM joueur 
    WHERE prenom IS NOT NULL AND prenom != ''
    LIMIT 10;" 2>/dev/null
    
else
    echo "❌ Erreur lors de la complétion"
    echo "Vérifiez que MAMP est démarré"
fi

echo ""
echo "========================================"
echo "Terminé"
echo "========================================"
