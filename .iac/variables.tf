# Variables pour la configuration Terraform ERP

variable "aws_region" {
  description = "AWS region pour déployer les ressources"
  type        = string
  default     = "eu-west-3"
}

variable "environments" {
  description = "Liste des environnements pour créer des namespaces dans le cluster EKS"
  type        = list(string)
  default     = ["dev", "test", "pprd", "prod"]

  validation {
    condition     = alltrue([for env in var.environments : contains(["dev", "test", "pprd", "prod"], env)])
    error_message = "Les environnements doivent être dev, test, pprd ou prod."
  }
}

variable "project_name" {
  description = "Nom du projet"
  type        = string
  default     = "erp-app"
}

variable "tags" {
  description = "Tags communs à appliquer aux ressources"
  type        = map(string)
  default = {
    Project    = "erp-app"
    ManagedBy  = "terraform"
    Owner      = "devops-team"
    CostCenter = "engineering"
  }
}

variable "eks_version" {
  description = "Version du cluster EKS"
  type        = string
  default     = "1.29"
}

variable "node_instance_types" {
  description = "Types d'instances pour les nœuds EKS"
  type        = list(string)
  default     = ["t3.large"]
}

variable "node_capacity_type" {
  description = "Type de capacité pour les nœuds EKS (ON_DEMAND ou SPOT)"
  type        = string
  default     = "ON_DEMAND"

  validation {
    condition     = contains(["ON_DEMAND", "SPOT"], var.node_capacity_type)
    error_message = "Le type de capacité doit être ON_DEMAND ou SPOT."
  }
}

variable "node_desired_size" {
  description = "Nombre de nœuds souhaité"
  type        = number
  default     = 4
}

variable "node_max_size" {
  description = "Nombre maximum de nœuds"
  type        = number
  default     = 4
}

variable "node_min_size" {
  description = "Nombre minimum de nœuds"
  type        = number
  default     = 1
}

