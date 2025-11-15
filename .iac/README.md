# Infrastructure as Code - ERP Application

Ce répertoire contient la configuration Terraform pour déployer l'infrastructure AWS nécessaire à l'application ERP.

## 🏗️ Architecture

L'infrastructure comprend :
- **VPC** avec sous-réseaux publics et privés
- **EKS Cluster unique** pour orchestrer les conteneurs
- **Kubernetes Namespaces** pour chaque environnement (dev, test, pprd, prod)
- **Kubernetes Dashboard** pour la gestion et le monitoring du cluster
- **ECR Repositories** pour stocker les images Docker (un par service et environnement)
- **RDS MySQL** : 4 instances séparées (une par environnement)
- **S3 Buckets** pour le stockage des fichiers uploadés
- **Security Groups** pour la sécurité réseau
- **IAM Roles** pour les permissions

### 🎯 Avantages de l'architecture multi-namespace

- **Coût réduit** : Un seul cluster EKS au lieu de plusieurs
- **Gestion simplifiée** : Un seul point de contrôle pour tous les environnements
- **Isolation** : Chaque environnement dans son propre namespace
- **Ressources partagées** : Les nœuds sont partagés entre les environnements

## 📋 Prérequis

- Terraform >= 1.0
- AWS CLI configuré
- kubectl installé
- Accès AWS avec permissions appropriées

## 📁 Structure des fichiers

- **`provider.tf`** : Configuration des providers Terraform (AWS, Kubernetes, Helm)
- **`variables.tf`** : Définition des variables d'entrée
- **`main.tf`** : Infrastructure principale (VPC, EKS, Security Groups, IAM)
- **`eks-access.tf`** : Configuration des accès EKS et ConfigMap aws-auth
- **`eks_dashboard.tf`** : Configuration du dashboard Kubernetes avec Helm
- **`s3.tf`** : Configuration des buckets S3
- **`rds.tf`** : Configuration RDS MySQL (instances par environnement)
- **`ecr.tf`** : Configuration des repositories ECR (avec force_delete pour la destruction)
- **`policies.tf`** : Politiques IAM pour les services ERP
- **`outputs.tf`** : Variables de sortie

## 🚀 Déploiement

### 1. Configuration initiale

```bash
# Cloner le repository
git clone <repository-url>
cd .iac

# Optionnel : Personnaliser les variables par défaut
# Les valeurs par défaut sont définies dans variables.tf
# Si vous voulez les modifier, créez un fichier terraform.tfvars :
cp terraform.tfvars.example terraform.tfvars
# Puis modifiez les valeurs selon vos besoins
vim terraform.tfvars
```

### 2. Initialisation Terraform

```bash
# 1. Initialiser Terraform
terraform init

# 2. Valider la configuration (recommandé)
terraform validate

# 3. Formater
terraform fmt

# 4. Planifier: Vérifier le plan de déploiement
terraform plan

# 5. Appliquer: Déployer l'infrastructure
terraform apply -auto-approve

# NB: Problèeme dans eks-access.tf (END OF FILE)
dos2unix ./eks-access.tf 

# 6. Destruction: Destruction des Ressources
# Supprimer les nodes EKS en premier 
terraform destroy -target=aws_eks_cluster.main -target=aws_eks_node_group.main
# Supprimer les autres ressources 
terraform destroy -auto-approve

```

### 3. Validation de la configuration

#### **🔍 Commandes de validation**

```bash
# Valider la syntaxe et la configuration
terraform validate

# Valider avec des variables spécifiques
terraform validate -var="project_name=erp-app"

# Valider avec un fichier de variables
terraform validate -var-file="terraform.tfvars"

# Valider et formater le code
terraform fmt -check
```

#### **📋 Vérifications automatiques**

```bash
# Vérifier la syntaxe de tous les fichiers .tf
terraform validate

# Formater le code (corriger l'indentation)
terraform fmt

# Vérifier le formatage sans le modifier
terraform fmt -check

# Vérifier la configuration avec un plan
terraform plan -detailed-exitcode
```

#### **🚨 Résolution des erreurs de validation**

```bash
# Erreur de syntaxe
terraform validate
# → Corriger les erreurs dans les fichiers .tf

# Erreur de formatage
terraform fmt -check
# → Exécuter: terraform fmt

# Erreur de variables manquantes
terraform validate
# → Créer terraform.tfvars ou définir les variables

# Erreur de provider
terraform init -upgrade
# → Mettre à jour les providers
```

#### **✅ Checklist de validation**

- [ ] **Syntaxe** : `terraform validate` sans erreurs
- [ ] **Formatage** : `terraform fmt -check` passe
- [ ] **Variables** : Toutes les variables requises définies
- [ ] **Providers** : Versions compatibles installées
- [ ] **État** : Pas de conflits dans l'état Terraform
- [ ] **Plan** : `terraform plan` s'exécute sans erreurs

