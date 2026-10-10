package com.clinic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments", indexes = {
    @Index(name = "idx_appointment_time", columnList = "appointment_time"),
    @Index(name = "idx_appointment_doctor", columnList = "doctor_id"),
    @Index(name = "idx_appointment_patient", columnList = "patient_id")
})
public class Appointment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "doctor_id", nullable = false) @NotNull private Doctor doctor;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "patient_id", nullable = false) @NotNull private Patient patient;
    @NotNull @Future @Column(name = "appointment_time", nullable = false) private LocalDateTime appointmentTime;
    @Column(nullable = false) private String status = "BOOKED";
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public Doctor getDoctor(){return doctor;} public void setDoctor(Doctor doctor){this.doctor=doctor;}
    public Patient getPatient(){return patient;} public void setPatient(Patient patient){this.patient=patient;}
    public LocalDateTime getAppointmentTime(){return appointmentTime;} public void setAppointmentTime(LocalDateTime appointmentTime){this.appointmentTime=appointmentTime;}
    public String getStatus(){return status;} public void setStatus(String status){this.status=status;}
}
