@echo off
echo === Test de chargement d'image ===
echo.
echo Verification du fichier maroc.png:
if exist "target\classes\images\equipes\maroc.png" (
    echo [OK] Fichier existe
    dir "target\classes\images\equipes\maroc.png"
) else (
    echo [ERREUR] Fichier n'existe pas!
)
echo.
echo Lancez l'application et regardez la console pour les messages de debug
pause
