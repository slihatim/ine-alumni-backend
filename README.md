# INE Alumni Backend

Backend API for the INE Alumni platform built with Spring Boot.

## Prerequisites

- **Docker** (version 20.10 or higher)
- **Docker Compose** (version 2.0 or higher)

For local development (without Docker):
- **Java 17** or higher
- **Maven 3.9** or higher
- **PostgreSQL 15** (if running database locally)
- **Redis** (if running Redis locally)

## Quick Start with Docker Compose (Recommended)

This is the easiest way to get the backend running. Docker Compose will handle everything automatically.

### 1. Clone the Repository

```bash
git clone https://github.com/slihatim/ine-alumni-backend.git
cd ine-alumni-backend
```

### 2. Configure Environment Variables

Copy the environment template and create your `.env` file:

```bash
cp .env-template .env
```

Edit `.env` and update the following values:

- **Database credentials** (defaults work for Docker Compose):
  ```env
  SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/ine
  SPRING_DATASOURCE_USERNAME=hatim
  SPRING_DATASOURCE_PASSWORD=hatim
  ```

- **JWT Secret** (generate a strong secret for production):
  ```env
  APP_JWT_SECRET=your-strong-secret-here
  APP_JWT_EXPIRATION_MS=86400000
  ```

- **Email settings** (optional, but required for email features):
  ```env
  SPRING_MAIL_USERNAME=your-email@gmail.com
  SPRING_MAIL_PASSWORD=your-app-password
  ```

> **Note:** For Gmail, you'll need to create an [App Password](https://support.google.com/accounts/answer/185833) instead of using your regular password.

### 3. Start All Services

```bash
docker-compose up -d
```

This command will:
- Build the backend application
- Start PostgreSQL database
- Start Redis cache
- Start pgAdmin (database administration tool)
- Automatically create the database
- Wait for services to be healthy before starting the backend

### 4. Verify Deployment

Check that all services are running:

```bash
docker-compose ps
```

You should see all services with status "Up" and "healthy".

### 5. Access the Services

- **Backend API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **pgAdmin**: http://localhost:5050
  - Email: `pgadmin@pgadmin.org` (or your `PGADMIN_DEFAULT_EMAIL`)
  - Password: `admin` (or your `PGADMIN_DEFAULT_PASSWORD`)

### 6. View Logs

```bash
# View all logs
docker-compose logs -f

# View backend logs only
docker-compose logs -f backend

# View database logs
docker-compose logs -f postgres
```

### 7. Stop Services

```bash
docker-compose down
```

To also remove volumes (WARNING: this will delete all data):

```bash
docker-compose down -v
```

## Local Development (Without Docker)

If you prefer to run the backend locally without Docker:

### 1. Setup Database

Start PostgreSQL and Redis using Docker Compose (only database services):

```bash
docker-compose up -d postgres redis pgadmin
```

Or install them locally on your machine.

### 2. Create Database

```bash
docker exec -it postgres psql -U hatim -c "CREATE DATABASE ine;"
```

### 3. Configure Environment

Create a `.env` file (or set environment variables):

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/ine
SPRING_DATASOURCE_USERNAME=hatim
SPRING_DATASOURCE_PASSWORD=hatim
APP_JWT_SECRET=your-secret-here
APP_JWT_EXPIRATION_MS=86400000
SPRING_MAIL_USERNAME=your-email@gmail.com
SPRING_MAIL_PASSWORD=your-app-password
```

### 4. Run the Application

```bash
./mvnw spring-boot:run
```

Or use your IDE (IntelliJ, Eclipse, VS Code) to run the `IneAlumniBackendApplication` class.

## Environment Variables

| Variable | Description | Default (Docker) | Required |
|----------|-------------|-------------------|----------|
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | `jdbc:postgresql://postgres:5432/ine` | Yes |
| `SPRING_DATASOURCE_USERNAME` | Database username | `hatim` | Yes |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `hatim` | Yes |
| `APP_JWT_SECRET` | JWT signing secret | - | Yes |
| `APP_JWT_EXPIRATION_MS` | JWT expiration time (ms) | `86400000` | Yes |
| `SPRING_MAIL_USERNAME` | SMTP email username | - | Optional* |
| `SPRING_MAIL_PASSWORD` | SMTP email password | - | Optional* |
| `PGADMIN_DEFAULT_EMAIL` | pgAdmin login email | `pgadmin@pgadmin.org` | No |
| `PGADMIN_DEFAULT_PASSWORD` | pgAdmin login password | `admin` | No |

*Email settings are required for email verification features but optional for basic API functionality.

## Database Management

### Using pgAdmin

1. Access pgAdmin at http://localhost:5050
2. Login with your credentials
3. Add a new server:
   - **Name**: INE Alumni DB
   - **Host**: `postgres` (or `localhost` if running locally)
   - **Port**: `5432`
   - **Username**: `hatim`
   - **Password**: `hatim`
   - **Database**: `ine`

### Using Command Line

```bash
# Connect to database
docker exec -it postgres psql -U hatim -d ine

# Run SQL script
docker exec -i postgres psql -U hatim -d ine < seed-data.sql
```

## Testing

```bash
# Run all tests
./mvnw test

# Run tests with coverage
./mvnw test jacoco:report
```

## Code Formatting

```bash
# Check code formatting
./mvnw spotless:check

# Format code
./mvnw spotless:apply
```

## Troubleshooting

### Backend won't start

1. **Check logs:**
   ```bash
   docker-compose logs backend
   ```

2. **Verify database is ready:**
   ```bash
   docker-compose ps postgres
   ```
   Should show status "healthy"

3. **Check environment variables:**
   ```bash
   docker-compose config
   ```

### Database connection errors

- Ensure PostgreSQL container is running: `docker-compose ps postgres`
- Check database URL uses `postgres` as hostname (not `localhost`) when using Docker Compose
- Verify credentials match in `.env` file

### Port already in use

If ports 8080, 5432, 6379, or 5050 are already in use:

1. Stop the conflicting service, or
2. Change ports in `docker-compose.yml` and `.env` file

### Rebuild after code changes

```bash
# Rebuild and restart
docker-compose up -d --build backend
```

## API Documentation

Once the backend is running, access the interactive API documentation:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## Security Notes

- **WARNING: Never commit `.env` file** - it contains sensitive credentials
- **Generate strong JWT secrets** for production:
  ```bash
  openssl rand -base64 32
  ```
- **Change default passwords** in production environments

## Project Structure

```
ine-alumni-backend/
├── src/
│   ├── main/
│   │   ├── java/com/ine/backend/
│   │   │   ├── controllers/     # REST controllers
│   │   │   ├── services/        # Business logic
│   │   │   ├── repositories/    # Data access
│   │   │   ├── entities/        # JPA entities
│   │   │   ├── dto/            # Data transfer objects
│   │   │   └── security/       # Security configuration
│   │   └── resources/
│   │       └── application.yml # Application configuration
│   └── test/                   # Test files
├── docker-compose.yml          # Docker Compose configuration
├── Dockerfile                  # Backend Docker image
├── .env-template              # Environment variables template
├── pom.xml                    # Maven dependencies
└── README.md                  # This file
```

## Support

For issues and questions:
- Open an issue on GitHub
- Check existing issues and discussions
- Review the API documentation at `/swagger-ui.html`
