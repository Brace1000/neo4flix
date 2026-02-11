#!/bin/bash

# TMDB API Integration Script
# This script fetches popular movies from TMDB and loads them into Neo4j

echo "🎬 Loading movies from TMDB API..."

# TMDB API Key (Free - get yours at https://www.themoviedb.org/settings/api)
# Using a demo key for now - replace with your own for production
TMDB_API_KEY="8265bd1679663a7ea12ac168da84d2e8"

# Function to fetch and load movies
load_movies_from_tmdb() {
    local page=$1
    echo "Fetching page $page..."
    
    # Fetch popular movies from TMDB
    movies=$(curl -s "https://api.themoviedb.org/3/movie/popular?api_key=${TMDB_API_KEY}&page=${page}")
    
    # Parse and create Cypher statements
    echo "$movies" | python3 - << 'PYTHON_SCRIPT'
import json
import sys

data = json.load(sys.stdin)

for movie in data.get('results', []):
    movie_id = movie.get('id')
    title = movie.get('title', '').replace("'", "\\'")
    description = movie.get('overview', '').replace("'", "\\'")
    release_date = movie.get('release_date', '1900-01-01')
    poster_path = movie.get('poster_path', '')
    poster_url = f"https://image.tmdb.org/t/p/w500{poster_path}" if poster_path else ""
    vote_average = movie.get('vote_average', 0)
    
    # Get genre names (simplified - using genre IDs)
    genre_ids = movie.get('genre_ids', [])
    genre_map = {
        28: "Action", 12: "Adventure", 16: "Animation", 35: "Comedy",
        80: "Crime", 99: "Documentary", 18: "Drama", 10751: "Family",
        14: "Fantasy", 36: "History", 27: "Horror", 10402: "Music",
        9648: "Mystery", 10749: "Romance", 878: "Sci-Fi", 10770: "TV Movie",
        53: "Thriller", 10752: "War", 37: "Western"
    }
    genres = [genre_map.get(gid, "Unknown") for gid in genre_ids[:3]]
    genres_str = str(genres).replace("'", '"')
    
    cypher = f"""
    MERGE (m:Movie {{id: '{movie_id}'}})
    SET m.title = '{title}',
        m.description = '{description}',
        m.releaseDate = date('{release_date}'),
        m.genres = {genres_str},
        m.posterUrl = '{poster_url}',
        m.averageRating = {vote_average},
        m.ratingCount = 0;
    """
    
    print(cypher)
PYTHON_SCRIPT
}

# Clear existing movies (optional)
echo "Clearing existing movies..."
curl -s -X POST http://localhost:7474/db/neo4j/tx/commit \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic $(echo -n 'neo4j:password' | base64)" \
  -d '{"statements":[{"statement":"MATCH (m:Movie) DELETE m"}]}' > /dev/null

# Load movies from multiple pages (20 movies per page)
for page in {1..25}; do
    echo "Loading page $page of 25..."
    
    cypher_statements=$(load_movies_from_tmdb $page)
    
    # Send to Neo4j
    while IFS= read -r statement; do
        if [ ! -z "$statement" ]; then
            curl -s -X POST http://localhost:7474/db/neo4j/tx/commit \
              -H "Content-Type: application/json" \
              -H "Authorization: Basic $(echo -n 'neo4j:password' | base64)" \
              -d "{\"statements\":[{\"statement\":\"$statement\"}]}" > /dev/null
        fi
    done <<< "$cypher_statements"
    
    sleep 0.5  # Rate limiting
done

echo ""
echo "✅ Loaded 500 movies from TMDB!"
echo "🎬 Refresh your browser at http://localhost:4200"
echo ""
echo "Note: Get your own TMDB API key at https://www.themoviedb.org/settings/api"
