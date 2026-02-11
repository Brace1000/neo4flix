#!/usr/bin/env python3
"""
TMDB On-Demand Movie Search and Loader
Search for ANY movie on TMDB and add it to your Neo4flix database
"""

import requests
import json
import base64
import sys

# Configuration
TMDB_API_KEY = "8265bd1679663a7ea12ac168da84d2e8"
NEO4J_URL = "http://localhost:7474/db/neo4j/tx/commit"
NEO4J_AUTH = base64.b64encode(b"neo4j:password").decode()

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
        print(f"Error: {e}")
        return False

def search_tmdb(query):
    """Search for movies on TMDB"""
    url = f"https://api.themoviedb.org/3/search/movie?api_key={TMDB_API_KEY}&query={query}"
    
    try:
        response = requests.get(url)
        if response.status_code == 200:
            return response.json().get('results', [])
    except Exception as e:
        print(f"Error searching TMDB: {e}")
    
    return []

def create_movie_in_neo4j(movie):
    """Create a movie node in Neo4j"""
    movie_id = str(movie.get('id'))
    title = movie.get('title', '').replace("'", "\\'").replace('"', '\\"')
    description = movie.get('overview', '').replace("'", "\\'").replace('"', '\\"')
    release_date = movie.get('release_date', '1900-01-01')
    poster_path = movie.get('poster_path', '')
    poster_url = f"https://image.tmdb.org/t/p/w500{poster_path}" if poster_path else ""
    vote_average = float(movie.get('vote_average', 0)) / 2
    
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
    if len(sys.argv) < 2:
        print("🎬 TMDB Movie Search & Add Tool")
        print("=" * 50)
        print("Usage: python3 search_and_add_movie.py 'movie name'")
        print()
        print("Examples:")
        print("  python3 search_and_add_movie.py 'titanic'")
        print("  python3 search_and_add_movie.py 'lord of the rings'")
        print("  python3 search_and_add_movie.py 'your favorite movie'")
        sys.exit(1)
    
    query = ' '.join(sys.argv[1:])
    
    print(f"🔍 Searching TMDB for: '{query}'...")
    print()
    
    movies = search_tmdb(query)
    
    if not movies:
        print("❌ No movies found!")
        print("Try a different search term.")
        sys.exit(1)
    
    print(f"✅ Found {len(movies)} movies!")
    print()
    
    # Display results
    for i, movie in enumerate(movies[:10], 1):
        title = movie.get('title', 'Unknown')
        year = movie.get('release_date', '')[:4]
        rating = movie.get('vote_average', 0)
        print(f"{i}. {title} ({year}) - Rating: {rating}/10")
    
    print()
    print("Adding all results to your database...")
    print()
    
    # Add all found movies
    added = 0
    for movie in movies[:10]:
        if create_movie_in_neo4j(movie):
            added += 1
            title = movie.get('title', 'Unknown')
            print(f"✅ Added: {title}")
    
    print()
    print("=" * 50)
    print(f"✅ Successfully added {added} movies!")
    print("🎬 Refresh your browser at http://localhost:4200")

if __name__ == "__main__":
    main()
