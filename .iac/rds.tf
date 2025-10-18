# Configuration RDS MySQL pour l'application ERP
# COMMENTÉ TEMPORAIREMENT - Décommenter quand nécessaire

# # Subnet Group pour RDS
# resource "aws_db_subnet_group" "main" {
#   name       = "${var.project_name}-db-subnet-group"
#   subnet_ids = aws_subnet.private[*].id

#   tags = merge(var.tags, {
#     Name    = "${var.project_name}-db-subnet-group"
#     Purpose = "rds-subnet-group"
#   })
# }

# # Security Group pour RDS
# resource "aws_security_group" "rds" {
#   name_prefix = "${var.project_name}-rds-"
#   vpc_id      = aws_vpc.main.id

#   ingress {
#     from_port       = 3306
#     to_port         = 3306
#     protocol        = "tcp"
#     security_groups = [aws_security_group.eks_nodes.id]
#   }

#   egress {
#     from_port   = 0
#     to_port     = 0
#     protocol    = "-1"
#     cidr_blocks = ["0.0.0.0/0"]
#   }

#   tags = merge(var.tags, {
#     Name    = "${var.project_name}-rds-sg"
#     Purpose = "rds-security-group"
#   })
# }

# # Instance RDS MySQL
# resource "aws_db_instance" "mysql" {
#   identifier = "${var.project_name}-mysql"

#   engine         = "mysql"
#   engine_version = "8.0.35"  # Version stable supportée dans eu-west-3
#   instance_class = "db.t3.micro"

#   allocated_storage     = 20
#   max_allocated_storage = 100
#   storage_type          = "gp2"
#   storage_encrypted     = true

#   db_name  = "erp_db"
#   username = "admin"
#   password = "erp_password_2024"

#   vpc_security_group_ids = [aws_security_group.rds.id]
#   db_subnet_group_name   = aws_db_subnet_group.main.name

#   backup_retention_period = 7
#   backup_window          = "03:00-04:00"
#   maintenance_window     = "sun:04:00-sun:05:00"

#   skip_final_snapshot = true
#   deletion_protection = false

#   tags = merge(var.tags, {
#     Name    = "${var.project_name}-mysql"
#     Purpose = "rds-mysql-instance"
#   })
# }
