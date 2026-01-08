#!/bin/bash

echo "🧪 TEST AFFICHAGE GROUPES CÔTÉ USER"
echo "===================================="
echo ""
echo "📋 INSTRUCTIONS:"
echo "1. L'application va se lancer"
echo "2. Connectez-vous en tant qu'UTILISATEUR (pas admin)"
echo "3. Cliquez sur le bouton 'Groupes' dans le menu"
echo "4. Vérifiez que les groupes créés par l'admin s'affichent"
echo "5. Allez dans 'Équipes' et testez le filtre par groupe"
echo ""
echo "📊 LOGS DE DÉBOGAGE:"
echo "Les logs afficheront:"
echo "  - Nombre de groupes trouvés dans la base"
echo "  - Nombre d'équipes trouvées"
echo "  - Détails de la structure FXML"
echo "  - Création des cartes de groupes"
echo ""
echo "🚀 Lancement de l'application..."
echo ""

./mvnw clean javafx:run
