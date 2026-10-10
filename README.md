# Smart Clinic Management System

A rubric-focused implementation of the IBM / Coursera Java Development Capstone Project. The project demonstrates a Spring Boot MVC application, REST APIs, JPA/Hibernate persistence, MySQL schema and stored procedures, MongoDB prescription documents, JWT token generation, Docker, and GitHub Actions CI.

> **Coursework demo, not production healthcare software.** Seed users and the development JWT secret are for local assessment only. Do not deploy with demo credentials or real patient information. The minimal UI is not a substitute for production-grade role-based authorization, audit controls, privacy safeguards, and security review.

## Publish to the requested GitHub branch

This workspace could read repository metadata but its GitHub integration refused repository and issue writes with HTTP 403. After extracting these files, run `./PUSH_TO_GITHUB.ps1` in PowerShell from the project root (with Git for Windows installed and GitHub authentication configured). It commits and pushes only `Java-capstone-project`; it never pushes to `main` and never force-pushes. Then create the three Issues using the templates under `.github/ISSUE_TEMPLATE/`.

## Technology

- Java 17 and Spring Boot 3
- Spring MVC / Thymeleaf and Bean Validation
- Spring Data JPA / Hibernate with MySQL (Docker) or H2 (local quick start)
- Spring Data MongoDB for prescriptions
- JJWT for token generation/verification
- Docker Compose and GitHub Actions

## Quick start: UI and REST API (H2)

Install JDK 17+ and Maven 3.9+. From the repository root:

```bash
mvn clean test
mvn spring-boot:run
```

Open:

- `http://localhost:8080/login-admin`
- `http://localhost:8080/login-doctor`
- `http://localhost:8080/login-patient`
- `http://localhost:8080/admin`
- `http://localhost:8080/patient`
- `http://localhost:8080/doctor`

Demo credentials (development only):

| Role | Email | Password |
|---|---|---|
| Admin | `admin@clinic.test` | `Admin123!` |
| Doctor | `doctor@clinic.test` | `Doctor123!` |
| Patient | `patient@clinic.test` | `Patient123!` |

The H2 profile is used by default so that the UI and API can be explored without a local MySQL install. H2 Console: `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:clinicdb`, user `sa`, empty password). Prescription POST operations require MongoDB; start MongoDB with Docker Compose before testing that feature.

## Full MySQL + MongoDB environment

```bash
docker compose up --build
```

The first MySQL initialization executes `schema.sql`, `data.sql`, and `procedures.sql`. The app starts with the `docker` Spring profile. Default Compose passwords are local placeholders; override `MYSQL_PASSWORD`, `MYSQL_ROOT_PASSWORD`, and `CLINIC_JWT_SECRET` before use. Initialization scripts run only on an empty MySQL volume. To deliberately recreate the local demo database, run `docker compose down -v` and then `docker compose up --build` (this deletes the Compose database volumes).

## API examples

List doctors:

```bash
curl -sS http://localhost:8080/api/doctors
```

Search Cardiology:

```bash
curl -sS 'http://localhost:8080/api/doctors/search?specialty=Cardiology'
```

Get a one-hour JWT for the seeded patient account:

```bash
TOKEN=$(curl -sS -X POST http://localhost:8080/api/auth/token \
  -H 'Content-Type: application/json' \
  -d '{"email":"patient@clinic.test","password":"Patient123!"}' | python3 -c 'import json,sys; print(json.load(sys.stdin)["token"])')
curl -sS http://localhost:8080/api/appointments/patient/1 -H "Authorization: Bearer $TOKEN"
```

Check available times (use a future date for the local demo):

```bash
curl -sS 'http://localhost:8080/api/doctors/1/availability?date=2026-10-11'
```

The patient appointment endpoint requires a valid bearer token whose subject matches the requested patient's email. `TokenService` uses `CLINIC_JWT_SECRET`; set a new random secret of at least 32 UTF-8 bytes outside local coursework.

## SQL assessment commands

With the Compose services running:

```bash
docker compose exec mysql mysql -ucms -pcms-local-password-change-me cms
```

Or run `bash scripts/capture-sql-outputs.sh` to capture the actual results in `artifacts/sql-outputs.txt`. Then copy the *actual output* into the Coursera fields:

```sql
SHOW TABLES;
SELECT * FROM patients LIMIT 5;
CALL GetDailyAppointmentReportByDoctor('2026-10-10');
CALL GetDoctorWithMostPatientsByMonth(10, 2026);
CALL GetDoctorWithMostPatientsByYear(2026);
```

On Windows PowerShell, the Compose default password can be used in the local demo command as `docker compose exec mysql mysql -ucms -pcms-local-password-change-me cms`. Never paste database passwords into a public repository or graded screenshots.

## Rubric file links

| Rubric item | File |
|---|---|
| Q1 | GitHub Issues tab — create the three stories from `docs/github-issues.md` |
| Q2 | [`docs/schema-design.md`](docs/schema-design.md) |
| Q3 | [`Doctor.java`](src/main/java/com/clinic/entity/Doctor.java) |
| Q4 | [`Appointment.java`](src/main/java/com/clinic/entity/Appointment.java) |
| Q5 | [`DoctorController.java`](src/main/java/com/clinic/controller/DoctorController.java) |
| Q6 | [`AppointmentService.java`](src/main/java/com/clinic/service/AppointmentService.java) |
| Q7 | [`PrescriptionController.java`](src/main/java/com/clinic/controller/PrescriptionController.java) |
| Q8 | [`PatientRepository.java`](src/main/java/com/clinic/repository/PatientRepository.java) |
| Q9 | [`TokenService.java`](src/main/java/com/clinic/service/TokenService.java) |
| Q10 | [`DoctorService.java`](src/main/java/com/clinic/service/DoctorService.java) |
| Q11 | [`Dockerfile`](Dockerfile) |
| Q12 | [`.github/workflows/ci.yml`](.github/workflows/ci.yml) |
| 6 UI screenshots | Follow [`docs/submission-guide.md`](docs/submission-guide.md) |
| 5 SQL terminal outputs | Follow [`docs/submission-guide.md`](docs/submission-guide.md) |
| 3 API outputs | Follow [`docs/submission-guide.md`](docs/submission-guide.md) |

## Known scope / assessment notes

- A real browser session and real SQL/API calls are necessary for the six screenshots and eight text-output questions. No screenshots or terminal output have been fabricated in this repository.
- The portal UI is intentionally small and focused on the grading evidence: the three login routes, admin doctor creation, patient doctor search/availability, and doctor appointment list.
- Seed data and fixed SQL report dates are course fixtures, not realistic clinic data.
