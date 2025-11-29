#!/bin/bash

# Load environment variables from .env file
if [ -f .env ]; then
    export $(cat .env | grep -v '^#' | xargs)
fi

# Start Docker containers
echo "Starting Docker containers..."
docker-compose up -d --build

# Wait for PostgreSQL to be ready
echo "Waiting for PostgreSQL to be ready..."
timeout 60 bash -c 'until docker exec postgres pg_isready -U hatim 2>/dev/null; do echo "Waiting..."; sleep 2; done'
echo "✓ PostgreSQL is ready!"

# Wait a bit more to ensure full initialization
sleep 3

# Create database if it doesn't exist
echo "Creating database if not exists..."
docker exec postgres psql -U hatim -tc "SELECT 1 FROM pg_database WHERE datname = 'ine_alumni_db'" | grep -q 1
if [ $? -ne 0 ]; then
    echo "Creating ine_alumni_db database..."
    docker exec postgres psql -U hatim -c "CREATE DATABASE ine_alumni_db;"
    echo "✓ Database created!"
else
    echo "✓ Database already exists!"
fi

# Run Flyway migrations
echo "Running database migrations..."
docker-compose exec backend ./mvnw flyway:migrate

# Additional wait to ensure database is fully ready
sleep 2

# Wait for backend container to be ready
echo "Waiting for backend to start..."
timeout 120 bash -c 'until docker-compose logs backend 2>&1 | grep -q "Started IneAlumniBackendApplication"; do echo "Waiting..."; sleep 3; done'
echo "✓ Backend is ready!"

echo ""
echo "========================================="
echo "✓ All services are running!"
echo "========================================="
echo ""
echo "Backend API:  http://localhost:8080"
echo "Swagger UI:   http://localhost:8080/swagger-ui.html"
echo "PgAdmin:      http://localhost:5050"
echo ""