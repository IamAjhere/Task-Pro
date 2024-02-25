# Task Pro - Spring Boot Task Manager API

Task Pro is a simple Task Manager application built using 
- Spring Boot
- MariaDB
- Java Persistence API (JPA)

## Prerequisites

Before running the application, ensure that you have the following installed:

- Java 17 
  - download from the [Oracle JDK Download Page](https://www.oracle.com/java/technologies/downloads/#java17).
- Docker and Docker Compose
  - Follow the official Docker installation guide, [Docker Installation Guide](https://docs.docker.com/get-docker/)
  - If you're using Mac or Linux, [Orbstack](https://orbstack.dev/) is recommended.

## Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/IamAjhere/Task-Pro.git
   cd Task-Pro
2. Configure MariaDB using Docker Compose:

    ```bash
   docker-compose up -d
This command will start a local MariaDB container using the provided docker-compose.yml file.

## Running the Application
Once MariaDB is set up and configured, you can run the Task Pro application using the following steps:

1. Build the application:

    ```bash
    ./mvnw clean install
2. Run the application:

    ```bash
    ./mvnw spring-boot:run

The application will be accessible at http://localhost:8080.

## Disclaimer
This project is configured to use create-drop for Hibernate's ddl-auto. It is recommended to change this setting to a more appropriate value for production use.