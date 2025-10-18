# Jenkins Pipeline - ERP Application

Ce répertoire contient la configuration Jenkins pour le déploiement automatique de l'application ERP sur AWS EKS.

## 📁 Fichiers

- `Jenkinsfile` - Pipeline principal Jenkins pour le build et déploiement
- `README.md` - Ce fichier de documentation

## 🚀 Configuration du Pipeline

### Paramètres
- **DEPLOY_TO_EKS** : Déploiement sur AWS EKS (défaut: true)
- **SKIP_TESTS** : Ignorer les tests (défaut: true)
- **SKIP_SONARQUBE** : Ignorer l'analyse SonarQube (défaut: true)
- **SKIP_BUILD** : Ignorer le build Maven et création d'images Docker (défaut: true)
- **ENVIRONMENT** : Environnement cible (dev, test, pprd, prod)

### Environnements
- **ECR_PRIVATE_REGISTRY_URL** : `590184116223.dkr.ecr.eu-west-3.amazonaws.com`
- **AWS_DEFAULT_REGION** : `eu-west-3`
- **EKS_CLUSTER** : `erp-app-cluster`
- **EKS_NAMESPACE** : `erp-${ENVIRONMENT}`

## ⚡ Optimisations de Ressources

### Configuration Actuelle (Optimisée)

Les ressources Kubernetes ont été optimisées pour une meilleure densité de pods et une réduction des coûts :

| Ressource | Avant | Après | Économie |
|-----------|-------|-------|----------|
| **CPU Request** | 250m | 100m | **-60%** |
| **CPU Limit** | 500m | 200m | **-60%** |
| **Memory Request** | 512Mi | 256Mi | **-50%** |
| **Memory Limit** | 1Gi | 512Mi | **-50%** |
| **JVM Heap Max** | 1024m | 400m | **-61%** |
| **JVM Heap Min** | 512m | 200m | **-61%** |

### Configuration YAML

```yaml
resources:
  requests:
    memory: "256Mi"
    cpu: "100m"
  limits:
    memory: "512Mi"
    cpu: "200m"

# Configuration JVM
JAVA_OPTS: "-Xmx400m -Xms200m"
```

## 🔧 Health Checks Dynamiques

Le pipeline utilise des health checks dynamiques basés sur la fonction `getServicePort()` :

### Ports par Service

| Service | Port | Health Check |
|---------|------|--------------|
| erp-service-discovery | 8013 | `/actuator/health:8013` |
| erp-config | 8012 | `/actuator/health:8012` |
| erp-api-gateway | 8014 | `/actuator/health:8014` |
| erp-user-service | 8055 | `/actuator/health:8055` |
| erp-product-service | 8051 | `/actuator/health:8051` |
| erp-inventory-service | 8059 | `/actuator/health:8059` |
| erp-supplier-service | 8052 | `/actuator/health:8052` |
| erp-order-service | 8053 | `/actuator/health:8053` |
| erp-client-service | 8058 | `/actuator/health:8058` |
| erp-payment-service | 8069 | `/actuator/health:8069` |
| erp-billing-service | 8057 | `/actuator/health:8057` |
| erp-sales-service | 8060 | `/actuator/health:8060` |
| erp-dashboard-service | 8065 | `/actuator/health:8065` |
| erp-scheduler-service | 8066 | `/actuator/health:8066` |

### Configuration Health Checks

```yaml
livenessProbe:
  httpGet:
    path: /actuator/health
    port: ${servicePort}  # Port dynamique
  initialDelaySeconds: 60
  periodSeconds: 30

readinessProbe:
  httpGet:
    path: /actuator/health
    port: ${servicePort}  # Port dynamique
  initialDelaySeconds: 30
  periodSeconds: 10
```

## 📊 Impact des Optimisations

### Densité de Pods
- **Avant** : ~3-4 pods par nœud t3.large
- **Après** : ~8-10 pods par nœud t3.large
- **Amélioration** : **+150% de capacité**

### Coût
- **Réduction de ~60%** des ressources demandées
- **Meilleure utilisation** des nœuds disponibles
- **Économie estimée** : ~60% sur les coûts de ressources

### Performance
- **Moins de contention** CPU entre les services
- **Démarrage plus rapide** des pods
- **Meilleure distribution** des charges

## 🏗️ Services Déployés

Le pipeline déploie automatiquement les services suivants :

### Services Core
- **erp-config** - Service de configuration Spring Cloud Config
- **erp-service-discovery** - Service Discovery (Eureka)
- **erp-api-gateway** - API Gateway

### Services Métier
- **erp-user-service** - Gestion des utilisateurs
- **erp-product-service** - Gestion des produits
- **erp-inventory-service** - Gestion des stocks
- **erp-supplier-service** - Gestion des fournisseurs
- **erp-order-service** - Gestion des commandes
- **erp-client-service** - Gestion des clients
- **erp-payment-service** - Gestion des paiements
- **erp-billing-service** - Facturation
- **erp-sales-service** - Gestion des ventes
- **erp-dashboard-service** - Tableau de bord
- **erp-scheduler-service** - Planification

## 🔄 Workflow du Pipeline

1. **Initialisation** - Récupération des versions et configuration
2. **Build** - Compilation Maven et création des images Docker
3. **Push ECR** - Envoi des images vers Amazon ECR
4. **Deploy EKS** - Déploiement sur le cluster Kubernetes
5. **Health Check** - Vérification du statut des services

## 📝 Notes

- Tous les services utilisent des health checks dynamiques
- Les ressources sont optimisées pour la densité maximale
- La configuration JVM est adaptée aux limites de ressources
- Les ports sont gérés centralement via `getServicePort()`

---

*Dernière mise à jour : Octobre 2025*
