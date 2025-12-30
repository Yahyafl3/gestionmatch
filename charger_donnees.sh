#!/bin/bash

echo "🔄 Chargement des données de la Coupe du Monde 2030..."
echo ""

mysql -u root gestionmatch < forcer_insertion_donnees.sql

if [ $? -eq 0 ]; then
    echo ""
    echo "========================================="
    echo "✅ DONNÉES CHARGÉES AVEC SUCCÈS !"
    echo "========================================="
    echo ""
    echo "📊 Vous pouvez maintenant :"
    echo "   1. Relancer l'application"
    echo "   2. Vous connecter avec admin@fifa2030.com / Admin123"
    echo "   3. Voir les équipes, joueurs, stades et matchs"
    echo ""
else
    echo ""
    echo "❌ Erreur lors du chargement des données"
    echo "Vérifiez que MySQL est démarré et accessible"
    exit 1
fi
