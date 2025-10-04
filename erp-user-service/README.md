# ERP User Service

Service de gestion des utilisateurs intégré avec Keycloak pour l'application ERP.

## 🚀 Fonctionnalités

- **Gestion des utilisateurs** : CRUD complet des utilisateurs
- **Intégration Keycloak** : Synchronisation avec Keycloak
- **Pagination** : Support de la pagination et de la recherche
- **API REST** : Endpoints RESTful pour le frontend
- **Sécurité** : Configuration de sécurité appropriée

## 📋 APIs Disponibles

### Endpoints Principaux

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/api/users` | Récupère la liste des utilisateurs avec pagination |
| `GET` | `/api/users/{id}` | Récupère un utilisateur par ID |
| `POST` | `/api/users` | Crée un nouvel utilisateur |
| `PUT` | `/api/users/{id}` | Met à jour un utilisateur |
| `DELETE` | `/api/users/{id}` | Supprime un utilisateur |
| `PATCH` | `/api/users/{id}/toggle-status` | Active/Désactive un utilisateur |
| `GET` | `/api/users/stats` | Récupère les statistiques des utilisateurs |

### Paramètres de Requête

#### GET /api/users
- `page` (int, default: 0) : Numéro de page
- `size` (int, default: 10) : Taille de la page
- `search` (string, optional) : Terme de recherche

#### Exemple de Requête
```bash
GET /api/users?page=0&size=10&search=admin
```

### Réponse Type

```json
{
  "users": [
    {
      "id": "user-uuid",
      "firstName": "Ahlem",
      "lastName": "Cherni",
      "fullName": "Ahlem Cherni",
      "email": "ahlem.cherni@example.com",
      "username": "ahlem.cherni",
      "enabled": true,
      "emailVerified": true,
      "createdTimestamp": "2024-01-15T10:30:00",
      "roles": ["Admin"],
      "status": "Actif",
      "phoneNumber": "+1234567890",
      "department": "IT",
      "jobTitle": "System Administrator"
    }
  ],
  "totalElements": 25,
  "totalPages": 3,
  "currentPage": 0,
  "size": 10,
  "first": true,
  "last": false
}
```

## ⚙️ Configuration

### Variables d'Environnement

```yaml
keycloak:
  server-url: http://localhost:8080
  realm: master
  client-id: admin-cli
  username: admin
  password: admin

server:
  port: 8083
```

### Configuration Keycloak

1. **Démarrer Keycloak** :
   ```bash
   docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:22.0.5 start-dev
   ```

2. **Créer un realm** (optionnel) :
   - Accéder à http://localhost:8080
   - Se connecter avec admin/admin
   - Créer un nouveau realm

3. **Configurer les utilisateurs** :
   - Créer des utilisateurs de test
   - Assigner des rôles

## 🚀 Démarrage

### Prérequis
- Java 17+
- Maven 3.6+
- Keycloak en cours d'exécution

### Installation
```bash
# Cloner le projet
git clone <repository-url>
cd erp-user-service

# Installer les dépendances
mvn clean install

# Démarrer le service
mvn spring-boot:run
```

### Test des APIs
```bash
# Récupérer tous les utilisateurs
curl -X GET "http://localhost:8083/api/users?page=0&size=10"

# Récupérer un utilisateur par ID
curl -X GET "http://localhost:8083/api/users/{user-id}"

# Créer un utilisateur
curl -X POST "http://localhost:8083/api/users" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "username": "john.doe",
    "password": "password123",
    "enabled": true,
    "roles": ["User"]
  }'
```

## 🎨 Interface Utilisateur

Une page HTML d'exemple est fournie dans `src/main/resources/static/angular-user-management-example.html` qui démontre :

- **Design moderne** : Interface utilisateur responsive
- **Fonctionnalités complètes** : Recherche, pagination, actions
- **Intégration API** : Appels REST vers le service
- **Gestion d'état** : Loading, erreurs, succès

### Accès à l'Interface
```
http://localhost:8083/angular-user-management-example.html
```

## 🔧 Développement

### Structure du Projet
```
src/main/java/com/iss4u/erp/services/
├── config/
│   ├── KeycloakConfig.java
│   └── SecurityConfig.java
├── controller/
│   └── UserManagementController.java
└── service/
    └── KeycloakUserService.java
```

### DTOs
- `UserRequest` : DTO pour la création/modification
- `UserResponse` : DTO pour la réponse
- `UserListResponse` : DTO pour la liste paginée

### Logs
```yaml
logging:
  level:
    com.iss4u.erp.services: DEBUG
    org.keycloak: INFO
```

## 🐛 Dépannage

### Problèmes Courants

1. **Erreur de connexion Keycloak** :
   - Vérifier que Keycloak est démarré
   - Vérifier les credentials dans application.yml

2. **Erreur CORS** :
   - Vérifier la configuration SecurityConfig
   - Ajouter les origines autorisées

3. **Erreur de permissions** :
   - Vérifier les rôles Keycloak
   - Vérifier la configuration du client

### Logs Utiles
```bash
# Voir les logs du service
tail -f logs/erp-user-service.log

# Voir les logs Keycloak
docker logs <keycloak-container>
```

## 📚 Documentation API

Pour une documentation complète des APIs, consultez :
- Swagger UI : http://localhost:8083/swagger-ui.html (si activé)
- Postman Collection : Voir le dossier `docs/`

## 🤝 Contribution

1. Fork le projet
2. Créer une branche feature
3. Commiter les changements
4. Pousser vers la branche
5. Ouvrir une Pull Request

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.
