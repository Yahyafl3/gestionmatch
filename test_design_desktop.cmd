@echo off
echo ========================================
echo   TEST DESIGN DESKTOP PROFESSIONNEL
echo ========================================
echo.
echo Lancement de l'application...
echo.
echo Pages transformees :
echo   - Joueurs (avec panneau details)
echo   - Matchs (avec panneau details)
echo   - Groupes (vue grille)
echo   - Equipes (avec stats)
echo   - Stades (avec infos)
echo.
echo Fonctionnalites a tester :
echo   1. MenuBar (Fichier, Edition, Affichage)
echo   2. Toolbar avec icones
echo   3. Recherche integree
echo   4. Panneau de details (clic sur ligne)
echo   5. SplitPane redimensionnable
echo   6. Raccourcis clavier (Ctrl+N, F5, etc.)
echo.
echo ========================================
echo.

call mvnw clean javafx:run

pause
