# Variables pour la configuration Terraform ERP

variable "aws_region" {
  description = "AWS region pour déployer les ressources"
  type        = string
  default     = "eu-west-3"
}

variable "environment" {
  description = "Environnement de déploiement (dev, test, prod)"
  type        = string
  default     = "prod"
  
  validation {
    condition     = contains(["dev", "test", "prod"], var.environment)
    error_message = "L'environnement doit être dev, test ou prod."
  }
}

variable "project_name" {
  description = "Nom du projet"
  type        = string
  default     = "erp-app"
}

variable "vpc_cidr" {
  description = "CIDR block pour le VPC"
  type        = string
  default     = "10.0.0.0/16"
}

variable "availability_zones" {
  description = "Zones de disponibilité à utiliser"
  type        = list(string)
  default     = ["eu-west-3a", "eu-west-3b", "eu-west-3c"]
}

variable "eks_cluster_version" {
  description = "Version du cluster EKS"
  type        = string
  default     = "1.28"
}

variable "node_instance_types" {
  description = "Types d'instances pour les nœuds EKS"
  type        = list(string)
  default     = ["t3.medium"]
}

variable "node_desired_size" {
  description = "Nombre de nœuds désirés"
  type        = number
  default     = 2
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

variable "rds_instance_class" {
  description = "Classe d'instance RDS"
  type        = string
  default     = "db.t3.micro"
}

variable "rds_allocated_storage" {
  description = "Stockage alloué pour RDS (GB)"
  type        = number
  default     = 20
}

variable "rds_max_allocated_storage" {
  description = "Stockage maximum alloué pour RDS (GB)"
  type        = number
  default     = 100
}

variable "rds_backup_retention_period" {
  description = "Période de rétention des sauvegardes RDS (jours)"
  type        = number
  default     = 7
}

variable "tags" {
  description = "Tags communs à appliquer aux ressources"
  type        = map(string)
  default = {
    Project     = "erp-app"
    Environment = "prod"
    ManagedBy   = "terraform"
  }
}
