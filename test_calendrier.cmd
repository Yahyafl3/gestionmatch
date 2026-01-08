@echo off
echo ========================================
echo   TEST DU CALENDRIER DYNAMIQUE
echo ========================================
echo.

echo [1/3] Compilation du projet...
call .\mvnw.cmd clean package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERREUR] La compilation a echoue!
    pause
    exit /b 1
)

echo.
echo [2/3] Arret des processus Java en cours...
taskkill /F /IM java.exe 2>nul
timeout /t 2 /nobreak >nul

echo.
echo [3/3] Lancement de l'application...
echo.
echo ========================================
echo   INSTRUCTIONS DE TEST:
echo ========================================
echo.
echo 1. Connectez-vous en tant qu'ADMIN
echo 2. Allez dans "Gestion des Matchs"
echo 3. Creez quelques matchs avec des dates differentes
echo 4. Cliquez sur "Calendrier" dans le menu
echo 5. Testez la navigation (Precedent/Suivant)
echo 6. Cliquez sur les jours avec des matchs
echo.
echo 7. Deconnectez-vous
echo 8. Connectez-vous en tant qu'UTILISATEUR
echo 9. Cliquez sur "Calendrier"
echo 10. Verifiez que vous voyez les memes matchs
echo.
echo ========================================
echo.

java -jar target\gestionmatch-1.0-SNAPSHOT.jar

pause
