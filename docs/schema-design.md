# Smart Clinic Management System — Schema Design

Relational data is stored in MySQL. Primary keys are generated numeric IDs. All timestamps are handled as local clinic times in this coursework demo; a production application should define and persist an explicit clinic timezone.

## 1. `users`

| Column | Type | Constraints | Purpose |
|---|---|---|---|
| `id` | BIGINT | PK, auto-increment | User identifier |
| `name` | VARCHAR(120) | NOT NULL | Display name |
| `email` | VARCHAR(180) | NOT NULL, UNIQUE | Login identifier |
| `password_hash` | VARCHAR(100) | NOT NULL | BCrypt password hash, never plaintext |
| `role` | VARCHAR(20) | NOT NULL | `ADMIN`, `DOCTOR`, or `PATIENT` |

## 2. `doctors`

| Column | Type | Constraints | Purpose |
|---|---|---|---|
| `id` | BIGINT | PK, auto-increment | Doctor identifier |
| `user_id` | BIGINT | FK to `users.id`, UNIQUE, nullable | Optional login profile link |
| `name` | VARCHAR(120) | NOT NULL | Doctor's display name |
| `email` | VARCHAR(180) | NOT NULL, UNIQUE | Professional email |
| `phone` | VARCHAR(30) | NOT NULL | Contact number |
| `specialization` | VARCHAR(100) | NOT NULL | Clinical specialty/search value |

Availability values are stored in the supporting `doctor_available_times` table using the JPA `@ElementCollection` mapping.

## 3. `patients`

| Column | Type | Constraints | Purpose |
|---|---|---|---|
| `id` | BIGINT | PK, auto-increment | Patient identifier |
| `user_id` | BIGINT | FK to `users.id`, UNIQUE, nullable | Optional login profile link |
| `name` | VARCHAR(120) | NOT NULL | Patient name |
| `email` | VARCHAR(180) | NOT NULL, UNIQUE | Patient email |
| `phone` | VARCHAR(30) | NOT NULL, UNIQUE | Patient phone |

## 4. `appointments`

| Column | Type | Constraints | Purpose |
|---|---|---|---|
| `id` | BIGINT | PK, auto-increment | Appointment identifier |
| `doctor_id` | BIGINT | NOT NULL, FK to `doctors.id` | Assigned clinician |
| `patient_id` | BIGINT | NOT NULL, FK to `patients.id` | Patient being seen |
| `appointment_time` | DATETIME | NOT NULL, indexed | Appointment date and time |
| `status` | VARCHAR(30) | NOT NULL | `BOOKED`, `CANCELLED`, etc. |

## Supporting `doctor_available_times`

| Column | Type | Constraints | Purpose |
|---|---|---|---|
| `doctor_id` | BIGINT | FK to `doctors.id`, part of PK | Owner of the time slot |
| `available_time` | TIME | part of PK, NOT NULL | Daily recurring time-of-day slot |

JPA mappings: `Appointment` has `@ManyToOne` relationships to `Doctor` and `Patient`; `Doctor` has an `@ElementCollection` for available times. `Doctor` and `Patient` can each link to one `User` profile through an optional `@OneToOne` relationship.

## MongoDB

Database: `cms`; collection: `prescriptions`.

A prescription document has `_id`, `patientEmail`, `doctorEmail`, `medication`, `instructions`, and `createdAt`. Emails and medication are validated at the REST boundary. MongoDB is used for flexible prescription content; relational identity and appointment data remain in MySQL.
