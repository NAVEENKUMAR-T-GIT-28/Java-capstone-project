package com.clinic.service;

import com.clinic.entity.Appointment;
import com.clinic.entity.Doctor;
import com.clinic.repository.AppointmentRepository;
import com.clinic.repository.DoctorRepository;
import com.clinic.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DoctorService {
    private final DoctorRepository doctors; private final AppointmentRepository appointments;
    private final UserRepository users; private final PasswordEncoder encoder;
    public DoctorService(DoctorRepository doctors, AppointmentRepository appointments, UserRepository users, PasswordEncoder encoder) {
        this.doctors=doctors; this.appointments=appointments; this.users=users; this.encoder=encoder;
    }
    @Transactional(readOnly = true)
    public Set<java.time.LocalTime> getAvailableTimes(Long doctorId, LocalDate date) {
        Doctor doctor = doctors.findById(doctorId).orElseThrow(() -> new IllegalArgumentException("Doctor not found: " + doctorId));
        Set<java.time.LocalTime> available = new java.util.TreeSet<>(doctor.getAvailableTimes());
        appointments.findByDoctor_IdAndAppointmentTimeBetweenOrderByAppointmentTimeAsc(
                doctorId, date.atStartOfDay(), date.plusDays(1).atStartOfDay())
                .forEach(appointment -> available.remove(appointment.getAppointmentTime().toLocalTime()));
        return available;
    }
    public boolean validateCredentials(String email, String rawPassword) {
        return users.findByEmail(email).filter(u -> "DOCTOR".equalsIgnoreCase(u.getRole()))
                .map(u -> encoder.matches(rawPassword, u.getPasswordHash())).orElse(false);
    }
}
