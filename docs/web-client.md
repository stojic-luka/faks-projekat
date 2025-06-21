# [`🠜`](/README.md) docs/web-client.md

# 🌐 Web Client (Vite + React)

## Overview
Single‐page application consumes Backend APIs.

## Tech Stack
- Node.js 20.15.0
- Vite
- React.js
- Yarn or npm
- Docker & Docker Compose

## Location
`Clients/web/`

## Running Locally
```bash
cd Clients/web
npm install
npm run dev
```

## Docker
Built by `docker-compose.yml` under service `react-client`,
served on port 5173 → 80.
```bash
docker compose up -d react-client
```
