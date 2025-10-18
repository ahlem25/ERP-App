# Configuration EKS - Voir eks-access.tf pour la gestion des accès

# 6. Politique pour les services ERP - Accès RDS
resource "aws_iam_policy" "erp_services_rds_access" {
  name        = "${var.project_name}-erp-services-rds-access"
  description = "Politique pour les services ERP pour accéder à RDS"
  path        = "/"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "rds:DescribeDBInstances",
          "rds:DescribeDBClusters",
          "rds:DescribeDBSubnetGroups",
          "rds:DescribeDBSecurityGroups"
        ]
        Resource = "*"
      }
    ]
  })

  tags = {
    Name    = "${var.project_name}-erp-services-rds-access"
    Project = var.project_name
  }
}

# 7. Politique pour les services ERP - Accès aux secrets
resource "aws_iam_policy" "erp_services_secrets_access" {
  name        = "${var.project_name}-erp-services-secrets-access"
  description = "Politique pour les services ERP pour accéder aux secrets AWS"
  path        = "/"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "secretsmanager:GetSecretValue",
          "secretsmanager:DescribeSecret"
        ]
        Resource = "arn:aws:secretsmanager:${var.aws_region}:${data.aws_caller_identity.current.account_id}:secret:${var.project_name}-*"
      }
    ]
  })

  tags = {
    Name    = "${var.project_name}-erp-services-secrets-access"
    Project = var.project_name
  }
}
