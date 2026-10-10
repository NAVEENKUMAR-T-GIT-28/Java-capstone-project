CREATE TABLE IF NOT EXISTS users (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(120) NOT NULL,
  email VARCHAR(180) NOT NULL UNIQUE,
  password_hash VARCHAR(100) NOT NULL,
  role VARCHAR(20) NOT NULL
);
CREATE TABLE IF NOT EXISTS doctors (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NULL UNIQUE,
  name VARCHAR(120) NOT NULL,
  email VARCHAR(180) NOT NULL UNIQUE,
  phone VARCHAR(30) NOT NULL,
  specialization VARCHAR(100) NOT NULL,
  CONSTRAINT fk_doctor_user FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE IF NOT EXISTS patients (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NULL UNIQUE,
  name VARCHAR(120) NOT NULL,
  email VARCHAR(180) NOT NULL UNIQUE,
  phone VARCHAR(30) NOT NULL UNIQUE,
  CONSTRAINT fk_patient_user FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE IF NOT EXISTS appointments (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  doctor_id BIGINT NOT NULL,
  patient_id BIGINT NOT NULL,
  appointment_time DATETIME NOT NULL,
  status VARCHAR(30) NOT NULL DEFAULT 'BOOKED',
  INDEX idx_appointment_time (appointment_time),
  INDEX idx_appointment_doctor (doctor_id),
  INDEX idx_appointment_patient (patient_id),
  CONSTRAINT fk_appointment_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
  CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id) REFERENCES patients(id)
);
CREATE TABLE IF NOT EXISTS doctor_available_times (
  doctor_id BIGINT NOT NULL,
  available_time TIME NOT NULL,
  PRIMARY KEY (doctor_id, available_time),
  CONSTRAINT fk_available_time_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE
);
