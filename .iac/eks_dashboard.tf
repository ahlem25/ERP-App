# =============================================================================
# EKS Dashboard Configuration
# =============================================================================
# Configuration pour automatiser l'installation du dashboard Kubernetes
# dans le cluster EKS existant avec les charts Helm

# =============================================================================
# Data Sources pour le cluster EKS existant
# =============================================================================

data "aws_eks_cluster_auth" "cluster_auth" {
  name = aws_eks_cluster.main.name
}

# =============================================================================
# Provider Helm
# =============================================================================

provider "helm" {
  kubernetes = {
    host                   = aws_eks_cluster.main.endpoint
    cluster_ca_certificate = base64decode(aws_eks_cluster.main.certificate_authority[0].data)
    token                  = data.aws_eks_cluster_auth.cluster_auth.token
  }
}

# =============================================================================
# Variables pour le dashboard (avec des valeurs par défaut)
# =============================================================================

variable "dashboard_enabled" {
  description = "Activer l'installation du dashboard Kubernetes"
  type        = bool
  default     = true
}

variable "dashboard_namespace" {
  description = "Namespace Kubernetes pour le dashboard"
  type        = string
  default     = "kubernetes-dashboard"
}

variable "dashboard_chart_version" {
  description = "Version du chart Helm pour le dashboard Kubernetes"
  type        = string
  default     = "7.0.0"
}

variable "dashboard_service_type" {
  description = "Type de service pour le dashboard (ClusterIP, NodePort, LoadBalancer)"
  type        = string
  default     = "ClusterIP"
  validation {
    condition     = contains(["ClusterIP", "NodePort", "LoadBalancer"], var.dashboard_service_type)
    error_message = "Le type de service doit être ClusterIP, NodePort ou LoadBalancer."
  }
}

variable "dashboard_service_port" {
  description = "Port du service dashboard"
  type        = number
  default     = 443
}

variable "dashboard_ingress_enabled" {
  description = "Activer l'ingress pour le dashboard"
  type        = bool
  default     = false
}

variable "dashboard_ingress_class" {
  description = "Classe d'ingress pour le dashboard"
  type        = string
  default     = "nginx"
}

variable "dashboard_ingress_hosts" {
  description = "Hôtes pour l'ingress du dashboard"
  type = list(object({
    host  = string
    paths = list(string)
  }))
  default = []
}

variable "dashboard_ingress_tls" {
  description = "Configuration TLS pour l'ingress du dashboard"
  type = list(object({
    secret_name = string
    hosts       = list(string)
  }))
  default = []
}

variable "dashboard_ingress_annotations" {
  description = "Annotations pour l'ingress du dashboard"
  type        = map(string)
  default     = {}
}

variable "dashboard_resources" {
  description = "Limites de ressources pour le dashboard"
  type = object({
    limits = object({
      cpu    = string
      memory = string
    })
    requests = object({
      cpu    = string
      memory = string
    })
  })
  default = {
    limits = {
      cpu    = "200m"
      memory = "256Mi"
    }
    requests = {
      cpu    = "100m"
      memory = "128Mi"
    }
  }
}

variable "dashboard_metrics_enabled" {
  description = "Activer les métriques pour le dashboard"
  type        = bool
  default     = true
}

variable "dashboard_skip_login" {
  description = "Ignorer la page de connexion"
  type        = bool
  default     = false
}

variable "dashboard_items_per_page" {
  description = "Nombre d'éléments par page dans le dashboard"
  type        = number
  default     = 10
}

variable "create_dashboard_rbac" {
  description = "Créer les rôles RBAC pour le dashboard"
  type        = bool
  default     = true
}

variable "create_load_balancer" {
  description = "Créer un Load Balancer pour le dashboard"
  type        = bool
  default     = false
}

variable "load_balancer_annotations" {
  description = "Annotations pour le Load Balancer"
  type        = map(string)
  default     = {}
}

# =============================================================================
# Kubernetes Namespace pour le dashboard
# =============================================================================

resource "kubernetes_namespace" "kubernetes_dashboard" {
  count = var.dashboard_enabled ? 1 : 0

  metadata {
    name = var.dashboard_namespace
    labels = merge(var.tags, {
      name        = var.dashboard_namespace
      environment = "shared"
      component   = "kubernetes-dashboard"
    })
  }

  depends_on = [aws_eks_cluster.main]
}

# =============================================================================
# IAM Role pour Kubernetes Dashboard
# =============================================================================

resource "aws_iam_role" "kubernetes_dashboard_role" {
  count = var.dashboard_enabled ? 1 : 0

  name = "${var.project_name}-kubernetes-dashboard-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRoleWithWebIdentity"
        Effect = "Allow"
        Principal = {
          Federated = "arn:aws:iam::${data.aws_caller_identity.current.account_id}:oidc-provider/${replace(aws_eks_cluster.main.identity[0].oidc[0].issuer, "https://", "")}"
        }
        Condition = {
          StringEquals = {
            "${replace(aws_eks_cluster.main.identity[0].oidc[0].issuer, "https://", "")}:sub" = "system:serviceaccount:${var.dashboard_namespace}:kubernetes-dashboard"
            "${replace(aws_eks_cluster.main.identity[0].oidc[0].issuer, "https://", "")}:aud" = "sts.amazonaws.com"
          }
        }
      }
    ]
  })

  tags = merge(var.tags, {
    Name      = "${var.project_name}-kubernetes-dashboard-role"
    Component = "kubernetes-dashboard"
  })
}

# IAM Policy pour les permissions du dashboard
resource "aws_iam_role_policy" "kubernetes_dashboard_policy" {
  count = var.dashboard_enabled ? 1 : 0

  name = "${var.project_name}-kubernetes-dashboard-policy"
  role = aws_iam_role.kubernetes_dashboard_role[0].id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "eks:DescribeCluster",
          "eks:ListClusters",
          "eks:AccessKubernetesApi"
        ]
        Resource = "*"
      }
    ]
  })
}

