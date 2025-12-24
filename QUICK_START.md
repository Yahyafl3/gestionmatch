# 🚀 Démarrage Rapide - FIFA 2030

## 1️⃣ Prérequis
- ✅ Java 21+
- ✅ MySQL 8.0+
- ✅ Maven
- ✅ XAMPP (ou MySQL Server)

## 2️⃣ Démarrer MySQL

### Avec XAMPP
1. Ouvrez XAMPP Control Panel
2. Cliquez sur "Start" pour MySQL
3. Vérifiez que le port 3306 est actif

### Avec MySQL Server
```bash
# Windows
net start MySQL80

# Linux
sudo systemctl start mysql

# macOS
brew services start mysql
```

## 3️⃣ Lancer l'Application

### Option A : Avec Maven
```bash
cd c:\Users\yahya\IdeaProjects\gestionmatch
mvn clean javafx:run
```

### Option B : Avec IntelliJ IDEA
1. Ouvrez le projet
2. Cliquez sur "Run" → "Run 'Main'"
3. Ou appuyez sur `Shift + F10`

### Option C : Avec Eclipse
1. Cliquez droit sur le projet
2. "Run As" → "Java Application"
3. Sélectionnez "Main"

## 4️⃣ Première Connexion

### Compte Admin
```
Email    : admin@fifa2030.com
Password : Admin123
```

### Créer un Compte Utilisateur
1. Cliquez sur "Créer un compte"
2. Remplissez le formulaire
3. Cliquez sur "S'INSCRIRE"
4. Connectez-vous avec vos identifiants

## 5️⃣ Utiliser le Dashboard Admin

### Navigation
- 📊 **Tableau de Bord** : Vue d'ensemble avec statistiques
- 👥 **Joueurs** : Gérer les joueurs
- ⚽ **Équipes** : Gérer les équipes
- 🏟️ **Stades** : Gérer les stades
- 🎯 **Groupes** : Gérer les groupes
- 🎮 **Matchs** : Gérer les matchs
- 🚪 **Déconnexion** : Se déconnecter

### Actions Rapides
- ➕ Ajouter un joueur
- ➕ Ajouter une équipe
- ➕ Ajouter un match
- ➕ Ajouter un stade

## 6️⃣ Dépannage

### Erreur : "Connexion à la base de données échouée"
```
✅ Solution :
1. Vérifiez que MySQL est démarré
2. Vérifiez le port 3306
3. Vérifiez les identifiants MySQL (root/vide par défaut)
```

### Erreur : "FXML non trouvé"
```
✅ Solution :
1. Vérifiez que les fichiers FXML sont dans src/main/resources/
2. Vérifiez les chemins dans les FXMLLoader
3. Relancez Maven clean
```

### Erreur : "Table n'existe pas"
```
✅ Solution :
1. Relancez l'application (elle créera les tables)
2. Ou exécutez setup_database.sql manuellement
```

## 7️⃣ Fichiers Importants

```
gestionmatch/
├── src/main/java/com/example/gestionmatch/
│   ├── Main.java                    ← Point d'entrée
│   ├── controller/
│   │   ├── LoginController.java     ← Connexion/Inscription
│   │   └── AdminDashboardController.java ← Dashboard Admin
│   ├── dao/
│   │   ├── UtilisateurDAO.java
│   │   ├── EquipeDAO.java
│   │   ├── JoueurDAO.java
│   │   ├── MatchDAO.java
│   │   ├── StadeDAO.java
│   │   └── GroupeDAO.java
│   └── model/
│       ├── Utilisateur.java
│       ├── Equipe.java
│       ├── Joueur.java
│       ├── Match.java
│       ├── Stade.java
│       └── Groupe.java
├── src/main/resources/
│   ├── fxml/
│   │   ├── login.fxml
│   │   └── admin-dashboard.fxml
│   └── styles/
│       ├── login-style.css
│       └── admin-dashboard-style.css
├── setup_database.sql               ← Script SQL
├── SETUP_INSTRUCTIONS.md            ← Guide complet
├── IMPLEMENTATION_SUMMARY.md        ← Résumé
└── QUICK_START.md                   ← Ce fichier
```

## 8️⃣ Commandes Utiles

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

## 9️⃣ Configuration MySQL

Si vous avez un mot de passe MySQL, modifiez :

**Fichier** : `src/main/java/com/example/gestionmatch/database/DatabaseConnection.java`

```java
private static final String PASSWORD = "votre_mot_de_passe";  // ← Changez ici
```

## 🔟 Support

Pour toute question :
1. Consultez `SETUP_INSTRUCTIONS.md`
2. Consultez `IMPLEMENTATION_SUMMARY.md`
3. Vérifiez les logs de la console
4. Vérifiez que MySQL est démarré

---

**Prêt à utiliser ! 🏆**

Lancez l'application et profitez du dashboard FIFA 2030 !
