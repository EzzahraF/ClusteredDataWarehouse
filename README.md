# Clustered Data Warehouse

## About
This project is a Spring Boot application to handle FX (foreign exchange) deals. The main goal is to receive, check, save, and manage FX deals in a PostgreSQL database. It also prevents duplicates and logs errors automatically.

I built it like a small production setup using Docker, unit tests, and mutation tests to ensure the code works well and is reliable.

---

## Features
* Add, view, and manage FX deals
* Upload multiple deals at once or via CSV files
* Validate deals to make sure all fields are correct
* Skip duplicate deals automatically
* Log errors when data is invalid
* REST APIs:

  * `GET /api/deals` — get all deals
  * `POST /api/deals` — add one deal
  * `POST /api/deals/bulk` — add multiple deals
  * `POST /api/deals/upload/csv` — upload deals from CSV
* Unit testing with H2 in-memory database
* Mutation testing with PIT
* Dockerized PostgreSQL for persistent storage

---

## Technologies
* Java 17
* Spring Boot 3.2 (Web, JPA, Validation)
* PostgreSQL
* H2 database (for testing)
* OpenCSV (for CSV files)
* PIT (mutation testing)
* Docker & Docker Compose

---

## How it Works
I created REST APIs to receive FX deals and validate them using annotations.
The service checks for duplicates before saving to the database.
Errors from invalid deals are logged using Spring’s logger.

For testing, I used H2 in-memory database for unit tests and **PIT** to check the quality of tests.
The database runs in Docker for persistent storage, like in a real production environment.

---

## Setup Instructions

### Prerequisites
* Java 17
* Maven
* Docker & Docker Compose

### Clone the project

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

Edit `application.properties`:
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

### Run tests

```bash
./mvnw test
./mvnw pitest:mutationCoverage
```
Reports for mutation testing are in `target/pit-reports/index.html`.



