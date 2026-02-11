# 🎬 Neo4flix - Real Movie Database Integration

## ✅ 500+ Real Movies from TMDB!

Your Neo4flix now has **500 real movies** from The Movie Database (TMDB) with:
- Real movie titles, descriptions, and posters
- Release dates and genres
- Ratings from TMDB
- Search functionality
- Watchlist support
- Rating system

## Quick Start

```bash
# 1. Start services
sudo docker compose up -d

# 2. Load 500 real movies from TMDB
python3 load_tmdb_movies.py

# 3. Open browser
# Go to http://localhost:4200
```

## Load More Movies

### Load 500 Movies (Default)
```bash
python3 load_tmdb_movies.py
```

### Load 1000 Movies
Edit `load_tmdb_movies.py` and change:
```python
PAGES_TO_LOAD = 50  # 50 pages × 20 movies = 1000 movies
```

Then run:
```bash
python3 load_tmdb_movies.py
```

### Load 2000+ Movies
```python
PAGES_TO_LOAD = 100  # 100 pages × 20 movies = 2000 movies
```

## Get Your Own TMDB API Key (Free)

1. Go to https://www.themoviedb.org/signup
2. Create a free account
3. Go to https://www.themoviedb.org/settings/api
4. Request an API key (free for non-commercial use)
5. Replace the API key in `load_tmdb_movies.py`:
   ```python
   TMDB_API_KEY = "your_api_key_here"
   ```

## Features Working

✅ **Browse 500+ Real Movies** - Latest and popular movies
✅ **Search by Title** - Find any movie instantly
✅ **Filter by Genre** - Action, Drama, Sci-Fi, Comedy, etc.
✅ **Movie Details** - Full descriptions and posters
✅ **Rate Movies** - Give 1-5 star ratings
✅ **Watchlist** - Save movies to watch later
✅ **Recommendations** - Get personalized suggestions
✅ **User Authentication** - Secure login with JWT

## Movie Categories Available

- Action
- Adventure
- Animation
- Comedy
- Crime
- Documentary
- Drama
- Family
- Fantasy
- History
- Horror
- Music
- Mystery
- Romance
- Sci-Fi
- Thriller
- War
- Western

## How It Works

1. **TMDB API** - Fetches real movie data from themoviedb.org
2. **Neo4j Database** - Stores movies as graph nodes
3. **Movie Service** - Provides REST API for movies
4. **Frontend** - Displays movies with search and filters
5. **Rating Service** - Tracks user ratings
6. **Recommendation Engine** - Suggests movies based on your ratings

## Useful Commands

### Check how many movies are loaded
```bash
curl -s http://localhost:8082/api/movies | python3 -c "import sys, json; print(len(json.load(sys.stdin)), 'movies')"
```

### Search for a specific movie
```bash
curl -s "http://localhost:8082/api/movies/search?query=matrix"
```

### Get movies by genre
```bash
curl -s "http://localhost:8082/api/movies/search/genre?genre=Action"
```

### Reload movies (clears and reloads)
```bash
python3 load_tmdb_movies.py
```

## Troubleshooting

### No movies showing after loading?
1. Refresh browser (Ctrl+F5)
2. Check movie service: `curl http://localhost:8082/api/movies`
3. Check logs: `sudo docker compose logs movie-service`

### Want more movies?
Edit `load_tmdb_movies.py`:
```python
PAGES_TO_LOAD = 50  # Change to 50, 100, or more
```

### TMDB API rate limit?
The script includes rate limiting (0.3s delay between requests).
For large loads, consider getting a paid TMDB API key.

## Project Architecture

```
User Browser
    ↓
Angular Frontend (Port 4200)
    ↓
Nginx Proxy
    ↓
┌─────────────────────────────────────┐
│  Spring Boot Microservices          │
│  - User Service (8081)              │
│  - Movie Service (8082) ← TMDB API  │
│  - Rating Service (8083)            │
│  - Recommendation Service (8084)    │
└─────────────────────────────────────┘
    ↓
Neo4j Database (7474, 7687)
```

## Next Steps

1. ✅ Browse 500 real movies
2. ✅ Search and filter movies
3. ✅ Rate your favorite movies
4. ✅ Add movies to watchlist
5. ✅ Get personalized recommendations
6. 🎯 Share recommendations with friends
7. 🎯 Enable 2FA for extra security

## Advanced: Continuous Movie Updates

To keep movies updated with latest releases, create a cron job:

```bash
# Add to crontab (run weekly)
0 0 * * 0 cd /path/to/neo4flix && python3 load_tmdb_movies.py
```

---

**🎉 Enjoy browsing 500+ real movies on Neo4flix!**

**Need help?** Check logs: `sudo docker compose logs -f`
