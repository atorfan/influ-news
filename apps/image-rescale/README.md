
# Image Rescale Application

## Prerequisites

- Java 21
- Docker (for containerized operations)

## Image Rescale Application Commands

```bash
make help                 # Display all available commands with descriptions
```

### Project Initialization
```bash
make init                 # Copy .env files from samples if they don't exist
```
This creates:
- `.env` in main project directory, if missing (develop locally without docker containers)
- `.env.dev` in main project directory, if missing (test it with docker containers)

### Build and Clean
```bash
make clean                # 🗑️ Erase the build/ directory
make build                # 📦 Build the .jar (ignoring tests)
```

### Running the Application
```bash
make run                  # 🚀 Run the app with 'dev' profile
```

The application will be available at `http://localhost:8080/`

### Testing

#### Test Commands
```bash
make tests                # ✅ Run all tests (unit + integration)
make unit-tests           # ✅ Run unit tests only
make integration-tests    # ✅ Run integration tests only
```

**Test Patterns:**
- **Unit tests**: `com.newsnow.*.core.*`
- **Integration tests**: `com.newsnow.*.adapters.*`, `com.newsnow.*.infrastructure.*`, `com.newsnow.*.imagerescale`

### Docker Operations

#### LocalStack Setup
```bash
make dk-localstack-init    # 🚀 Create initial configurations (S3 bucket: rescaled-images)
```

#### Database Setup
```bash
make dk-database-up       # 🐘 Build and start PostgreSQL database container
```

#### Application Container Management
```bash
make dk-build             # 📦 Build docker image (with tests)
make dk-build-skip-tests  # 📦 Build docker image (skipping tests)
make dk-rmi               # 🧹 Remove docker image
```

#### Container Lifecycle
```bash
make up                   # 🚀 Start application container
make down                 # 🛑 Stop application container
```

When the container is running, access:
- **Application**: http://localhost:8080/health
- **API Documentation**: http://localhost:8080/swagger-ui/

#### Versioned Builds (CI/CD)
```bash
make dk-build-version IMAGE_VERSION=1.0.0 PROFILE=prod
make dk-push IMAGE_VERSION=1.0.0 PROFILE=prod
```

## Complete Development Workflow

### First Time Setup

1. **Initialize the project:**
   ```bash
   # From root directory
   make localstack-build
   make localstack-up
   
   # From apps/image-rescale/
   make init
   ```

2. **Build and test the application:**
   ```bash
   cd apps/image-rescale/
   make build
   make tests
   ```

3. **Run locally:**
   ```bash
   make run
   ```

### Docker Development

1. **Start infrastructure:**
   ```bash
   # From root
   make localstack-up
   
   # From apps/image-rescale/
   make dk-init
   ```

2. **Build and run in containers:**
   ```bash
   cd apps/image-rescale/
   make dk-build-skip-tests  # 🔴 skipping tests
   make dk-build             # 🟢 executing tests
   make up
   ```

### Testing Workflow

```bash
cd apps/image-rescale/

# Run specific test types
make unit-tests           # Fast feedback for business logic
make integration-tests    # Test external integrations
make tests                # Complete test suite
```

## Environment Configuration

The application supports multiple environments through profiles:
- **dev**: Development (default)
- **prod**: Production environment

Environment files are automatically created from samples during `make init`.

## Troubleshooting

### Common Issues

1. **Port conflicts**: Ensure ports 8080 (app) and PostgreSQL port aren't in use
2. **Missing .env files**: Run `make init` to create from samples
3. **Docker issues**: Ensure Docker daemon is running
4. **Java version**: Verify Java 21 is installed and active

### Cleanup Commands

```bash
# Stop all containers
make down                 # Stop app containers
make localstack-down      # Stop LocalStack

# Clean builds
make clean                # Clean Gradle build
make dk-rmi               # Remove Docker images
```

## Configuration Files

- **LocalStack**: `etc/docker/localstack`
- **PostgreSQL**: `etc/docker/postgres`
- **App Container**:
  - `etc/docker/docker-compose.yml`
  - `etc/docker/docker-compose.override.yml`
  - `etc/docker/docker-compose.dev.yml`
  - `etc/docker/image-rescale/docker-compose.dev.yml`
- **Environment**: `.env`, `.env.dev`, `.env-sample` files

## Testing with UI

We can use a simple static frontend application to test the image rescale service in a more user-friendly way.

You'll need the service running (locally or in a docker container). Then, just open [this HTML file](./uploader.html) in a browser and start interacting.
