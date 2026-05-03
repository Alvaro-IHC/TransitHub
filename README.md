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

## WebSocket Real-Time Events

TransitHub includes WebSocket support for real-time event broadcasting across all connected clients. This enables instant synchronization without requiring page refreshes.

### WebSocket Endpoints

#### 1. Travel Events: `/ws/travel`

Broadcasts real-time events for trips, tickets, and parcels.

**Connection URL:**
```
ws://localhost:8080/ws/travel
```

**Supported Events:**

| Entity | Event Type | Trigger | When | Obs |
|--------|-----------|---------|------|-----|
| **TICKET** | CREATED | `POST /api/tickets` | New ticket is created | Done |
| **TICKET** | UPDATED | `PUT /api/tickets/{id}` | Ticket is modified | TBD |
| **TICKET** | DELETED | `DELETE /api/tickets/{id}` | Ticket is deleted | TBD |
| **PARCEL** | CREATED | `POST /api/parcels` | New parcel is created | Done |
| **PARCEL** | UPDATED | `PUT /api/parcels/{id}` | Parcel is modified | TBD |
| **PARCEL** | DELETED | `DELETE /api/parcels/{id}` | Parcel is deleted | TBD |
| **TRIP** | CREATED | `POST /api/trips` | New trip is created | TBD |
| **TRIP** | UPDATED | `PUT /api/trips/{id}` | Trip status changes or modified | Done |
| **TRIP** | DELETED | `DELETE /api/trips/{id}` | Trip is deleted | TBD |

**Message Format:**

All WebSocket messages use this JSON structure:

```json
{
  "eventType": "CREATED|UPDATED|DELETED",
  "entityType": "TICKET|PARCEL|TRIP",
  "timestamp": 1714686887000
}
```

**Event Type Values:**
- `CREATED` - Entity has been created
- `UPDATED` - Entity has been modified
- `DELETED` - Entity has been removed

**Entity Type Values:**
- `TICKET` - A ticket/seat sale
- `PARCEL` - A package/shipment
- `TRIP` - A transportation journey

**Example Messages:**

```javascript
// Ticket Created
{
  "eventType": "CREATED",
  "entityType": "TICKET",
  "timestamp": 1714686887123
}

// Parcel Created
{
  "eventType": "CREATED",
  "entityType": "PARCEL",
  "timestamp": 1714686888456
}

// Trip Status Changed
{
  "eventType": "UPDATED",
  "entityType": "TRIP",
  "timestamp": 1714686889789
}
```

#### 2. Custom Events: `/ws/custom`

Reserved for custom event handling and future extensions.

**Connection URL:**
```
ws://localhost:8080/ws/custom
```

### Monitoring WebSocket Connections

#### Backend Logs

When clients connect/disconnect:

```
WebSocket client connected: 550e8400-e29b-41d4-a716-446655440000
WebSocket client disconnected: 550e8400-e29b-41d4-a716-446655440000
```

View in application logs when running `./gradlew bootRun`
