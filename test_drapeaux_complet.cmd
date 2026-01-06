@echo off
chcp 65001 >nul
echo ========================================
echo Test Complet des Drapeaux dans Groupes
echo ========================================
echo.

echo [1/4] Vérification des images...
powershell -ExecutionPolicy Bypass -File verifier_correspondance_images.ps1
echo.

echo [2/4] Vérification de la base de données...
echo Exécutez manuellement: mysql -u root -p ^< verifier_drapeaux_groupes.sql
echo.

echo [3/4] Compilation du projet...
echo Dans IntelliJ IDEA:
echo   - Cliquez sur Build ^> Rebuild Project
echo   - Attendez la fin de la compilation
echo.

echo [4/4] Lancement de l'application...
echo Dans IntelliJ IDEA:
echo   - Cliquez sur le bouton Run (▶️)
echo   - Connectez-vous avec un compte admin
echo   - Allez dans Gestion des Groupes
echo   - Vérifiez que les drapeaux s'affichent
echo.

echo ========================================
echo Résultats Attendus:
echo ========================================
echo ✓ Drapeaux (32x24 px) AVANT le nom
echo ✓ Format: 🇲🇦 maroc (drapeau + nom)
echo ✓ Effet d'ombre portée
echo.
echo Si vous voyez 🏴 au lieu du drapeau:
echo   - Le fichier image n'existe pas
echo   - Recompilez le projet
echo.

pause
