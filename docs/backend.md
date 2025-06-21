# [`🠜`](/README.md) docs/backend.md

# 🖥️ Backend (Java Spring Boot)

## Overview
Provides RESTful API, business logic, authentication/authorization.

## Tech Stack
- Java 17
- Spring Boot
- Maven
- Docker & Docker Compose

## Location
`Server/`

## Configuration
Edit `Server/src/main/resources/application.yml` or
override via environment variables in `docker-compose.yml`.

## Running Locally
```bash
cd Server
mvn clean package
java -jar target/*.jar
```

## Docker
Built automatically by `docker-compose.yml` under service `spring-server`.
```bash
docker compose up -d spring-server
```

## Endpoints
- Auth
    - `POST   /api/v1/auth/login`
    - `POST   /api/v1/auth/signin`
    - `GET    /api/v1/auth/refresh`
- Chat
    - `POST   /api/v1/chat`
    - `DELETE /api/v1/chat`
    - `DELETE /api/v1/chat/all`
    - `GET    /api/v1/chat/models`
- Recipe
    - `GET    /api/v1/recipe`
    - `GET    /api/v1/recipe/random`
    - `GET    /api/v1/recipe/favorite`
    - `POST   /api/v1/recipe`
    - `PUT    /api/v1/recipe`
    - `DELETE /api/v1/recipe`
- User
    - `GET    /api/v1/user/self`
