# ✅ Neo4flix - WORKING SETUP

## Single Command to Start Everything

```bash
sudo docker compose up -d
```

**All services are now running!**

## Access Points

- **Frontend**: http://localhost:4200
- **Neo4j Browser**: http://localhost:7474
  - Username: `neo4j`
  - Password: `password`

## Quick Start Steps

### 1. Load Sample Data (Required)
1. Open http://localhost:7474
2. Login with `neo4j` / `password`
3. Open `init-data.cypher` file
4. Copy all content
5. Paste in Neo4j Browser and execute

### 2. Use the Application
1. Go to http://localhost:4200
2. Click "Sign up"
3. Create account:
   - Username: `testuser`
   - Email: `test@example.com`
   - Password: `SecurePass123!`
4. Login and explore!

## ✅ Registration & Login Now Working!

Password requirements:
- At least 8 characters
- One uppercase letter
- One lowercase letter
- One number
- One special character (@#$%^&+=!)

## Check Services Status

```bash
sudo docker compose ps
```

All 6 services should show "Up":
- neo4flix-neo4j
- neo4flix-user-service
- neo4flix-movie-service
- neo4flix-rating-service
- neo4flix-recommendation-service
- neo4flix-frontend

## View Logs

```bash
# All services
sudo docker compose logs -f

# Specific service
sudo docker compose logs -f user-service
```

## Stop Services

```bash
sudo docker compose down
```

## Restart Services

```bash
sudo docker compose restart
```

## Clean Everything and Start Fresh

```bash
sudo docker compose down -v
sudo docker compose up -d
```

## Troubleshooting

### Services not starting?
```bash
sudo systemctl start docker
sudo docker compose up -d
```

### Check specific service logs:
```bash
sudo docker compose logs user-service
sudo docker compose logs frontend
```

### Registration still failing?
1. Make sure Neo4j is running: http://localhost:7474
2. Check user-service logs: `sudo docker compose logs user-service`
3. Wait 30 seconds for all services to fully start
4. Use password with allowed special characters: @#$%^&+=!

## Features to Test

1. **Register & Login** - Create account and login ✅
2. **Browse Movies** - View all movies on home page
3. **Search** - Search by title or genre
4. **Rate Movies** - Click on a movie and rate it
5. **Recommendations** - Get personalized recommendations
6. **Watchlist** - Add movies to your watchlist

---

**Everything is working! Enjoy Neo4flix! 🎬**
