#!/usr/bin/env python3
"""
TMDB Movie Loader for Neo4flix
Fetches popular movies from The Movie Database API and loads them into Neo4j
"""

import requests
import json
import base64
import time

# Configuration
TMDB_API_KEY = "8265bd1679663a7ea12ac168da84d2e8"  # Demo key - get your own at themoviedb.org
NEO4J_URL = "http://localhost:7474/db/neo4j/tx/commit"
NEO4J_AUTH = base64.b64encode(b"neo4j:password").decode()
PAGES_TO_LOAD = 25  # 20 movies per page = 500 total movies

# Genre mapping
GENRE_MAP = {
    28: "Action", 12: "Adventure", 16: "Animation", 35: "Comedy",
    80: "Crime", 99: "Documentary", 18: "Drama", 10751: "Family",
    14: "Fantasy", 36: "History", 27: "Horror", 10402: "Music",
    9648: "Mystery", 10749: "Romance", 878: "Sci-Fi", 10770: "TV Movie",
    53: "Thriller", 10752: "War", 37: "Western"
}

def execute_cypher(statement):
    """Execute a Cypher statement in Neo4j"""
    headers = {
        "Content-Type": "application/json",
        "Authorization": f"Basic {NEO4J_AUTH}"
    }
    data = {"statements": [{"statement": statement}]}
    
    try:
        response = requests.post(NEO4J_URL, headers=headers, json=data)
        return response.status_code == 200
    except Exception as e:
        print(f"Error executing Cypher: {e}")
        return False

def fetch_movies_from_tmdb(page):
    """Fetch movies from TMDB API"""
    url = f"https://api.themoviedb.org/3/movie/popular?api_key={TMDB_API_KEY}&page={page}"
    
    try:
        response = requests.get(url)
        if response.status_code == 200:
            return response.json().get('results', [])
    except Exception as e:
        print(f"Error fetching from TMDB: {e}")
    
    return []

def create_movie_in_neo4j(movie):
    """Create a movie node in Neo4j"""
    movie_id = str(movie.get('id'))
    title = movie.get('title', '').replace("'", "\\'").replace('"', '\\"')
    description = movie.get('overview', '').replace("'", "\\'").replace('"', '\\"')
    release_date = movie.get('release_date', '1900-01-01')
    poster_path = movie.get('poster_path', '')
    poster_url = f"https://image.tmdb.org/t/p/w500{poster_path}" if poster_path else ""
    vote_average = float(movie.get('vote_average', 0)) / 2  # Convert 0-10 to 0-5 scale
    
    # Get genres
    genre_ids = movie.get('genre_ids', [])
    genres = [GENRE_MAP.get(gid, "Unknown") for gid in genre_ids[:3]]
    genres_json = json.dumps(genres)
    
    cypher = f"""
    MERGE (m:Movie {{id: '{movie_id}'}})
    SET m.title = "{title}",
        m.description = "{description}",
        m.releaseDate = date('{release_date}'),
        m.genres = {genres_json},
        m.posterUrl = '{poster_url}',
        m.averageRating = {vote_average:.1f},
        m.ratingCount = 0
    """
    
    return execute_cypher(cypher)

def main():
    print("🎬 Neo4flix TMDB Movie Loader")
    print("=" * 50)
    print(f"Loading {PAGES_TO_LOAD} pages (≈{PAGES_TO_LOAD * 20} movies)...")
    print()
    
    # Clear existing movies
    print("Clearing existing movies...")
    execute_cypher("MATCH (m:Movie) DETACH DELETE m")
    
    total_loaded = 0
    
    # Load movies from TMDB
    for page in range(1, PAGES_TO_LOAD + 1):
        print(f"📥 Fetching page {page}/{PAGES_TO_LOAD}...", end=" ")
        
        movies = fetch_movies_from_tmdb(page)
        
        if not movies:
            print("❌ Failed")
            continue
        
        loaded = 0
        for movie in movies:
            if create_movie_in_neo4j(movie):
                loaded += 1
        
        total_loaded += loaded
        print(f"✅ Loaded {loaded} movies")
        
        time.sleep(0.3)  # Rate limiting
    
    print()
    print("=" * 50)
    print(f"✅ Successfully loaded {total_loaded} movies!")
    print("🎬 Refresh your browser at http://localhost:4200")
    print()
    print("💡 Tip: Get your own TMDB API key at:")
    print("   https://www.themoviedb.org/settings/api")

if __name__ == "__main__":
    main()
