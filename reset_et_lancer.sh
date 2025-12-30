#!/bin/bash

echo "🔄 Réinitialisation de la base de données..."
mysql -u root -p gestionmatch < reset_database.sql

if [ $? -eq 0 ]; then
    echo "✅ Base de données réinitialisée !"
    echo ""
    echo "🚀 Lancement de l'application..."
    mvn javafx:run
else
    echo "❌ Erreur lors de la réinitialisation"
    exit 1
fi
