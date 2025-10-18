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
    Project     = "erp-app"
    ManagedBy   = "terraform"
    Owner       = "devops-team"
    CostCenter  = "engineering"
  }
}

variable "eks_version" {
  description = "Version du cluster EKS"
  type        = string
  default     = "1.28"
}

