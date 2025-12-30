@echo off
echo Chargement des donnees de la Coupe du Monde 2030...
echo.

mysql -u root gestionmatch < forcer_insertion_donnees.sql

if %ERRORLEVEL% EQU 0 (
    echo.
    echo =========================================
    echo DONNEES CHARGEES AVEC SUCCES !
    echo =========================================
    echo.
    echo Vous pouvez maintenant :
    echo    1. Relancer l'application
    echo    2. Vous connecter avec admin@fifa2030.com / Admin123
    echo    3. Voir les equipes, joueurs, stades et matchs
    echo.
) else (
    echo.
    echo Erreur lors du chargement des donnees
    echo Verifiez que MySQL est demarre et accessible
    exit /b 1
)

pause
