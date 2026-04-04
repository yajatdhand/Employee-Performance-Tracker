# Employee-Performance-Tracker

## Overview
A production-ready Spring Boot backend for an internal HR tool designed to help managers track and review employee performance. The system manages employees, review cycles, goals, and performance reviews, exposing clean RESTful APIs while preventing common database bottlenecks like N+1 query issues and unindexed full-table scans.

## Tech Stack
* **Java 17**
* **Spring Boot 3.3.x** (Web, Data JPA, Validation, Cache)
* **Database:** H2 (In-memory for development)
* **Migrations:** Flyway
* **Connection Pool:** HikariCP
* **Build Tool:** Maven

## Getting Started

### Prerequisites
* Java 17 installed
* Maven 3.8+ installed

### Running Locally (Development Mode)
The application is configured to run out-of-the-box with zero infrastructure setup using an in-memory H2 database and Spring's Simple Cache.

```bash
mvn clean install
mvn spring-boot:run
```
Once running, the H2 database console is available at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:trackerdb`, Username: `sa`, Password: *leave blank*).

## API Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **POST** | `/employees` | Create a new employee. |
| **POST** | `/reviews` | Submit a performance review. *Requires `Idempotency-Key` header.* |
| **GET** | `/employees/{id}/reviews` | Fetch an employee and all their reviews across cycles. |
| **GET** | `/cycles/{id}/summary` | Get average rating, top performer, and goal counts for a cycle. |
| **GET** | `/employees` | Filter employees by department and minimum rating (Paginated). |

## Key Engineering Decisions

* **Atomic Idempotency:** The `POST /reviews` endpoint is protected against network retries/accidental double-clicks using a thread-safe `ConcurrentHashMap` (which would be replaced by Redis in production). It implements an atomic lock that releases immediately if the database transaction fails.
* **N+1 Query Prevention:** The `GET /employees/{id}/reviews` endpoint utilizes a custom JPQL `JOIN FETCH` to grab the employee, their reviews, and the cycle details in a single database round-trip, avoiding the lazy-loading proxy trap.
* **Pagination for Memory Safety:** The `/employees` filter endpoint relies on Spring Data's `Pageable` to prevent OutOfMemory (OOM) errors when querying massive departments.
* **Smart Cache Invalidation:** Creating a new review explicitly triggers a `@CacheEvict` for both the specific employee's review history and the aggregate cycle summary, preventing managers from seeing stale data.