### 4. Configuration kubectl

```bash
# Mettre à jour la configuration kubectl
aws eks update-kubeconfig --region eu-west-3 --name erp-app-cluster

# Vérifier la connexion
kubectl get nodes

# Vérifier les namespaces créés
kubectl get namespaces

# Vérifier le dashboard Kubernetes (si activé)
kubectl get pods -n kubernetes-dashboard
```

## 📊 Services déployés

### Kubernetes Namespaces
- `erp-dev` - Environnement de développement
- `erp-test` - Environnement de test
- `erp-pprd` - Environnement de pré-production
- `erp-prod` - Environnement de production

### ECR Repositories (par type)
Pour chaque service, 2 repositories :
- **Stages** : Pour les images SNAPSHOT (développement/test)
- **Releases** : Pour les images RELEASE (production)

#### Services backend :
- `erp-service-discovery-stages` / `erp-service-discovery-releases`
- `erp-config-stages` / `erp-config-releases`
- `erp-api-gateway-stages` / `erp-api-gateway-releases`
- `erp-user-service-stages` / `erp-user-service-releases`
- `erp-product-service-stages` / `erp-product-service-releases`
- `erp-inventory-service-stages` / `erp-inventory-service-releases`
- `erp-supplier-service-stages` / `erp-supplier-service-releases`
- `erp-order-service-stages` / `erp-order-service-releases`
- `erp-client-service-stages` / `erp-client-service-releases`
- `erp-payment-service-stages` / `erp-payment-service-releases`
- `erp-billing-service-stages` / `erp-billing-service-releases`
- `erp-sales-service-stages` / `erp-sales-service-releases`
- `erp-dashboard-service-stages` / `erp-dashboard-service-releases`
- `erp-scheduler-service-stages` / `erp-scheduler-service-releases`

#### Service UI :
- `erp-ui-service-stages` / `erp-ui-service-releases`

### RDS MySQL (Instances par environnement)
- **Instances** : 4 instances séparées (une par environnement)
- **Identifiants** : `erp-database-dev`, `erp-database-test`, `erp-database-pprd`, `erp-database-prod`
- **Engine** : MySQL 8.0.43
- **Instance Class** : `db.t3.micro`
- **Storage** : 20GB (max 100GB) par instance
- **Backup** : 7 jours de rétention
- **Bases de données** : `erp_dev`, `erp_test`, `erp_pprd`, `erp_prod`
- **Utilisateur** : `admin` / `erp_password_2024` (commun à toutes les instances)

### S3 Bucket
- **Uploads Bucket** : Stockage des fichiers uploadés par les utilisateurs
- **Chiffrement** : AES256
- **Versioning** : Activé
- **Lifecycle** : Transition automatique vers IA/Glacier pour optimiser les coûts

### Kubernetes Dashboard
- **Namespace** : `kubernetes-dashboard`
- **Installation** : Automatique via Helm chart
- **Version** : 7.0.0 (configurable)
- **Service** : ClusterIP par défaut (configurable)
- **RBAC** : Rôles IAM et ClusterRoleBinding configurés
- **Métriques** : ServiceMonitor activé pour Prometheus
- **Sécurité** : OIDC et IAM roles pour l'authentification

## 🔧 Configuration

### Variables principales

| Variable | Description | Valeur par défaut |
|----------|-------------|-------------------|
| `aws_region` | Région AWS | `eu-west-3` |
| `environments` | Liste des environnements | `["dev", "test", "pprd", "prod"]` |
| `project_name` | Nom du projet | `erp-app` |
| `vpc_cidr` | CIDR du VPC | `10.0.0.0/16` |
| `node_desired_size` | Nombre de nœuds | `2` |

### Configuration RDS pour l'application

Après le déploiement, récupérez les informations RDS nécessaires pour configurer votre backend :

```bash
# Récupérer la configuration RDS complète par environnement
terraform output environment_databases

# Récupérer des informations spécifiques
terraform output rds_endpoint
terraform output rds_port
```

#### Configuration dans votre application Spring Boot

Ajoutez ces variables d'environnement dans votre configuration :

```yaml
# application.yml
spring:
  datasource:
    url: jdbc:mysql://erp-database-${ENVIRONMENT}.cnsyguwokgsk.eu-west-3.rds.amazonaws.com:3306/erp_${ENVIRONMENT}?useSSL=false
    username: admin
    password: erp_password_2024
    driver-class-name: com.mysql.cj.jdbc.Driver
```

#### URLs de connexion par environnement

