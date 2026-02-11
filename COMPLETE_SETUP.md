# 🎬 Neo4flix - Complete Working Setup

## ✅ Everything is Working!

- Registration ✅
- Login ✅
- Movies Loaded ✅
- All Services Running ✅

## Quick Start (3 Commands)

```bash
# 1. Start all services
sudo docker compose up -d

# 2. Load sample movies
bash load-movies.sh

# 3. Open browser
# Go to http://localhost:4200
```

## Access Points

- **Frontend**: http://localhost:4200
- **Neo4j Browser**: http://localhost:7474 (neo4j/password)
- **API Endpoints**: 
  - User Service: http://localhost:8081
  - Movie Service: http://localhost:8082
  - Rating Service: http://localhost:8083
  - Recommendation Service: http://localhost:8084

## First Time Setup

### 1. Start Services
```bash
sudo docker compose up -d
```

### 2. Load Movies (REQUIRED)
```bash
bash load-movies.sh
```

This loads 10 sample movies:
- The Matrix
- Inception
- The Dark Knight
- Interstellar
- Pulp Fiction
- The Shawshank Redemption
- Forrest Gump
- The Godfather
- Fight Club
- Goodfellas

### 3. Create Account & Login
1. Go to http://localhost:4200
2. Click "Sign up"
3. Create account:
   - Username: `yourname`
   - Email: `your@email.com`
   - Password: `SecurePass123!`
4. Login and browse movies!

## Password Requirements

- Minimum 8 characters
- One uppercase letter
- One lowercase letter
- One number
- One special character: `@#$%^&+=!`

## Features Working

✅ User Registration & Login
✅ Browse Movies
✅ Search Movies (by title/genre)
✅ Rate Movies
✅ Add to Watchlist
✅ Get Recommendations
✅ Two-Factor Authentication (2FA)

## Useful Commands

### Check Services Status
```bash
sudo docker compose ps
```

### View Logs
```bash
# All services
sudo docker compose logs -f

# Specific service
sudo docker compose logs -f user-service
sudo docker compose logs -f movie-service
```

### Restart Services
```bash
sudo docker compose restart
```

### Stop Services
```bash
sudo docker compose down
```

### Clean & Restart Fresh
```bash
sudo docker compose down -v
sudo docker compose up -d
bash load-movies.sh
```

## Troubleshooting

### No movies showing?
```bash
bash load-movies.sh
```

### Services not starting?
```bash
sudo systemctl start docker
sudo docker compose up -d
```

### Check if movies are loaded
```bash
curl http://localhost:8082/api/movies
```

### Registration failing?
- Use password with special characters: `@#$%^&+=!`
- Check user-service logs: `sudo docker compose logs user-service`

## Project Structure

```
neo4flix/
├── backend/
│   ├── user-service/       # Port 8081 - Auth & Users
│   ├── movie-service/      # Port 8082 - Movies
│   ├── rating-service/     # Port 8083 - Ratings
│   └── recommendation-service/ # Port 8084 - Recommendations
├── frontend/               # Port 4200 - Angular UI
├── docker-compose.yml      # Docker orchestration
├── load-movies.sh         # Load sample data
└── init-data.cypher       # Sample movie data

```

## Tech Stack

- **Backend**: Spring Boot 3.2, Java 17
- **Frontend**: Angular 17, TailwindCSS
- **Database**: Neo4j 5.13
- **Security**: JWT, BCrypt, 2FA
- **Deployment**: Docker, Docker Compose

---

**🎉 Enjoy Neo4flix!**

Need help? Check logs with `sudo docker compose logs -f`
