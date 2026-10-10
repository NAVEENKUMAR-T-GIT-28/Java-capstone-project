package com.clinic.repository;
import com.clinic.entity.Appointment;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient_IdAndAppointmentTimeBetweenOrderByAppointmentTimeAsc(Long patientId, LocalDateTime start, LocalDateTime end);
    List<Appointment> findByDoctor_IdAndAppointmentTimeBetweenOrderByAppointmentTimeAsc(Long doctorId, LocalDateTime start, LocalDateTime end);
    List<Appointment> findByPatient_IdOrderByAppointmentTimeAsc(Long patientId);
    List<Appointment> findByDoctor_IdOrderByAppointmentTimeAsc(Long doctorId);
    boolean existsByDoctor_IdAndAppointmentTime(Long doctorId, LocalDateTime appointmentTime);
}
