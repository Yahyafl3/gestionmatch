#!/bin/bash

echo "========================================"
echo "Complétion des Prénoms et Tests"
echo "========================================"

# Couleurs
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Configuration MySQL
MYSQL_PATH="/opt/homebrew/opt/mysql@8.0/bin/mysql"
DB_NAME="gestionmatch"
DB_USER="root"
DB_PASS="root"

echo ""
echo "${YELLOW}[1/3] Complétion des prénoms manquants...${NC}"
if $MYSQL_PATH -u$DB_USER -p$DB_PASS $DB_NAME < completer_prenoms_joueurs.sql 2>/dev/null; then
    echo "${GREEN}✅ Prénoms complétés avec succès${NC}"
else
    echo "${RED}❌ Erreur lors de la complétion des prénoms${NC}"
    echo "Vérifiez que MySQL est démarré et que la base existe"
fi

echo ""
echo "${YELLOW}[2/3] Vérification des données...${NC}"
echo "Nombre de joueurs avec/sans prénom:"
$MYSQL_PATH -u$DB_USER -p$DB_PASS $DB_NAME -e "
SELECT 
    COUNT(*) as total_joueurs, 
    SUM(CASE WHEN prenom IS NOT NULL AND prenom != '' THEN 1 ELSE 0 END) as avec_prenom,
    SUM(CASE WHEN prenom IS NULL OR prenom = '' THEN 1 ELSE 0 END) as sans_prenom
FROM joueur;" 2>/dev/null

echo ""
echo "${YELLOW}[3/3] Compilation et test de l'application...${NC}"
echo "Compilation du projet..."
if mvn clean compile -q 2>&1 | grep -q "BUILD SUCCESS"; then
    echo "${GREEN}✅ Compilation réussie${NC}"
    echo ""
    echo "${GREEN}========================================${NC}"
    echo "${GREEN}✅ TOUT EST PRÊT!${NC}"
    echo "${GREEN}========================================${NC}"
    echo ""
    echo "Pour lancer l'application:"
    echo "  ${YELLOW}mvn javafx:run${NC}"
    echo ""
    echo "Connexion:"
    echo "  Admin: admin / admin"
    echo "  User:  user / user"
    echo ""
    echo "Nouvelles fonctionnalités à tester:"
    echo "  1. ${GREEN}Menu ANALYSES${NC} → Statistiques, Calendrier, Comparaison"
    echo "  2. ${GREEN}Header${NC} → Recherche, Notifications, Profil"
    echo "  3. ${GREEN}Joueurs${NC} → Tous les prénoms complétés"
    echo ""
else
    echo "${RED}❌ Erreur de compilation${NC}"
    mvn clean compile 2>&1 | tail -20
fi

echo ""
echo "========================================"
echo "Script terminé"
echo "========================================"
