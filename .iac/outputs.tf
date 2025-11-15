# Outputs pour la configuration Terraform ERP

output "vpc_id" {
  description = "ID du VPC"
  value       = aws_vpc.main.id
}

output "vpc_cidr_block" {
  description = "CIDR block du VPC"
  value       = aws_vpc.main.cidr_block
}

output "public_subnet_ids" {
  description = "IDs des sous-réseaux publics"
  value       = aws_subnet.public[*].id
}

output "private_subnet_ids" {
  description = "IDs des sous-réseaux privés"
  value       = aws_subnet.private[*].id
}

output "internet_gateway_id" {
  description = "ID de l'Internet Gateway"
  value       = aws_internet_gateway.main.id
}

# output "nat_gateway_ids" {
#   description = "IDs des NAT Gateways"
#   value       = aws_nat_gateway.main[*].id
# }

# EKS Outputs
output "cluster_name" {
  description = "Nom du cluster EKS"
  value       = aws_eks_cluster.main.name
}

output "cluster_id" {
  description = "ID du cluster EKS"
  value       = aws_eks_cluster.main.id
}

output "cluster_arn" {
  description = "ARN du cluster EKS"
  value       = aws_eks_cluster.main.arn
}

output "cluster_endpoint" {
  description = "Endpoint du cluster EKS"
  value       = aws_eks_cluster.main.endpoint
}

output "cluster_version" {
  description = "Version du cluster EKS"
  value       = aws_eks_cluster.main.version
}

output "cluster_security_group_id" {
  description = "ID du security group du cluster EKS"
  value       = aws_eks_cluster.main.vpc_config[0].cluster_security_group_id
}

output "cluster_certificate_authority_data" {
  description = "Données du certificat d'autorité du cluster"
  value       = aws_eks_cluster.main.certificate_authority[0].data
}

output "node_group_arn" {
  description = "ARN du groupe de nœuds EKS"
  value       = aws_eks_node_group.main.arn
}

output "node_group_status" {
  description = "Statut du groupe de nœuds EKS"
  value       = aws_eks_node_group.main.status
}

# ECR Outputs
output "ecr_repository_urls" {
  description = "URLs des repositories ECR"
  value = {
    for key, repo in aws_ecr_repository.erp_services : key => repo.repository_url
  }
}

output "ecr_repository_arns" {
  description = "ARNs des repositories ECR"
  value = {
    for key, repo in aws_ecr_repository.erp_services : key => repo.arn
  }
}

# RDS Outputs par environnement
output "environment_databases" {
  description = "Instances RDS par environnement"
  value = {
    for env in var.environments : env => {
      identifier    = "erp-database-${env}"
      database_name = "erp_${env}"
      username      = "admin"
      password      = "erp_password_2024"
      endpoint      = aws_db_instance.mysql[env].endpoint
      port          = aws_db_instance.mysql[env].port
      jdbc_url      = "jdbc:mysql://${aws_db_instance.mysql[env].endpoint}:${aws_db_instance.mysql[env].port}/erp_${env}?useSSL=false"
    }
  }
  sensitive = true
}

# Security Groups
output "eks_cluster_security_group_id" {
  description = "ID du security group du cluster EKS"
  value       = aws_security_group.eks_cluster.id
}

output "eks_nodes_security_group_id" {
  description = "ID du security group des nœuds EKS"
  value       = aws_security_group.eks_nodes.id
}

output "rds_security_group_id" {
  description = "ID du security group RDS"
  value       = aws_security_group.rds.id
}

# IAM Roles
output "eks_cluster_role_arn" {
  description = "ARN du rôle IAM du cluster EKS"
  value       = aws_iam_role.eks_cluster.arn
}

output "eks_nodes_role_arn" {
  description = "ARN du rôle IAM des nœuds EKS"
  value       = aws_iam_role.eks_nodes.arn
}

# Account Information
output "aws_account_id" {
  description = "ID du compte AWS"
  value       = data.aws_caller_identity.current.account_id
}

output "aws_region" {
  description = "Région AWS utilisée"
  value       = var.aws_region
}

# Configuration pour kubectl
output "kubectl_config" {
  description = "Configuration kubectl pour se connecter au cluster"
  value = {
    cluster_name = aws_eks_cluster.main.name
    region       = var.aws_region
    endpoint     = aws_eks_cluster.main.endpoint
  }
}

# S3 Outputs
output "s3_uploads_bucket_name" {
  description = "Nom du bucket S3 pour les fichiers uploadés"
  value       = aws_s3_bucket.uploads.bucket
}

output "s3_uploads_bucket_arn" {
  description = "ARN du bucket S3 pour les fichiers uploadés"
  value       = aws_s3_bucket.uploads.arn
}

output "s3_uploads_bucket_domain_name" {
  description = "Nom de domaine du bucket S3 pour les fichiers uploadés"
  value       = aws_s3_bucket.uploads.bucket_domain_name
}

output "s3_uploads_bucket_regional_domain_name" {
  description = "Nom de domaine régional du bucket S3 pour les fichiers uploadés"
  value       = aws_s3_bucket.uploads.bucket_regional_domain_name
}


# Configuration S3 pour l'application
output "s3_configuration" {
  description = "Configuration S3 pour l'application backend"
  value = {
    uploads_bucket = {
      name   = aws_s3_bucket.uploads.bucket
      arn    = aws_s3_bucket.uploads.arn
      region = var.aws_region
    }
  }
}

# =============================================================================
# Kubernetes Dashboard Outputs
# =============================================================================

output "dashboard_enabled" {
  description = "Dashboard Kubernetes activé"
  value       = var.dashboard_enabled
}

