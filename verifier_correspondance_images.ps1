# Script pour vérifier la correspondance entre les images et la base de données

Write-Host "=== Vérification des images de drapeaux ===" -ForegroundColor Cyan
Write-Host ""

# Vérifier les répertoires
$srcDir = "src\main\resources\images\equipes"
$targetDir = "target\classes\images\equipes"

Write-Host "1. Vérification des répertoires:" -ForegroundColor Yellow
if (Test-Path $srcDir) {
    $srcCount = (Get-ChildItem $srcDir -Filter *.png).Count
    Write-Host "   ✓ $srcDir existe ($srcCount fichiers)" -ForegroundColor Green
} else {
    Write-Host "   ✗ $srcDir n'existe pas" -ForegroundColor Red
}

if (Test-Path $targetDir) {
    $targetCount = (Get-ChildItem $targetDir -Filter *.png).Count
    Write-Host "   ✓ $targetDir existe ($targetCount fichiers)" -ForegroundColor Green
} else {
    Write-Host "   ✗ $targetDir n'existe pas" -ForegroundColor Red
}

Write-Host ""
Write-Host "2. Liste des images dans target/classes:" -ForegroundColor Yellow
if (Test-Path $targetDir) {
    Get-ChildItem $targetDir -Filter *.png | ForEach-Object {
        $size = [math]::Round($_.Length / 1KB, 2)
        Write-Host "   - $($_.Name) ($size KB)" -ForegroundColor White
    }
} else {
    Write-Host "   Aucune image trouvée" -ForegroundColor Red
}

Write-Host ""
Write-Host "3. Vérification des images principales:" -ForegroundColor Yellow
$mainImages = @("algerie.png", "maroc.png", "tunisie.png", "egypte.png", "france.png", "bresil.png")
foreach ($img in $mainImages) {
    $srcPath = Join-Path $srcDir $img
    $targetPath = Join-Path $targetDir $img
    
    $srcExists = Test-Path $srcPath
    $targetExists = Test-Path $targetPath
    
    if ($srcExists -and $targetExists) {
        Write-Host "   ✓ $img présent dans les deux répertoires" -ForegroundColor Green
    } elseif ($srcExists) {
        Write-Host "   ⚠ $img présent dans src mais pas dans target" -ForegroundColor Yellow
    } elseif ($targetExists) {
        Write-Host "   ⚠ $img présent dans target mais pas dans src" -ForegroundColor Yellow
    } else {
        Write-Host "   ✗ $img absent des deux répertoires" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "=== Fin de la vérification ===" -ForegroundColor Cyan
