# 🔍 Neo4flix Search Functionality Guide

## ✅ Search is Working!

Your Neo4flix has full search capabilities across 500+ movies.

## How to Search

### 1. Search by Movie Title
1. Go to http://localhost:4200
2. Type movie name in search box (e.g., "Avatar", "Matrix", "Inception")
3. Press Enter or click "Search" button
4. Results appear instantly!

### 2. Filter by Genre
1. Use the "Filter by Genre" dropdown
2. Select: Action, Comedy, Drama, Horror, Sci-Fi, etc.
3. Movies filtered automatically!

### 3. Combined Search
- Search for a title AND filter by genre
- Example: Search "war" + Filter "Action"

## Test Search Functionality

### Via Browser (http://localhost:4200)
1. Search for "avatar" - Should show Avatar movies
2. Search for "spider" - Should show Spider-Man movies
3. Search for "dark" - Should show The Dark Knight, etc.
4. Filter by "Action" - Shows all action movies
5. Filter by "Sci-Fi" - Shows all sci-fi movies

### Via API (Command Line)
```bash
# Search by title
curl "http://localhost:4200/api/movies/search?query=avatar"

# Search by genre
curl "http://localhost:4200/api/movies/search/genre?genre=Action"

# Get all movies
curl "http://localhost:4200/api/movies"
```

## Available Genres

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

## Popular Movies to Search

Try searching for these popular movies:
- Avatar
- Spider-Man
- Batman
- Iron Man
- Avengers
- Joker
- Inception
- Matrix
- Deadpool
- Venom
- Dune
- Oppenheimer
- Barbie
- Guardians
- Thor
- Captain America
- Black Panther
- Wonder Woman
- Superman
- Aquaman

## Troubleshooting

### Search not working?

1. **Clear browser cache**
   ```
   Ctrl+Shift+Delete (Chrome/Firefox)
   Or hard refresh: Ctrl+F5
   ```

2. **Check if movies are loaded**
   ```bash
   curl http://localhost:4200/api/movies | python3 -c "import sys, json; print(len(json.load(sys.stdin)), 'movies')"
   ```

3. **Rebuild frontend**
   ```bash
   sudo docker compose up --build -d frontend
   ```

4. **Check logs**
   ```bash
   sudo docker compose logs frontend
   sudo docker compose logs movie-service
   ```

### No results found?

1. **Load more movies**
   ```bash
   python3 load_tmdb_movies.py
   ```

2. **Try different search terms**
   - Use partial names: "spider" instead of "spider-man"
   - Try genre filter instead
   - Check spelling

3. **Verify API works**
   ```bash
   curl "http://localhost:8082/api/movies/search?query=YOUR_SEARCH"
   ```

## Search Features

✅ **Case-insensitive** - "avatar" = "Avatar" = "AVATAR"
✅ **Partial matching** - "spider" finds "Spider-Man"
✅ **Real-time** - Results appear instantly
✅ **Genre filtering** - Filter by multiple genres
✅ **Combined search** - Search + Genre filter together
✅ **500+ movies** - Large database to search

## Advanced: Load Specific Movies

Want specific movies? Edit `load_tmdb_movies.py` to search TMDB:

```python
# Add this function to load_tmdb_movies.py

def search_and_load_movie(movie_name):
    """Search TMDB and load specific movie"""
    url = f"https://api.themoviedb.org/3/search/movie?api_key={TMDB_API_KEY}&query={movie_name}"
    response = requests.get(url)
    movies = response.json().get('results', [])
    
    for movie in movies[:5]:  # Load top 5 results
        create_movie_in_neo4j(movie)
    
    print(f"✅ Loaded movies matching '{movie_name}'")

# Usage
search_and_load_movie("your favorite movie")
```

## Quick Reference

| Action | Command |
|--------|---------|
| Search movies | Type in search box + Enter |
| Filter by genre | Select from dropdown |
| Clear search | Delete text + Enter |
| View all movies | Clear search and genre filter |
| Reload movies | `python3 load_tmdb_movies.py` |
| Test API | `curl "http://localhost:4200/api/movies/search?query=test"` |

---

**🎬 Happy searching! Browse 500+ movies on Neo4flix!**

**Need more movies?** Run: `python3 load_tmdb_movies.py`
