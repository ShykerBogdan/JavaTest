# Docker Development Setup for Java Smart Contract Deploy

This guide explains how to use Docker for development and debugging with VS Code without installing JDK locally.

## Setup Instructions

### Prerequisites
- Docker and Docker Compose installed on your machine
- VS Code with the following extensions:
  - Remote Development extension pack
  - Java Extension Pack

### Directory Structure
- `Dockerfile`: Defines the Java 11 environment with debugging enabled
- `docker-compose.yml`: Configures the application and PostgreSQL services
- `dev-config/`: Contains VS Code configuration files that you'll need to copy to your `.vscode` directory

## Getting Started

1. **Copy VS Code configuration files**

   Copy the configuration files from `dev-config/` to your `.vscode/` directory:
   ```
   mkdir -p .vscode
   cp dev-config/launch.json .vscode/
   cp dev-config/tasks.json .vscode/
   ```

2. **Start the Docker environment**

   Start the containers:
   ```
   docker compose up --build
   ```
   
   Alternatively, in VS Code:
   - Open the Command Palette (Cmd+Shift+P)
   - Select "Tasks: Run Task"
   - Choose "Docker Compose Up"

3. **Debugging**

   To debug the application:
   - Set breakpoints in your Java code
   - In VS Code, go to the Run and Debug view (Cmd+Shift+D)
   - Select "Debug (Attach) - Docker" from the dropdown
   - Click the green play button

   VS Code will attach to the remote JVM running in the Docker container and your breakpoints will be hit.

4. **Stopping the environment**

   To stop all containers:
   ```
   docker compose down
   ```
   
   Alternatively, in VS Code:
   - Open the Command Palette (Cmd+Shift+P)
   - Select "Tasks: Run Task"
   - Choose "Docker Compose Down"

## Development Workflow

1. Make changes to your Java code
2. Docker will not automatically reload your changes - you'll need to restart the container:
   ```
   docker compose restart app
   ```
3. Reattach the debugger if needed

## Troubleshooting

- **Connection refused when attaching debugger**: Make sure the application container is fully started before attaching. You should see "Started ContractRegistryApplication" in the Docker logs.
- **Changes not reflected**: Remember that you need to restart the app container after code changes.
- **Database connection issues**: Ensure PostgreSQL container is up and running with `docker compose ps`.
