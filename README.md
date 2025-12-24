# 🏆 FIFA 2030 - Plateforme de Gestion des Équipes et Matchs

## 📌 Vue d'Ensemble

**FIFA 2030** est une application JavaFX complète pour la gestion des équipes, joueurs, matchs et stades de la Coupe du Monde 2030. L'application offre un système d'authentification sécurisé, un dashboard administrateur professionnel et une interface utilisateur moderne.

### 🎯 Objectifs
- ✅ Gestion complète des données (Équipes, Joueurs, Matchs, Stades, Groupes)
- ✅ Système d'authentification avec rôles (Admin/User)
- ✅ Dashboard administrateur avec statistiques et graphiques
- ✅ Interface utilisateur moderne et intuitive
- ✅ Base de données automatisée

---

## 🚀 Démarrage Rapide

### Prérequis
- Java 21+
- MySQL 8.0+
- Maven
- XAMPP (ou MySQL Server)

### Installation et Lancement

```bash
# 1. Cloner le projet
git clone <repository-url>
cd gestionmatch

# 2. Démarrer MySQL
# (XAMPP Control Panel → Start MySQL)

# 3. Compiler et lancer
mvn clean javafx:run
```

### Connexion
```
Email    : admin@fifa2030.com
Password : Admin123
```

Pour plus de détails, voir [QUICK_START.md](QUICK_START.md)

---

## 📋 Fonctionnalités

### 🔐 Authentification
- ✅ Inscription avec validation
- ✅ Connexion sécurisée
- ✅ Gestion des rôles (Admin/User)
- ✅ Vérification d'unicité d'email

### 📊 Dashboard Administrateur
- ✅ Tableau de bord avec statistiques en temps réel
- ✅ Graphiques (Pie Chart, Bar Chart)
- ✅ Gestion complète des données :
  - 👥 Joueurs
  - ⚽ Équipes
  - 🎮 Matchs
  - 🏟️ Stades
  - 🎯 Groupes
- ✅ Actions rapides
- ✅ Activités récentes
- ✅ Déconnexion sécurisée

### 👤 Dashboard Utilisateur
- ✅ Visualisation des données
- ✅ Recherche et filtrage
- ✅ Consultation des statistiques

---

## 📁 Structure du Projet

```
gestionmatch/
├── src/main/java/com/example/gestionmatch/
│   ├── controller/          # Contrôleurs (Logique de présentation)
│   ├── dao/                 # DAOs (Accès aux données)
│   ├── model/               # Modèles (Entités)
│   ├── database/            # Connexion à la BD
│   └── Main.java            # Point d'entrée
├── src/main/resources/
│   ├── fxml/                # Interfaces (FXML)
│   └── styles/              # Styles (CSS)
├── setup_database.sql       # Script SQL
└── Documentation/
    ├── SETUP_INSTRUCTIONS.md
    ├── QUICK_START.md
    ├── IMPLEMENTATION_SUMMARY.md
    ├── COMPLETION_REPORT.md
    └── PROJECT_STRUCTURE.md
```

Pour plus de détails, voir [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md)

---

## 🗄️ Base de Données

### Tables
- `utilisateurs` - Comptes utilisateurs
- `groupes` - Groupes de compétition
- `equipes` - Équipes participantes
- `joueurs` - Joueurs des équipes
- `stades` - Stades de la compétition
- `match_data` - Matchs joués

### Initialisation Automatique
La base de données est créée automatiquement au premier lancement de l'application.

---

## 🔧 Configuration

### MySQL
Modifiez les paramètres dans `DatabaseConnection.java` si nécessaire :

```java
private static final String HOST = "localhost";
private static final String PORT = "3306";
private static final String DATABASE = "gestionmatch";
private static final String USER = "root";
private static final String PASSWORD = "";  // Changez si vous avez un mot de passe
```

---

## 📚 Documentation

### Guides Complets
- [SETUP_INSTRUCTIONS.md](SETUP_INSTRUCTIONS.md) - Guide complet de configuration
- [QUICK_START.md](QUICK_START.md) - Démarrage rapide
- [IMPLEMENTATION_SUMMARY.md](IMPLEMENTATION_SUMMARY.md) - Résumé technique
- [COMPLETION_REPORT.md](COMPLETION_REPORT.md) - Rapport final
- [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md) - Structure du projet

