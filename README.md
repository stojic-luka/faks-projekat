# 📚 Faks Projekat

> A full-stack, multi-tier application demonstrating modern microservices-style architecture
> with a Java Spring Boot backend, React web client, .NET MAUI desktop client,
> Python-powered AI service, and MongoDB, all orchestrated via Docker Compose.

## 🚀 Features

- RESTful CRUD API (Spring Boot)
- Web UI (Vite + React)
- Cross-platform Desktop UI (​.NET MAUI)
- AI-driven text generation (Python + Ollama LLM)
- Asynchronous messaging (RabbitMQ)
- Persistent NoSQL storage (MongoDB)

## 🛠️ Tech Stack

- Backend: Java 17, Spring Boot, Maven
- Frontend: Node.js 20.15.0, Vite, React.js v18.3
- Desktop: .NET 8, .NET MAUI
- AI Service: Python 3.12.x, Pika (RabbitMQ)
- Messaging: RabbitMQ 3.8.0
- Database: MongoDB 8.0
- LLM Host: Ollama

## 📂 Repository Structure

```text
faks-projekat/
├─ Clients/
│  ├─ web/          → Vite + React web client
│  └─ desktop/      → .NET MAUI desktop client
├─ Server/
│  ├─ src/          → Spring Boot backend
│  └─ services/
│     └─ ai-service/→ Python AI microservice
├─ Database/
│  └─ mongo/        → MongoDB config, init scripts, data
├─ Communication/
│  ├─ rabbitmq/     → RabbitMQ config & definitions
│  └─ ollama/       → Ollama config & data
├─ docs/            → per-module documentation (see below)
└─ docker-compose.yml
```

## 📋 Documentation

- [Backend (Spring Boot)](docs/backend.md)
- [Web Client (Vite + React)](docs/web-client.md)
- [Desktop Client (.NET MAUI)](docs/desktop-client.md)
- [AI Service (Python)](docs/ai-service.md)
- [Database (MongoDB)](docs/database.md)

## ⚙️ Prerequisites

- Git
- Docker Engine ≥ 20.x & Docker Compose
- (Optional) Only for local dev:
	- JDK 17, Maven (for local backend dev)
	- Node.js 20.15.0 & npm (for local web dev)
	- .NET 8 SDK + MAUI workload (for local desktop dev)
	- Python 3.9+ & pip (for local AI-service dev)

## 🔧 Environment Configuration

1. Copy and edit environment files:

   - `Database/mongo/mongo.env`
     ```env
	 MONGO_INITDB_ROOT_USERNAME=admin
	 MONGO_INITDB_ROOT_PASSWORD=password
     ```
   - `Communication/rabbitmq/rabbitmq.env`
     ```env
     RABBITMQ_DEFAULT_USER=user
	 RABBITMQ_DEFAULT_PASS=pass
     ```
   - `Server\services\ai-service\.env`:
     ```env
     OLLAMA_API_URL=http://localhost:11434/v1
	 RABBITMQ_HOST=localhost
	 ...
     ```

2. Ensure the `volumes` directories exist and are writeable:
   - `Database/mongo/data/`
   - `Database/mongo/logs/`
   - `Ollama/data/`

## 🚀 Quickstart (Docker)

```bash
git clone https://github.com/stojic-luka/faks-projekat.git
cd faks-projekat
docker compose up -d --build
```

Once up:

- Web client → http://localhost:5173
- Backend API → http://localhost:8080/api
- RabbitMQ Management → http://localhost:15672

To tear down:

```bash
docker compose down
```

## 📦 Building Locally (without Docker)

### Backend

```bash
cd Server
mvn clean package
java -jar target/*.jar
```

### Web Client

```bash
cd Clients/web
npm install
npm run dev
```

### Desktop Client

```bash
cd Clients/desktop
dotnet build
dotnet run -f net7.0-windows
```

### AI Service

```bash
cd Server/services/ai-service
conda env create --prefix ./ai_service_env -f environment.yml
conda activate ai_service_env
uvicorn main:app --reload
```
