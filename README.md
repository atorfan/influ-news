# influ-news

NewsNow wants to set up a website to gain wider audiences.

We need an MVP of a simple system that will serve news to users.

We foresee that there will be huge traffic spikes since we will be relying heavily on influencers to create content and advertise it.

If you want documentation about the system, check [this based on C4 model](docs/ARCHITECTURE.md).

----

Also, in order to serve Images to clients on different devices, we decided to create a simple service that dynamically rescales images to different resolutions.

We are going to implement a functional PoC for team allowing them to test it as soon as possible.

You can check it at [apps/image-rescale](apps/image-rescale) folder.

# Development Setup and Commands

This section provides a comprehensive guide to running, testing, and working with the applications in this monorepo using the provided Makefiles.

## Project Structure

The repository follows a monorepo structure:
- **Root Makefile**: Located at the project root, manages AWS LocalStack infrastructure (and could manage more in the future).
- **Application Makefile**: Located in `apps/${APP_NAME}/`, manages the application we want to develop.

## Prerequisites

### Root Project Requirements
- Docker and Docker Compose

## Root Project Commands (Infrastructure)

The root Makefile manages AWS LocalStack for local development with AWS services.

### Available Commands

```bash
make help                 # Display all available commands with descriptions
```
#### Project Initialization
```bash
make init                 # Copy .env files from samples if they don't exist
```
This creates:
- `.env` in project root (if missing)

### AWS LocalStack Management

```bash
make localstack-build     # 📦 Build AWS LocalStack docker image
make localstack-up        # 🚀 Start LocalStack container 'local-aws'
make localstack-down      # 🛑 Stop LocalStack container 'local-aws'
```

### LocalStack Workflow

1. **Build the LocalStack image:**
   ```bash
   make localstack-build
   ```

2. **Start LocalStack services:**
   ```bash
   make localstack-up
   ```

4. **Stop services when done:**
   ```bash
   make localstack-down
   ```
