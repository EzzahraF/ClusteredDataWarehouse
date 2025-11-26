# Clustered Data Warehouse

## Overview

This project is a **Spring Boot application** designed to handle **FX (foreign exchange) deal records**. The main goal was to **receive, validate, store, and manage FX deals** in a PostgreSQL database while ensuring **duplicate prevention, error logging, and data persistence**.

It simulates a **real production environment** with Dockerized PostgreSQL, unit testing, and mutation testing for code quality.

---

## Features

* CRUD operations for FX deals
* Bulk insert and CSV upload
* Validation of FX deals using annotations
* Prevention of duplicate deals
* Error logging for invalid data
* REST APIs:

  * `GET /api/deals` — List all deals
  * `POST /api/deals` — Add a single deal
  * `POST /api/deals/bulk` — Add multiple deals
  * `POST /api/deals/upload/csv` — Upload deals from CSV
* Unit testing with **H2 in-memory database**
* Mutation testing with **PIT**
* Dockerized PostgreSQL for persistent storage

---

## Technologies Used

* **Java 17**
* **Spring Boot 3.2** (Web, JPA, Validation)
* **PostgreSQL**
* **H2 Database** (for tests)
* **OpenCSV** (for CSV parsing)
* **JUnit 5** and **Mockito** (unit testing)
* **PIT** (mutation testing)
* **Docker & Docker Compose** (PostgreSQL persistence)
* **Lombok** (reducing boilerplate code)

---

## Assignment Requirements & Implementation

| Requirement           | How it was implemented                                                           |
| --------------------- | -------------------------------------------------------------------------------- |
| Receive FX deals      | REST APIs (`POST /api/deals`, `/bulk`, `/upload/csv`)                            |
| Validate deals        | Bean Validation annotations (`@NotBlank`, `@NotNull`, `@Positive`)               |
| Save deals in DB      | Spring Data JPA with PostgreSQL                                                  |
| Skip duplicates       | `existsByDealId` method in repository before saving                              |
| Log errors            | Standard Spring logging (`Logger`) for invalid deals                             |
| Unit testing          | `FxDealRepositoryTest` with H2 database to test repository logic                 |
| Mutation testing      | PIT plugin to check test coverage and strength                                   |
| Data persistence      | Dockerized PostgreSQL with volume (`-v pgdata:/var/lib/postgresql/data`)         |
| Optional improvements | Considered resilience (Resilience4j + HikariCP) for production-grade DB handling |

---

## Setup Instructions

### Prerequisites

* Java 17
* Maven
* Docker & Docker Compose

### Clone the repository

```bash
git clone https://github.com/EzzahraF/ClusteredDataWarehouse.git
cd ClusteredDataWarehouse
```

### Run PostgreSQL with Docker

```bash
docker run --name pg-fxdeals \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=postadmin1234 \
  -e POSTGRES_DB=mydb \
  -p 5432:5432 \
  -v pgdata:/var/lib/postgresql/data \
  -d postgres:latest
```

### Configure Spring Boot

In `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=admin
spring.datasource.password=postadmin1234
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Run the application

```bash
./mvnw spring-boot:run
```

### Run Unit Tests

```bash
./mvnw test
```

### Run Mutation Tests

```bash
./mvnw pitest:mutationCoverage
```

Reports will be generated under `target/pit-reports/index.html`.

---

## Testing

* **Unit Tests:** Verify repository and service logic using H2 in-memory DB.
* **Mutation Testing (PIT):** Measures the effectiveness of tests by generating small code mutations and checking if tests catch them.
* **Build Status:** Tests pass successfully, ensuring correctness of implemented logic.

---

## How I Worked

1. **Database setup:** PostgreSQL in Docker with persistent volume to mimic production environment.
2. **Spring Boot application:** REST APIs, validation, repository, service, and DTOs implemented.
d **Resilience4j** with retries and circuit breakers for robust PostgreSQL connections.
3. **Unit testing:** H2 in-memory database used for testing repository methods.
4. **Mutation testing:** Added PIT plugin to measure test strength and identify gaps.
5. **Git workflow:** Committed incrementally, resolved conflicts using `git pull --rebase`, pushed to GitHub.

