DROP PROCEDURE IF EXISTS GetDailyAppointmentReportByDoctor;
DROP PROCEDURE IF EXISTS GetDoctorWithMostPatientsByMonth;
DROP PROCEDURE IF EXISTS GetDoctorWithMostPatientsByYear;
DELIMITER //
CREATE PROCEDURE GetDailyAppointmentReportByDoctor(IN report_date DATE)
BEGIN
  SELECT d.id AS doctor_id, d.name AS doctor_name, d.specialization,
         COUNT(a.id) AS appointment_count
  FROM doctors d
  LEFT JOIN appointments a ON a.doctor_id = d.id AND DATE(a.appointment_time) = report_date
  GROUP BY d.id, d.name, d.specialization
  ORDER BY appointment_count DESC, d.name;
END //
CREATE PROCEDURE GetDoctorWithMostPatientsByMonth(IN report_month INT, IN report_year INT)
BEGIN
  SELECT d.id AS doctor_id, d.name AS doctor_name,
         COUNT(DISTINCT a.patient_id) AS patient_count
  FROM doctors d
  JOIN appointments a ON a.doctor_id = d.id
  WHERE MONTH(a.appointment_time) = report_month AND YEAR(a.appointment_time) = report_year
  GROUP BY d.id, d.name
  ORDER BY patient_count DESC, d.name
  LIMIT 1;
END //
CREATE PROCEDURE GetDoctorWithMostPatientsByYear(IN report_year INT)
BEGIN
  SELECT d.id AS doctor_id, d.name AS doctor_name,
         COUNT(DISTINCT a.patient_id) AS patient_count
  FROM doctors d
  JOIN appointments a ON a.doctor_id = d.id
  WHERE YEAR(a.appointment_time) = report_year
  GROUP BY d.id, d.name
  ORDER BY patient_count DESC, d.name
  LIMIT 1;
END //
DELIMITER ;
