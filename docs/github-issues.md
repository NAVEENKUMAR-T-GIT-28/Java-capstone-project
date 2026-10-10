# GitHub issue templates for Q1 (create these as three separate issues)

The Coursera rubric asks for three role-oriented user stories. Create these three issues in the repository's **Issues** tab and paste the Issues URL into Q1. The GitHub integration available to this workspace is read-only for repository writes, so the issues are provided here for manual creation.

## Issue 1 — Admin: Manage the doctor directory

**User story**

As an **Admin**, I want to add and maintain doctor profiles with contact details and specializations, so that patients can find the correct clinicians and the clinic can keep its directory current.

**Acceptance criteria**

- A doctor profile requires a name, valid email, phone, and specialization.
- Duplicate email addresses are rejected with a clear response.
- Successfully created doctors appear in the directory and patient search.

## Issue 2 — Doctor: Review daily appointments

**User story**

As a **Doctor**, I want to view my scheduled patient appointments by date, so that I can prepare for the clinic day and manage my consultation schedule.

**Acceptance criteria**

- The doctor can view appointment time, patient name, patient email, and status.
- Appointments can be queried by doctor and date.
- Available time slots exclude slots already booked for that date.

## Issue 3 — Patient: Find a doctor and book an appointment

**User story**

As a **Patient**, I want to search for doctors by name or specialization and see their available times, so that I can choose an appropriate clinician and request an appointment.

**Acceptance criteria**

- Search supports a doctor's name and/or specialty.
- Availability is returned for a selected date.
- Appointment requests reject past times, unknown IDs, unavailable slots, and already-booked slots.
