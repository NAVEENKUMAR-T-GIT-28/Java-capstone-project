#!/usr/bin/env bash
set -euo pipefail
mkdir -p artifacts
OUT="artifacts/sql-outputs.txt"
PASSWORD="${MYSQL_PASSWORD:-cms-local-password-change-me}"
: > "$OUT"
docker compose exec -T mysql mysql -t -v -ucms "-p${PASSWORD}" cms 2>&1 <<'SQL' | tee "$OUT"
SHOW TABLES;
SELECT * FROM patients LIMIT 5;
CALL GetDailyAppointmentReportByDoctor('2026-10-10');
CALL GetDoctorWithMostPatientsByMonth(10, 2026);
CALL GetDoctorWithMostPatientsByYear(2026);
SQL
printf '\nSaved actual MySQL output to %s\n' "$OUT"
