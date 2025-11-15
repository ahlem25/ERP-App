# Configuration S3 pour le stockage des fichiers uploadés

# Bucket S3 principal pour les fichiers uploadés
resource "aws_s3_bucket" "uploads" {
  bucket = "${var.project_name}-uploads"

  tags = merge(var.tags, {
    Name        = "${var.project_name}-uploads"
    Purpose     = "file-uploads"
    Environment = "all"
  })
}

# Configuration de versioning pour le bucket
resource "aws_s3_bucket_versioning" "uploads" {
  bucket = aws_s3_bucket.uploads.id
  versioning_configuration {
    status = "Enabled"
  }
}

# Configuration de chiffrement pour le bucket
resource "aws_s3_bucket_server_side_encryption_configuration" "uploads" {
  bucket = aws_s3_bucket.uploads.id

  rule {
    apply_server_side_encryption_by_default {
      sse_algorithm = "AES256"
    }
    bucket_key_enabled = true
  }
}

# Configuration de blocage d'accès public
resource "aws_s3_bucket_public_access_block" "uploads" {
  bucket = aws_s3_bucket.uploads.id

  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}

# Politique de cycle de vie pour optimiser les coûts
resource "aws_s3_bucket_lifecycle_configuration" "uploads" {
  bucket = aws_s3_bucket.uploads.id

  rule {
    id     = "transition_to_ia"
    status = "Enabled"

    filter {
      prefix = ""
    }

    transition {
      days          = 30
      storage_class = "STANDARD_IA"
    }

    transition {
      days          = 90
      storage_class = "GLACIER"
    }

    transition {
      days          = 365
      storage_class = "DEEP_ARCHIVE"
    }
  }

  rule {
    id     = "delete_old_versions"
    status = "Enabled"

    filter {
      prefix = ""
    }

    noncurrent_version_transition {
      noncurrent_days = 30
      storage_class   = "STANDARD_IA"
    }

    noncurrent_version_transition {
      noncurrent_days = 90
      storage_class   = "GLACIER"
    }

    noncurrent_version_expiration {
      noncurrent_days = 2555 # 7 ans
    }
  }
}


# Politique IAM pour permettre aux services EKS d'accéder au bucket S3
resource "aws_iam_policy" "s3_uploads_access" {
  name        = "${var.project_name}-s3-uploads-access"
  description = "Politique pour accéder au bucket S3 des uploads"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "s3:GetObject",
          "s3:PutObject",
          "s3:DeleteObject",
          "s3:ListBucket"
        ]
        Resource = [
          aws_s3_bucket.uploads.arn,
          "${aws_s3_bucket.uploads.arn}/*"
        ]
      }
    ]
  })

  tags = merge(var.tags, {
    Name = "${var.project_name}-s3-uploads-access-policy"
  })
}

# Attachement de la politique S3 au rôle des nœuds EKS
resource "aws_iam_role_policy_attachment" "eks_nodes_s3_uploads" {
  policy_arn = aws_iam_policy.s3_uploads_access.arn
  role       = aws_iam_role.eks_nodes.name
}

