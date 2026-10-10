INSERT INTO doctors (id, user_id, name, email, phone, specialization) VALUES
 (1, NULL, 'Dr. Asha Raman', 'asha.raman@clinic.test', '9000000001', 'Cardiology'),
 (2, NULL, 'Dr. Kiran Kumar', 'kiran.kumar@clinic.test', '9000000002', 'Dermatology')
ON DUPLICATE KEY UPDATE name=VALUES(name);
INSERT INTO doctor_available_times (doctor_id, available_time) VALUES
 (1,'09:00:00'),(1,'10:00:00'),(1,'11:00:00'),(1,'17:00:00'),(1,'18:00:00'),
 (2,'13:00:00'),(2,'14:00:00'),(2,'15:00:00')
ON DUPLICATE KEY UPDATE available_time=VALUES(available_time);
INSERT INTO patients (id, user_id, name, email, phone) VALUES
 (1,NULL,'Demo Patient','patient@clinic.test','9111111111'),
 (2,NULL,'Sam Patient','sam.patient@clinic.test','9222222222'),
 (3,NULL,'Priya Patient','priya.patient@clinic.test','9333333333'),
 (4,NULL,'Ravi Patient','ravi.patient@clinic.test','9444444444'),
 (5,NULL,'Meera Patient','meera.patient@clinic.test','9555555555')
ON DUPLICATE KEY UPDATE name=VALUES(name);
INSERT INTO appointments (id, doctor_id, patient_id, appointment_time, status) VALUES
 (1,1,1,'2026-10-10 17:00:00','BOOKED'),
 (2,1,2,'2026-10-10 18:00:00','BOOKED'),
 (3,2,3,'2026-10-10 14:00:00','BOOKED'),
 (4,1,4,'2026-10-11 09:00:00','BOOKED')
ON DUPLICATE KEY UPDATE status=VALUES(status);
