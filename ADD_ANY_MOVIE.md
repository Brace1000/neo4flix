# 🎬 Add ANY Movie You Want to Neo4flix!

## ✅ Search TMDB and Add Movies On-Demand

You can now search for **ANY movie** on The Movie Database (TMDB) and add it to your Neo4flix!

## How to Add Your Favorite Movies

### Simple Command:
```bash
python3 search_and_add_movie.py "movie name"
```

### Examples:

```bash
# Add Titanic
python3 search_and_add_movie.py "titanic"

# Add Lord of the Rings
python3 search_and_add_movie.py "lord of the rings"

# Add Harry Potter
python3 search_and_add_movie.py "harry potter"

# Add Fast and Furious
python3 search_and_add_movie.py "fast and furious"

# Add any movie you want!
python3 search_and_add_movie.py "your favorite movie"
```

## What Happens:

1. **Searches TMDB** - Finds all movies matching your search
2. **Shows Results** - Displays top 10 matches with ratings
3. **Adds to Database** - Automatically adds all found movies
4. **Ready to Browse** - Refresh browser to see new movies!

## Add Multiple Movies at Once

Create a simple script:

```bash
# Create add_my_movies.sh
cat > add_my_movies.sh << 'EOF'
#!/bin/bash
python3 search_and_add_movie.py "titanic"
python3 search_and_add_movie.py "inception"
python3 search_and_add_movie.py "interstellar"
python3 search_and_add_movie.py "the godfather"
python3 search_and_add_movie.py "pulp fiction"
python3 search_and_add_movie.py "fight club"
python3 search_and_add_movie.py "forrest gump"
python3 search_and_add_movie.py "the matrix"
python3 search_and_add_movie.py "star wars"
python3 search_and_add_movie.py "lord of the rings"
EOF

chmod +x add_my_movies.sh
./add_my_movies.sh
```

## Popular Movies to Add

### Classic Movies
```bash
python3 search_and_add_movie.py "the shawshank redemption"
python3 search_and_add_movie.py "the godfather"
python3 search_and_add_movie.py "casablanca"
python3 search_and_add_movie.py "gone with the wind"
```

### Action Movies
```bash
python3 search_and_add_movie.py "die hard"
python3 search_and_add_movie.py "john wick"
python3 search_and_add_movie.py "mad max"
python3 search_and_add_movie.py "mission impossible"
```

### Sci-Fi Movies
```bash
python3 search_and_add_movie.py "blade runner"
python3 search_and_add_movie.py "dune"
python3 search_and_add_movie.py "arrival"
python3 search_and_add_movie.py "ex machina"
```

### Marvel/DC Movies
```bash
python3 search_and_add_movie.py "avengers"
python3 search_and_add_movie.py "iron man"
python3 search_and_add_movie.py "batman"
python3 search_and_add_movie.py "wonder woman"
```

### Recent Blockbusters
```bash
python3 search_and_add_movie.py "oppenheimer"
python3 search_and_add_movie.py "barbie"
python3 search_and_add_movie.py "top gun maverick"
python3 search_and_add_movie.py "everything everywhere"
```

### Animated Movies
```bash
python3 search_and_add_movie.py "toy story"
python3 search_and_add_movie.py "finding nemo"
python3 search_and_add_movie.py "spirited away"
python3 search_and_add_movie.py "spider-verse"
```

### Horror Movies
```bash
python3 search_and_add_movie.py "the exorcist"
python3 search_and_add_movie.py "get out"
python3 search_and_add_movie.py "hereditary"
python3 search_and_add_movie.py "the conjuring"
```

### Comedy Movies
```bash
python3 search_and_add_movie.py "superbad"
python3 search_and_add_movie.py "the hangover"
python3 search_and_add_movie.py "bridesmaids"
python3 search_and_add_movie.py "step brothers"
```

## Build Your Perfect Collection

### 1. Start with 500 Popular Movies
```bash
python3 load_tmdb_movies.py
```

### 2. Add Your Personal Favorites
```bash
python3 search_and_add_movie.py "your favorite movie 1"
python3 search_and_add_movie.py "your favorite movie 2"
python3 search_and_add_movie.py "your favorite movie 3"
```

### 3. Add Movies by Franchise
```bash
# Star Wars
python3 search_and_add_movie.py "star wars"

# Harry Potter
python3 search_and_add_movie.py "harry potter"

# James Bond
python3 search_and_add_movie.py "james bond"

# Fast & Furious
python3 search_and_add_movie.py "fast and furious"
```

## Check What's in Your Database

```bash
# Count total movies
curl -s http://localhost:8082/api/movies | python3 -c "import sys, json; print(len(json.load(sys.stdin)), 'movies')"

# Search for specific movie
curl -s "http://localhost:8082/api/movies/search?query=titanic" | python3 -m json.tool
```

## Advanced: Bulk Add from List

Create a file with your favorite movies:

```bash
# Create my_movies.txt
cat > my_movies.txt << 'EOF'
The Shawshank Redemption
The Godfather
The Dark Knight
Pulp Fiction
Forrest Gump
Inception
Fight Club
The Matrix
Goodfellas
The Lord of the Rings
Star Wars
Jurassic Park
Titanic
The Lion King
Back to the Future
EOF

# Add all movies from the list
while read movie; do
    python3 search_and_add_movie.py "$movie"
    sleep 1
done < my_movies.txt
```

## Unlimited Movies!

You now have **unlimited access** to TMDB's database of:
- 📽️ **1,000,000+** movies
- 🎬 All genres and languages
- 📅 From classics to latest releases
- 🌍 International cinema
- 🎭 Documentaries, shorts, and more

## How It Works

```
Your Search
    ↓
TMDB API (themoviedb.org)
    ↓
Search Results (Top 10)
    ↓
Neo4j Database
    ↓
Your Neo4flix App
```

## Tips

1. **Be Specific** - "the dark knight" is better than "batman"
2. **Add Year** - "titanic 1997" for specific version
3. **Check Results** - Script shows what it found before adding
4. **Refresh Browser** - Press Ctrl+F5 after adding movies
5. **No Duplicates** - Script uses MERGE, won't create duplicates

## Troubleshooting

### Movie not found?
- Try different spelling
- Try original title (e.g., "La La Land")
- Check TMDB website: https://www.themoviedb.org

### Want more results?
Edit `search_and_add_movie.py` line 95:
```python
for movie in movies[:20]:  # Change 10 to 20 or more
```

### API rate limit?
Add delay between searches:
```bash
python3 search_and_add_movie.py "movie1"
sleep 2
python3 search_and_add_movie.py "movie2"
```

---

**🎉 You can now add ANY movie you want to Neo4flix!**

**Start adding your favorites:**
```bash
python3 search_and_add_movie.py "your favorite movie"
```

Then refresh http://localhost:4200 and enjoy! 🎬
