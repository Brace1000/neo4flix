# Neo4flix - Quick Start Guide

## Prerequisites

### Start Docker Daemon (if not running):
```bash
sudo systemctl start docker
```

### Add your user to docker group (optional, to avoid sudo):
```bash
sudo usermod -aG docker $USER
newgrp docker
```

## Single Command to Start Everything

```bash
sudo docker compose up --build
```

**OR run in background:**
```bash
sudo docker compose up --build -d
```

This will:
- Build all 4 Spring Boot microservices
- Build the Angular frontend
- Start Neo4j database
- Start all services

**First build takes 5-10 minutes**

## Check Build Progress

```bash
sudo docker compose ps
```

## Access the Application

Once all services show "running":
- **Frontend**: http://localhost:4200
- **Neo4j Browser**: http://localhost:7474 (username: `neo4j`, password: `password`)

## Initial Setup

### 1. Load Sample Data
1. Open http://localhost:7474
2. Login with `neo4j` / `password`
3. Copy content from `init-data.cypher`
4. Paste and execute in Neo4j Browser

### 2. Use the Application
1. Go to http://localhost:4200
2. Click "Sign up"
3. Create account with strong password (e.g., `SecurePass123!`)
4. Login and explore!

## Troubleshooting

### Check if services are running:
```bash
sudo docker compose ps
```

### View logs:
```bash
sudo docker compose logs -f
```

### View specific service logs:
```bash
sudo docker compose logs -f user-service
sudo docker compose logs -f frontend
```

### Restart everything:
```bash
sudo docker compose down -v
sudo docker compose up --build
```

### Login/Registration not working?
1. Check all services are running: `sudo docker compose ps`
2. Check user-service logs: `sudo docker compose logs user-service`
3. Verify Neo4j is accessible: http://localhost:7474
4. Wait 2-3 minutes for all services to fully start
5. Clear browser cache and try again

### Docker daemon not running?
```bash
sudo systemctl start docker
sudo systemctl enable docker
```

## Stop the Application

```bash
sudo docker compose down
```

## Clean Everything (including data)

```bash
sudo docker compose down -v
```
