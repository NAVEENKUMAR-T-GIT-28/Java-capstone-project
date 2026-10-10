#!/usr/bin/env bash
set -euo pipefail
BASE_URL="${BASE_URL:-http://localhost:8080}"
mkdir -p artifacts
OUT="artifacts/api-outputs.txt"
: > "$OUT"
run_capture() {
  local label="$1"; shift
  printf '\n===== %s =====\n' "$label" | tee -a "$OUT"
  "$@" 2>&1 | tee -a "$OUT"
}
run_capture 'Q24: GET /api/doctors' curl -sS -X GET "$BASE_URL/api/doctors"
TOKEN="$(curl -sS -X POST "$BASE_URL/api/auth/token" -H 'Content-Type: application/json' -d '{"email":"patient@clinic.test","password":"Patient123!"}' | python3 -c 'import json,sys; print(json.load(sys.stdin)["token"])')"
run_capture 'Q25: GET /api/appointments/patient/1' curl -sS -X GET "$BASE_URL/api/appointments/patient/1" -H "Authorization: Bearer $TOKEN"
run_capture 'Q26: GET /api/doctors/search?specialty=Cardiology' curl -sS -X GET "$BASE_URL/api/doctors/search?specialty=Cardiology"
printf '\nSaved actual responses to %s\n' "$OUT"
