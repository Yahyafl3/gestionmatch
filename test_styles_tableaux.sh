#!/bin/bash

echo "========================================"
echo "Test des Styles de Tableaux et Boutons"
echo "========================================"
echo ""

echo "[1/3] Compilation du projet..."
mvn clean compile -q
if [ $? -ne 0 ]; then
    echo "ERREUR: La compilation a échoué"
    exit 1
fi
echo "✓ Compilation réussie"

echo ""
echo "[2/3] Vérification des fichiers CSS..."
if [ -f "src/main/resources/styles/dashboard.css" ]; then
    echo "✓ dashboard.css trouvé"
else
    echo "✗ dashboard.css manquant"
    exit 1
fi

if [ -f "src/main/resources/styles/modern-management.css" ]; then
    echo "✓ modern-management.css trouvé"
else
    echo "✗ modern-management.css manquant"
    exit 1
fi

echo ""
echo "[3/3] Lancement de l'application..."
echo ""
echo "========================================"
echo "VÉRIFICATIONS À FAIRE:"
echo "========================================"
echo "1. Tableaux avec bordures épaisses (3px)"
echo "2. Bordures entre colonnes visibles (2px)"
echo "3. En-tête rouge avec texte blanc"
echo "4. Lignes alternées (blanc/gris)"
echo "5. Effet hover rouge clair"
echo "6. Boutons avec effet scale 1.08"
echo "7. Ombre prononcée sur les boutons"
echo "========================================"
echo ""

mvn javafx:run
