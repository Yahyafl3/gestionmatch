@echo off
echo ========================================
echo Test des Styles de Tableaux et Boutons
echo ========================================
echo.

echo [1/3] Compilation du projet...
call mvn clean compile -q
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: La compilation a echoue
    pause
    exit /b 1
)
echo ✓ Compilation reussie

echo.
echo [2/3] Verification des fichiers CSS...
if exist "src\main\resources\styles\dashboard.css" (
    echo ✓ dashboard.css trouve
) else (
    echo ✗ dashboard.css manquant
    pause
    exit /b 1
)

if exist "src\main\resources\styles\modern-management.css" (
    echo ✓ modern-management.css trouve
) else (
    echo ✗ modern-management.css manquant
    pause
    exit /b 1
)

echo.
echo [3/3] Lancement de l'application...
echo.
echo ========================================
echo VERIFICATIONS A FAIRE:
echo ========================================
echo 1. Tableaux avec bordures epaisses (3px)
echo 2. Bordures entre colonnes visibles (2px)
echo 3. En-tete rouge avec texte blanc
echo 4. Lignes alternees (blanc/gris)
echo 5. Effet hover rouge clair
echo 6. Boutons avec effet scale 1.08
echo 7. Ombre prononcee sur les boutons
echo ========================================
echo.

call mvn javafx:run

pause
