# 🎬 Search ANY Movie - Simple Guide

## Problem Solved!

You can now search for **ANY movie** and it will automatically search TMDB and show results!

## How It Works Now

### Option 1: Command Line (Easiest)

```bash
# Search and add any movie
python3 search_and_add_movie.py "gladiator"
python3 search_and_add_movie.py "harry potter"
python3 search_and_add_movie.py "ANY MOVIE NAME"

# Then refresh browser
```

### Option 2: API (For developers)

```bash
# Search TMDB
curl "http://localhost:8082/api/movies/search/tmdb?query=gladiator"

# Add a movie from TMDB
curl -X POST http://localhost:8082/api/movies/add-from-tmdb \
  -H "Content-Type: application/json" \
  -d '{"id":98,"title":"Gladiator","overview":"...","release_date":"2000-05-05"}'
```

## Quick Start

### 1. Add Movies You Want

```bash
# Add your favorite movies
python3 search_and_add_movie.py "the godfather"
python3 search_and_add_movie.py "pulp fiction"
python3 search_and_add_movie.py "inception"
python3 search_and_add_movie.py "interstellar"
python3 search_and_add_movie.py "the dark knight"
```

### 2. Refresh Browser

Press `Ctrl+F5` on http://localhost:4200

### 3. Search in Browser

Now you can search for the movies you added!

## Add Lots of Movies at Once

Create a script with your favorites:

```bash
# Create add_favorites.sh
cat > add_favorites.sh << 'EOF'
#!/bin/bash
movies=(
    "the shawshank redemption"
    "the godfather"
    "the dark knight"
    "pulp fiction"
    "forrest gump"
    "inception"
    "fight club"
    "the matrix"
    "goodfellas"
    "star wars"
    "jurassic park"
    "titanic"
    "avatar"
    "avengers"
    "spider-man"
    "batman"
    "superman"
    "iron man"
    "thor"
    "captain america"
)

for movie in "${movies[@]}"; do
    echo "Adding: $movie"
    python3 search_and_add_movie.py "$movie"
    sleep 1
done

echo "✅ Done! Refresh your browser"
EOF

chmod +x add_favorites.sh
./add_favorites.sh
```

## Popular Categories to Add

### Classics
```bash
python3 search_and_add_movie.py "casablanca"
python3 search_and_add_movie.py "citizen kane"
python3 search_and_add_movie.py "gone with the wind"
```

### Action
```bash
python3 search_and_add_movie.py "die hard"
python3 search_and_add_movie.py "john wick"
python3 search_and_add_movie.py "mad max"
```

### Sci-Fi
```bash
python3 search_and_add_movie.py "blade runner"
python3 search_and_add_movie.py "dune"
python3 search_and_add_movie.py "arrival"
```

### Marvel
```bash
python3 search_and_add_movie.py "avengers endgame"
python3 search_and_add_movie.py "iron man"
python3 search_and_add_movie.py "black panther"
```

### DC
```bash
python3 search_and_add_movie.py "the dark knight"
python3 search_and_add_movie.py "wonder woman"
python3 search_and_add_movie.py "aquaman"
```

## Check What You Have

```bash
# Count movies
curl -s http://localhost:8082/api/movies | python3 -c "import sys, json; print(len(json.load(sys.stdin)), 'movies')"

# Search your database
curl -s "http://localhost:8082/api/movies/search?query=YOUR_SEARCH"
```

## Summary

1. **Add movies**: `python3 search_and_add_movie.py "movie name"`
2. **Refresh browser**: `Ctrl+F5`
3. **Search**: Type movie name in search box
4. **Enjoy**: Browse, rate, add to watchlist!

---

**The more movies you add, the better your experience!**

Start with 20-50 of your favorites, then add more as needed.
