# Quick Start Guide

## Prerequisites Check
You have Docker Compose v2 installed ✅

## Start Neo4flix

### Option 1: Using Docker Compose (Recommended)
```bash
docker compose up --build
```

### Option 2: Run in background
```bash
docker compose up --build -d
```

### Option 3: Without building (if already built)
```bash
docker compose up
```

## Access the Application

Once all services are running (wait ~2 minutes):

1. **Frontend**: http://localhost:4200
2. **Neo4j Browser**: http://localhost:7474
   - Username: `neo4j`
   - Password: `password`

## Load Sample Data

1. Open Neo4j Browser at http://localhost:7474
2. Login with credentials above
3. Copy and paste content from `init-data.cypher`
4. Execute the script

## Use the Application

1. Go to http://localhost:4200
2. Click "Sign up" to create an account
3. Use a strong password (e.g., `SecurePass123!`)
4. Login and explore movies!

## Stop the Application

```bash
docker compose down
```

## View Logs

```bash
# All services
docker compose logs -f

# Specific service
docker compose logs -f user-service
docker compose logs -f movie-service
docker compose logs -f frontend
```

## Troubleshooting

### Services not starting
```bash
docker compose down -v
docker compose up --build
```

### Check service status
```bash
docker compose ps
```

### Restart a specific service
```bash
docker compose restart user-service
```

## Development Mode (Without Docker)

### Backend
```bash
# Terminal 1 - Start Neo4j
docker run -p 7474:7474 -p 7687:7687 -e NEO4J_AUTH=neo4j/password neo4j:5.13.0

# Terminal 2 - Build common module
cd backend/common && mvn clean install

# Terminal 3 - Start User Service
cd backend/user-service && mvn spring-boot:run

# Terminal 4 - Start Movie Service
cd backend/movie-service && mvn spring-boot:run

# Terminal 5 - Start Rating Service
cd backend/rating-service && mvn spring-boot:run

# Terminal 6 - Start Recommendation Service
cd backend/recommendation-service && mvn spring-boot:run
```

### Frontend
```bash
cd frontend
npm install
npm start
```

Access at http://localhost:4200
