[![Test Count](https://gist.githubusercontent.com/atorfan/6e6ebc0bea68c8d2115d0cb9de886276/raw/badge.svg)](https://github.com/atorfan/influ-news/actions)

# Image Rescale Application

To serve Images to clients on different devices, we decided to create a simple service that dynamically rescales images to different resolutions.

We are going to implement a functional PoC for the team, allowing them to test it as soon as possible.


## Prerequisites 📝

- Docker
- Docker Compose
- Java 21

## Available Commands ⌘

```bash
make help                         # Display all available commands with descriptions
```

## Standalone Application Lifecycle Management ☕

To be able to run the application locally, you'll need to have running some infrastructure services:

1. **Project Initialization**
   ```bash
   make init                      # Copy .env and .env.dev files from samples if they don't exist
   ```
   This creates:
   - `.env` in main project directory, if missing (to test it without docker containers)
   - `.env.dev` in main project directory, if missing (to test it with docker containers)


2. **LocalStack** (Refer to [the root documentation](../../README.md#aws-localstack-management) for more information about how to start it).<br>
   Once LocalStack is running, execute this command to create the needed resources:
   ```bash
   make localstack-init           # 🪣 Creates the S3 Bucket 'rescaled-images' in LocalStack
   ```

3. **PostgreSQL Database**
   ```bash
   make database-up               # 🐘 Build and starts a docker image with PostgreSQL database
   ```

4. **Build the application**
   ```bash
   make clean                     # 🗑️ Erase the build/ directory
   ```
   ```bash
   make build                     # 📦 Build the .jar
   ```
   ```bash
   make build-skipping-tests      # 📦 Build the .jar (skipping tests)
   ```

5. **Run the application**
   ```bash
   make run                       # 🚀 Run the app with 'dev' profile
   ```
   The application will be available at `http://localhost:8080/`


## Dockerized Application Lifecycle Management 🐳

1. **Build the Docker image**
   ```bash
   make dk-build                  # 📦 Build docker image
   ```
   ```bash
   make dk-build-skip-tests       # 📦 Build docker image (skipping tests)
   ```
2. **Run the Docker container**
   ```bash
   make up                        # 🚀 Start application container
   ```
   The application will be available at `http://localhost:8080/`

3. **Stop the Docker container**
   ```bash
   make down                      # 🛑 Stop application container
   ```

4. **Remove the Docker image**
   ```bash
   make dk-rmi                    # 🧹 Remove docker image
   ```

## Testing 🧪

```bash
make tests                        # ✅ Run all tests (unit + integration)
```
```bash
make unit-tests                   # ✅ Run unit tests only 
```
```bash
make integration-tests            # ✅ Run integration tests only
```

**Test Patterns:**
- **Unit tests**: the ones located at the packages that matches the pattern `com.newsnow.*.core.*`
- **Integration tests**: located at the packages that matches the pattern `com.newsnow.*.adapters.*`, `com.newsnow.*.infrastructure.*`, `com.newsnow.*.imagerescale`

## Postman 📬

You can find a [Postman collection in this folder](./postman) with the local environment configuration. Just import into Postman and start testing the API.

## URLs of interest 🔗

When the container is running, you can access:
- **Application Health Check**: http://localhost:8080/health
- **API Documentation**: http://localhost:8080/swagger-ui/index.html
- **Manual testing**: Just open [this HTML file](./uploader.html) in a browser and start interacting.<br>
  It's a simple static frontend application to test the image rescale service in a more user-friendly way.


## Versioned Builds (CI/CD) 📦
```bash
make dk-build-version IMAGE_VERSION=1.0.0 PROFILE=prod
make dk-push IMAGE_VERSION=1.0.0 PROFILE=prod
```

### Environment Configuration

The application supports multiple environments through profiles:
- **dev**: Development (default)
- **prod**: Production environment

Environment files are automatically created from samples during `make init`.

## Troubleshooting ⚠️

### Common Issues

1. **Port conflicts**: Ensure ports 8080 (app) and PostgreSQL port aren't in use
2. **Missing .env files**: Run `make init` to create from samples
3. **Docker issues**: Ensure Docker daemon is running
4. **Java version**: Verify Java 21 is installed and active
5. **Configured URLs**: Review the configured URLs in the .env files

### Cleanup Commands

```bash
# Stop all containers
make down                 # Stop app containers
make database-down        # Stop PostgreSQL database

# Clean builds
make clean                # Clean Gradle build
make dk-rmi               # Remove Docker images
```

## Configuration Files ⚙️

- **LocalStack**:
  - `etc/docker/localstack`
- **PostgreSQL**:
  - `etc/docker/postgres`
- **App Container**:
  - `etc/docker/docker-compose.yml`
  - `etc/docker/docker-compose.override.yml`
  - `etc/docker/docker-compose.dev.yml`
  - `etc/docker/image-rescale/docker-compose.dev.yml`
- **Environment**:
  - `.env`
  - `.env.dev`
  - `.env-sample`
