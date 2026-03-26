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
- Gradle 8.0+

## Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd TransitHub
```

### 2. Configure Database
Update `src/main/resources/application.yaml` with your PostgreSQL connection details.

### 3. Build the Project
```bash
./gradlew build
```

### 4. Run the Application
```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

## API Documentation

Once the application is running, access the Swagger UI at:
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
│   └── entities/        # Vehicle entities
│   └── ...
├── travel/
│   └── entities/        # Travel-related entities
│   └── ...
├── config/              # Application configuration
└── ...
```

## Features

- **CRUD Operations** for users, drivers, ticket agents, and vehicles
- **REST API** with Swagger documentation
- **Database** with JPA/Hibernate and PostgreSQL
- **Validation** with custom error handling
- **DTO Pattern** for clean API contracts


