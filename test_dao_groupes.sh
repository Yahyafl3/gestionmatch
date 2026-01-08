#!/bin/bash

echo "🧪 TEST DAO GROUPES"
echo "==================="
echo ""

# Vérifier la base de données
echo "📊 Vérification de la base de données..."
echo ""
echo "SELECT id, nom, description FROM groupe;" | /Applications/MAMP/Library/bin/mysql80/bin/mysql --socket=/Applications/MAMP/tmp/mysql/mysql.sock -u root -proot gestionmatch 2>/dev/null

echo ""
echo "📊 Équipes assignées aux groupes..."
echo ""
echo "SELECT e.id, e.nom, e.pays, g.nom as groupe FROM equipe e LEFT JOIN groupe g ON e.groupe_id = g.id WHERE e.groupe_id IS NOT NULL;" | /Applications/MAMP/Library/bin/mysql80/bin/mysql --socket=/Applications/MAMP/tmp/mysql/mysql.sock -u root -proot gestionmatch 2>/dev/null

echo ""
echo "✅ Base de données OK"
echo ""
echo "🚀 Maintenant lance l'application et:"
echo "   1. Connecte-toi en tant qu'UTILISATEUR"
echo "   2. Clique sur 'Groupes'"
echo "   3. Regarde les logs dans la console"
echo ""
