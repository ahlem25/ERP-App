-- Script de migration pour l'entité Utilisateur
-- Ajout des champs manquants pour l'authentification

-- Créer la table utilisateurs si elle n'existe pas
CREATE TABLE IF NOT EXISTS utilisateurs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    nom_utilisateur VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    nom_famille VARCHAR(255) NOT NULL,
    actif BOOLEAN DEFAULT TRUE,
    email_verifie BOOLEAN DEFAULT FALSE,
    langue VARCHAR(10) DEFAULT 'fr',
    fuseau_horaire VARCHAR(50) DEFAULT 'Europe/Paris',
    token_reset_password VARCHAR(255),
    token_verification VARCHAR(255),
    derniere_maj TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Créer les tables de relations si elles n'existent pas
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL UNIQUE,
    nom_affichage VARCHAR(100),
    description TEXT,
    systeme BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS autorisations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL UNIQUE,
    nom_affichage VARCHAR(100),
    ressource VARCHAR(100),
    action VARCHAR(100),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tables de relations many-to-many
CREATE TABLE IF NOT EXISTS utilisateur_roles (
    utilisateur_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (utilisateur_id, role_id),
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS utilisateur_autorisations (
    utilisateur_id BIGINT,
    autorisation_id BIGINT,
    PRIMARY KEY (utilisateur_id, autorisation_id),
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE,
    FOREIGN KEY (autorisation_id) REFERENCES autorisations(id) ON DELETE CASCADE
);

-- Insérer des rôles par défaut
INSERT IGNORE INTO roles (nom, nom_affichage, description, systeme) VALUES
('ADMIN', 'Administrateur', 'Accès complet au système', TRUE),
('USER', 'Utilisateur', 'Utilisateur standard', TRUE),
('MANAGER', 'Gestionnaire', 'Gestionnaire de département', FALSE),
('EMPLOYEE', 'Employé', 'Employé standard', FALSE);

-- Insérer des autorisations par défaut
INSERT IGNORE INTO autorisations (nom, nom_affichage, ressource, action, description) VALUES
('USER_READ', 'Lire utilisateurs', 'USER', 'READ', 'Consulter les utilisateurs'),
('USER_WRITE', 'Modifier utilisateurs', 'USER', 'WRITE', 'Créer/modifier les utilisateurs'),
('USER_DELETE', 'Supprimer utilisateurs', 'USER', 'DELETE', 'Supprimer les utilisateurs'),
('DASHBOARD_READ', 'Lire tableau de bord', 'DASHBOARD', 'READ', 'Consulter le tableau de bord'),
('REPORTS_READ', 'Lire rapports', 'REPORTS', 'READ', 'Consulter les rapports'),
('SETTINGS_WRITE', 'Modifier paramètres', 'SETTINGS', 'WRITE', 'Modifier les paramètres système');

-- Créer un utilisateur admin par défaut (mot de passe: admin123)
-- Note: Le mot de passe doit être hashé en production
INSERT IGNORE INTO utilisateurs (
    email, nom_utilisateur, password, prenom, nom_famille, 
    actif, email_verifie, langue, fuseau_horaire
) VALUES (
    'admin@erp.com', 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 
    'Admin', 'Système', TRUE, TRUE, 'fr', 'Europe/Paris'
);

-- Assigner le rôle admin à l'utilisateur admin
INSERT IGNORE INTO utilisateur_roles (utilisateur_id, role_id) 
SELECT u.id, r.id 
FROM utilisateurs u, roles r 
WHERE u.email = 'admin@erp.com' AND r.nom = 'ADMIN';

-- Assigner toutes les autorisations à l'admin
INSERT IGNORE INTO utilisateur_autorisations (utilisateur_id, autorisation_id)
SELECT u.id, a.id 
FROM utilisateurs u, autorisations a 
WHERE u.email = 'admin@erp.com';

-- Créer des index pour améliorer les performances
CREATE INDEX IF NOT EXISTS idx_utilisateurs_email ON utilisateurs(email);
CREATE INDEX IF NOT EXISTS idx_utilisateurs_nom_utilisateur ON utilisateurs(nom_utilisateur);
CREATE INDEX IF NOT EXISTS idx_utilisateurs_actif ON utilisateurs(actif);
CREATE INDEX IF NOT EXISTS idx_utilisateurs_token_reset ON utilisateurs(token_reset_password);
CREATE INDEX IF NOT EXISTS idx_utilisateurs_token_verification ON utilisateurs(token_verification);