output "dashboard_namespace" {
  description = "Namespace du dashboard Kubernetes"
  value       = var.dashboard_enabled ? kubernetes_namespace.kubernetes_dashboard[0].metadata[0].name : null
}

output "dashboard_service_account" {
  description = "ServiceAccount du dashboard"
  value       = var.dashboard_enabled ? kubernetes_service_account.kubernetes_dashboard[0].metadata[0].name : null
}

output "dashboard_iam_role_arn" {
  description = "ARN du rôle IAM pour le dashboard"
  value       = var.dashboard_enabled ? aws_iam_role.kubernetes_dashboard_role[0].arn : null
}

output "dashboard_service_name" {
  description = "Nom du service du dashboard"
  value       = var.dashboard_enabled ? helm_release.kubernetes_dashboard[0].name : null
}

output "dashboard_service_port" {
  description = "Port du service du dashboard"
  value       = var.dashboard_enabled ? var.dashboard_service_port : null
}

output "dashboard_service_type" {
  description = "Type du service du dashboard"
  value       = var.dashboard_enabled ? var.dashboard_service_type : null
}

output "dashboard_ingress_enabled" {
  description = "Ingress activé pour le dashboard"
  value       = var.dashboard_enabled ? var.dashboard_ingress_enabled : null
}

output "dashboard_ingress_hosts" {
  description = "Hôtes de l'ingress du dashboard"
  value       = var.dashboard_enabled ? var.dashboard_ingress_hosts : []
}

output "dashboard_load_balancer_enabled" {
  description = "Load Balancer activé pour le dashboard"
  value       = var.dashboard_enabled ? var.create_load_balancer : null
}

output "dashboard_load_balancer_dns" {
  description = "DNS du Load Balancer du dashboard"
  value       = var.dashboard_enabled && var.create_load_balancer ? kubernetes_service.kubernetes_dashboard_lb[0].status[0].load_balancer[0].ingress[0].hostname : null
}

output "dashboard_load_balancer_ip" {
  description = "IP du Load Balancer du dashboard"
  value       = var.dashboard_enabled && var.create_load_balancer ? kubernetes_service.kubernetes_dashboard_lb[0].status[0].load_balancer[0].ingress[0].ip : null
}

# Commandes utiles pour le dashboard
output "dashboard_kubectl_commands" {
  description = "Commandes kubectl utiles pour accéder au dashboard"
  value = var.dashboard_enabled ? {
    get_dashboard_pods     = "kubectl get pods -n ${kubernetes_namespace.kubernetes_dashboard[0].metadata[0].name}"
    get_dashboard_services = "kubectl get services -n ${kubernetes_namespace.kubernetes_dashboard[0].metadata[0].name}"
    get_dashboard_ingress  = "kubectl get ingress -n ${kubernetes_namespace.kubernetes_dashboard[0].metadata[0].name}"
    port_forward           = "kubectl port-forward -n ${kubernetes_namespace.kubernetes_dashboard[0].metadata[0].name} service/kubernetes-dashboard 8443:443"
    get_dashboard_token    = "kubectl -n ${kubernetes_namespace.kubernetes_dashboard[0].metadata[0].name} get secret $(kubectl -n ${kubernetes_namespace.kubernetes_dashboard[0].metadata[0].name} get sa kubernetes-dashboard -o jsonpath='{.secrets[0].name}') -o go-template='{{.data.token | base64decode}}'"
  } : {}
}

# URLs d'accès au dashboard
output "dashboard_access_urls" {
  description = "URLs d'accès au dashboard"
  value = var.dashboard_enabled ? {
    port_forward  = "https://localhost:8443"
    load_balancer = var.create_load_balancer ? "https://${kubernetes_service.kubernetes_dashboard_lb[0].status[0].load_balancer[0].ingress[0].hostname}" : null
    ingress       = var.dashboard_ingress_enabled ? "https://${var.dashboard_ingress_hosts[0].host}" : null
  } : {}
}

# Configuration du dashboard
output "dashboard_configuration" {
  description = "Configuration du dashboard"
  value = var.dashboard_enabled ? {
    namespace             = kubernetes_namespace.kubernetes_dashboard[0].metadata[0].name
    service_account       = kubernetes_service_account.kubernetes_dashboard[0].metadata[0].name
    admin_service_account = kubernetes_service_account.dashboard_admin[0].metadata[0].name
    iam_role              = aws_iam_role.kubernetes_dashboard_role[0].arn
    chart_version         = var.dashboard_chart_version
    service_type          = var.dashboard_service_type
    service_port          = var.dashboard_service_port
    ingress_enabled       = var.dashboard_ingress_enabled
    metrics_enabled       = var.dashboard_metrics_enabled
    rbac_enabled          = var.create_dashboard_rbac
  } : null
}

# Token d'authentification pour le dashboard
output "dashboard_admin_token" {
  description = "Token d'authentification pour le dashboard (utilisez la commande kubectl pour le récupérer)"
  value = var.dashboard_enabled ? {
    secret_name     = kubernetes_secret.dashboard_admin_token[0].metadata[0].name
    namespace       = kubernetes_namespace.kubernetes_dashboard[0].metadata[0].name
    service_account = kubernetes_service_account.dashboard_admin[0].metadata[0].name
    command         = "kubectl -n ${kubernetes_namespace.kubernetes_dashboard[0].metadata[0].name} get secret ${kubernetes_secret.dashboard_admin_token[0].metadata[0].name} -o jsonpath='{.data.token}' | base64 -d"
  } : null
  sensitive = true
}