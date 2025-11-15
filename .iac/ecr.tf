# Configuration ECR pour les repositories des services ERP

# Liste des services ERP
locals {
  erp_services = [
    "erp-service-discovery",
    "erp-config",
    "erp-api-gateway",
    "erp-user-service",
    "erp-product-service",
    "erp-inventory-service",
    "erp-supplier-service",
    "erp-order-service",
    "erp-client-service",
    "erp-payment-service",
    "erp-billing-service",
    "erp-sales-service",
    "erp-dashboard-service",
    "erp-scheduler-service",
    "erp-ui-service"
  ]
}

# ECR Repositories for each service (stages and releases)
resource "aws_ecr_repository" "erp_services" {
  for_each = toset(flatten([
    for service in local.erp_services : [
      for repo_type in ["stages", "releases"] : "${service}-${repo_type}"
    ]
  ]))

  name                 = each.key
  image_tag_mutability = "MUTABLE"
  force_delete         = true

  image_scanning_configuration {
    scan_on_push = true
  }

  tags = merge(var.tags, {
    Name    = each.key
    Type    = split("-", each.key)[1] # Extract repo type (stages/releases)
    Purpose = "ecr-repository"
  })
}

# Politique de cycle de vie pour les images ECR
resource "aws_ecr_lifecycle_policy" "erp_services" {
  for_each = aws_ecr_repository.erp_services

  repository = each.value.name

  policy = jsonencode({
    rules = [
      {
        rulePriority = 1
        description  = "Keep last 10 images"
        selection = {
          tagStatus     = "tagged"
          tagPrefixList = ["v"]
          countType     = "imageCountMoreThan"
          countNumber   = 10
        }
        action = {
          type = "expire"
        }
      },
      {
        rulePriority = 2
        description  = "Delete untagged images older than 1 day"
        selection = {
          tagStatus   = "untagged"
          countType   = "sinceImagePushed"
          countUnit   = "days"
          countNumber = 1
        }
        action = {
          type = "expire"
        }
      }
    ]
  })
}
