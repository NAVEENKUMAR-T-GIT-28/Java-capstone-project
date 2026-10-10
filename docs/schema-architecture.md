# Smart Clinic Management System — Architecture

## Overview

The application follows a three-tier architecture. The presentation tier is server-rendered with Thymeleaf and browser JavaScript; the application tier is a Spring Boot MVC/REST service; the data tier uses MySQL for relational clinic records and MongoDB for prescription documents.

## Seven-step request sequence

1. **Browser / presentation layer** — an Admin, Doctor, or Patient opens a portal and submits a form or action. The browser validates required fields for usability; server-side validation remains authoritative.
2. **HTTP request** — the browser sends an HTTP request to a Spring MVC controller. REST endpoints exchange JSON; page routes return Thymeleaf templates.
3. **Controller validation and routing** — the controller resolves the route and path/query parameters, applies `@Valid` to request bodies, and returns an appropriate HTTP status and response body.
4. **Service/business layer** — the controller calls a service such as `AppointmentService` or `DoctorService`. The service enforces scheduling rules, checks availability, prevents duplicate time-slot bookings, and applies credential/token rules.
5. **Repository/data access layer** — the service uses a Spring Data JPA repository for relational records or a Spring Data MongoDB repository for prescription documents. Controllers do not build SQL directly.
6. **Database operation** — MySQL persists users, doctors, patients, appointments, and doctor time slots with foreign keys. MongoDB stores flexible prescription documents. A transaction is used for appointment booking operations.
7. **Response and UI update** — the repository result returns through the service and controller as JSON or a rendered page. The browser displays success/error feedback or refreshes the directory/schedule.

## Logical component flow

```text
Admin / Doctor / Patient browser
              |
       Thymeleaf + JavaScript
              |
 Spring MVC page and REST controllers
              |
   Services + JWT token service
              |
     Spring Data repositories
           /       \
        MySQL     MongoDB
   clinic records  prescriptions
```

## Main responsibility boundaries

- **Controllers:** HTTP routing, request validation, status codes, and response serialization.
- **Services:** scheduling rules, doctor availability, credential checks, and appointment workflows.
- **Repositories:** persistence queries through Spring Data.
- **MySQL:** normalized user, doctor, patient, appointment, and availability records.
- **MongoDB:** prescription documents that can contain medication and instruction details.

## Security and deployment notes

JWTs are signed with an HS256 key derived from `CLINIC_JWT_SECRET`. The provided secret default is only for local coursework and must be replaced for any deployed environment. Demo login credentials are seeded in development for repeatable screenshots. A production system would also require role-enforced authorization on every administrative and clinical endpoint, audit logging, secrets management, TLS, rate limiting, and a formal privacy/security review.
