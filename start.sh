#!/bin/bash

echo "========================================="
echo "   NEO4FLIX - Movie Recommendation Engine"
echo "========================================="
echo ""

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "❌ Docker is not installed. Please install Docker first."
    exit 1
fi

# Check if Docker Compose is installed (v1 or v2)
if ! command -v docker-compose &> /dev/null && ! docker compose version &> /dev/null; then
    echo "❌ Docker Compose is not installed. Please install Docker Compose first."
    exit 1
fi

echo "✅ Docker and Docker Compose are installed"
echo ""

# Use docker compose (v2) or docker-compose (v1)
if docker compose version &> /dev/null; then
    DOCKER_COMPOSE="docker compose"
else
    DOCKER_COMPOSE="docker-compose"
fi

# Stop and remove existing containers
echo "🧹 Cleaning up existing containers..."
$DOCKER_COMPOSE down -v

echo ""
echo "🏗️  Building and starting services..."
echo "This may take several minutes on first run..."
echo ""

# Build and start all services
$DOCKER_COMPOSE up --build -d

echo ""
echo "⏳ Waiting for services to be ready..."
sleep 30

echo ""
echo "========================================="
echo "   ✅ NEO4FLIX IS READY!"
echo "========================================="
echo ""
echo "📱 Frontend:              http://localhost:4200"
echo "🗄️  Neo4j Browser:        http://localhost:7474"
echo "👤 User Service:          http://localhost:8081"
echo "🎬 Movie Service:         http://localhost:8082"
echo "⭐ Rating Service:        http://localhost:8083"
echo "🎯 Recommendation Service: http://localhost:8084"
echo ""
echo "🔐 Neo4j Credentials:"
echo "   Username: neo4j"
echo "   Password: password"
echo ""
echo "📝 Next Steps:"
echo "   1. Open Neo4j Browser at http://localhost:7474"
echo "   2. Login with credentials above"
echo "   3. Run the init-data.cypher script to load sample movies"
echo "   4. Open the app at http://localhost:4200"
echo "   5. Register a new account and start exploring!"
echo ""
echo "📊 View logs:"
echo "   $DOCKER_COMPOSE logs -f [service-name]"
echo ""
echo "🛑 Stop services:"
echo "   $DOCKER_COMPOSE down"
echo ""
echo "========================================="