- **Dev** : `jdbc:mysql://erp-database-dev.cnsyguwokgsk.eu-west-3.rds.amazonaws.com:3306/erp_dev?useSSL=false`
- **Test** : `jdbc:mysql://erp-database-test.cnsyguwokgsk.eu-west-3.rds.amazonaws.com:3306/erp_test?useSSL=false`
- **PPRD** : `jdbc:mysql://erp-database-pprd.cnsyguwokgsk.eu-west-3.rds.amazonaws.com:3306/erp_pprd?useSSL=false`
- **Prod** : `jdbc:mysql://erp-database-prod.cnsyguwokgsk.eu-west-3.rds.amazonaws.com:3306/erp_prod?useSSL=false`

#### Endpoints RDS par environnement

- **Dev** : `erp-database-dev.cnsyguwokgsk.eu-west-3.rds.amazonaws.com:3306`
- **Test** : `erp-database-test.cnsyguwokgsk.eu-west-3.rds.amazonaws.com:3306`
- **PPRD** : `erp-database-pprd.cnsyguwokgsk.eu-west-3.rds.amazonaws.com:3306`
- **Prod** : `erp-database-prod.cnsyguwokgsk.eu-west-3.rds.amazonaws.com:3306`

### Configuration S3 pour l'application

Après le déploiement, récupérez les informations S3 nécessaires pour configurer votre backend :

```bash
# Récupérer la configuration S3 complète
terraform output s3_configuration

# Récupérer des informations spécifiques
terraform output s3_uploads_bucket_name
terraform output s3_uploads_bucket_arn
```

#### Configuration dans votre application Spring Boot

Ajoutez ces variables d'environnement dans votre configuration :

```yaml
# application.yml
aws:
  s3:
    region: eu-west-3
    uploads-bucket: erp-app-uploads-xxxxx
```

#### Permissions IAM

Les nœuds EKS ont automatiquement les permissions pour :
- `s3:GetObject` - Lire les fichiers
- `s3:PutObject` - Écrire les fichiers
- `s3:DeleteObject` - Supprimer les fichiers
- `s3:ListBucket` - Lister le contenu des buckets

#### Utilisation dans le code

```java
// Exemple de configuration Spring Boot
@Value("${aws.s3.uploads-bucket}")
private String uploadsBucket;

@Value("${aws.s3.region}")
private String region;

// Configuration du client S3
@Bean
public S3Client s3Client() {
    return S3Client.builder()
        .region(Region.of(region))
        .build();
}
```

### Configuration du Dashboard Kubernetes

Le dashboard Kubernetes est automatiquement installé et configuré via Terraform. Voici comment l'utiliser :

#### **🚀 Démarrage rapide**

```bash
# 1. Vérifier que le dashboard est déployé
kubectl get pods -n kubernetes-dashboard

# 2. Récupérer le token d'authentification
kubectl -n kubernetes-dashboard get secret dashboard-admin-token -o jsonpath='{.data.token}' | base64 -d

# 3. Accéder au dashboard via Kong proxy (recommandé)
kubectl port-forward -n kubernetes-dashboard service/kubernetes-dashboard-kong-proxy 8443:443
# Ouvrir https://localhost:8443 dans le navigateur et utiliser le token
```

#### **🔧 Variables de configuration**

| Variable | Description | Défaut |
|----------|-------------|---------|
| `dashboard_enabled` | Activer le dashboard | `true` |
| `dashboard_namespace` | Namespace pour le dashboard | `kubernetes-dashboard` |
| `dashboard_chart_version` | Version du chart Helm | `7.0.0` |
| `dashboard_service_type` | Type de service | `ClusterIP` |
| `dashboard_service_port` | Port du service | `443` |
| `dashboard_ingress_enabled` | Activer l'ingress | `false` |
| `dashboard_metrics_enabled` | Activer les métriques | `true` |
| `create_dashboard_rbac` | Créer les rôles RBAC | `true` |
| `create_load_balancer` | Créer un Load Balancer | `false` |

#### **🌐 Accès au Dashboard**

##### **1. Port Forward via Kong Proxy (recommandé)**
```bash
# Mettre à jour la configuration kubectl
aws eks update-kubeconfig --region eu-west-3 --name erp-app-cluster

# Vérifier les namespaces créés
kubectl get namespaces

# Port Forward via Kong proxy (recommandé pour éviter les problèmes CSRF)
kubectl port-forward -n kubernetes-dashboard service/kubernetes-dashboard-kong-proxy 8443:443
```
Accédez à : `https://localhost:8443`

##### **2. Port Forward direct (alternative)**
```bash
# Port Forward direct vers le service web
kubectl port-forward -n kubernetes-dashboard service/kubernetes-dashboard-web 8080:8000
```
Accédez à : `http://localhost:8080`

