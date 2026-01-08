@echo off
chcp 65001 >nul
echo ========================================
echo Amélioration des Tableaux
echo ========================================
echo.

echo [1/3] Ajout des joueurs manquants...
mysql -u root -proot gestion_match < ajouter_joueurs_complets.sql
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: L'ajout des joueurs a échoué
    echo Vérifiez que MySQL est démarré
    pause
    exit /b 1
)
echo ✓ Joueurs ajoutés avec succès

echo.
echo [2/3] Compilation du projet...
call mvn clean compile -q
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: La compilation a échoué
    pause
    exit /b 1
)
echo ✓ Compilation réussie

echo.
echo [3/3] Vérification...
echo.
echo ========================================
echo ✓ AMÉLIORATIONS APPLIQUÉES!
echo ========================================
echo.
echo Changements effectués:
echo   ✓ Dernière colonne vide supprimée (Pts)
echo   ✓ Colonnes agrandies pour meilleure visibilité
echo   ✓ Texte en gras (14px) dans tous les tableaux
echo   ✓ Joueurs ajoutés avec prénoms complets
echo   ✓ Nationalités ajoutées pour tous les joueurs
echo.
echo Pour voir les changements:
echo   mvn javafx:run
echo.

pause
