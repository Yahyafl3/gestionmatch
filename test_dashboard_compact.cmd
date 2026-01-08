@echo off
echo ========================================
echo TEST DASHBOARD COMPACT - Petites Cartes
echo ========================================
echo.
echo Compilation...
call mvnw.cmd clean compile
echo.
echo Lancement de l'application...
echo Verifiez que les 6 cartes sont PETITES (85px hauteur)
echo.
call mvnw.cmd javafx:run
