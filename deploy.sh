#!/bin/bash

# TransitHub Application Deployment Script for Linux/Mac
# This script sets up environment variables and runs the JAR

# Configuration
JAR_FILE="TransitHub-0.0.1-SNAPSHOT.jar"
PORT=8080

# Database Configuration
DB_HOST=${DB_HOST:-localhost}
DB_PORT=${DB_PORT:-5432}
DB_NAME=${DB_NAME:-transithub}
DB_USER=${DB_USER:-postgres}
DB_PASSWORD=${DB_PASSWORD:-your_password}

# Java Memory Configuration
JAVA_HEAP_MIN=${JAVA_HEAP_MIN:-256m}
JAVA_HEAP_MAX=${JAVA_HEAP_MAX:-512m}

# Check if JAR exists
if [ ! -f "$JAR_FILE" ]; then
    echo "Error: $JAR_FILE not found!"
    echo "Please ensure the JAR file is in the current directory."
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

# Print configuration
echo "========================================"
echo "Starting TransitHub"
echo "========================================"
echo "JAR File: $JAR_FILE"
echo "Database: postgresql://$DB_HOST:$DB_PORT/$DB_NAME"
echo "Java Memory: -Xms$JAVA_HEAP_MIN -Xmx$JAVA_HEAP_MAX"
echo "Server Port: $PORT"
echo "========================================"

# Set environment variables
export SPRING_DATASOURCE_URL="jdbc:postgresql://$DB_HOST:$DB_PORT/$DB_NAME"
export SPRING_DATASOURCE_USERNAME="$DB_USER"
export SPRING_DATASOURCE_PASSWORD="$DB_PASSWORD"
export SERVER_PORT="$PORT"

# Run the JAR
java -Xms$JAVA_HEAP_MIN -Xmx$JAVA_HEAP_MAX \
     -Dspring.config.location=optional:application.yaml,optional:application.properties \
     -jar "$JAR_FILE"
