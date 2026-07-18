# 🎟️ Event Ticket Platform Backend
> A secure and scalable REST API for event management and ticket booking, built with **Spring Boot**, **Keycloak**, **OAuth2 Resource Server**, **JWT**, **PostgreSQL**, and **Swagger/OpenAPI**.
>
> ![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-brightgreen)
![Spring Security](https://img.shields.io/badge/Spring%20Security-OAuth2-success)
![Keycloak](https://img.shields.io/badge/Keycloak-Identity%20Provider-blue)
![JWT](https://img.shields.io/badge/JWT-Authentication-yellow)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-green)
![MapStruct](https://img.shields.io/badge/MapStruct-Mapping-red)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED)



> ## 📖 Overview
The **Event Ticket Platform Backend** is a secure RESTful API that enables organizers to create and manage events while allowing users to browse events, purchase tickets, and validate entry using QR codes.

The application follows a layered architecture and demonstrates modern backend development practices by integrating **Spring Security**, **OAuth2 Resource Server**, and **Keycloak** for authentication and authorization. After successful authentication, users are automatically provisioned into the application's database, allowing business logic to remain independent of the external identity provider.

The project emphasizes clean architecture, secure API design, and maintainable code through the use of DTOs, MapStruct, global exception handling, and Spring Data JPA.

## ✨ Key Features

### 🔐 Secure Authentication & Authorization
- OAuth2 Resource Server with JWT-based authentication
- Keycloak integration for identity and access management
- Automatic user provisioning on first successful authentication
- Role-based endpoint protection using Spring Security

### 🎟️ Event Management
- Create, update, and manage events
- Publish events for public access
- Retrieve detailed event information

### 🎫 Ticket Management
- Create multiple ticket types for an event
- Purchase tickets for published events
- Track ticket availability and prevent overselling

### 📱 QR Code Ticket Validation
- Generate QR codes for purchased tickets
- Validate tickets through QR code scanning
- Prevent duplicate ticket usage during validation

### 📖 API Documentation
- Interactive API documentation using Swagger/OpenAPI
- Secure API testing with Bearer JWT authentication

### 🏗️ Clean Architecture
- Layered architecture (Controller → Service → Repository)
- DTO-based request and response models
- MapStruct for object mapping
- Centralized exception handling
- Spring Data JPA with PostgreSQL

## 🏛️ System Architecture

```text
                          +-------------------------+
                          |     Client Application  |
                          |  Swagger UI / Postman   |
                          +------------+------------+
                                       |
                                       | HTTP Request
                                       |
                                       ▼
                          +-------------------------+
                          |    Spring Security      |
                          | OAuth2 Resource Server  |
                          +------------+------------+
                                       |
                         Validates JWT Access Token
                                       |
                                       ▼
                          +-------------------------+
                          | UserProvisioningFilter  |
                          +------------+------------+
                                       |
               Creates user in database (first login only)
                                       |
                                       ▼
                    +--------------------------------------+
                    |            Controllers               |
                    +----------------+---------------------+
                                     |
                                     ▼
                    +--------------------------------------+
                    |             Services                 |
                    +----------------+---------------------+
                                     |
                                     ▼
                    +--------------------------------------+
                    |          Repositories                |
                    |         Spring Data JPA              |
                    +----------------+---------------------+
                                     |
                                     ▼
                          +-------------------------+
                          |      PostgreSQL         |
                          +-------------------------+
```

The application follows a layered architecture where every request first passes through **Spring Security** for authentication and authorization.

Once a JWT is successfully validated, the `UserProvisioningFilter` ensures that authenticated users are automatically synchronized with the application's database before any business logic is executed.

Business operations are organized into dedicated **Controller**, **Service**, and **Repository** layers, promoting separation of concerns, maintainability, and testability.

## 🔐 Authentication Flow

The application uses **Keycloak** as the Identity Provider and **Spring Security OAuth2 Resource Server** to authenticate and authorize incoming requests.

```text
                 +----------------------+
                 |        User          |
                 +----------+-----------+
                            |
                            | Login
                            ▼
                 +----------------------+
                 |      Keycloak        |
                 | Identity Provider    |
                 +----------+-----------+
                            |
                     Issues JWT Token
                            |
                            ▼
                 +----------------------+
                 | Swagger UI / Postman |
                 +----------+-----------+
                            |
             Authorization: Bearer <JWT>
                            |
                            ▼
                 +----------------------+
                 |  Spring Security     |
                 | OAuth2 Resource      |
                 | Server               |
                 +----------+-----------+
                            |
                  JWT Signature Validation
                            |
                            ▼
                 +----------------------+
                 | UserProvisioningFilter|
                 +----------+-----------+
                            |
         Creates user on first authenticated request
                            |
                            ▼
                 +----------------------+
                 |   Business APIs      |
                 +----------------------+
```

### Authentication Workflow

1. The user authenticates with **Keycloak**.
2. Keycloak issues a signed **JWT Access Token**.
3. The client sends the JWT using the `Authorization: Bearer <token>` header.
4. Spring Security validates the JWT before the request reaches the application.
5. On the first authenticated request, the `UserProvisioningFilter` creates the corresponding user record in the application's database.
6. The request is forwarded to the Controller only after successful authentication and user provisioning.

## 👥 Role-Based Access Control

The application uses **Keycloak Realm Roles** to control access to protected resources. Each endpoint is secured using Spring Security based on the authenticated user's assigned role.

| Role | Responsibilities | Example Endpoints |
|------|------------------|-------------------|
| **Organizer** | Create and manage events, publish events, manage ticket types | `POST /api/v1/events`, `POST /api/v1/ticket-types` |
| **Attendee** | Browse events and purchase tickets | `GET /api/v1/published-events`, `POST /api/v1/tickets` |
| **Staff** | Validate tickets during event check-in | `POST /api/v1/ticket-validation` |

### Assigning Roles in Keycloak

1. Open your Keycloak Admin Console.
2. Navigate to **Users**.
3. Select the desired user.
4. Open the **Role Mapping** tab.
5. Assign the required realm role (`Organizer`, `Attendee`, or `Staff`).
6. Request a new access token after updating roles.

## 🛠️ Tech Stack

| Category | Technology |
|----------|------------|
| **Language** | Java 21 |
| **Framework** | Spring Boot |
| **Security** | Spring Security, OAuth2 Resource Server |
| **Identity Provider** | Keycloak |
| **Authentication** | JWT (JSON Web Token) |
| **Database** | PostgreSQL |
| **Persistence** | Spring Data JPA, Hibernate |
| **API Documentation** | Swagger / OpenAPI |
| **Object Mapping** | MapStruct |
| **Build Tool** | Maven |
| **Containerization** | Docker Compose |


## 📂 Project Structure

```text
src
├── main
│   ├── java
│   │   └── com.navneet.event_ticket_platform
│   │       ├── Advices
│   │       ├── Configs
│   │       ├── Controllers
│   │       ├── Exceptions
│   │       ├── Filter
│   │       ├── Repo
│   │       ├── Services
│   │       ├── domain
│   │       │   ├── DTOs
│   │       │   ├── Entities
│   │       │   └── Enums
│   │       ├── mappers
│   │       └── util
│   └── resources
│       ├── application.properties
│       └── META-INF
```

The project follows a layered architecture that separates business logic, persistence, configuration, security, and API contracts. This organization improves maintainability, readability, and scalability while keeping responsibilities clearly defined.

## 🚀 Getting Started

Follow these steps to set up and run the project locally.

### 📋 Prerequisites

Make sure you have the following installed:

- Java 21
- Maven 3.9+
- PostgreSQL
- Docker & Docker Compose
- Keycloak
- Git

---

## 📥 Clone the Repository

```bash
git clone https://github.com/Navneet3112/event-ticket-platform-backend.git

cd event-ticket-platform-backend
```

---

## ⚙️ Configure the Database

Create a PostgreSQL database:

```text
event_ticket_platform
```

Create a file named:

```text
src/main/resources/application-secret.properties
```

Add your local database credentials:

```properties
spring.datasource.username=YOUR_DATABASE_USERNAME
spring.datasource.password=YOUR_DATABASE_PASSWORD
```

> **Note:** This file is ignored by Git to prevent committing sensitive credentials.

---

## 🔐 Configure Keycloak

Create a Keycloak Realm:

```text
event-ticket-platform
```

Create a client:

```text
event-ticket-platform-app
```

Configure the client as follows:

- Client Authentication: OFF
- Standard Flow: ON
- Direct Access Grants: ON (for API testing)
- Authorization: OFF

Configure the application to use your Keycloak server by updating the issuer URI if required.

---

## 🐳 Start Required Services

Start PostgreSQL and Keycloak:

```bash
docker compose up -d
```

---

## ▶️ Run the Application

Using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

---

## 📖 Access Swagger UI

After the application starts successfully:

```text
http://localhost:8080/swagger-ui/index.html
```

Authorize using a valid JWT access token obtained from Keycloak.

---

## 🧪 Test the APIs

Typical workflow:

1. Authenticate using Keycloak
2. Obtain an Access Token
3. Click **Authorize** in Swagger
4. Paste the JWT
5. Test secured endpoints


## ⭐ Project Highlights

This project was designed with production-oriented backend practices rather than focusing solely on CRUD operations.

### 🔑 Keycloak-Based Authentication
Authentication is delegated to **Keycloak**, allowing the application to focus on business logic while relying on a dedicated Identity Provider for user authentication and access management.

### 🛡️ Stateless JWT Security
The application uses **Spring Security OAuth2 Resource Server** to validate JWT access tokens, enabling stateless authentication without maintaining server-side sessions.

### 👤 Automatic User Provisioning
When a user successfully authenticates for the first time, their profile is automatically synchronized with the application's database through a custom `UserProvisioningFilter`. This allows the application to maintain domain-specific user information while Keycloak manages identity.

### 🏗️ Layered Architecture
The project follows a clean layered architecture consisting of Controllers, Services, Repositories, DTOs, and Mappers, improving maintainability and separation of concerns.

### ⚡ DTO-Based API Design
Request and response DTOs are used throughout the application to avoid exposing persistence entities directly and to provide a clean API contract.

### 🔄 Object Mapping with MapStruct
MapStruct is used for compile-time object mapping, reducing boilerplate code and improving performance compared to reflection-based mappers.

### 📖 Interactive API Documentation
Swagger/OpenAPI provides interactive documentation, allowing secured endpoints to be tested directly using Bearer JWT authentication.

## 📈 Future Enhancements

Planned improvements include:

- Email notifications for ticket confirmations
- Payment gateway integration
- Redis caching for frequently accessed data
- Unit and integration testing
- CI/CD pipeline using GitHub Actions
- Docker deployment for production

## 👨‍💻 Author

**Navneet Singh**

- GitHub: https://github.com/Navneet3112
- LinkedIn: https://www.linkedin.com/in/navneet-singh-63a62531a/

## 📄 License

This project is licensed under the **MIT License**.

You are free to use, modify, and distribute this project in accordance with the terms of the MIT License. See the `LICENSE` file for more details.

## 🙏 Acknowledgements

This project was built as part of a guided learning journey using a YouTube tutorial on building an Event Ticket Platform with Spring Boot and Keycloak.

While the tutorial provided the initial project direction, I independently implemented, debugged, configured, and documented the application to deepen my understanding of Spring Security, OAuth2, JWT authentication, Keycloak integration, Docker, and REST API development.
