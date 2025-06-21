# [`🠜`](/README.md) docs/ai-service.md

# 🤖 AI Service (Python)

## Overview
Wraps Ollama LLM and processes messages via RabbitMQ.

## Tech Stack
- Python 3.12.x
- Pika (RabbitMQ client)
- Docker & Docker Compose

## Location
`Server/services/ai-service/`

## Configuration
Copy and edit `.env` (create alongside `docker-compose.yml`):
```env
OLLAMA_API_URL=http://localhost:11434/v1
RABBITMQ_HOST=172.20.195.72
RABBITMQ_PORT=5672
RABBITMQ_USER=user
RABBITMQ_PASS=pass
EXCHANGE_NAME=ai-chat-exchange
REQUEST_QUEUE=ai-request-queue
RESPONSE_QUEUE=ai-response-queue
REQUEST_ROUTING_KEY=ai.request
RESPONSE_ROUTING_KEY=ai.response
```

## Install & Run Locally
```bash
cd Server/services/ai-service
conda env create --prefix ./ai_service_env -f environment.yml
conda activate ai_service_env
uvicorn main:app --reload
```

## Docker
Built by `docker-compose.yml` under service `ai-service`.
```bash
docker compose up -d ai-service
```