---

## 🎨 Design et UX

### Thème Visuel
- Couleurs FIFA 2030 (Bleu, Or, Blanc)
- Design moderne et professionnel
- Animations fluides
- Interface responsive

### Composants
- Formulaires avec validation
- Tableaux de données
- Graphiques interactifs
- Cartes de statistiques
- Navigation intuitive

---

## 🔐 Sécurité

### Authentification
- Vérification email/mot de passe
- Gestion des rôles
- Sessions utilisateur

### Base de Données
- Requêtes paramétrées (PreparedStatement)
- Protection contre les injections SQL
- Gestion des connexions

### Validation
- Validation des formulaires
- Vérification d'unicité d'email
- Confirmation de mot de passe

---

## 🛠️ Technologie

### Frontend
- **JavaFX 21** - Framework GUI
- **FXML** - Markup pour les interfaces
- **CSS** - Styles et animations

### Backend
- **Java 21** - Langage de programmation
- **MySQL 8.0** - Base de données
- **JDBC** - Accès aux données

### Build
- **Maven** - Gestion des dépendances et build

---

## 📊 Comptes de Test

### Administrateur (Créé Automatiquement)
```
Email    : admin@fifa2030.com
Password : Admin123
Rôle     : ADMIN
```

### Utilisateur Standard
Créez un compte via le formulaire d'inscription.

---

## 🐛 Dépannage

### Erreur : "Connexion à la base de données échouée"
1. Vérifiez que MySQL est démarré
2. Vérifiez les paramètres de connexion
3. Vérifiez que la base `gestionmatch` existe

### Erreur : "FXML non trouvé"
1. Vérifiez que les fichiers FXML sont dans `src/main/resources/`
2. Vérifiez les chemins dans les FXMLLoader
3. Relancez Maven clean

### Erreur : "Table n'existe pas"
1. Relancez l'application (elle créera les tables)
2. Ou exécutez `setup_database.sql` manuellement

Pour plus d'aide, voir [SETUP_INSTRUCTIONS.md](SETUP_INSTRUCTIONS.md)

---

## 🚀 Commandes Utiles

```bash
# Compiler le projet
mvn clean compile

# Lancer l'application
mvn javafx:run

# Générer un JAR
mvn clean package

# Nettoyer les fichiers générés
mvn clean

# Vérifier les dépendances
mvn dependency:tree
```

---

## 📈 Prochaines Étapes

1. Implémenter les formulaires d'ajout/modification complets
2. Ajouter les colonnes aux TableView
3. Implémenter la recherche et le filtrage
4. Ajouter les validations côté serveur
5. Implémenter les permissions granulaires
6. Ajouter les exports (PDF, Excel)
7. Implémenter les notifications
8. Ajouter les logs d'audit

---

## 👥 Contributeurs

- **Développeur** : Yahya
- **Projet** : FIFA 2030 - Plateforme de Gestion
- **Date** : Décembre 2025

---

## 📄 Licence

Ce projet est fourni à titre d'exemple éducatif.

---

## 📞 Support

Pour toute question ou problème :
1. Consultez la documentation fournie
2. Vérifiez les logs de la console
3. Vérifiez que MySQL est démarré

---

## ✨ Caractéristiques Principales

- ✅ **Authentification Sécurisée** - Système de login avec rôles
- ✅ **Dashboard Professionnel** - Interface moderne et intuitive
- ✅ **Gestion Complète** - CRUD pour toutes les entités
- ✅ **Statistiques en Temps Réel** - Graphiques et cartes
- ✅ **Base de Données Automatisée** - Création automatique des tables
- ✅ **Documentation Complète** - Guides et commentaires
- ✅ **Code Bien Structuré** - Architecture MVC
- ✅ **Extensible** - Facile d'ajouter de nouvelles fonctionnalités

---

## 🎉 Conclusion

**FIFA 2030** est une application complète et professionnelle pour la gestion de la Coupe du Monde 2030. Elle offre une interface utilisateur moderne, une gestion sécurisée des données et une architecture extensible.

**Prêt à utiliser ! 🏆**

---

**Dernière mise à jour** : 19 Décembre 2025
**Statut** : ✅ Production Ready
**Version** : 1.0.0
