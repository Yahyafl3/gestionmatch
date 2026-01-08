#!/bin/bash

echo "🚀 Lancement de l'application avec le nouveau calendrier..."
echo ""
echo "✅ Nouveau design du calendrier :"
echo "   - Phase de poules avec les 8 groupes (A-H) affichés horizontalement"
echo "   - Calendrier des matchs par date avec couleurs par groupe"
echo "   - Phase finale avec bracket (huitièmes, quarts, demi-finales, finale)"
echo "   - Couleurs : Rouge #C1272D, Or #FFD700, couleurs distinctes par groupe"
echo "   - Drapeaux affichés partout"
echo ""
echo "📋 Pour tester :"
echo "   1. Connectez-vous (admin/admin ou user/user)"
echo "   2. Cliquez sur '📅 Calendrier' dans le menu ANALYSES"
echo "   3. Vous verrez le nouveau design type Coupe du Monde"
echo ""

./mvnw clean javafx:run
