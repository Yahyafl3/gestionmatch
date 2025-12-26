# Script PowerShell pour lancer l'application FIFA 2030

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "🏆 Lancement de l'Application FIFA 2030" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Vérifier que le projet est compilé
Write-Host "📦 Vérification de la compilation..." -ForegroundColor Yellow

if (Test-Path "target/classes/com/example/gestionmatch/Main.class") {
    Write-Host "✅ Projet compilé" -ForegroundColor Green
} else {
    Write-Host "⚠️  Compilation du projet..." -ForegroundColor Yellow
    .\mvnw.cmd clean compile -DskipTests=true
    if ($LASTEXITCODE -ne 0) {
        Write-Host "❌ Erreur de compilation" -ForegroundColor Red
        exit 1
    }
}

Write-Host ""
Write-Host "🚀 Démarrage de l'application..." -ForegroundColor Cyan
Write-Host ""

# Lancer avec Java directement (plus rapide que maven exec)
$javaHome = if ($env:JAVA_HOME) { $env:JAVA_HOME } else {
    # Essayer de trouver Java 17+
    (Get-Command java -ErrorAction SilentlyContinue).Source | Split-Path -Parent | Split-Path -Parent
}

if (-not $javaHome -or -not (Test-Path "$javaHome\bin\java.exe")) {
    Write-Host "❌ Java 17+ non trouvé. Utilisez maven exec..." -ForegroundColor Red
    Write-Host ""
    Write-Host "Commande alternative:" -ForegroundColor Yellow
    Write-Host ".\mvnw.cmd -q exec:java -Dexec.mainClass=com.example.gestionmatch.Main" -ForegroundColor Gray
    exit 1
}

# Lancer l'application
$cp = "target/classes"
Get-ChildItem "target/dependency" -ErrorAction SilentlyContinue | ForEach-Object {
    $cp += ";$($_.FullName)"
}

Write-Host "Classpath: $cp" -ForegroundColor Gray
Write-Host ""

& "$javaHome\bin\java.exe" `
    --module-path "target/classes:target/dependency" `
    --add-modules javafx.controls,javafx.fxml `
    -cp "$cp" `
    com.example.gestionmatch.Main

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "👋 Application fermée" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Cyan

