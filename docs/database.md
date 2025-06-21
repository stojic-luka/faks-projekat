# [`🠜`](/README.md) docs/database.md

# 🗄️ Database (MongoDB)

## Overview
Primary datastore for the application.

## Tech Stack
- MongoDB 8.0
- Docker volume for persistence

## Location
`Database/mongo/`

## Configuration
Edit `Database/mongo/mongo.env`:
```env
MONGO_INITDB_ROOT_USERNAME=admin
MONGO_INITDB_ROOT_PASSWORD=secret
```

The `docker-compose.yml` maps:
- Host port `27027` → Container port `27027`
- Data persisted at `Database/mongo/data/`
- Logs at `Database/mongo/logs/`

## Connecting
Use URI:
```
mongodb://admin:password@localhost:27027/recipes?authSource=admin
```

## Backup & Restore
```bash
# Backup
docker exec MongoDB mongodump --archive=/backup/archive.gz \
  --gzip --db recipes
docker cp MongoDB:/backup/archive.gz ./backups/

# Restore
docker cp ./backups/archive.gz MongoDB:/restore/
docker exec MongoDB mongorestore --gzip --archive=/restore/archive.gz
```
