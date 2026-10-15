# University Project

This project is a Spring Boot microservices-based university application that separates student and address management into independent services. It includes service discovery, API routing, centralized configuration, and resilience patterns.

## Overview

The system contains the following services:

- Student Service: manages student data and fetches address details for each student.
- Address Service: manages address records.
- API Gateway: routes requests to backend services.
- Eureka Server: service registry and discovery.
- Config Server: centralizes external configuration for services.

## Architecture

The application follows a basic microservice pattern:

- Student and Address services are independent Spring Boot applications.
- Eureka Server registers all services.
- API Gateway exposes the unified entry point for client requests.
- Spring Cloud Config provides externalized configuration.
- Feign Client + Resilience4j are used for inter-service communication and fallback handling.

## Project Structure

```text
University-Project/
├── address-service/
│   ├── src/main/java
│   ├── src/main/resources
│   └── pom.xml
├── student-service/
│   ├── src/main/java
│   ├── src/main/resources
│   └── pom.xml
├── api-gateway/
│   ├── src/main/java
│   ├── src/main/resources
│   └── pom.xml
├── config-server/
│   ├── src/main/java
│   ├── src/main/resources
│   └── pom.xml
├── eureka-server/
│   ├── src/main/java
│   ├── src/main/resources
│   └── pom.xml
├── .gitignore
└── README.md
```

## Technologies Used

- Java 17
- Spring Boot 3/4 based project setup
- Spring Cloud
- Spring Cloud Gateway
- Netflix Eureka
- Spring Cloud Config
- Spring Data JPA
- MySQL
- OpenFeign
- Resilience4j
- Zipkin / tracing support

## Prerequisites

Before running the project, make sure you have:

- Java 17 or later
- Maven
- MySQL database server
- Git

## Database Setup

Create a MySQL database named:

```sql
CREATE DATABASE university;
```

Update database credentials in the service configuration if needed:

- student-service/src/main/resources/application.properties
- address-service configuration through the config server

The current project configuration uses:

```properties
spring.datasource.url = jdbc:mysql://localhost:3306/university
spring.datasource.username = root
spring.datasource.password = root
```

## Service Ports

- Eureka Server: http://localhost:8761
- Config Server: http://localhost:8888
- API Gateway: http://localhost:9090
- Student Service: http://localhost:8080
- Address Service: configuration is managed by Spring Cloud Config (and the project comments show 8083 in local config examples)

## Run the Project

Run the services in this order:

1. Start Eureka Server
2. Start Config Server
3. Start Address Service
4. Start Student Service
5. Start API Gateway

From each service directory:

```bash
mvn spring-boot:run
```

Example:

```bash
cd eureka-server
mvn spring-boot:run
```

Then:

```bash
cd config-server
mvn spring-boot:run
```

Then continue with the remaining services.

## Main Endpoints

### Student Service

- POST /api/student/create
- GET /api/student/getById/{id}
- GET /api/student/getByUserName/{userName}

### Address Service

- POST /api/address/create
- GET /api/address/getById/{id}

### Gateway

Requests can be routed via the gateway, and service discovery is enabled for internal service lookup.

## Features

- Create student records
- Retrieve student details by ID
- Retrieve student details by username
- Fetch associated address information
- Inter-service communication using Feign
- Circuit breaker fallback with Resilience4j
- API routing through gateway
- Service registration/discovery with Eureka
- Config-driven setup with Spring Cloud Config

## Notes

This project is a learning/demo microservices application. Some configuration values and service URLs are environment-specific and may need to be adjusted depending on your local setup or cloud configuration repository.

## Summary

This University Project demonstrates how a simple university domain can be modeled as a set of independent Spring Boot microservices with shared concerns such as discovery, routing, configuration, and service-to-service communication.
