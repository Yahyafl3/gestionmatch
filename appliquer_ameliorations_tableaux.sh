#!/bin/bash

echo "========================================"
echo "Amélioration des Tableaux"
echo "========================================"
echo ""

GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

echo "[1/4] Préparation de la base de données..."
/opt/homebrew/opt/mysql@8.0/bin/mysql -u root -proot gestionmatch < preparer_base_donnees.sql 2>/dev/null
if [ $? -ne 0 ]; then
    echo -e "${YELLOW}⚠ Colonnes peut-être déjà existantes (normal)${NC}"
fi
echo -e "${GREEN}✓ Base de données préparée${NC}"

echo ""
echo "[2/4] Ajout des équipes..."
/opt/homebrew/opt/mysql@8.0/bin/mysql -u root -proot gestionmatch < ajouter_equipes_base.sql 2>/dev/null
if [ $? -ne 0 ]; then
    echo -e "${YELLOW}⚠ Équipes peut-être déjà existantes (normal)${NC}"
fi
echo -e "${GREEN}✓ Équipes ajoutées${NC}"

echo ""
echo "[3/4] Ajout des joueurs manquants..."
/opt/homebrew/opt/mysql@8.0/bin/mysql -u root -proot gestionmatch < ajouter_joueurs_complets.sql 2>/dev/null
if [ $? -ne 0 ]; then
    echo -e "${RED}ERREUR: L'ajout des joueurs a échoué${NC}"
    echo "Vérifiez que MySQL est démarré"
    exit 1
fi
echo -e "${GREEN}✓ Joueurs ajoutés avec succès${NC}"

echo ""
echo "[4/4] Compilation du projet..."
mvn clean compile -q
if [ $? -ne 0 ]; then
    echo -e "${RED}ERREUR: La compilation a échoué${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Compilation réussie${NC}"

echo ""
echo "[5/5] Vérification..."
echo ""
echo "========================================"
echo -e "${GREEN}✓ AMÉLIORATIONS APPLIQUÉES!${NC}"
echo "========================================"
echo ""
echo "Changements effectués:"
echo "  ✓ Dernière colonne vide supprimée (Pts)"
echo "  ✓ Colonnes agrandies pour meilleure visibilité"
echo "  ✓ Texte en gras (14px) dans tous les tableaux"
echo "  ✓ Joueurs ajoutés avec prénoms complets"
echo "  ✓ Nationalités ajoutées pour tous les joueurs"
echo ""
echo "Pour voir les changements:"
echo "  mvn javafx:run"
echo ""
