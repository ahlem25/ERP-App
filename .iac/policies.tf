# Configuration EKS pour Jenkins - Accès au cluster

# 1. Data source pour l'utilisateur Jenkins existant
data "aws_iam_user" "jenkins_user" {
  user_name = "jenkins-user"
}

# 2. Ajouter l'utilisateur Jenkins au configmap aws-auth (via null_resource)

# 3. Utiliser un patch pour ajouter l'utilisateur
resource "null_resource" "add_jenkins_to_aws_auth" {
  depends_on = [aws_eks_cluster.main]

  provisioner "local-exec" {
    command = <<-EOT
      # Attendre que le cluster soit prêt
      aws eks wait cluster-active --name ${aws_eks_cluster.main.name} --region ${var.aws_region}
      
      # Mettre à jour kubeconfig
      aws eks update-kubeconfig --region ${var.aws_region} --name ${aws_eks_cluster.main.name}
      
      # Vérifier si l'utilisateur existe déjà
      if ! kubectl get configmap aws-auth -n kube-system -o yaml | grep -q "jenkins-user"; then
        # Ajouter l'utilisateur Jenkins au configmap
        kubectl patch configmap/aws-auth -n kube-system --type merge -p '{
          "data": {
            "mapUsers": "- userarn: arn:aws:iam::${data.aws_caller_identity.current.account_id}:user/jenkins-user\n  username: jenkins-user\n  groups:\n  - system:masters\n"
          }
        }'
        echo "✅ Utilisateur jenkins-user ajouté au configmap aws-auth"
      else
        echo "ℹ️ Utilisateur jenkins-user déjà présent dans aws-auth"
      fi
    EOT
  }

  triggers = {
    cluster_name = aws_eks_cluster.main.name
    user_arn     = "arn:aws:iam::${data.aws_caller_identity.current.account_id}:user/jenkins-user"
  }
}

# 4. Vérification de l'accès EKS
resource "null_resource" "verify_jenkins_eks_access" {
  depends_on = [null_resource.add_jenkins_to_aws_auth]

  provisioner "local-exec" {
    command = <<-EOT
      echo "🔍 Vérification de l'accès EKS pour jenkins-user..."
      
      # Tester l'accès au cluster
      if kubectl get nodes > /dev/null 2>&1; then
        echo "✅ Accès EKS configuré avec succès"
        echo "📋 Nœuds disponibles :"
        kubectl get nodes --no-headers | wc -l | xargs echo "   - Nombre de nœuds:"
      else
        echo "❌ Erreur: Impossible d'accéder au cluster EKS"
        exit 1
      fi
    EOT
  }
}

# 5. Output pour confirmer la configuration
output "jenkins_eks_access" {
  description = "Configuration d'accès EKS pour Jenkins"
  value = {
    user_arn  = "arn:aws:iam::${data.aws_caller_identity.current.account_id}:user/jenkins-user"
    username  = data.aws_iam_user.jenkins_user.user_name
    groups    = ["system:masters"]
    cluster   = aws_eks_cluster.main.name
    region    = var.aws_region
  }
}

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
