# Configuration EKS API et ConfigMap pour les accès

# Data source pour l'utilisateur Jenkins existant
data "aws_iam_user" "jenkins_user" {
  user_name = "jenkins-user"
}

# Data source pour l'identité du compte AWS (défini dans main.tf)

# Configuration du ConfigMap aws-auth pour les accès EKS
# Utilisation d'un null_resource pour gérer le ConfigMap existant
resource "null_resource" "configure_aws_auth" {
  depends_on = [aws_eks_cluster.main]

  provisioner "local-exec" {
    command = <<-EOT
      # Attendre que le cluster soit prêt
      aws eks wait cluster-active --name ${aws_eks_cluster.main.name} --region ${var.aws_region}
      
      # Mettre à jour kubeconfig
      aws eks update-kubeconfig --region ${var.aws_region} --name ${aws_eks_cluster.main.name}
      
      # Créer le ConfigMap aws-auth s'il n'existe pas
      if ! kubectl get configmap aws-auth -n kube-system > /dev/null 2>&1; then
        echo "📝 Création du ConfigMap aws-auth..."
        kubectl create configmap aws-auth -n kube-system
      fi
      
      # Appliquer la configuration aws-auth
      echo "📝 Configuration du ConfigMap aws-auth..."
      kubectl patch configmap/aws-auth -n kube-system --type merge -p '{
        "data": {
          "mapRoles": "- rolearn: ${aws_iam_role.eks_nodes.arn}\n  username: system:node:{{EC2PrivateDNSName}}\n  groups:\n  - system:bootstrappers\n  - system:nodes\n",
          "mapUsers": "- userarn: arn:aws:iam::${data.aws_caller_identity.current.account_id}:user/jenkins-user\n  username: jenkins-user\n  groups:\n  - system:masters\n- userarn: arn:aws:iam::${data.aws_caller_identity.current.account_id}:root\n  username: root\n  groups:\n  - system:masters\n"
        }
      }'
      
      echo "✅ ConfigMap aws-auth configuré avec succès"
    EOT
  }

  triggers = {
    cluster_name = aws_eks_cluster.main.name
    jenkins_user = data.aws_iam_user.jenkins_user.user_name
    account_id   = data.aws_caller_identity.current.account_id
  }
}

# Vérification de l'accès EKS
resource "null_resource" "verify_eks_access" {
  depends_on = [null_resource.configure_aws_auth]

  provisioner "local-exec" {
    command = <<-EOT
      echo "🔍 Vérification de l'accès EKS..."
      
      # Attendre que le cluster soit prêt
      aws eks wait cluster-active --name ${aws_eks_cluster.main.name} --region ${var.aws_region}
      
      # Mettre à jour kubeconfig
      aws eks update-kubeconfig --region ${var.aws_region} --name ${aws_eks_cluster.main.name}
      
      # Tester l'accès au cluster
      if kubectl get nodes > /dev/null 2>&1; then
        echo "✅ Accès EKS configuré avec succès"
        echo "📋 Informations du cluster :"
        echo "   - Nom du cluster: ${aws_eks_cluster.main.name}"
        echo "   - Région: ${var.aws_region}"
        echo "   - Version: ${aws_eks_cluster.main.version}"
        echo "   - Nombre de nœuds: $(kubectl get nodes --no-headers | wc -l)"
        echo ""
        echo "👥 Utilisateurs configurés :"
        echo "   - jenkins-user: arn:aws:iam::${data.aws_caller_identity.current.account_id}:user/jenkins-user"
        echo "   - root: arn:aws:iam::${data.aws_caller_identity.current.account_id}:root"
        echo "   - Groupes: system:masters"
      else
        echo "❌ Erreur: Impossible d'accéder au cluster EKS"
        exit 1
      fi
    EOT
  }

  triggers = {
    cluster_name = aws_eks_cluster.main.name
    aws_auth_configured = null_resource.configure_aws_auth.id
  }
}

# Output pour la configuration d'accès EKS
output "eks_access_configuration" {
  description = "Configuration d'accès EKS"
  value = {
    cluster = {
      name    = aws_eks_cluster.main.name
      region  = var.aws_region
      version = aws_eks_cluster.main.version
    }
    users = {
      jenkins = {
        user_arn = "arn:aws:iam::${data.aws_caller_identity.current.account_id}:user/jenkins-user"
        username = data.aws_iam_user.jenkins_user.user_name
        groups   = ["system:masters"]
      }
      root = {
        user_arn = "arn:aws:iam::${data.aws_caller_identity.current.account_id}:root"
        username = "root"
        groups   = ["system:masters"]
      }
    }
    configmap = {
      name      = "aws-auth"
      namespace = "kube-system"
    }
  }
}

# Output pour les commandes kubectl
output "kubectl_commands" {
  description = "Commandes kubectl utiles"
  value = {
    update_kubeconfig = "aws eks update-kubeconfig --region ${var.aws_region} --name ${aws_eks_cluster.main.name}"
    get_nodes         = "kubectl get nodes"
    get_namespaces    = "kubectl get namespaces"
    get_pods          = "kubectl get pods --all-namespaces"
    check_auth        = "kubectl get configmap aws-auth -n kube-system -o yaml"
  }
}
