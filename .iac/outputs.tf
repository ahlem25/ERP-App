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
      name = aws_s3_bucket.uploads.bucket
      arn  = aws_s3_bucket.uploads.arn
      region = var.aws_region
    }
  }
}