-- Script d'initialisation pour créer la base de données billing et l'utilisateur
-- Ce script est exécuté automatiquement lors du premier démarrage de MySQL

-- Créer la base de données achat_db
CREATE DATABASE IF NOT EXISTS achat_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Créer l'utilisateur ahlem
CREATE USER IF NOT EXISTS 'ahlem'@'%' IDENTIFIED BY 'achat';

-- Accorder tous les privilèges sur la base achat_db à l'utilisateur ahlem
GRANT ALL PRIVILEGES ON achat_db.* TO 'ahlem'@'%';

-- Appliquer les changements
FLUSH PRIVILEGES;

-- Afficher un message de confirmation
SELECT 'Billing database and user created successfully' as status;