Pour récupérer le token **il faut utiliser la méthode 4 pour que tout fonctionne correctement ✅ (les autes méthodes j'arrive pas à les tester)**

##### **3. Via Ingress (production)**
Si vous avez configuré un ingress, accédez à l'URL configurée :
`https://dashboard.yourdomain.com`

##### **4. Via Load Balancer**
Si vous avez activé le Load Balancer, utilisez l'URL fournie dans les outputs.

#### **🔐 Authentification**

##### **Récupérer le token d'authentification**

**🔍 Diagnostic préalable :**
```bash
# 1. Vérifier que le namespace existe
kubectl get namespace kubernetes-dashboard

# 2. Vérifier que le service account existe
kubectl get serviceaccount -n kubernetes-dashboard

# 3. Vérifier les secrets du service account
kubectl get secrets -n kubernetes-dashboard | grep kubernetes-dashboard

# 4. Vérifier si le service account a des secrets associés
kubectl -n kubernetes-dashboard get sa kubernetes-dashboard -o jsonpath='{.secrets[0].name}'
# Si cette commande retourne vide, le service account n'a pas de secret (normal avec les versions récentes)

# 5. Vérifier la version de kubectl
kubectl version --client
# Si la version est < 1.24, la Méthode 1 ne fonctionnera pas
```

**Méthode 1 : Commande moderne (⚠️ Nécessite kubectl 1.24+)**
```bash
# Cette méthode fonctionne UNIQUEMENT avec kubectl 1.24+ et Kubernetes 1.24+
# Votre version actuelle : kubectl 1.21.2 (trop ancienne)
kubectl -n kubernetes-dashboard create token kubernetes-dashboard

# Si vous obtenez "unknown command 'token'", utilisez la Méthode 4 (industrialisée)
```

**Méthode 2 : Récupération depuis le secret (compatible)**
```bash
# Étape 1 : Trouver le nom du secret
SECRET_NAME=$(kubectl -n kubernetes-dashboard get sa kubernetes-dashboard -o jsonpath='{.secrets[0].name}')

# Étape 2 : Récupérer le token
kubectl -n kubernetes-dashboard get secret $SECRET_NAME -o jsonpath='{.data.token}' | base64 -d
```

**Méthode 3 : Alternative simple (⚠️ Ne fonctionne pas avec le service account par défaut)**
```bash
# Cette méthode ne fonctionne pas car le service account kubernetes-dashboard n'a pas de secret
# Utilisez plutôt la Méthode 4 (industrialisée) ou la Méthode 5 (manuelle)
kubectl -n kubernetes-dashboard get secret $(kubectl -n kubernetes-dashboard get sa kubernetes-dashboard -o jsonpath='{.secrets[0].name}') -o jsonpath='{.data.token}' | base64 -d
```

**Méthode 4 : Solution industrialisée avec Terraform (recommandée)**
```bash
# 1. Récupérer les informations du token
terraform output dashboard_admin_token

# 2. Exécuter la commande fournie pour récupérer le token
kubectl -n kubernetes-dashboard get secret dashboard-admin-token -o jsonpath='{.data.token}' | base64 -d

# 3. Accéder au dashboard
kubectl port-forward -n kubernetes-dashboard service/kubernetes-dashboard 8443:443
# Puis aller sur https://localhost:8443 et utiliser le token
```

**Méthode 5 : Solution manuelle (si Terraform n'est pas utilisé)**
```bash
# 1. Créer un service account temporaire avec permissions admin
kubectl create serviceaccount temp-admin -n kubernetes-dashboard
kubectl create clusterrolebinding temp-admin --clusterrole=cluster-admin --serviceaccount=kubernetes-dashboard:temp-admin

# 2. Créer un secret pour le service account
kubectl apply -f - <<EOF
apiVersion: v1
kind: Secret
metadata:
  name: temp-admin-token
  namespace: kubernetes-dashboard
  annotations:
    kubernetes.io/service-account.name: temp-admin
type: kubernetes.io/service-account-token
EOF

# 3. Récupérer le token
kubectl -n kubernetes-dashboard get secret temp-admin-token -o jsonpath='{.data.token}' | base64 -d
```

**📋 Résumé des méthodes :**
- **Méthode 4** (industrialisée) : **Recommandée** - Automatique et fiable via Terraform
- **Méthode 1** : ⚠️ **Nécessite kubectl 1.24+** - Votre version (1.21.2) est trop ancienne
- **Méthode 2** : Compatible - Fonctionne avec toutes les versions de kubectl
- **Méthode 3** : ⚠️ **Ne fonctionne pas** - Le service account par défaut n'a pas de secret
- **Méthode 5** : Manuelle - Pour les cas où Terraform n'est pas utilisé

**🚨 Dépannage :**
- Si le namespace n'existe pas : `kubectl create namespace kubernetes-dashboard`
- Si le service account n'existe pas : Le dashboard n'est pas encore déployé
- Si les secrets sont vides : Problème de configuration RBAC
- **Pourquoi la Méthode 1 ne fonctionne pas** : Vous avez kubectl 1.21.2, mais la commande `create token` nécessite kubectl 1.24+. Utilisez la Méthode 4 (industrialisée) ou la Méthode 5 (manuelle).
- **Pourquoi la Méthode 3 ne fonctionne pas** : Le service account `kubernetes-dashboard` créé par Helm n'a pas de secret associé (c'est normal avec les versions récentes de Kubernetes). Utilisez la Méthode 4 (industrialisée) ou la Méthode 5 (manuelle) qui créent un service account avec secret.
- **Mise à jour de kubectl** (optionnel) : `brew upgrade kubectl` (macOS) ou télécharger depuis [kubernetes.io](https://kubernetes.io/docs/tasks/tools/)

##### **🔧 Résolution du problème d'erreur CSRF**

Si vous rencontrez l'erreur `Unknown error (200): Http failure during parsing for http://localhost:8000/api/v1/csrftoken/login` :

**🎯 Solution recommandée :**
```bash
# 1. Utiliser le port-forward via Kong proxy (évite les problèmes CSRF)
kubectl port-forward -n kubernetes-dashboard service/kubernetes-dashboard-kong-proxy 8443:443

# 2. Accéder via le navigateur sur https://localhost:8443
# 3. Sélectionner "Token" comme méthode d'authentification
# 4. Coller le token récupéré avec la commande ci-dessous
kubectl -n kubernetes-dashboard get secret dashboard-admin-token -o jsonpath='{.data.token}' | base64 -d
```

**🔍 Pourquoi cette erreur se produit :**
- Le dashboard utilise Kong comme proxy pour gérer les requêtes
- L'accès direct au service web peut causer des problèmes de validation CSRF
- Le port-forward via Kong proxy résout ces problèmes

**✅ Vérification que tout fonctionne :**
```bash
# 1. Vérifier que le dashboard est accessible
curl -k -I https://localhost:8443

# 2. Tester l'API CSRF via Kong
curl -k -X GET https://localhost:8443/api/v1/csrftoken/login

# 3. Vérifier que le token est valide
kubectl -n kubernetes-dashboard get secret dashboard-admin-token -o jsonpath='{.data.token}' | base64 -d | wc -c
```

##### **Créer un utilisateur admin (optionnel)**

**1. Créer l'utilisateur admin**
```bash
# Créer un service account admin
kubectl create serviceaccount admin-user -n kubernetes-dashboard

# Lui donner les permissions d'administrateur
kubectl create clusterrolebinding admin-user --clusterrole=cluster-admin --serviceaccount=kubernetes-dashboard:admin-user
```

**2. Récupérer le token de l'utilisateur admin**

**Méthode 1 : Commande moderne (⚠️ Nécessite kubectl 1.24+)**
```bash
# Cette méthode fonctionne UNIQUEMENT avec kubectl 1.24+ et Kubernetes 1.24+
# Votre version actuelle : kubectl 1.21.2 (trop ancienne)
kubectl -n kubernetes-dashboard create token admin-user

# Si vous obtenez "unknown command 'token'", utilisez la Méthode 4 (industrialisée)
```

**Méthode 2 : Récupération depuis le secret**
```bash
kubectl -n kubernetes-dashboard get secret $(kubectl -n kubernetes-dashboard get sa admin-user -o jsonpath='{.secrets[0].name}') -o jsonpath='{.data.token}' | base64 -d
```

**Méthode 3 : Alternative avec jq (si installé)**
```bash
kubectl -n kubernetes-dashboard get secret $(kubectl -n kubernetes-dashboard get sa admin-user -o jsonpath='{.secrets[0].name}') -o json | jq -r '.data.token' | base64 -d
```

#### **📊 Monitoring et Métriques**

Les métriques sont activées par défaut. Pour les visualiser :
```bash
# Vérifier les métriques
kubectl get servicemonitor -n kubernetes-dashboard

# Port forward pour Prometheus (si configuré)
kubectl port-forward -n monitoring service/prometheus-server 9090:80
```

#### **🔧 Configuration avancée**

##### **Activer l'ingress**
```hcl
# Dans terraform.tfvars
dashboard_ingress_enabled = true
dashboard_ingress_class   = "nginx"
dashboard_ingress_hosts = [
  {
    host  = "dashboard.yourdomain.com"
    paths = ["/"]
  }
]
```

##### **Activer le Load Balancer**
```hcl
# Dans terraform.tfvars
create_load_balancer = true
load_balancer_annotations = {
  "service.beta.kubernetes.io/aws-load-balancer-type" = "nlb"
}
```

#### **📝 Outputs utiles**

```bash
# Afficher tous les outputs du dashboard
terraform output | grep dashboard

# Afficher les URLs d'accès
terraform output dashboard_access_urls

# Afficher les commandes kubectl
terraform output dashboard_kubectl_commands

# Afficher la configuration
terraform output dashboard_configuration

# Récupérer les informations du token d'authentification
terraform output dashboard_admin_token
```

#### **🏭 Industrialisation avec Terraform**

Le dashboard Kubernetes est entièrement industrialisé avec Terraform :

**✅ Ressources créées automatiquement :**
- Service Account `dashboard-admin` avec permissions cluster-admin
- ClusterRoleBinding pour les permissions
- Secret `dashboard-admin-token` pour l'authentification
- Configuration RBAC complète

**✅ Avantages de l'approche Terraform :**
- **Reproductible** : Même configuration à chaque déploiement
- **Versionnable** : Configuration dans le code source
- **Automatisée** : Pas d'intervention manuelle
- **Idempotente** : Peut être exécutée plusieurs fois sans problème
- **Intégrée** : Fait partie de l'infrastructure complète

**✅ Utilisation complète (workflow recommandé) :**
```bash
# 1. Vérifier que le dashboard est déployé
kubectl get pods -n kubernetes-dashboard

# 2. Récupérer les informations du token
terraform output dashboard_admin_token

# 3. Récupérer le token d'authentification
kubectl -n kubernetes-dashboard get secret dashboard-admin-token -o jsonpath='{.data.token}' | base64 -d

# 4. Accéder au dashboard via Kong proxy (évite les problèmes CSRF)
kubectl port-forward -n kubernetes-dashboard service/kubernetes-dashboard-kong-proxy 8443:443

# 5. Ouvrir le navigateur sur https://localhost:8443
# 6. Sélectionner "Token" et coller le token récupéré à l'étape 3
```

**✅ Commandes de vérification :**
```bash
# Vérifier les ressources créées
kubectl get serviceaccount -n kubernetes-dashboard | grep dashboard-admin
kubectl get clusterrolebinding | grep dashboard-admin
kubectl get secret -n kubernetes-dashboard | grep dashboard-admin-token

# Vérifier les permissions
kubectl auth can-i '*' '*' --as=system:serviceaccount:kubernetes-dashboard:dashboard-admin
```

**✅ Test complet du dashboard :**
```bash
# 1. Vérifier que tout fonctionne
kubectl get pods -n kubernetes-dashboard
kubectl get serviceaccount -n kubernetes-dashboard | grep dashboard-admin
kubectl get secret -n kubernetes-dashboard | grep dashboard-admin-token

# 2. Récupérer et tester le token
TOKEN=$(kubectl -n kubernetes-dashboard get secret dashboard-admin-token -o jsonpath='{.data.token}' | base64 -d)
echo "Token récupéré: ${TOKEN:0:50}..."

# 3. Tester l'accès au dashboard via Kong proxy (recommandé)
kubectl port-forward -n kubernetes-dashboard service/kubernetes-dashboard-kong-proxy 8443:443 &
echo "Dashboard accessible sur: https://localhost:8443"
echo "Token: $TOKEN"

# 4. Vérifier que le dashboard répond correctement
sleep 5
curl -k -I https://localhost:8443
curl -k -X GET https://localhost:8443/api/v1/csrftoken/login
```

#### **🚨 Dépannage**

##### **Problèmes courants**
1. **Dashboard inaccessible** : Vérifiez les pods avec `kubectl get pods -n kubernetes-dashboard`
2. **Erreur d'authentification** : Vérifiez les rôles RBAC avec `kubectl get clusterrolebinding kubernetes-dashboard`
3. **Problèmes d'ingress** : Vérifiez que le contrôleur d'ingress est installé

##### **Commandes de diagnostic**
```bash
# Vérifier l'état des pods
kubectl get pods -n kubernetes-dashboard

# Vérifier les services
kubectl get services -n kubernetes-dashboard

# Vérifier les logs
kubectl logs -n kubernetes-dashboard deployment/kubernetes-dashboard

# Vérifier les événements
kubectl get events -n kubernetes-dashboard
```

### Tags

Toutes les ressources sont taguées avec :
- `Project` : erp-app
- `ManagedBy` : terraform

## 🔒 Sécurité

### Security Groups
- **EKS Cluster** : Communication interne uniquement
- **EKS Nodes** : Communication avec le cluster et internet
- **RDS** : Accès depuis les nœuds EKS uniquement

### IAM Roles
- **EKS Cluster** : Permissions pour gérer le cluster
- **EKS Nodes** : Permissions pour les nœuds worker

## 🔐 Gestion des Accès EKS (eks-access.tf)

Le fichier `eks-access.tf` gère automatiquement l'accès au cluster EKS via le ConfigMap aws-auth.

### 🎯 Configuration des Accès

#### **1. ConfigMap aws-auth**
- **Rôles EKS** : Configuration automatique des rôles des nœuds
- **Utilisateurs** : Jenkins et Root User avec accès complet
- **Groupes** : `system:masters` pour les administrateurs

#### **2. Utilisateurs Configurés**
- **jenkins-user** : `arn:aws:iam::ACCOUNT_ID:user/jenkins-user`
- **root** : `arn:aws:iam::ACCOUNT_ID:root`
- **Groupes** : `system:masters` (accès complet au cluster)

#### **3. Vérification Automatique**
- Test d'accès au cluster après configuration
- Affichage des informations du cluster
- Validation des permissions

### 📋 Commandes Utiles

```bash
# Mettre à jour kubeconfig
aws eks update-kubeconfig --region eu-west-3 --name erp-app-cluster

# Vérifier l'accès
kubectl get nodes
kubectl get namespaces

# Voir la configuration aws-auth
kubectl get configmap aws-auth -n kube-system -o yaml
```

## 🔐 Gestion des Politiques IAM (policies.tf)

Le fichier `policies.tf` gère les permissions IAM pour les services ERP.

### 🎯 Rôle et Utilité

#### **1. Accès EKS pour Jenkins**
- **Problème** : Même avec `AdministratorAccess`, Jenkins ne peut pas déployer dans EKS par défaut
- **Solution** : Ajout automatique de l'utilisateur `jenkins-user` au configmap `aws-auth`
- **Permissions** : `system:masters` (accès complet au cluster)

#### **2. Configuration Automatique**
```hcl
# Ajout automatique de Jenkins au cluster EKS
resource "null_resource" "add_jenkins_to_aws_auth" {
  # Attendre que le cluster soit prêt
  # Mettre à jour kubeconfig
  # Ajouter l'utilisateur au configmap aws-auth
}
```

#### **3. Vérification d'Accès**
- **Test automatique** : Vérification que Jenkins peut accéder au cluster
- **Validation** : Commande `kubectl get nodes` pour confirmer l'accès
- **Logs** : Messages de confirmation ou d'erreur

### 📋 Ressources Créées

#### **Pour Jenkins :**
- **Data source** : Référence à l'utilisateur `jenkins-user` existant
- **Configmap update** : Ajout automatique au `aws-auth`
- **Verification** : Test d'accès au cluster EKS

#### **Pour les Services ERP :**
- **`erp_services_rds_access`** : Accès aux métadonnées RDS
- **`erp_services_secrets_access`** : Accès aux secrets AWS Secrets Manager

### 🔧 Configuration Requise

#### **Prérequis :**
1. **Utilisateur IAM** : `jenkins-user` doit exister
2. **Permissions** : `AdministratorAccess` ou permissions EKS/ECR
3. **Cluster EKS** : Doit être créé avant l'exécution des politiques

#### **Déploiement :**
```bash
# Appliquer les politiques
terraform apply -target=null_resource.add_jenkins_to_aws_auth

# Vérifier l'accès
terraform apply -target=null_resource.verify_jenkins_eks_access
```

### 🚨 Points Importants

#### **Sécurité :**
- **Principe du moindre privilège** : Seules les permissions nécessaires sont accordées
- **Isolation** : Chaque service a ses propres politiques
- **Audit** : Toutes les actions sont tracées dans CloudTrail

#### **Maintenance :**
- **Automatique** : La configuration se fait automatiquement lors du déploiement
- **Idempotent** : Peut être exécuté plusieurs fois sans problème
- **Détection** : Vérifie si l'utilisateur existe déjà avant de l'ajouter

### 📊 Output Disponible

```hcl
output "jenkins_eks_access" {
  value = {
    user_arn  = "arn:aws:iam::ACCOUNT_ID:user/jenkins-user"
    username  = "jenkins-user"
    groups    = ["system:masters"]
    cluster   = "erp-app-cluster"
    region    = "eu-west-3"
  }
}
```

### 🔍 Vérification Manuelle

```bash
# Vérifier que Jenkins est dans aws-auth
kubectl get configmap aws-auth -n kube-system -o yaml

# Tester l'accès (avec les credentials de jenkins-user)
aws sts get-caller-identity
kubectl get nodes
kubectl get pods
```

## 📈 Monitoring

### CloudWatch
- Logs du cluster EKS
- Métriques des nœuds
- Logs RDS

### Kubernetes Dashboard
- Interface web pour la gestion du cluster
- Visualisation des pods, services, namespaces
- Métriques et logs en temps réel
- Gestion des ressources et déploiements

### Coûts estimés
- **EKS Cluster** : ~$73/mois
- **EC2 Nodes (4x t3.large)** : ~$240/mois
- **RDS MySQL (4x db.t3.micro)** : ~$60/mois (4 instances)
- **S3 Bucket** : ~$5-15/mois (selon le volume de données)
- **Total estimé** : ~$378-388/mois

## 🗑️ Destruction des Ressources

### **⚠️ ATTENTION : Destruction complète**

La destruction supprimera **TOUTES** les ressources AWS créées par Terraform, y compris :
- Cluster EKS et tous les pods/services
- Base de données RDS (avec perte de données)
- Repositories ECR et toutes les images
- VPC, sous-réseaux, NAT Gateway
- Security Groups et IAM policies

### **🚀 Commandes de destruction**

#### **1. Destruction standard**
```bash
# Aller dans le répertoire Terraform
cd .iac

# Voir ce qui sera détruit (recommandé)
terraform plan -destroy

# Détruire l'infrastructure
terraform destroy

# Confirmer la destruction
yes
```

#### **2. Destruction forcée (si nécessaire)**
```bash
# Destruction sans confirmation interactive
terraform destroy -auto-approve

# Destruction avec variables spécifiques
terraform destroy -var="project_name=erp-app" -auto-approve
```

#### **3. Destruction sélective**
```bash
# Détruire seulement certaines ressources
terraform destroy -target=aws_eks_cluster.main
terraform destroy -target=aws_db_instance.mysql
terraform destroy -target=aws_ecr_repository.erp_services

# Détruire le dashboard Kubernetes
terraform destroy -target=helm_release.kubernetes_dashboard
terraform destroy -target=kubernetes_namespace.kubernetes_dashboard

# Détruire par type de ressource
terraform destroy -target='aws_ecr_repository.*'
terraform destroy -target='aws_security_group.*'
```

### **🔍 Vérification après destruction**

```bash
# Vérifier que les ressources sont supprimées
aws eks list-clusters --region eu-west-3
aws rds describe-db-instances --region eu-west-3
aws ecr describe-repositories --region eu-west-3

# Vérifier l'état Terraform
terraform show
terraform state list
```

### **🚨 Problèmes courants et solutions**

#### **Erreur : "Resource is in use"**
```bash
# Forcer la destruction (attention aux dépendances)
terraform destroy -auto-approve

# Ou détruire dans l'ordre inverse
terraform destroy -target=aws_eks_node_group.main
terraform destroy -target=aws_eks_cluster.main
```

#### **Erreur : "Cannot delete VPC"**
```bash
# Vérifier les ressources restantes
aws ec2 describe-vpcs --vpc-ids vpc-xxxxxxxxx
aws ec2 describe-internet-gateways --filters "Name=attachment.vpc-id,Values=vpc-xxxxxxxxx"

# Supprimer manuellement si nécessaire
aws ec2 delete-internet-gateway --internet-gateway-id igw-xxxxxxxxx
aws ec2 delete-vpc --vpc-id vpc-xxxxxxxxx
```

#### **Erreur : "RDS deletion protection"**
```bash
# Désactiver la protection avant destruction
aws rds modify-db-instance \
    --db-instance-identifier erp-app-mysql \
    --no-deletion-protection \
    --apply-immediately
```

### **💾 Sauvegarde avant destruction**

```bash
# Sauvegarder l'état Terraform
cp terraform.tfstate terraform.tfstate.backup

# Exporter les outputs importants
terraform output -json > outputs.json

# Sauvegarder la base de données RDS
aws rds create-db-snapshot \
    --db-instance-identifier erp-app-mysql \
    --db-snapshot-identifier erp-app-mysql-backup-$(date +%Y%m%d)
```

### **🔄 Nettoyage complet**

```bash
# Supprimer les fichiers Terraform temporaires
rm -rf .terraform/
rm -f .terraform.lock.hcl
rm -f terraform.tfstate*

# Supprimer les fichiers de configuration kubectl
rm -f ~/.kube/config

# Nettoyer les caches AWS
aws configure list-profiles
# Supprimer les profils inutiles si nécessaire
```

## 📝 Notes importantes

1. **Coûts** : Cette infrastructure génère des coûts AWS
2. **Sécurité** : Changez les mots de passe par défaut
3. **Backup** : Configurez des sauvegardes automatiques
4. **Monitoring** : Activez CloudWatch pour le monitoring
5. **Scaling** : Ajustez les paramètres selon vos besoins
6. **Dashboard** : Le dashboard Kubernetes est activé par défaut, désactivez-le si non nécessaire
7. **Accès** : Utilisez le port-forward pour accéder au dashboard en local

## 🔗 Liens utiles

- [Documentation EKS](https://docs.aws.amazon.com/eks/)
- [Documentation ECR](https://docs.aws.amazon.com/ecr/)
- [Documentation RDS](https://docs.aws.amazon.com/rds/)
- [Documentation Terraform AWS](https://registry.terraform.io/providers/hashicorp/aws/latest)
