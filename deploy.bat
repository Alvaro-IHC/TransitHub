@echo off
REM TransitHub Application Deployment Script for Windows
REM This script sets up environment variables and runs the JAR

setlocal enabledelayedexpansion

REM Configuration
set JAR_FILE=TransitHub-0.0.1-SNAPSHOT.jar
set PORT=8080

REM Database Configuration
if not defined DB_HOST set DB_HOST=localhost
if not defined DB_PORT set DB_PORT=5432
if not defined DB_NAME set DB_NAME=transithub
if not defined DB_USER set DB_USER=postgres
if not defined DB_PASSWORD set DB_PASSWORD=your_password

REM Java Memory Configuration
if not defined JAVA_HEAP_MIN set JAVA_HEAP_MIN=256m
if not defined JAVA_HEAP_MAX set JAVA_HEAP_MAX=512m

REM Check if JAR exists
if not exist "%JAR_FILE%" (
    echo Error: %JAR_FILE% not found!
    echo Please ensure the JAR file is in the current directory.
    pause
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>nul
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    pause
    exit /b 1
)

REM Print configuration
echo ========================================
echo Starting TransitHub
echo ========================================
echo JAR File: %JAR_FILE%
echo Database: postgresql://%DB_HOST%:%DB_PORT%/%DB_NAME%
echo Java Memory: -Xms%JAVA_HEAP_MIN% -Xmx%JAVA_HEAP_MAX%
echo Server Port: %PORT%
echo ========================================

REM Set environment variables
set SPRING_DATASOURCE_URL=jdbc:postgresql://%DB_HOST%:%DB_PORT%/%DB_NAME%
set SPRING_DATASOURCE_USERNAME=%DB_USER%
set SPRING_DATASOURCE_PASSWORD=%DB_PASSWORD%
set SERVER_PORT=%PORT%

REM Run the JAR
java -Xms%JAVA_HEAP_MIN% -Xmx%JAVA_HEAP_MAX% ^
     -Dspring.config.location=optional:application.yaml,optional:application.properties ^
     -jar "%JAR_FILE%"

pause
