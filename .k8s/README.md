# Kubernetes Manifests - ERP Application

Ce répertoire contient les manifests Kubernetes pour déployer l'application ERP sur un cluster EKS.

## 📁 Structure des fichiers

```
.k8s/
├── namespace.yaml              # Namespace erp-prod
├── configmap.yaml             # Configuration partagée
├── secrets.yaml               # Secrets (mots de passe, tokens)
├── mysql.yaml                 # Base de données MySQL
├── keycloak.yaml              # Service d'authentification
├── service-discovery.yaml     # Service Discovery (Eureka)
├── config-service.yaml        # Service de configuration
├── api-gateway.yaml           # API Gateway
├── user-service.yaml          # Service utilisateurs
├── business-services.yaml     # Services métier (product, inventory, billing)
├── dashboard-service.yaml     # Service tableau de bord
└── README.md                  # Ce fichier
```

## 🚀 Déploiement

### Prérequis

- Cluster EKS configuré
- kubectl configuré pour se connecter au cluster
- Images Docker poussées dans ECR

### 1. Déploiement séquentiel

```bash
# 1. Créer le namespace
kubectl apply -f namespace.yaml

# 2. Créer les configurations
kubectl apply -f configmap.yaml
kubectl apply -f secrets.yaml

# 3. Déployer MySQL
kubectl apply -f mysql.yaml

# 4. Attendre que MySQL soit prêt
kubectl wait --for=condition=ready pod -l app=mysql -n erp-prod --timeout=300s

# 5. Déployer Keycloak
kubectl apply -f keycloak.yaml

# 6. Déployer Service Discovery
kubectl apply -f service-discovery.yaml

# 7. Attendre que Service Discovery soit prêt
kubectl wait --for=condition=ready pod -l app=erp-service-discovery -n erp-prod --timeout=300s

# 8. Déployer Config Service
kubectl apply -f config-service.yaml

# 9. Attendre que Config Service soit prêt
kubectl wait --for=condition=ready pod -l app=erp-config -n erp-prod --timeout=300s

# 10. Déployer les autres services
kubectl apply -f api-gateway.yaml
kubectl apply -f user-service.yaml
kubectl apply -f business-services.yaml
kubectl apply -f dashboard-service.yaml
```

### 2. Déploiement en une commande

```bash
# Déployer tous les manifests
kubectl apply -f .
```

## 🔧 Configuration

### Variables d'environnement

Les variables d'environnement sont centralisées dans `configmap.yaml` et `secrets.yaml` :

#### ConfigMap (configmap.yaml)
- `SPRING_PROFILES_ACTIVE`: prd
- `DB_URL`: URL de connexion MySQL
- `DB_USERNAME`: Nom d'utilisateur MySQL
- `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE`: URL Eureka
- `SPRING_CLOUD_CONFIG_URI`: URL Config Service
- `KEYCLOAK_*`: Configuration Keycloak

#### Secrets (secrets.yaml)
- `DB_PASSWORD`: Mot de passe MySQL (base64)
- `KEYCLOAK_CLIENT_SECRET`: Secret client Keycloak (base64)
- `KEYCLOAK_ADMIN_PASSWORD`: Mot de passe admin Keycloak (base64)

### Ressources

Chaque service est configuré avec :
- **Requests** : 512Mi RAM, 250m CPU
- **Limits** : 1Gi RAM, 500m CPU
- **Replicas** : 2 (sauf MySQL, Service Discovery, Config Service = 1)

### Health Checks

Tous les services incluent :
- **Liveness Probe** : `/actuator/health` toutes les 30s
- **Readiness Probe** : `/actuator/health` toutes les 10s

## 🌐 Services exposés

### LoadBalancer (Accès externe)
- **Service Discovery** : Port 8013
- **API Gateway** : Port 8014
- **Keycloak** : Port 8080

### ClusterIP (Accès interne)
- **Config Service** : Port 8012
- **User Service** : Port 8055
- **Product Service** : Port 8051
- **Inventory Service** : Port 8059
- **Billing Service** : Port 8057
- **Dashboard Service** : Port 8065
- **MySQL** : Port 3306

## 📊 Monitoring

### Vérifier le statut des pods

```bash
# Voir tous les pods
kubectl get pods -n erp-prod

# Voir les services
kubectl get services -n erp-prod

# Voir les logs d'un service
kubectl logs -f deployment/erp-user-service-deployment -n erp-prod
```

### Vérifier les health checks

```bash
# Vérifier les endpoints de santé
kubectl get endpoints -n erp-prod

# Tester un health check
kubectl port-forward service/erp-user-service-service 8055:8055 -n erp-prod
curl http://localhost:8055/actuator/health
```

## 🔄 Mise à jour des images

### 1. Mettre à jour l'image d'un service

```bash
# Mettre à jour l'image
kubectl set image deployment/erp-user-service-deployment erp-user-service=erp-user-service:1.0.0-RELEASE -n erp-prod

# Vérifier le rollout
kubectl rollout status deployment/erp-user-service-deployment -n erp-prod
```

### 2. Rollback

```bash
# Voir l'historique des rollouts
kubectl rollout history deployment/erp-user-service-deployment -n erp-prod

# Rollback vers la version précédente
kubectl rollout undo deployment/erp-user-service-deployment -n erp-prod
```

## 🗑️ Nettoyage

### Supprimer tous les services

```bash
# Supprimer tous les manifests
kubectl delete -f .

# Supprimer le namespace (supprime tout)
kubectl delete namespace erp-prod
```

### Supprimer un service spécifique

```bash
# Supprimer un service
kubectl delete -f user-service.yaml
```

## 🔒 Sécurité

### Secrets

Les secrets sont stockés en base64. Pour les mettre à jour :

```bash
# Encoder un nouveau mot de passe
echo -n "new_password" | base64

# Mettre à jour le secret
kubectl patch secret erp-secrets -n erp-prod -p='{"data":{"DB_PASSWORD":"bmV3X3Bhc3N3b3Jk"}}'
```

### RBAC

Pour une sécurité renforcée, ajoutez des rôles RBAC :

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
  namespace: erp-prod
  name: erp-role
rules:
- apiGroups: [""]
  resources: ["pods", "services"]
  verbs: ["get", "list", "watch"]
```

## 📝 Notes importantes

1. **Ordre de déploiement** : Respectez l'ordre pour éviter les erreurs de dépendances
2. **Health checks** : Attendez que les services soient prêts avant de déployer les suivants
3. **Ressources** : Ajustez les ressources selon vos besoins
4. **Monitoring** : Configurez Prometheus/Grafana pour le monitoring avancé
5. **Backup** : Configurez des sauvegardes pour MySQL

## 🔗 Liens utiles

- [Documentation Kubernetes](https://kubernetes.io/docs/)
- [Documentation EKS](https://docs.aws.amazon.com/eks/)
- [Documentation Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
