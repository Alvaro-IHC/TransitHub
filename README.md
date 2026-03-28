# TransitHub

A Spring Boot-based transportation management system for managing drivers, ticket agents, vehicles, travel operations, etc.

## Overview

TransitHub is a REST API application that handles the complete management of transportation operations including:
- User management (drivers, ticket agents, etc)
- Vehicle management (minibuses and dump trucks)
- Travel operations (trips, tickets, and parcels)
- etc.

## Requirements

- Java 21
- PostgreSQL 12+
- Gradle 8.0+ (only for development)

## Quick Start

### Option 1: Using Gradle (Development)

```bash
# Clone and setup
git clone <repository-url>
cd TransitHub

# Build
./gradlew build

# Run
./gradlew bootRun
```

Application starts at: `http://localhost:8080`

### Option 2: Using JAR (Production)

```bash
# Build JAR
./gradlew build

# Run with command-line arguments
java -jar build/libs/TransitHub-0.0.1-SNAPSHOT.jar \
  --spring.datasource.url=jdbc:postgresql://localhost:5432/db_name \
  --spring.datasource.username=db_user \
  --spring.datasource.password=db_password
```

Replace `db_name`, `db_user`, and `db_password` with your actual database credentials.

### Option 3: Using JAR with Configuration File

```bash
# Create application.yaml in the same directory as JAR
java -jar build/libs/TransitHub-0.0.1-SNAPSHOT.jar
```

### Option 4: Using Docker Compose (Easiest All-in-One, not tested yet)

```bash
# Build and start with Docker
docker-compose up --build

# Application and database start automatically
# Access at http://localhost:8080
```

See [docker-compose.yml](docker-compose.yml) for details.

## API Documentation

Once running, access Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```

## Project Structure

```
src/main/java/com/aihc/transithub/
├── user/
│   ├── controllers/     # REST endpoints for users
│   ├── services/        # Business logic
│   ├── repositories/    # Data access layer
│   ├── entities/        # JPA entities
│   └── dtos/            # Data transfer objects
├── vehicle/
│   ├── controllers/     # REST endpoints for vehicles
│   ├── services/        # Vehicle business logic
│   ├── repositories/    # Vehicle data access
│   ├── entities/        # Vehicle JPA entities
│   └── dtos/            # Vehicle DTOs
├── travel/
│   ├── controllers/     # REST endpoints for travel
│   ├── services/        # Travel business logic
│   ├── repositories/    # Travel data access
│   ├── entities/        # Travel JPA entities
│   ├── enums/           # Status enumerations
│   └── dtos/            # Travel DTOs
└── config/              # Application configuration
└── ...
```

## Features

- **CRUD Operations** for users, drivers, ticket agents, vehicles, etc.
- **REST API** with Swagger/OpenAPI documentation
- **Database** with JPA/Hibernate and PostgreSQL
- **Validation** with custom error handling
- **DTO Pattern** for clean API contracts

## Database Configuration

### Local Development
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/th01
    username: aihc
    password: aihc123
```

### Via Environment Variables
```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://host:5432/database
export SPRING_DATASOURCE_USERNAME=username
export SPRING_DATASOURCE_PASSWORD=password
java -jar TransitHub-0.0.1-SNAPSHOT.jar
```

### Remote Database
```bash
java -jar TransitHub-0.0.1-SNAPSHOT.jar \
  --spring.datasource.url=jdbc:postgresql://remote-db-host:5432/transithub \
  --spring.datasource.username=db_user \
  --spring.datasource.password=secure_password
```

## Deployment Scripts

- `deploy.sh` - Deployment script for Linux/Mac
- `deploy.bat` - Deployment script for Windows
- `application.yaml.example` - Configuration template

