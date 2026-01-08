# ============================================
# Script PowerShell pour télécharger tous les drapeaux
# ============================================

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Téléchargement des drapeaux des équipes" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Créer le dossier si nécessaire
$dossier = "src/main/resources/images/equipes"
if (-not (Test-Path $dossier)) {
    New-Item -ItemType Directory -Path $dossier -Force | Out-Null
    Write-Host "✓ Dossier créé: $dossier" -ForegroundColor Green
}

# Liste des pays avec leurs URLs de drapeaux (Flagpedia - haute qualité)
$drapeaux = @{
    "allemagne" = "https://flagcdn.com/w320/de.png"
    "angleterre" = "https://flagcdn.com/w320/gb-eng.png"
    "argentine" = "https://flagcdn.com/w320/ar.png"
    "australie" = "https://flagcdn.com/w320/au.png"
    "bresil" = "https://flagcdn.com/w320/br.png"
    "croatie" = "https://flagcdn.com/w320/hr.png"
    "danemark" = "https://flagcdn.com/w320/dk.png"
    "espagne" = "https://flagcdn.com/w320/es.png"
    "etats-unis" = "https://flagcdn.com/w320/us.png"
    "france" = "https://flagcdn.com/w320/fr.png"
    "japon" = "https://flagcdn.com/w320/jp.png"
    "maroc" = "https://flagcdn.com/w320/ma.png"
    "mexique" = "https://flagcdn.com/w320/mx.png"
    "pays-bas" = "https://flagcdn.com/w320/nl.png"
    "portugal" = "https://flagcdn.com/w320/pt.png"
    "senegal" = "https://flagcdn.com/w320/sn.png"
    "algerie" = "https://flagcdn.com/w320/dz.png"
    "belgique" = "https://flagcdn.com/w320/be.png"
    "cameroun" = "https://flagcdn.com/w320/cm.png"
    "canada" = "https://flagcdn.com/w320/ca.png"
    "coree-sud" = "https://flagcdn.com/w320/kr.png"
    "costa-rica" = "https://flagcdn.com/w320/cr.png"
    "equateur" = "https://flagcdn.com/w320/ec.png"
    "ghana" = "https://flagcdn.com/w320/gh.png"
    "iran" = "https://flagcdn.com/w320/ir.png"
    "italie" = "https://flagcdn.com/w320/it.png"
    "nigeria" = "https://flagcdn.com/w320/ng.png"
    "pologne" = "https://flagcdn.com/w320/pl.png"
    "qatar" = "https://flagcdn.com/w320/qa.png"
    "serbie" = "https://flagcdn.com/w320/rs.png"
    "suisse" = "https://flagcdn.com/w320/ch.png"
    "tunisie" = "https://flagcdn.com/w320/tn.png"
    "uruguay" = "https://flagcdn.com/w320/uy.png"
    "pays-de-galles" = "https://flagcdn.com/w320/gb-wls.png"
    "arabie-saoudite" = "https://flagcdn.com/w320/sa.png"
}

$total = $drapeaux.Count
$telecharges = 0
$deja_existants = 0
$erreurs = 0

Write-Host "Téléchargement de $total drapeaux..." -ForegroundColor Yellow
Write-Host ""

foreach ($pays in $drapeaux.Keys) {
    $fichier = "$dossier/$pays.png"
    $url = $drapeaux[$pays]
    
    if (Test-Path $fichier) {
        Write-Host "  ⊙ $pays.png" -ForegroundColor Gray -NoNewline
        Write-Host " (déjà existant)" -ForegroundColor DarkGray
        $deja_existants++
    } else {
        try {
            Write-Host "  ↓ Téléchargement de $pays.png..." -ForegroundColor Cyan -NoNewline
            Invoke-WebRequest -Uri $url -OutFile $fichier -ErrorAction Stop
            Write-Host " ✓" -ForegroundColor Green
            $telecharges++
            Start-Sleep -Milliseconds 200  # Pause pour ne pas surcharger le serveur
        } catch {
            Write-Host " ✗ ERREUR" -ForegroundColor Red
            Write-Host "    Erreur: $($_.Exception.Message)" -ForegroundColor Red
            $erreurs++
        }
    }
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "RÉSUMÉ" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Total de drapeaux: $total" -ForegroundColor White
Write-Host "Téléchargés: $telecharges" -ForegroundColor Green
Write-Host "Déjà existants: $deja_existants" -ForegroundColor Gray
Write-Host "Erreurs: $erreurs" -ForegroundColor $(if ($erreurs -gt 0) { "Red" } else { "Green" })
Write-Host ""

# Vérifier les fichiers téléchargés
Write-Host "Vérification des fichiers..." -ForegroundColor Yellow
$fichiers = Get-ChildItem -Path $dossier -Filter "*.png"
Write-Host "✓ $($fichiers.Count) fichiers PNG trouvés dans $dossier" -ForegroundColor Green
Write-Host ""

# Afficher la liste des fichiers
Write-Host "Fichiers disponibles:" -ForegroundColor Cyan
foreach ($fichier in $fichiers | Sort-Object Name) {
    $taille = [math]::Round($fichier.Length / 1KB, 2)
    Write-Host "  • $($fichier.Name)" -ForegroundColor White -NoNewline
    Write-Host " ($taille KB)" -ForegroundColor Gray
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "PROCHAINES ÉTAPES" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "1. Exécuter le script SQL:" -ForegroundColor Yellow
Write-Host "   mysql -u root -proot gestion_match < charger_logos_equipes.sql" -ForegroundColor White
Write-Host ""
Write-Host "2. Ou utiliser le script automatique:" -ForegroundColor Yellow
Write-Host "   .\appliquer_logos_equipes.cmd" -ForegroundColor White
Write-Host ""
Write-Host "3. Relancer l'application pour voir les logos!" -ForegroundColor Yellow
Write-Host "   mvn javafx:run" -ForegroundColor White
Write-Host ""

if ($erreurs -eq 0 -and $telecharges -gt 0) {
    Write-Host "✓ Téléchargement terminé avec succès!" -ForegroundColor Green
} elseif ($erreurs -eq 0 -and $deja_existants -eq $total) {
    Write-Host "✓ Tous les drapeaux sont déjà présents!" -ForegroundColor Green
} else {
    Write-Host "⚠ Téléchargement terminé avec des erreurs" -ForegroundColor Yellow
}

Write-Host ""
