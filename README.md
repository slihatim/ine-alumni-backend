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

