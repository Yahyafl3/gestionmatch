@echo off
REM Script de lancement - Application FIFA 2030

setlocal enabledelayedexpansion

color 0B
cls

echo ========================================
echo [4m[32m 🏆 Lancement de l'Application FIFA 2030[0m
echo ========================================
echo.

REM Verifier la compilation
echo [33m📦 Vérification de la compilation...[0m

if exist "target\classes\com\example\gestionmatch\Main.class" (
    echo [32m✅ Projet compilé[0m
) else (
    echo [33m⚠️  Compilation du projet en cours...[0m
    call mvnw.cmd clean compile -DskipTests=true
    if !errorlevel! neq 0 (
        echo [31m❌ Erreur de compilation[0m
        pause
        exit /b 1
    )
)

echo.
echo [36m🚀 Démarrage de l'application...[0m
echo.

REM Lancer avec Java directement (plus rapide)
if defined JAVA_HOME (
    set JAVA_CMD=!JAVA_HOME!\bin\java.exe
) else (
    REM Essayer de trouver Java dans le PATH
    for /f "delims=" %%A in ('where java 2^>nul') do set JAVA_CMD=%%A
    if not defined JAVA_CMD (
        echo [31m❌ Java 17+ non trouvé[0m
        echo.
        echo [33mAlternative :[0m
        echo mvnw.cmd -q exec:java -Dexec.mainClass=com.example.gestionmatch.Main
        echo.
        pause
        exit /b 1
    )
)

REM Executer l'application avec Maven (gère automatiquement JavaFX)
call mvnw.cmd -q javafx:run

echo.
echo ========================================
echo [33m👋 Application fermée[0m
echo ========================================

endlocal
pause

