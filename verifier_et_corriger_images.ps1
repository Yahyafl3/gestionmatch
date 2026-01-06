# Script pour vérifier et corriger les images des équipes

Write-Host "=== Vérification des Images des Équipes ===" -ForegroundColor Cyan
Write-Host ""

# 1. Vérifier les images disponibles
Write-Host "1. Images disponibles dans target/classes/images/equipes/:" -ForegroundColor Yellow
if (Test-Path "target\classes\images\equipes") {
    $images = Get-ChildItem "target\classes\images\equipes\*.png"
    $images | ForEach-Object {
        Write-Host "   ✓ $($_.Name)" -ForegroundColor Green
    }
    Write-Host "   Total: $($images.Count) images" -ForegroundColor Cyan
} else {
    Write-Host "   ✗ Dossier n'existe pas!" -ForegroundColor Red
}

Write-Host ""

# 2. Copier les images si nécessaire
Write-Host "2. Copie des images depuis src vers target:" -ForegroundColor Yellow
if (Test-Path "src\main\resources\images\equipes") {
    if (-not (Test-Path "target\classes\images\equipes")) {
        New-Item -ItemType Directory -Path "target\classes\images\equipes" -Force | Out-Null
    }
    Copy-Item "src\main\resources\images\equipes\*" "target\classes\images\equipes\" -Force
    Write-Host "   ✓ Images copiées" -ForegroundColor Green
} else {
    Write-Host "   ✗ Dossier source n'existe pas!" -ForegroundColor Red
}

Write-Host ""

# 3. Instructions pour corriger la base de données
Write-Host "3. Pour corriger la base de données:" -ForegroundColor Yellow
Write-Host "   a) Ouvrez MySQL Workbench ou ligne de commande MySQL" -ForegroundColor White
Write-Host "   b) Exécutez le script: corriger_images_equipes.sql" -ForegroundColor White
Write-Host "   c) OU exécutez cette commande:" -ForegroundColor White
Write-Host ""
Write-Host "      mysql -u root -p gestionmatch < corriger_images_equipes.sql" -ForegroundColor Cyan
Write-Host ""

# 4. Recompiler l'application
Write-Host "4. Recompilation de l'application:" -ForegroundColor Yellow
Write-Host "   Exécutez: .\mvnw.cmd clean compile" -ForegroundColor White
Write-Host ""

# 5. Lancer l'application
Write-Host "5. Lancer l'application:" -ForegroundColor Yellow
Write-Host "   Exécutez: .\mvnw.cmd javafx:run" -ForegroundColor White
Write-Host ""

Write-Host "=== Fin de la vérification ===" -ForegroundColor Cyan
Write-Host ""
Write-Host "Appuyez sur une touche pour continuer..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
