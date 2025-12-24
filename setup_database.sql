-- ============================================
-- CRÉATION DE LA BASE DE DONNÉES
-- ============================================

CREATE DATABASE IF NOT EXISTS gestionmatch;
USE gestionmatch;

-- ============================================
-- TABLE UTILISATEURS
-- ============================================

CREATE TABLE IF NOT EXISTS utilisateurs (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) UNIQUE NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    role VARCHAR(50) DEFAULT 'USER',
    actif BOOLEAN DEFAULT TRUE,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- TABLE GROUPES
-- ============================================

CREATE TABLE IF NOT EXISTS groupes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    lettre VARCHAR(1) UNIQUE NOT NULL,
    nom VARCHAR(100) NOT NULL,
    description TEXT
);

-- ============================================
-- TABLE ÉQUIPES
-- ============================================

CREATE TABLE IF NOT EXISTS equipes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    pays VARCHAR(100) NOT NULL,
    entraineur VARCHAR(100),
    nombre_joueurs INT DEFAULT 0,
    groupe_id INT,
    groupe_lettre VARCHAR(1),
    matchs_joues INT DEFAULT 0,
    victoires INT DEFAULT 0,
    nuls INT DEFAULT 0,
    defaites INT DEFAULT 0,
    buts_marques INT DEFAULT 0,
    buts_encaisses INT DEFAULT 0,
    points INT DEFAULT 0,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (groupe_id) REFERENCES groupes(id)
);

-- ============================================
-- TABLE JOUEURS
-- ============================================

CREATE TABLE IF NOT EXISTS joueurs (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    date_naissance DATE,
    poste VARCHAR(50),
    numero INT,
    equipe_id INT NOT NULL,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (equipe_id) REFERENCES equipes(id)
);

-- ============================================
-- TABLE STADES
-- ============================================

CREATE TABLE IF NOT EXISTS stades (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    ville VARCHAR(100) NOT NULL,
    pays VARCHAR(100) NOT NULL,
    capacite INT,
    surface VARCHAR(50),
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- TABLE MATCHS
-- ============================================

CREATE TABLE IF NOT EXISTS match_data (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    score VARCHAR(10),
    date_match DATE NOT NULL,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- INSERTION DE DONNÉES PAR DÉFAUT
-- ============================================

-- Utilisateur administrateur
INSERT INTO utilisateurs (email, mot_de_passe, nom, prenom, role, actif) 
VALUES ('admin@fifa2030.com', 'Admin123', 'Admin', 'FIFA', 'ADMIN', TRUE);

-- Groupes
INSERT INTO groupes (lettre, nom, description) VALUES 
('A', 'Groupe A', 'Premier groupe de la phase de groupes'),
('B', 'Groupe B', 'Deuxième groupe de la phase de groupes'),
('C', 'Groupe C', 'Troisième groupe de la phase de groupes'),
('D', 'Groupe D', 'Quatrième groupe de la phase de groupes');

-- Stades
INSERT INTO stades (nom, ville, pays, capacite, surface) VALUES 
('Stade de Fès', 'Fès', 'Maroc', 45000, 'Gazon naturel'),
('Stade de Casablanca', 'Casablanca', 'Maroc', 55000, 'Gazon naturel'),
('Stade de Rabat', 'Rabat', 'Maroc', 52000, 'Gazon naturel'),
('Stade de Marrakech', 'Marrakech', 'Maroc', 45000, 'Gazon naturel'),
('Stade de Tanger', 'Tanger', 'Maroc', 40000, 'Gazon naturel'),
('Stade de Mérida', 'Mérida', 'Espagne', 41000, 'Gazon naturel'),
('Stade de Valence', 'Valence', 'Espagne', 55000, 'Gazon naturel'),
('Stade de Séville', 'Séville', 'Espagne', 43000, 'Gazon naturel'),
('Stade de Lisbonne', 'Lisbonne', 'Portugal', 65000, 'Gazon naturel'),
('Stade de Porto', 'Porto', 'Portugal', 50000, 'Gazon naturel'),
('Stade de Braga', 'Braga', 'Portugal', 30000, 'Gazon naturel'),
('Stade de Guarda', 'Guarda', 'Portugal', 25000, 'Gazon naturel'),
('Stade de Covilhã', 'Covilhã', 'Portugal', 20000, 'Gazon naturel'),
('Stade de Viseu', 'Viseu', 'Portugal', 22000, 'Gazon naturel'),
('Stade de Aveiro', 'Aveiro', 'Portugal', 28000, 'Gazon naturel'),
('Stade de Funchal', 'Funchal', 'Portugal', 5000, 'Gazon naturel');

-- ============================================
-- AFFICHAGE DES TABLES CRÉÉES
-- ============================================

SELECT '✅ Base de données créée avec succès !' AS Status;
SHOW TABLES;
