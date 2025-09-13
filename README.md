Follow the steps below to setup and run the project on your local machine.
# Project setup

### 1. Install docker and docker-compose
- Docker : https://docs.docker.com/desktop/setup/install/windows-install/
- Docker compose : https://docs.docker.com/compose/install/

### 2. Clone the backend repo
1. Ensure you have `git` installed and run from your terminal:
    ```bash
    git clone https://github.com/slihatim/ine-alumni-backend.git
    ```
2. Go inside the directory with:
    ```bash
    cd ine-alumni-backend
    ```

# Run the project
1. Run first the containers for `postgres` database, and `pgadmin` (for the administration of the database), by running:
    ```bash
    docker-compose up -d
    ```
2. Then you need to create a database named `ine` inside the postgres DB
    ```bash
    docker exec -it postgres bash
    psql -U hatim
    > CREATE DATABASE ine;
    ```
3. Now open the project with your IDE:
   - If using `Intellij`, just click on the play button on top right of the IDE.
   - If using other IDEs, run from the terminal:
    ```bash
    ./mvnw spring-boot:run
    ```
If you want to re-run the project, clean the target directory first by running:
```bash
./mvnw clean
```
and then run the previous command.

# Formatting
1. To check if your code is formatted run:
    ```bash
    ./mvnw spotless:check
    ```
2. To format your code:
    ```bash
    ./mvnw spotless:apply
    ```

## Environment variables and procedure

Before running the backend locally you should create a local `.env` file from the provided template and fill in secrets (database password, JWT secret, etc.). The project provides a `.env-template` with examples.

1. Copy the template to a local `.env` file:

```bash
cp .env-template .env
```

2. Edit `.env` and set the real values for:
- `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`
- `APP_JWT_SECRET` and `APP_JWT_EXPIRATION_MS` (use a strong secret for production)
- `SERVER_PORT` (optional)

Notes:
- `.env` is listed in `.gitignore` to avoid committing secrets — keep it private.
- If your environment does not automatically load `.env` into environment variables, use a tool like `direnv`, `env-cmd`, or configure your IDE to load env files.

# API Documentation
- To access Swagger API documentation, head to:
    ```http://localhost:8080/swagger-ui.html```

# Pre-commit Setup
Pre-commit hooks help maintain code quality by running checks before each commit.

1. Install pre-commit:
    ```bash
    pip install pre-commit
    ```

2. Install the hooks:
    ```bash
    pre-commit install
    ```

The configured hooks will:
- Check for trailing whitespace and fix file endings
- Validate YAML files
- Prevent large files (>1MB) from being committed
- Check for debug logs in Java code
- Look for sensitive information in config files
- Automatically format code using Spotless
- Ensure the code compiles successfully
- Run tests before pushing (pre-push hook)
