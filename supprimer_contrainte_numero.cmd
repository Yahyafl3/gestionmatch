@echo off
echo ========================================
echo   SUPPRESSION CONTRAINTE NUMERO UNIQUE
echo ========================================
echo.
echo Ce script va permettre d'avoir plusieurs joueurs
echo avec le meme numero dans une equipe.
echo.
pause

echo.
echo Connexion a la base de donnees...
echo.

REM Trouver le chemin de MySQL
set MYSQL_PATH=
if exist "C:\xampp\mysql\bin\mysql.exe" set MYSQL_PATH=C:\xampp\mysql\bin\mysql.exe
if exist "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" set MYSQL_PATH=C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe
if exist "C:\Program Files\MySQL\MySQL Server 5.7\bin\mysql.exe" set MYSQL_PATH=C:\Program Files\MySQL\MySQL Server 5.7\bin\mysql.exe
if exist "C:\wamp64\bin\mysql\mysql8.0.31\bin\mysql.exe" set MYSQL_PATH=C:\wamp64\bin\mysql\mysql8.0.31\bin\mysql.exe

if "%MYSQL_PATH%"=="" (
    echo [ERREUR] MySQL non trouve!
    echo Veuillez installer XAMPP, WAMP ou MySQL Server.
    pause
    exit /b 1
)

echo MySQL trouve: %MYSQL_PATH%
echo.

REM Demander le mot de passe
set /p MYSQL_PASSWORD="Entrez le mot de passe MySQL (par defaut: 123456): "
if "%MYSQL_PASSWORD%"=="" set MYSQL_PASSWORD=123456

echo.
echo Verification des contraintes...
echo.

"%MYSQL_PATH%" -u root -p%MYSQL_PASSWORD% gestion_match -e "SELECT CONSTRAINT_NAME, TABLE_NAME, COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_SCHEMA = 'gestion_match' AND (TABLE_NAME = 'joueur' OR TABLE_NAME = 'joueurs') AND COLUMN_NAME = 'numero';"

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERREUR] Impossible de se connecter a la base de donnees!
    echo Verifiez le mot de passe et que MySQL est demarre.
    pause
    exit /b 1
)

echo.
echo ========================================
echo   SUPPRESSION DE LA CONTRAINTE
echo ========================================
echo.

REM Supprimer la contrainte UNIQUE si elle existe
"%MYSQL_PATH%" -u root -p%MYSQL_PASSWORD% gestion_match -e "ALTER TABLE joueur DROP INDEX IF EXISTS unique_numero_equipe;"
"%MYSQL_PATH%" -u root -p%MYSQL_PASSWORD% gestion_match -e "ALTER TABLE joueur DROP INDEX IF EXISTS numero;"
"%MYSQL_PATH%" -u root -p%MYSQL_PASSWORD% gestion_match -e "ALTER TABLE joueurs DROP INDEX IF EXISTS unique_numero_equipe;"
"%MYSQL_PATH%" -u root -p%MYSQL_PASSWORD% gestion_match -e "ALTER TABLE joueurs DROP INDEX IF EXISTS numero;"

echo.
echo ========================================
echo   VERIFICATION
echo ========================================
echo.

"%MYSQL_PATH%" -u root -p%MYSQL_PASSWORD% gestion_match -e "SHOW CREATE TABLE joueur\G"

echo.
echo ========================================
echo   TERMINE!
echo ========================================
echo.
echo Vous pouvez maintenant ajouter des joueurs
echo avec le meme numero dans une equipe.
echo.
pause
