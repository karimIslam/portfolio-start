terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = "us-east-1"
}

# S3 Bucket for Frontend
resource "aws_s3_bucket" "frontend_bucket" {
  bucket_prefix = "portfolio-frontend-"
}

resource "aws_s3_bucket_website_configuration" "frontend_bucket_website" {
  bucket = aws_s3_bucket.frontend_bucket.id

  index_document {
    suffix = "index.html"
  }

  error_document {
    key = "index.html"
  }
}

resource "aws_s3_bucket_public_access_block" "frontend_bucket_public_access" {
  bucket = aws_s3_bucket.frontend_bucket.id

  block_public_acls       = false
  block_public_policy     = false
  ignore_public_acls      = false
  restrict_public_buckets = false
}

resource "aws_s3_bucket_policy" "frontend_bucket_policy" {
  bucket = aws_s3_bucket.frontend_bucket.id
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Sid       = "PublicReadGetObject"
        Effect    = "Allow"
        Principal = "*"
        Action    = "s3:GetObject"
        Resource  = "${aws_s3_bucket.frontend_bucket.arn}/*"
      },
    ]
  })
  depends_on = [aws_s3_bucket_public_access_block.frontend_bucket_public_access]
}

# RDS Instance
resource "aws_db_instance" "default" {
  allocated_storage    = 20
  db_name              = "portfolio"
  engine               = "postgres"
  engine_version       = "16.3"
  instance_class       = "db.t3.micro"
  username             = "postgres"
  password             = "postgrespassword" # Change this in production!
  parameter_group_name = "default.postgres16"
  skip_final_snapshot  = true
  publicly_accessible  = true # For simplicity in this demo, but restrict via SG
}

# ECR Repository
resource "aws_ecr_repository" "backend_repo" {
  name                 = "portfolio-backend"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}

# IAM Role for App Runner
resource "aws_iam_role" "apprunner_role" {
  name = "apprunner-service-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "build.apprunner.amazonaws.com"
        }
      },
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "tasks.apprunner.amazonaws.com"
        }
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "apprunner_policy" {
  role       = aws_iam_role.apprunner_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSAppRunnerServicePolicyForECRAccess"
}

# App Runner Service
resource "aws_apprunner_service" "backend_service" {
  service_name = "portfolio-backend-service"

  source_configuration {
    authentication_configuration {
      access_role_arn = aws_iam_role.apprunner_role.arn
    }
    
    image_repository {
      image_configuration {
        port = "8080"
        runtime_environment_variables = {
          DB_URL      = "jdbc:postgresql://${aws_db_instance.default.endpoint}/${aws_db_instance.default.db_name}"
          DB_USERNAME = aws_db_instance.default.username
          DB_PASSWORD = aws_db_instance.default.password
        }
      }
      image_identifier      = "${aws_ecr_repository.backend_repo.repository_url}:latest"
      image_repository_type = "ECR"
    }
    
    auto_deployments_enabled = true
  }

  instance_configuration {
    cpu    = "1024"
    memory = "2048"
  }
}

# Output
output "website_url" {
  value = aws_s3_bucket_website_configuration.frontend_bucket_website.website_endpoint
}

output "db_endpoint" {
  value = aws_db_instance.default.endpoint
}

output "ecr_repository_url" {
  value = aws_ecr_repository.backend_repo.repository_url
}

output "backend_service_url" {
  value = aws_apprunner_service.backend_service.service_url
}
