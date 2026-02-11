# Neo4flix Complete Setup Guide

## Quick Start

### 1. Prerequisites
- Docker & Docker Compose
- Java 17+ (for local development)
- Node.js 18+ (for local development)

### 2. Start the Application
```bash
docker-compose up --build
```

### 3. Access Services
- **Frontend**: http://localhost:4200
- **Neo4j Browser**: http://localhost:7474 (neo4j/password)
- **User Service**: http://localhost:8081
- **Movie Service**: http://localhost:8082
- **Rating Service**: http://localhost:8083
- **Recommendation Service**: http://localhost:8084

## Default Credentials

### Neo4j Database
- Username: `neo4j`
- Password: `password`

### Test User Account
- Username: `testuser`
- Password: `Test123!`

## Features

- ✅ User authentication with JWT
- ✅ Two-factor authentication (2FA)
- ✅ Movie browsing and search
- ✅ Advanced filtering (genre, release date)
- ✅ Movie rating system
- ✅ Personalized recommendations
- ✅ Watchlist management

## API Examples

### Register User
```bash
curl -X POST http://localhost:8081/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "SecurePass123!"
  }'
```

### Login
```bash
curl -X POST http://localhost:8081/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "SecurePass123!"
  }'
```

### Get Movies
```bash
curl http://localhost:8082/api/movies
```

### Search Movies
```bash
curl "http://localhost:8082/api/movies/search?query=inception"
```

### Rate a Movie
```bash
curl -X POST http://localhost:8083/api/ratings \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "user-id",
    "movieId": "movie-id",
    "score": 4.5,
    "review": "Great movie!"
  }'
```

### Get Recommendations
```bash
curl http://localhost:8084/api/recommendations/user/{userId}?limit=10 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Development

### Backend Development
```bash
# Build common module
cd backend/common
mvn clean install

# Run a service
cd backend/user-service
mvn spring-boot:run
```

### Frontend Development
```bash
cd frontend
npm install
npm start
```

## Troubleshooting

### Docker containers not starting
```bash
docker-compose down -v
docker-compose up --build
```

### Neo4j connection refused
```bash
docker logs neo4flix-neo4j
```

### Frontend can't connect to backend
- Check all services are running: `docker-compose ps`
- Verify CORS configuration
- Check API URLs in `frontend/src/environments/environment.ts`

## Architecture

### Microservices
1. **User Service** (8081) - Authentication, 2FA, User management
2. **Movie Service** (8082) - Movie CRUD, Search, Filtering
3. **Rating Service** (8083) - Ratings, Reviews
4. **Recommendation Service** (8084) - Collaborative & Content-based filtering

### Database Schema
```
(User)-[:RATED {score, review}]->(Movie)
(User)-[:WATCHLIST]->(Movie)
```

## Additional Resources

- TMDB Integration: See `TMDB_INTEGRATION.md`
- Login Credentials: See `LOGIN_CREDENTIALS.md`
- API Collection: Import `Neo4flix-API-Collection.postman_collection.json`
