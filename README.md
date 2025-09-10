
# influ-news

NewsNow wants to set up a website to gain wider audiences.

We need an MVP of a simple system that will serve news to users.

We foresee that there will be huge traffic spikes since we will be relying heavily on influencers to create content and advertise it.

If you want documentation about the system, check [this based on C4 model](docs/ARCHITECTURE.md).


# Development Setup and Commands

This repository follows a monorepo structure, and each application has its own documentation to provide a
comprehensive guide to running, testing, and working with the application using the provided Makefiles.

- **Root Makefile**: Located at the project root, manages cross-infrastructure services (e.g.: AWS LocalStack).
- **Application Makefile**: Located in `apps/${APP_NAME}/`, manages the application we want to develop.


## Root Project

### Prerequisites

- Docker
- Docker Compose

### Available Commands

```bash
make help                 # Display all available commands with descriptions
```

### AWS LocalStack Management

A fully functional local AWS cloud stack allows us to test our application without having to create and configure AWS resources.

You may need to have it running locally to run some services. These are the steps to follow:

1. Root Project Initialization
   ```bash
   make init                 # Copy .env files from samples if they don't exist
   ```

2. Build the LocalStack image
   ```bash
   make localstack-build     # 📦 Build LocalStack docker image
   ```

3. Start LocalStack services
   ```bash
   make localstack-up        # 🚀 Start LocalStack container
   ```

4. Stop services when done
   ```bash
   make localstack-down      # 🛑 Stop LocalStack container
   ```
