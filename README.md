# 🚚 FleetTrack - Fleet Management System

FleetTrack is a comprehensive, enterprise-grade backend application designed for logistics and delivery companies. It provides a centralized platform to manage vehicle fleets, monitor GPS statuses, automate maintenance schedules, and ensure system security.

## ✨ Key Features

*   🔐 **Robust Security & Authentication:** 
    *   Stateless authentication using **JSON Web Tokens (JWT)** and Role-Based Access Control (RBAC).
    *   **Rate Limiting** implemented via `Bucket4j` on the Login API to prevent Brute-Force and DDoS attacks.
*   📡 **Automated Alert System:** 
    *   Real-time detection of offline vehicles (e.g., GPS signal loss or device tampering).
    *   Asynchronous **Email Notifications** sent to administrators using `Spring Mail`.
*   📊 **Dynamic PDF Reporting:** 
    *   Automated generation of Fleet Summary and Maintenance Due reports using `iTextPDF`.
    *   Reports are returned directly as downloadable byte arrays via REST endpoints.
*   ⚙️ **Scheduled Background Tasks:** 
    *   Cron jobs (`@Scheduled`) to automatically check for vehicles requiring upcoming technical maintenance.
*   📖 **Interactive API Documentation:** 
    *   Fully documented RESTful APIs using **Swagger (OpenAPI 3)**.

## 🛠️ Tech Stack

*   **Language:** Java 21
*   **Framework:** Spring Boot 3.x (Web, Data JPA, Security, Mail, Validation)
*   **Database:** PostgreSQL (with Docker Compose)
*   **Security:** Spring Security, JWT, Bucket4j (Rate Limiting)
*   **Tools & Libraries:** Lombok, MapStruct, iTextPDF (PDF Generation)
*   **API Documentation:** Springdoc OpenAPI (Swagger UI)

## 🚀 Getting Started

### Prerequisites
*   Java 21 or higher
*   Maven 3.6+
*   Docker & Docker Compose (for the database)

### 1. Clone the repository
```bash
git clone [https://github.com/mustafaqasimov/FleetTrack.git](https://github.com/mustafaqasimov/FleetTrack.git)
cd FleetTrack
