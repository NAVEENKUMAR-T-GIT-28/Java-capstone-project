package com.clinic.service;

import com.clinic.dto.AppointmentRequest;
import com.clinic.entity.Appointment;
import com.clinic.entity.Doctor;
import com.clinic.entity.Patient;
import com.clinic.repository.AppointmentRepository;
import com.clinic.repository.DoctorRepository;
import com.clinic.repository.PatientRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointments; private final DoctorRepository doctors; private final PatientRepository patients; private final DoctorService doctorService;
    public AppointmentService(AppointmentRepository appointments, DoctorRepository doctors, PatientRepository patients, DoctorService doctorService) {
        this.appointments=appointments; this.doctors=doctors; this.patients=patients; this.doctorService=doctorService;
    }
    @Transactional
    public Appointment bookAppointment(AppointmentRequest request) {
        Doctor doctor=doctors.findById(request.getDoctorId()).orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        Patient patient=patients.findById(request.getPatientId()).orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        if (!doctorService.getAvailableTimes(doctor.getId(), request.getAppointmentTime().toLocalDate()).contains(request.getAppointmentTime().toLocalTime()))
            throw new IllegalArgumentException("Selected time is not available for this doctor on that date");
        if (appointments.existsByDoctor_IdAndAppointmentTime(doctor.getId(), request.getAppointmentTime()))
            throw new IllegalArgumentException("The selected slot has already been booked");
        Appointment appointment=new Appointment(); appointment.setDoctor(doctor); appointment.setPatient(patient); appointment.setAppointmentTime(request.getAppointmentTime()); appointment.setStatus("BOOKED");
        return appointments.save(appointment);
    }
    @Transactional(readOnly = true)
    public List<Appointment> findAppointmentsForPatientOnDate(Long patientId, LocalDate date) {
        return appointments.findByPatient_IdAndAppointmentTimeBetweenOrderByAppointmentTimeAsc(patientId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());
    }
    @Transactional(readOnly = true)
    public List<Appointment> findAppointmentsForDoctorOnDate(Long doctorId, LocalDate date) {
        return appointments.findByDoctor_IdAndAppointmentTimeBetweenOrderByAppointmentTimeAsc(doctorId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());
    }
    @Transactional(readOnly = true) public List<Appointment> findByPatientId(Long id) { return appointments.findByPatient_IdOrderByAppointmentTimeAsc(id); }
    @Transactional(readOnly = true) public List<Appointment> findByDoctorId(Long id) { return appointments.findByDoctor_IdOrderByAppointmentTimeAsc(id); }
}
