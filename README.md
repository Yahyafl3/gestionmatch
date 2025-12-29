# ⚽ Gestion Coupe du Monde 2030

Application JavaFX moderne pour la gestion complète de la Coupe du Monde de Football 2030.

## 🎯 Fonctionnalités

- **Dashboard Admin** : Vue d'ensemble avec statistiques en temps réel
- **Gestion des Joueurs** : CRUD complet avec recherche
- **Gestion des Équipes** : Suivi des équipes participantes
- **Gestion des Stades** : Base de données des stades
- **Gestion des Groupes** : Organisation de la phase de groupes
- **Gestion des Matchs** : Planification et résultats
- **Authentification** : Système de login/inscription sécurisé

## 🚀 Installation Rapide

### Prérequis
- Java 17 ou supérieur
- Maven 3.6+
- MySQL 8.0+

### Étapes

1. **Cloner le projet**
```bash
git clone <votre-repo>
cd gestionmatch
```

2. **Configurer la base de données**
```bash
mysql -u root -p < setup_database.sql
```

3. **Compiler et lancer**
```bash
mvn clean javafx:run
```

Ou utilisez les scripts fournis :
- Windows : `run.cmd` ou `run.ps1`
- Mac/Linux : `./mvnw clean javafx:run`

## 📁 Structure du Projet

```
gestionmatch/
├── src/main/
│   ├── java/com/example/gestionmatch/
│   │   ├── controller/     # Contrôleurs JavaFX
│   │   ├── model/          # Modèles de données
│   │   ├── dao/            # Accès aux données
│   │   ├── services/       # Logique métier
│   │   └── util/           # Utilitaires
│   └── resources/
│       ├── fxml/           # Interfaces FXML
│       ├── styles/         # Fichiers CSS
│       └── images/         # Images et logos
├── setup_database.sql      # Script de création BDD
└── pom.xml                 # Configuration Maven
```

## 🎨 Design

- **Couleurs** : Rouge FIFA (#C1272D) et Noir (#0a0e27)
- **Fond** : Terrain de football avec overlay sombre
- **Style** : Moderne avec coins arrondis et ombres
- **Tableaux** : Optimisés avec colonnes essentielles uniquement

## 🔐 Connexion par Défaut

- **Admin** : `admin@fifa2030.com` / `Admin123`
- **Utilisateur** : Créer un compte via inscription

## 📊 Base de Données

Tables principales :
- `utilisateurs` : Comptes utilisateurs
- `joueurs` : Joueurs des équipes
- `equipes` : Équipes participantes
- `stades` : Stades de la compétition
- `groupes` : Groupes de la phase de poules
- `match_data` : Matchs programmés

## 🛠️ Technologies

- **JavaFX 17** : Interface graphique
- **MySQL** : Base de données
- **Maven** : Gestion des dépendances
- **JDBC** : Connexion BDD

## 📝 Documentation

- `INSTALLATION_ET_LANCEMENT.md` : Guide d'installation détaillé
- `QUICK_START.md` : Démarrage rapide
- `OPTIMISATION_TABLEAUX.md` : Détails des optimisations récentes

## ✨ Nouveautés Version 2.0

### Pages avec Fond de Terrain
Toutes les pages de gestion ont maintenant un fond de terrain de football immersif :
- `joueurs-view.fxml` : Gestion des joueurs
- `equipe-view.fxml` : Gestion des équipes
- `stades-view.fxml` : Gestion des stades
- `matchs-view.fxml` : Gestion des matchs
- `groupe-view.fxml` : Phase de groupes

### Tableaux Optimisés
Les colonnes inutilisées (ID, etc.) ont été supprimées pour une meilleure lisibilité :
- **Joueurs** : Nom, Prénom, N°, Poste
- **Équipes** : Nom, Pays, Entraîneur
- **Stades** : Nom, Ville, Capacité
- **Matchs** : Match, Date, Stade
- **Groupes** : Nom, Description

### Design Moderne
- Nouveau fichier CSS `tables.css` pour les tableaux
- Effets hover en rouge FIFA
- Scrollbar personnalisée
- Bordures et ombres élégantes

## 🐛 Dépannage

### Erreur de connexion MySQL
```bash
# Vérifier que MySQL est démarré
mysql -u root -p

# Créer la base si nécessaire
mysql -u root -p < setup_database.sql
```

### Erreur de compilation
```bash
# Nettoyer et recompiler
mvn clean compile
```

## 🤝 Contribution

Les contributions sont les bienvenues ! N'hésitez pas à ouvrir une issue ou une pull request.

## 📄 Licence

Ce projet est sous licence MIT.

---

**Version** : 2.0  
**Date** : Décembre 2025  
**Statut** : ✅ Production Ready
