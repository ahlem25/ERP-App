# Infrastructure as Code - ERP Application

Ce répertoire contient la configuration Terraform pour déployer l'infrastructure AWS nécessaire à l'application ERP.

## 🏗️ Architecture

L'infrastructure comprend :
- **VPC** avec sous-réseaux publics et privés
- **EKS Cluster** pour orchestrer les conteneurs
- **ECR Repositories** pour stocker les images Docker
- **RDS MySQL** pour la base de données
- **Security Groups** pour la sécurité réseau
- **IAM Roles** pour les permissions

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

# Copier le fichier de variables
cp terraform.tfvars.example terraform.tfvars

# Modifier les variables selon vos besoins
vim terraform.tfvars
```

### 2. Initialisation Terraform

```bash
# Initialiser Terraform
terraform init

# Vérifier le plan de déploiement
terraform plan

# Déployer l'infrastructure
terraform apply
```

### 3. Configuration kubectl

```bash
# Mettre à jour la configuration kubectl
aws eks update-kubeconfig --region eu-west-3 --name erp-app-prod-cluster

# Vérifier la connexion
kubectl get nodes
```

## 📊 Services déployés

### ECR Repositories
- `erp-service-discovery-prod`
- `erp-config-prod`
- `erp-api-gateway-prod`
- `erp-user-service-prod`
- `erp-product-service-prod`
- `erp-inventory-service-prod`
- `erp-supplier-service-prod`
- `erp-order-service-prod`
- `erp-client-service-prod`
- `erp-payment-service-prod`
- `erp-billing-service-prod`
- `erp-sales-service-prod`
- `erp-dashboard-service-prod`
- `erp-scheduler-service-prod`

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
| `environment` | Environnement | `prod` |
| `project_name` | Nom du projet | `erp-app` |
| `vpc_cidr` | CIDR du VPC | `10.0.0.0/16` |
| `node_desired_size` | Nombre de nœuds | `2` |

### Tags

Toutes les ressources sont taguées avec :
- `Project` : erp-app
- `Environment` : prod
- `ManagedBy` : terraform

## 🔒 Sécurité

### Security Groups
- **EKS Cluster** : Communication interne uniquement
- **EKS Nodes** : Communication avec le cluster et internet
- **RDS** : Accès depuis les nœuds EKS uniquement

### IAM Roles
- **EKS Cluster** : Permissions pour gérer le cluster
- **EKS Nodes** : Permissions pour les nœuds worker

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

## 🗑️ Nettoyage

```bash
# Détruire l'infrastructure
terraform destroy

# Confirmer la destruction
yes
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