# =============================================================================
# Kubernetes Dashboard ServiceAccount
# =============================================================================

resource "kubernetes_service_account" "kubernetes_dashboard" {
  count = var.dashboard_enabled ? 1 : 0

  metadata {
    name      = "kubernetes-dashboard"
    namespace = kubernetes_namespace.kubernetes_dashboard[0].metadata[0].name
    labels = {
      k8s-app = "kubernetes-dashboard"
    }
    annotations = {
      "eks.amazonaws.com/role-arn" = aws_iam_role.kubernetes_dashboard_role[0].arn
    }
  }
}

# =============================================================================
# Service Account Admin pour l'accès au dashboard
# =============================================================================

resource "kubernetes_service_account" "dashboard_admin" {
  count = var.dashboard_enabled ? 1 : 0

  metadata {
    name      = "dashboard-admin"
    namespace = kubernetes_namespace.kubernetes_dashboard[0].metadata[0].name
    labels = {
      app = "kubernetes-dashboard"
      component = "admin"
    }
  }
}

# =============================================================================
# ClusterRoleBinding pour l'admin du dashboard
# =============================================================================

resource "kubernetes_cluster_role_binding" "dashboard_admin" {
  count = var.dashboard_enabled ? 1 : 0

  metadata {
    name = "dashboard-admin"
  }

  role_ref {
    api_group = "rbac.authorization.k8s.io"
    kind      = "ClusterRole"
    name      = "cluster-admin"
  }

  subject {
    kind      = "ServiceAccount"
    name      = kubernetes_service_account.dashboard_admin[0].metadata[0].name
    namespace = kubernetes_namespace.kubernetes_dashboard[0].metadata[0].name
  }
}

# =============================================================================
# Secret pour le token d'authentification
# =============================================================================

resource "kubernetes_secret" "dashboard_admin_token" {
  count = var.dashboard_enabled ? 1 : 0

  metadata {
    name      = "dashboard-admin-token"
    namespace = kubernetes_namespace.kubernetes_dashboard[0].metadata[0].name
    annotations = {
      "kubernetes.io/service-account.name" = kubernetes_service_account.dashboard_admin[0].metadata[0].name
    }
  }

  type = "kubernetes.io/service-account-token"

  depends_on = [kubernetes_service_account.dashboard_admin]
}

# =============================================================================
# Helm Repository et Release
# =============================================================================

resource "helm_release" "kubernetes_dashboard" {
  count = var.dashboard_enabled ? 1 : 0

  name       = "kubernetes-dashboard"
  repository = "https://kubernetes.github.io/dashboard/"
  chart      = "kubernetes-dashboard"
  version    = var.dashboard_chart_version
  namespace  = kubernetes_namespace.kubernetes_dashboard[0].metadata[0].name

  values = [
    yamlencode({
      serviceAccount = {
        create = false
        name   = kubernetes_service_account.kubernetes_dashboard[0].metadata[0].name
      }
      
      service = {
        type = var.dashboard_service_type
        port = var.dashboard_service_port
        annotations = {}
      }
      
      ingress = {
        enabled = var.dashboard_ingress_enabled
        className = var.dashboard_ingress_class
        annotations = var.dashboard_ingress_annotations
        hosts = var.dashboard_ingress_hosts
        tls = var.dashboard_ingress_tls
      }
      
      resources = var.dashboard_resources
      
      metrics = {
        enabled = var.dashboard_metrics_enabled
        serviceMonitor = {
          enabled = var.dashboard_metrics_enabled
        }
      }
      
      settings = {
        clusterName = aws_eks_cluster.main.name
        itemsPerPage = var.dashboard_items_per_page
        logsAutoRefreshTimeInterval = 5
        resourceAutoRefreshTimeInterval = 5
        skipLoginPage = var.dashboard_skip_login
        disableAccessDeniedNotifications = false
      }
    })
  ]

  depends_on = [
    kubernetes_namespace.kubernetes_dashboard,
    kubernetes_service_account.kubernetes_dashboard,
    aws_iam_role.kubernetes_dashboard_role
  ]
}

# =============================================================================
# ClusterRoleBinding pour l'accès au dashboard
# =============================================================================

resource "kubernetes_cluster_role_binding" "kubernetes_dashboard" {
  count = var.dashboard_enabled && var.create_dashboard_rbac ? 1 : 0
  
  metadata {
    name = "kubernetes-dashboard"
  }
  
  role_ref {
    api_group = "rbac.authorization.k8s.io"
    kind      = "ClusterRole"
    name      = "cluster-admin"
  }
  
  subject {
    kind      = "ServiceAccount"
    name      = kubernetes_service_account.kubernetes_dashboard[0].metadata[0].name
    namespace = kubernetes_namespace.kubernetes_dashboard[0].metadata[0].name
  }
}

# =============================================================================
# Service Load Balancer (optionnel)
# =============================================================================

resource "kubernetes_service" "kubernetes_dashboard_lb" {
  count = var.dashboard_enabled && var.create_load_balancer ? 1 : 0
  
  metadata {
    name      = "kubernetes-dashboard-lb"
    namespace = kubernetes_namespace.kubernetes_dashboard[0].metadata[0].name
    annotations = var.load_balancer_annotations
  }
  
  spec {
    type = "LoadBalancer"
    selector = {
      "k8s-app" = "kubernetes-dashboard"
    }
    port {
      port        = var.dashboard_service_port
      target_port = 9090
      protocol    = "TCP"
    }
  }
  
  depends_on = [helm_release.kubernetes_dashboard]
}
