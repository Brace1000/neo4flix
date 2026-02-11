#!/bin/bash

echo "Loading sample movie data into Neo4j..."

# Wait for Neo4j to be ready
echo "Waiting for Neo4j to be ready..."
sleep 5

# Load the data using cypher-shell
sudo docker exec neo4flix-neo4j cypher-shell -u neo4j -p password -f /var/lib/neo4j/import/init-data.cypher 2>/dev/null

# Alternative: Load via HTTP API
curl -X POST http://localhost:7474/db/neo4j/tx/commit \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic $(echo -n 'neo4j:password' | base64)" \
  -d @- << 'EOF'
{
  "statements": [
    {
      "statement": "CREATE (m1:Movie {id: '1', title: 'The Matrix', description: 'A computer hacker learns about the true nature of reality and his role in the war against its controllers.', releaseDate: date('1999-03-31'), genres: ['Action', 'Sci-Fi'], posterUrl: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg', averageRating: 4.5, ratingCount: 0})"
    },
    {
      "statement": "CREATE (m2:Movie {id: '2', title: 'Inception', description: 'A thief who steals corporate secrets through dream-sharing technology is given the inverse task of planting an idea.', releaseDate: date('2010-07-16'), genres: ['Action', 'Sci-Fi', 'Thriller'], posterUrl: 'https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg', averageRating: 4.8, ratingCount: 0})"
    },
    {
      "statement": "CREATE (m3:Movie {id: '3', title: 'The Dark Knight', description: 'When the menace known as the Joker wreaks havoc on Gotham, Batman must accept one of the greatest tests.', releaseDate: date('2008-07-18'), genres: ['Action', 'Crime', 'Drama'], posterUrl: 'https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg', averageRating: 4.9, ratingCount: 0})"
    },
    {
      "statement": "CREATE (m4:Movie {id: '4', title: 'Interstellar', description: 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity survival.', releaseDate: date('2014-11-07'), genres: ['Sci-Fi', 'Drama'], posterUrl: 'https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg', averageRating: 4.7, ratingCount: 0})"
    },
    {
      "statement": "CREATE (m5:Movie {id: '5', title: 'Pulp Fiction', description: 'The lives of two mob hitmen, a boxer, a gangster and his wife intertwine in four tales of violence.', releaseDate: date('1994-10-14'), genres: ['Crime', 'Drama'], posterUrl: 'https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg', averageRating: 4.6, ratingCount: 0})"
    },
    {
      "statement": "CREATE (m6:Movie {id: '6', title: 'The Shawshank Redemption', description: 'Two imprisoned men bond over years, finding solace and eventual redemption through acts of common decency.', releaseDate: date('1994-09-23'), genres: ['Drama'], posterUrl: 'https://image.tmdb.org/t/p/w500/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg', averageRating: 4.9, ratingCount: 0})"
    },
    {
      "statement": "CREATE (m7:Movie {id: '7', title: 'Forrest Gump', description: 'The presidencies of Kennedy and Johnson unfold through the perspective of an Alabama man.', releaseDate: date('1994-07-06'), genres: ['Drama', 'Romance'], posterUrl: 'https://image.tmdb.org/t/p/w500/saHP97rTPS5eLmrLQEcANmKrsFl.jpg', averageRating: 4.7, ratingCount: 0})"
    },
    {
      "statement": "CREATE (m8:Movie {id: '8', title: 'The Godfather', description: 'The aging patriarch of an organized crime dynasty transfers control to his reluctant son.', releaseDate: date('1972-03-24'), genres: ['Crime', 'Drama'], posterUrl: 'https://image.tmdb.org/t/p/w500/3bhkrj58Vtu7enYsRolD1fZdja1.jpg', averageRating: 4.9, ratingCount: 0})"
    },
    {
      "statement": "CREATE (m9:Movie {id: '9', title: 'Fight Club', description: 'An insomniac office worker and a devil-may-care soap maker form an underground fight club.', releaseDate: date('1999-10-15'), genres: ['Drama'], posterUrl: 'https://image.tmdb.org/t/p/w500/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg', averageRating: 4.6, ratingCount: 0})"
    },
    {
      "statement": "CREATE (m10:Movie {id: '10', title: 'Goodfellas', description: 'The story of Henry Hill and his life in the mob, covering his relationship with his wife.', releaseDate: date('1990-09-19'), genres: ['Crime', 'Drama'], posterUrl: 'https://image.tmdb.org/t/p/w500/aKuFiU82s5ISJpGZp7YkIr3kCUd.jpg', averageRating: 4.7, ratingCount: 0})"
    }
  ]
}
EOF

echo ""
echo "✅ Sample movie data loaded!"
echo "Refresh your browser at http://localhost:4200"
