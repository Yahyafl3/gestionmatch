# 📋 Checklist de Vérification du Design

## ✅ Page de Connexion (Login)

### Design Visuel
- [ ] Image de stade en arrière-plan (1400x900)
- [ ] Overlay sombre semi-transparent visible
- [ ] Formulaire blanc centré avec ombre professionnelle
- [ ] Arrondi des coins du formulaire (20px)
- [ ] Section gauche avec "FIFA 2030" en blanc gros texte
- [ ] Section droite avec formulaire de connexion

### Formulaire de Connexion
- [ ] Titre "CONNEXION" en noir (#1a1a1a)
- [ ] Sous-titre gris (#666666) explicatif
- [ ] Champ Email avec placeholder "admin@fifa2030.com"
- [ ] Champ Mot de passe avec placeholder approprié
- [ ] Case "Se souvenir de moi"
- [ ] Lien "Mot de passe oublié ?"
- [ ] Bouton "SE CONNECTER" en vert (#4ade80)
- [ ] Séparateur horizontal gris
- [ ] Lien "Créer un compte ici" (couleur verte)

### Interactions
- [ ] Champs de saisie avec border grise (#e2e8f0) au repos
- [ ] Champs avec border verte et ombre au focus
- [ ] Fond des champs clair (#f8f9fa)
- [ ] Bouton vert au repos
- [ ] Bouton plus foncé (#22c55e) au hover
- [ ] Animation 3D au hover sur la carte :
  - [ ] Agrandissement légèrement (1.03x)
  - [ ] Rotation Y axis de 4°

### Avantages (Section Gauche)
- [ ] 4 points listés avec puces vertes
- [ ] Texte blanc lisible
- [ ] Points : authentification, dashboard, gestion, interface

### Footer
- [ ] Copyright visible en bas du formulaire
- [ ] Texte gris clair (#a0aec0)
- [ ] Formaté correctement

---

## ✅ Page d'Inscription (Signup)

### Design Visuel
- [ ] Image de stade identique en arrière-plan
- [ ] Overlay sombre identique
- [ ] Formulaire blanc centré (même design que connexion)
- [ ] Cohérence visuelle avec la page de connexion

### Formulaire d'Inscription
- [ ] Titre "CRÉER UN COMPTE"
- [ ] Sous-titre "Remplissez le formulaire pour commencer"
- [ ] Champ Prénom avec placeholder "Jean"
- [ ] Champ Nom avec placeholder "Dupont"
- [ ] Champ Email avec placeholder "votre.email@exemple.com"
- [ ] Champ Mot de passe avec placeholder "Au minimum 6 caractères"
- [ ] Champ Confirmation Mot de passe
- [ ] Bouton "S'INSCRIRE MAINTENANT" en vert
- [ ] Séparateur horizontal
- [ ] Lien "Se connecter ici" (couleur verte)

### Avantages (Section Gauche)
- [ ] Section "REJOIGNEZ" en blanc gros
- [ ] 4 avantages personnalisés pour inscription :
  - [ ] Inscription gratuite et instantanée
  - [ ] Accès immédiat à tous les outils
  - [ ] Données chiffrées et sécurisées
  - [ ] Support technique 24h/24

### Interactions
- [ ] Mêmes animations 3D que la page login
- [ ] Mêmes états de focus et hover

---

## 🔗 Navigation

### Depuis la Page de Connexion
- [ ] Clic sur "Créer un compte ici" → Redirection vers Inscription
- [ ] Validation de formulaire avant soumission

### Depuis la Page d'Inscription
- [ ] Clic sur "Se connecter ici" → Redirection vers Connexion
- [ ] Validation de formulaire avant soumission

---

## 🎨 Vérification des Couleurs

### Palette de Couleurs Utilisées
- [ ] Vert primaire #4ade80 (Boutons, accents)
- [ ] Vert hover #22c55e (Plus foncé)
- [ ] Noir texte #1a1a1a (Titres)
- [ ] Gris labels #2d3748
- [ ] Gris borders #e2e8f0
- [ ] Fond champs #f8f9fa
- [ ] Blanc formulaire rgba(255,255,255,0.95)
- [ ] Overlay noir rgba(0,0,0,0.5)

---

## 📏 Vérification des Dimensions

### Responsive et Sizing
- [ ] Résolution minimale testée : 1400x900
- [ ] Formulaire centré horizontalement
- [ ] Formulaire centré verticalement
- [ ] Largeur formulaire : 420px
- [ ] Max-width formulaire : 480px
- [ ] Spacing cohérent entre éléments

---

## 🖱️ Animations et Transitions

### Effet 3D Souris
- [ ] Animation déclenchée au survol du formulaire
- [ ] Scale de 1.0 à 1.03 (3% plus grand)
- [ ] Rotation Y axis de 0 à 4°
- [ ] Durée : 220ms
- [ ] Animation fluide et sans saccade

### Reset Animation
- [ ] Au sortie de la souris : retour à l'état normal
- [ ] Durée : 220ms
- [ ] Animation fluide

---

## 🔒 Validation et Sécurité

### Validation Formulaire Login
- [ ] Email requis
- [ ] Mot de passe requis
- [ ] Message d'erreur en cas de champ vide
- [ ] Authentification BCrypt vérifiée
- [ ] Message succès au connexion valide

### Validation Formulaire Inscription
- [ ] Prénom requis
- [ ] Nom requis
- [ ] Email requis et format valide
- [ ] Mot de passe min 6 caractères
- [ ] Confirmation mot de passe match
- [ ] Vérification email n'existe pas
- [ ] Message succès au inscription valide

---

## 📱 Accessibilité et UX

### Lisibilité
- [ ] Texte bien lisible sur le background
- [ ] Contraste suffisant avec overlay
- [ ] Police claire et moderne
- [ ] Taille de police appropriée

### Usabilité
- [ ] Placeholders instructifs
- [ ] Labels clairs pour chaque champ
- [ ] Ordre logique des champs
- [ ] Focus visible sur les champs
- [ ] Boutons cliquables et visibles

---

## 🚀 Statut de Vérification

| Élément | Statut | Notes |
|---------|--------|-------|
| Page Login - Design | 🔄 | En attente de test |
| Page Inscription - Design | 🔄 | En attente de test |
| Navigation | 🔄 | En attente de test |
| Animations | 🔄 | En attente de test |
| Validation | 🔄 | En attente de test |
| Couleurs | ✅ | Intégrées dans FXML |
| Styles Inline | ✅ | Tous intégrés |
| Image Background | ✅ | Présente et correcte |

---

## 📝 Notes Additionnelles

- Les styles CSS sont intégrés directement dans les fichiers FXML
- Pas de fichier CSS externe utilisé
- Même image pour les deux pages (cohérence)
- Contrôleurs prêts avec animations et validation
- Design professionnel et moderne 3D

---

**À Tester En Ordre :**
1. Lancer l'application
2. Vérifier page de connexion
3. Cliquer sur "Créer un compte"
4. Vérifier page d'inscription
5. Cliquer sur "Se connecter"
6. Retourner à page connexion
7. Tester animations 3D
8. Tester validation formulaires
9. Vérifier toutes les couleurs

**Dernier Test :** 2025-12-25

