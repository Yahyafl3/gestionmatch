# 🏆 FIFA 2030 - Système de Gestion des Équipes et Matchs

## 📋 Description

Application desktop JavaFX moderne et professionnelle pour la gestion complète de la **Coupe du Monde 2030**. Système complet avec authentification sécurisée BCrypt, dashboard administrateur et interface intuitive.

## ✨ Caractéristiques

- ✅ **Authentification sécurisée** avec chiffrement BCrypt
- ✅ **Dashboard administrateur** complet et fonctionnel
- ✅ **Interface moderne** avec design professionnel desktop
- ✅ **Gestion des équipes** - CRUD complet
- ✅ **Gestion des joueurs** - Ajout, modification, suppression
- ✅ **Gestion des matchs** - Planification et résultats
- ✅ **Gestion des groupes** - Organisation des compétitions
- ✅ **Gestion des stades** - Base de données complète
- ✅ **Base de données MySQL** intégrée

## 🚀 Démarrage Rapide

### Prérequis

- **Java 17+** (openjdk-17 ou supérieur)
- **Maven 3.8+**
- **MySQL Server** (ou compatible)
- **Windows 10/11** (PowerShell 5.1+)

### Installation et Lancement

#### Option 1 : Via PowerShell (Recommandé)

```powershell
# Depuis le répertoire du projet
.\run.ps1
```

#### Option 2 : Via Maven

```powershell
# Compiler et lancer
.\mvnw.cmd clean compile -DskipTests=true
.\mvnw.cmd exec:java -Dexec.mainClass="com.example.gestionmatch.Main"
```

#### Option 3 : Depuis l'IDE IntelliJ IDEA

1. Ouvrir le projet dans IntelliJ IDEA
2. Attendre l'indexation Maven
3. Clic droit sur `Main.java` → **Run 'Main.main()'**

## 🔐 Accès et Comptes de Test

### Compte Administrateur par Défaut

| Champ | Valeur |
|-------|--------|
| **Email** | `admin@fifa2030.com` |
| **Mot de passe** | `Admin123` |
| **Rôle** | ADMIN |

### Comptes Utilisateurs Additionnels

Pour créer un compte utilisateur :
1. Cliquer sur **"Créer un compte ici"** sur la page de connexion
2. Remplir le formulaire d'inscription
3. Cliquer sur **"S'INSCRIRE MAINTENANT"**
4. Se connecter avec vos identifiants

## 📱 Pages et Fonctionnalités

### Page de Connexion
- Design moderne avec image de fond
- Formulaire centré élégant
- Lien d'inscription intégré
- Authentification sécurisée

### Page d'Inscription
- Formulaire complet et intuitif
- Validation des données
- Création de compte utilisateur
- Retour vers la connexion

### Dashboard Administrateur
- Vue d'ensemble complète
- Gestion des entités (Équipes, Joueurs, Matchs, Groupes, Stades)
- Graphiques et statistiques
- Interface réactive et intuitive

## 🗂️ Structure du Projet

```
gestionmatch/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/gestionmatch/
│   │   │       ├── Main.java                    # Point d'entrée
│   │   │       ├── controller/
│   │   │       │   ├── LoginController.java
│   │   │       │   ├── InscriptionController.java
│   │   │       │   └── ...
│   │   │       ├── dao/
│   │   │       │   └── UtilisateurDAO.java
│   │   │       ├── model/
│   │   │       └── database/
│   │   └── resources/
│   │       ├── fxml/
│   │       │   ├── login.fxml
│   │       │   ├── inscription.fxml
│   │       │   └── ...
│   │       ├── styles/
│   │       │   ├── auth.css                     # Styles login/inscription
│   │       │   └── ...
│   │       └── images/
│   │           └── mauro-lima-wfKiXwGRSgQ-unsplash.jpg
│   └── test/
├── pom.xml
├── mvnw / mvnw.cmd                              # Maven wrapper
├── run.ps1                                      # Script de lancement
└── README.md
```

## 🎨 Design et Interface

### Technologie
- **JavaFX 21** - Framework UI moderne
- **CSS3** - Styling professionnel
- **MySQL** - Base de données relationnelle
- **BCrypt** - Sécurité des mots de passe

### Couleurs Principales
- **Primaire** : `#667eea` (Bleu indigo)
- **Secondaire** : `#764ba2` (Violet)
- **Texte** : `#1a202c` (Gris foncé)
- **Arrière-plan** : Image professionnelle + overlay

### Polices
- **Principal** : "Segoe UI", "Roboto"
- **Taille** : 13px (base), 32px (titres)

## 🔧 Configuration de la Base de Données

### Initialisation Automatique

La base de données se crée automatiquement au premier lancement grâce à `SetupDatabaseAuto.java`.

**Fichiers créés :**
- Tables : `utilisateur`, `equipe`, `joueur`, `match`, `groupe`, `stade`
- Données d'exemple : Compte admin pré-créé

### Fichiers SQL

- `setup_database.sql` - Script de création manuelle (si besoin)
- `SetupDatabaseAuto.java` - Initialisation automatique

## 🐛 Troubleshooting

### Erreur : "ClassCastException: BoxBounds cannot be cast to RectBounds"

**Solution** : Déjà corrigée dans le code - workaround appliqué dans `Main.java`

### Erreur : "Rectangle is not a valid type"

**Solution** : Remplacé par `Region` - déjà fixé

### Erreur : "Invalid color specification"

**Solution** : Syntaxe CSS JavaFX corrigée - déjà appliquée

### Base de données non accessible

1. Vérifier que MySQL est en cours d'exécution
2. Vérifier les identifiants dans `DatabaseConnection.java`
3. Vérifier le port MySQL (par défaut : 3306)

## 📝 Notes de Développement

### Dépendances Principales
- `javafx-controls:21` - Contrôles UI
- `javafx-fxml:21` - Fichiers FXML
- `mysql-connector-j:8.3.0` - Driver MySQL
- `jbcrypt:0.4` - Chiffrement de mots de passe

### Points d'Entrée Clés
- `Main.java` - Lancement de l'application
- `LoginController.java` - Gestion de la connexion
- `UtilisateurDAO.java` - Accès aux données utilisateur

## 📚 Documentation

- Voir `SOLUTION_FINALE.md` pour les détails techniques complets
- Voir `RESUME_FINAL_DASHBOARD.md` pour les fonctionnalités du dashboard

## 📄 Licence

© 2024-2030 FIFA World Cup Management System. Tous droits réservés.

---

**Développé avec ❤️ pour la Coupe du Monde 2030**

Pour toute question ou problème, consultez la documentation technique ou exécutez avec les logs en mode verbose.

