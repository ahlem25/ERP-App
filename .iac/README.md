# Infrastructure as Code - ERP Application

Ce répertoire contient la configuration Terraform pour déployer l'infrastructure AWS nécessaire à l'application ERP.

## 🏗️ Architecture

L'infrastructure comprend :
- **VPC** avec sous-réseaux publics et privés
- **EKS Cluster unique** pour orchestrer les conteneurs
- **Kubernetes Namespaces** pour chaque environnement (dev, test, prod)
- **ECR Repositories** pour stocker les images Docker (un par service et environnement)
- **RDS MySQL** pour la base de données
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

# 6. Destruction: Destruction des Ressources
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

### RDS MySQL
- **Instance** : `db.t3.micro`
- **Engine** : MySQL 8.0.35
- **Storage** : 20GB (max 100GB)
- **Backup** : 7 jours de rétention

## 🔧 Configuration

### Variables principales

| Variable | Description | Valeur par défaut |
|----------|-------------|-------------------|
| `aws_region` | Région AWS | `eu-west-3` |
| `environments` | Liste des environnements | `["dev", "test", "pprd", "prod"]` |
| `project_name` | Nom du projet | `erp-app` |
| `vpc_cidr` | CIDR du VPC | `10.0.0.0/16` |
| `node_desired_size` | Nombre de nœuds | `2` |

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

## 🔐 Gestion des Politiques IAM (policies.tf)

Le fichier `policies.tf` gère automatiquement les permissions et l'accès aux ressources AWS pour Jenkins et les services ERP.

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

### Coûts estimés
- **EKS Cluster** : ~$73/mois
- **EC2 Nodes (2x t3.medium)** : ~$60/mois
- **RDS MySQL (db.t3.micro)** : ~$15/mois
- **NAT Gateway** : ~$45/mois
- **Total estimé** : ~$193/mois

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

## 🔗 Liens utiles

- [Documentation EKS](https://docs.aws.amazon.com/eks/)
- [Documentation ECR](https://docs.aws.amazon.com/ecr/)
- [Documentation RDS](https://docs.aws.amazon.com/rds/)
- [Documentation Terraform AWS](https://registry.terraform.io/providers/hashicorp/aws/latest)
