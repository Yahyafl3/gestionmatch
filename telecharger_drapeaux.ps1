# Script pour telecharger les drapeaux des equipes
# Utilise l'API Flagpedia pour obtenir des images de drapeaux de haute qualite

$dossierFlags = "$env:USERPROFILE\Desktop\drapeaux"

# Creer le dossier s'il n'existe pas
if (-not (Test-Path $dossierFlags)) {
    New-Item -ItemType Directory -Path $dossierFlags -Force
}

# Liste des pays avec leurs codes ISO
$pays = @{
    "maroc" = "ma"
    "algerie" = "dz"
    "tunisie" = "tn"
    "egypte" = "eg"
    "senegal" = "sn"
    "nigeria" = "ng"
    "cameroun" = "cm"
    "ghana" = "gh"
    "cote_d_ivoire" = "ci"
    "france" = "fr"
    "espagne" = "es"
    "portugal" = "pt"
    "allemagne" = "de"
    "italie" = "it"
    "angleterre" = "gb-eng"
    "pays_bas" = "nl"
    "belgique" = "be"
    "croatie" = "hr"
    "suisse" = "ch"
    "danemark" = "dk"
    "suede" = "se"
    "pologne" = "pl"
    "bresil" = "br"
    "argentine" = "ar"
    "uruguay" = "uy"
    "colombie" = "co"
    "chili" = "cl"
    "perou" = "pe"
    "equateur" = "ec"
    "mexique" = "mx"
    "etats_unis" = "us"
    "canada" = "ca"
    "costa_rica" = "cr"
    "japon" = "jp"
    "coree_du_sud" = "kr"
    "iran" = "ir"
    "arabie_saoudite" = "sa"
    "australie" = "au"
    "qatar" = "qa"
}

Write-Host "Telechargement des drapeaux..." -ForegroundColor Cyan
Write-Host ""

$compteur = 0
$total = $pays.Count

foreach ($nomPays in $pays.Keys) {
    $compteur++
    $codeISO = $pays[$nomPays]
    $fichierDestination = "$dossierFlags\$nomPays.png"
    
    # URL de l'API Flagpedia (format 640x480)
    $url = "https://flagcdn.com/w640/$codeISO.png"
    
    try {
        Write-Host "[$compteur/$total] Telechargement: $nomPays ($codeISO)..." -NoNewline
        Invoke-WebRequest -Uri $url -OutFile $fichierDestination -ErrorAction Stop
        Write-Host " OK" -ForegroundColor Green
    }
    catch {
        Write-Host " ERREUR" -ForegroundColor Red
        Write-Host "  URL: $url" -ForegroundColor Yellow
    }
}

Write-Host ""
Write-Host "Telechargement termine!" -ForegroundColor Green
Write-Host "Drapeaux sauvegardes dans: $dossierFlags" -ForegroundColor Cyan
