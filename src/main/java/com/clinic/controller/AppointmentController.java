package com.clinic.controller;

import com.clinic.dto.AppointmentRequest;
import com.clinic.entity.Appointment;
import com.clinic.entity.Patient;
import com.clinic.repository.PatientRepository;
import com.clinic.service.AppointmentService;
import com.clinic.service.TokenService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointments; private final PatientRepository patients; private final TokenService tokens;
    public AppointmentController(AppointmentService appointments, PatientRepository patients, TokenService tokens){this.appointments=appointments;this.patients=patients;this.tokens=tokens;}
    @PostMapping public ResponseEntity<?> book(@Valid @RequestBody AppointmentRequest request) {
        try { return ResponseEntity.status(HttpStatus.CREATED).body(appointments.bookAppointment(request)); }
        catch (IllegalArgumentException ex) { return ResponseEntity.badRequest().body(Map.of("error",ex.getMessage())); }
    }
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> forPatient(@PathVariable Long patientId, @RequestHeader(value="Authorization",required=false) String authorization) {
        String email=subjectFrom(authorization); if(email==null) return ResponseEntity.status(401).body(Map.of("error","A valid Bearer token is required"));
        Patient patient=patients.findById(patientId).orElse(null);
        if(patient==null) return ResponseEntity.notFound().build();
        if(!patient.getEmail().equalsIgnoreCase(email)) return ResponseEntity.status(403).body(Map.of("error","Token does not belong to this patient"));
        return ResponseEntity.ok(appointments.findByPatientId(patientId));
    }
    @GetMapping("/doctor/{doctorId}") public ResponseEntity<List<Appointment>> forDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(appointments.findByDoctorId(doctorId));
    }
    @GetMapping("/doctor/{doctorId}/date/{date}") public ResponseEntity<List<Appointment>> forDoctorOnDate(@PathVariable Long doctorId,@PathVariable LocalDate date) {
        return ResponseEntity.ok(appointments.findAppointmentsForDoctorOnDate(doctorId,date));
    }
    @GetMapping("/patient/{patientId}/date/{date}") public ResponseEntity<List<Appointment>> forPatientOnDate(@PathVariable Long patientId,@PathVariable LocalDate date) {
        return ResponseEntity.ok(appointments.findAppointmentsForPatientOnDate(patientId,date));
    }
    private String subjectFrom(String authorization) {
        if(authorization==null || !authorization.startsWith("Bearer ")) return null;
        String token=authorization.substring(7).trim(); if(!tokens.isValid(token)) return null;
        try{return tokens.extractSubject(token);}catch(RuntimeException ex){return null;}
    }
}
