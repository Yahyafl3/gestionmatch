@echo off
chcp 65001 >nul
echo ========================================
echo Application des logos des équipes
echo ========================================
echo.

echo [1/3] Téléchargement des drapeaux...
powershell -ExecutionPolicy Bypass -File telecharger_tous_drapeaux.ps1
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: Le téléchargement a échoué
    pause
    exit /b 1
)

echo.
echo [2/3] Mise à jour de la base de données...
mysql -u root -proot gestion_match < charger_logos_equipes.sql
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: La mise à jour de la base de données a échoué
    echo Vérifiez que MySQL est démarré et que les identifiants sont corrects
    pause
    exit /b 1
)

echo.
echo [3/3] Compilation du projet...
call mvn clean compile -q
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: La compilation a échoué
    pause
    exit /b 1
)

echo.
echo ========================================
echo ✓ TERMINÉ!
echo ========================================
echo.
echo Les logos des équipes ont été chargés avec succès!
echo.
echo Pour voir les logos, lancez l'application:
echo   mvn javafx:run
echo.
echo Ou utilisez:
echo   run.cmd
echo.

pause
