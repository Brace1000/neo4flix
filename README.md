# Neo4flix - Movie Recommendation Engine

A comprehensive movie recommendation system built with Neo4j, Spring Boot microservices, Angular, and Docker.

## Features

### Core Functionality
- ✅ User authentication with JWT
- ✅ Two-factor authentication (2FA) with QR code
- ✅ Movie browsing and search
- ✅ Advanced filtering (genre, release date, title)
- ✅ Movie rating system
- ✅ Personalized recommendations (Collaborative Filtering + Content-Based)
- ✅ Watchlist management
- ✅ Share recommendations with friends

### Security
- ✅ JWT-based authentication
- ✅ Two-factor authentication (2FA)
- ✅ Password complexity requirements
- ✅ HTTPS ready (SSL certificate support)
- ✅ Secure password hashing (BCrypt)

### Architecture
- ✅ Microservices architecture
- ✅ Neo4j graph database
- ✅ Spring Boot backend
- ✅ Angular 17 frontend with standalone components
- ✅ Docker containerization
- ✅ RESTful APIs

## Prerequisites

- Docker & Docker Compose
- Java 17+ (for local development)
- Node.js 18+ (for local development)
- Maven 3.9+ (for local development)

## Quick Start

### 1. Clone the repository
```bash
git clone <repository-url>
cd neo4flix
```

### 2. Build and run with Docker
```bash
docker-compose up --build
```

### 3. Access the application
- Frontend: http://localhost:4200
- Neo4j Browser: http://localhost:7474
- User Service: http://localhost:8081
- Movie Service: http://localhost:8082
- Rating Service: http://localhost:8083
- Recommendation Service: http://localhost:8084

### 4. Default Neo4j Credentials
- Username: `neo4j`
- Password: `password`

## Architecture

### Microservices

#### 1. User Service (Port 8081)
- User registration and authentication
- JWT token generation
- Two-factor authentication (2FA)
- Watchlist management
- CRUD operations for users

#### 2. Movie Service (Port 8082)
- Movie CRUD operations
- Search functionality (title, genre, date)
- Top-rated movies
- Movie filtering

#### 3. Rating Service (Port 8083)
- Rating CRUD operations
- User ratings management
- Movie ratings aggregation
- Automatic average rating calculation

#### 4. Recommendation Service (Port 8084)
- Collaborative filtering algorithm
- Content-based filtering
- Hybrid recommendations
- Genre-based filtering

### Database Schema

#### Neo4j Graph Model
```
(User)-[:RATED {score, review, createdAt}]->(Movie)
(User)-[:WATCHLIST]->(Movie)
```

#### Node Properties
- **User**: id, username, email, password, twoFactorSecret, twoFactorEnabled, createdAt
- **Movie**: id, title, description, releaseDate, genres[], posterUrl, averageRating, ratingCount

## API Documentation

### User Service

#### Register
```http
POST /api/users/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "SecurePass123!"
}
```

#### Login
```http
POST /api/users/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "SecurePass123!",
  "twoFactorCode": "123456"
}
```

#### Enable 2FA
```http
POST /api/users/{userId}/2fa/enable
Authorization: Bearer <token>
```

### Movie Service

#### Get All Movies
```http
GET /api/movies
```

#### Search Movies
```http
GET /api/movies/search?query=inception
```

#### Search by Genre
```http
GET /api/movies/search/genre?genre=Action
```

#### Create Movie
```http
POST /api/movies
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Inception",
  "description": "A thief who steals corporate secrets...",
  "releaseDate": "2010-07-16",
  "genres": ["Action", "Sci-Fi", "Thriller"],
  "posterUrl": "https://example.com/poster.jpg"
}
```

### Rating Service

#### Create Rating
```http
POST /api/ratings
Authorization: Bearer <token>
Content-Type: application/json

{
  "userId": "user-id",
  "movieId": "movie-id",
  "score": 4.5,
  "review": "Great movie!"
}
```

### Recommendation Service

#### Get Recommendations
```http
GET /api/recommendations/user/{userId}?limit=10
Authorization: Bearer <token>
```

#### Get Recommendations by Genre
```http
GET /api/recommendations/user/{userId}/genre/Action?limit=10
Authorization: Bearer <token>
```

## Development

### Backend Development

#### Build Common Module
```bash
cd backend/common
mvn clean install
```

#### Run User Service
```bash
cd backend/user-service
mvn spring-boot:run
```

### Frontend Development

#### Install Dependencies
```bash
cd frontend
npm install
```

#### Run Development Server
```bash
npm start
```

Access at http://localhost:4200

## Testing

### Manual Testing Checklist

#### Authentication
- [ ] User registration with valid credentials
- [ ] User registration with invalid credentials (weak password)
- [ ] User login with correct credentials
- [ ] User login with incorrect credentials
- [ ] 2FA setup and verification
- [ ] JWT token expiration handling

#### Movie Operations
- [ ] Browse all movies
- [ ] Search movies by title
- [ ] Filter movies by genre
- [ ] Filter movies by release date
- [ ] View movie details
- [ ] Create new movie (admin)

#### Rating System
- [ ] Rate a movie (1-5 stars)
- [ ] Add review to rating
- [ ] View user's ratings
- [ ] View movie's ratings
- [ ] Update existing rating

#### Recommendations
- [ ] View personalized recommendations
- [ ] Filter recommendations by genre
- [ ] Verify collaborative filtering works
- [ ] Verify content-based filtering works

#### Watchlist
- [ ] Add movie to watchlist
- [ ] Remove movie from watchlist
- [ ] View watchlist
- [ ] Empty watchlist handling

### Security Testing
- [ ] SQL injection attempts
- [ ] XSS attempts
- [ ] CSRF protection
- [ ] JWT token tampering
- [ ] Unauthorized access attempts
- [ ] Password strength validation

### Performance Testing
```bash
# Use Apache Bench for stress testing
ab -n 1000 -c 10 http://localhost:8082/api/movies
```

## Deployment

### Production Considerations

1. **SSL/TLS Configuration**
   - Use Let's Encrypt for free SSL certificates
   - Configure HTTPS in nginx

2. **Environment Variables**
   - Store sensitive data in environment variables
   - Use Docker secrets for production

3. **Database Backup**
   - Regular Neo4j backups
   - Backup strategy implementation

4. **Monitoring**
   - Application logs
   - Performance metrics
   - Error tracking

## Troubleshooting

### Common Issues

#### Docker containers not starting
```bash
docker-compose down -v
docker-compose up --build
```

#### Neo4j connection refused
- Ensure Neo4j container is running
- Check Neo4j logs: `docker logs neo4flix-neo4j`

#### Frontend can't connect to backend
- Verify all services are running
- Check CORS configuration
- Verify API URLs in environment.ts

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

MIT License

## Authors

Neo4flix Development Team
