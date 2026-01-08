#!/bin/bash

echo "========================================"
echo "Application des logos des équipes"
echo "========================================"
echo ""

# Couleurs
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Fonction pour télécharger un drapeau
telecharger_drapeau() {
    local pays=$1
    local code=$2
    local fichier="src/main/resources/images/equipes/${pays}.png"
    local url="https://flagcdn.com/w320/${code}.png"
    
    if [ -f "$fichier" ]; then
        echo -e "  ${YELLOW}⊙${NC} ${pays}.png (déjà existant)"
        return 0
    else
        echo -n "  ↓ Téléchargement de ${pays}.png..."
        if curl -s -o "$fichier" "$url"; then
            echo -e " ${GREEN}✓${NC}"
            return 0
        else
            echo -e " ${RED}✗ ERREUR${NC}"
            return 1
        fi
    fi
}

echo "[1/3] Téléchargement des drapeaux..."
echo ""

# Créer le dossier si nécessaire
mkdir -p src/main/resources/images/equipes

# Télécharger tous les drapeaux
telecharger_drapeau "allemagne" "de"
telecharger_drapeau "angleterre" "gb-eng"
telecharger_drapeau "argentine" "ar"
telecharger_drapeau "australie" "au"
telecharger_drapeau "bresil" "br"
telecharger_drapeau "croatie" "hr"
telecharger_drapeau "danemark" "dk"
telecharger_drapeau "espagne" "es"
telecharger_drapeau "etats-unis" "us"
telecharger_drapeau "france" "fr"
telecharger_drapeau "japon" "jp"
telecharger_drapeau "maroc" "ma"
telecharger_drapeau "mexique" "mx"
telecharger_drapeau "pays-bas" "nl"
telecharger_drapeau "portugal" "pt"
telecharger_drapeau "senegal" "sn"
telecharger_drapeau "algerie" "dz"
telecharger_drapeau "belgique" "be"
telecharger_drapeau "cameroun" "cm"
telecharger_drapeau "canada" "ca"
telecharger_drapeau "coree-sud" "kr"
telecharger_drapeau "costa-rica" "cr"
telecharger_drapeau "equateur" "ec"
telecharger_drapeau "ghana" "gh"
telecharger_drapeau "iran" "ir"
telecharger_drapeau "italie" "it"
telecharger_drapeau "nigeria" "ng"
telecharger_drapeau "pologne" "pl"
telecharger_drapeau "qatar" "qa"
telecharger_drapeau "serbie" "rs"
telecharger_drapeau "suisse" "ch"
telecharger_drapeau "tunisie" "tn"
telecharger_drapeau "uruguay" "uy"
telecharger_drapeau "pays-de-galles" "gb-wls"
telecharger_drapeau "arabie-saoudite" "sa"

echo ""
echo "[2/3] Mise à jour de la base de données..."
/opt/homebrew/opt/mysql@8.0/bin/mysql -u root -proot gestionmatch < charger_logos_equipes.sql 2>/dev/null
if [ $? -ne 0 ]; then
    echo -e "${RED}ERREUR: La mise à jour de la base de données a échoué${NC}"
    echo "Vérifiez que MySQL est démarré et que les identifiants sont corrects"
    exit 1
fi

echo ""
echo "[3/3] Compilation du projet..."
mvn clean compile -q
if [ $? -ne 0 ]; then
    echo -e "${RED}ERREUR: La compilation a échoué${NC}"
    exit 1
fi

echo ""
echo "========================================"
echo -e "${GREEN}✓ TERMINÉ!${NC}"
echo "========================================"
echo ""
echo "Les logos des équipes ont été chargés avec succès!"
echo ""
echo "Pour voir les logos, lancez l'application:"
echo "  mvn javafx:run"
echo ""
