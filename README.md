# Portfolio Website

A modern, full-stack portfolio website built with **Kotlin Spring Boot** (backend), **React + TypeScript** (frontend), deployed on **AWS** using **Terraform**, and automated with **Docker** and **GitHub Actions**.

## 🚀 Features

- **Backend**: RESTful API with Spring Boot, JPA, PostgreSQL
- **Frontend**: Modern React UI with TypeScript and Vite
- **Infrastructure**: AWS (S3, RDS, ECR, App Runner) managed by Terraform
- **CI/CD**: GitHub Actions for automated testing and deployment
- **Docker**: Full Docker Compose setup for local development
- **Structured Project**: Clean architecture with separate layers (controller, service, repository, entity)

## 📋 Prerequisites

- **Java 17+**
- **Node.js 20+**
- **Docker & Docker Compose**
- **Maven**
- **Terraform** (for AWS deployment)
- **AWS CLI** (for deployment)

## 🛠️ Project Structure

```
portfolio/
├── backend/                    # Spring Boot backend
│   └── src/
│       └── main/kotlin/com/example/portfolio/
│           ├── controller/     # REST controllers
│           ├── service/        # Business logic
│           ├── repository/     # Data access layer
│           ├── entity/         # JPA entities
│           ├── core/           # DTOs and models
│           └── util/           # Utility classes
├── frontend/                   # React frontend
│   └── src/
│       ├── components/         # React components
│       ├── App.tsx
│       └── App.css
├── infra/                      # Terraform infrastructure
│   └── main.tf
└── .github/workflows/          # CI/CD pipelines
```

## 🏃 Running Locally with Docker Compose

### Quick Start

```bash
cd portfolio
docker-compose up
```

This will start:
- **Backend**: http://localhost:8080
- **Frontend**: http://localhost:5173
- **PostgreSQL**: localhost:5432

### Initialize Data

Visit `http://localhost:8080/api/init` to populate the database with initial data.

## 💻 Running Without Docker

### Backend

```bash
cd portfolio/backend
mvn spring-boot:run
```

The API will be available at http://localhost:8080

### Frontend

```bash
cd portfolio/frontend
npm install
npm run dev
```

The UI will be available at http://localhost:5173

## 🧪 Running Tests

### Backend Tests

```bash
cd portfolio/backend
mvn test
```

### Frontend Tests

```bash
cd portfolio/frontend
npm test
```

## 🌐 Deployment

### Infrastructure Setup

```bash
cd portfolio/infra
terraform init
terraform apply
```

This creates:
- S3 bucket for frontend hosting
- RDS PostgreSQL database (db.t3.micro)
- ECR repository for Docker images
- App Runner service for backend

### Automated Deployment

Push to the `main` branch to trigger GitHub Actions workflow:

1. Builds and tests backend and frontend
2. Builds Docker image and pushes to ECR
3. Deploys infrastructure with Terraform
4. Updates App Runner service

### Required GitHub Secrets

Set these in your repository settings:
- `AWS_ACCESS_KEY_ID`
- `AWS_SECRET_ACCESS_KEY`
- `BACKEND_API_URL` (optional, for frontend build)

## 🔧 Configuration

### Backend Configuration

Edit `backend/src/main/resources/application.properties`:

```properties
server.port=8080
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/portfolio}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:postgres}
```

### Frontend Configuration

Edit `frontend/.env.development`:

```
VITE_API_URL=http://localhost:8080
```

## 📚 API Endpoints

- `GET /api/profile` - Get portfolio profile data
- `GET /api/init` - Initialize database with sample data

## 🎨 Customization

- Update profile data in `backend/src/main/kotlin/com/example/portfolio/service/PortfolioService.kt`
- Modify frontend design in `frontend/src/App.tsx` and `frontend/src/App.css`
- Adjust infrastructure in `infra/main.tf`

## 📄 License

This project is open source and available under the MIT License.

## 👤 Author

**Karim Ibrahim**
- Email: Karim.ibrahemm@gmail.com
- LinkedIn: [linkedin.com/in/karim-ibrahem](https://linkedin.com/in/karim-ibrahem)

---

Built with ❤️ using Kotlin, Spring Boot, React, and AWS
