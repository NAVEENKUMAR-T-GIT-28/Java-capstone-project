# Coursera Capstone Submission Guide (Q1–Q26)

Complete each item using the live repository and local app. The repository contains rubric-focused implementation files, but only live GitHub and local execution can generate the required links, screenshots, and real outputs.

## Q1–Q12: GitHub links

Use the branch URL below once the branch is pushed:

`https://github.com/NAVEENKUMAR-T-GIT-28/Java-capstone-project/tree/Java-capstone-project`

Direct link patterns (replace `Java-capstone-project` with the exact branch in each URL):

1. Q1 Issues: `https://github.com/NAVEENKUMAR-T-GIT-28/Java-capstone-project/issues` — create the three issue stories from `docs/github-issues.md` first.
2. Q2: `.../blob/Java-capstone-project/docs/schema-design.md`
3. Q3: `.../blob/Java-capstone-project/src/main/java/com/clinic/entity/Doctor.java`
4. Q4: `.../blob/Java-capstone-project/src/main/java/com/clinic/entity/Appointment.java`
5. Q5: `.../blob/Java-capstone-project/src/main/java/com/clinic/controller/DoctorController.java`
6. Q6: `.../blob/Java-capstone-project/src/main/java/com/clinic/service/AppointmentService.java`
7. Q7: `.../blob/Java-capstone-project/src/main/java/com/clinic/controller/PrescriptionController.java`
8. Q8: `.../blob/Java-capstone-project/src/main/java/com/clinic/repository/PatientRepository.java`
9. Q9: `.../blob/Java-capstone-project/src/main/java/com/clinic/service/TokenService.java`
10. Q10: `.../blob/Java-capstone-project/src/main/java/com/clinic/service/DoctorService.java`
11. Q11: `.../blob/Java-capstone-project/Dockerfile`
12. Q12: `.../blob/Java-capstone-project/.github/workflows/ci.yml`

## Q13–Q18: six genuine UI screenshots

Run `mvn spring-boot:run`, open the route in the browser at 100% zoom, ensure browser content is visible and no developer tools/credentials are exposed unnecessarily, and save PNG screenshots to a local folder. Capture the live app; do not submit mock-ups.

- Q13: `/login-admin` — admin login page.
- Q14: `/login-doctor` — doctor login page.
- Q15: `/login-patient` — patient login page.
- Q16: `/admin` — submit the Add a doctor form, then show the success message and new doctor in the directory.
- Q17: `/patient` — enter `Asha` or `Cardiology` in search; show matching doctor results.
- Q18: `/doctor` — show the scheduled appointment table (default H2 seed data contains a demo appointment).

## Q19–Q23: actual MySQL outputs

Start the full stack with `docker compose up --build`, then run these commands against the Compose MySQL database. If initialization ran previously on a non-empty volume, remember that changing init scripts does not rerun them automatically.

```sql
SHOW TABLES;
SELECT * FROM patients LIMIT 5;
CALL GetDailyAppointmentReportByDoctor('2026-10-10');
CALL GetDoctorWithMostPatientsByMonth(10, 2026);
CALL GetDoctorWithMostPatientsByYear(2026);
```

Copy the terminal output exactly, including column headings and row counts. If a procedure returns no rows, check the sample data and date/year before submitting.

## Q24–Q26: actual API outputs

Run the app on port 8080. Capture the following commands' real output:

```bash
curl -X GET http://localhost:8080/api/doctors

TOKEN=$(curl -sS -X POST http://localhost:8080/api/auth/token \
  -H 'Content-Type: application/json' \
  -d '{"email":"patient@clinic.test","password":"Patient123!"}' | python3 -c 'import json,sys; print(json.load(sys.stdin)["token"])')
curl -X GET http://localhost:8080/api/appointments/patient/1 -H "Authorization: Bearer $TOKEN"

curl -X GET 'http://localhost:8080/api/doctors/search?specialty=Cardiology'
```

Copy the response body from each command. For Q25, replace the placeholder token with a valid token from the local auth endpoint. Do not submit an invalid or expired token.

## Before clicking Submit

- Verify that the public repository and target branch are accessible in a private/incognito browser window.
- Check each direct link opens the intended file on the target branch.
- Create the three GitHub issues so the Q1 Issues URL contains them.
- Make sure the six screenshot files are readable and correspond to the actual app.
- Use actual SQL and API outputs rather than examples from documentation.
- Confirm the Maven CI workflow passes on the published branch.
