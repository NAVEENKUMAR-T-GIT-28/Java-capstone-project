package com.clinic.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "prescriptions")
public class Prescription {
    @Id private String id;
    @Email @NotBlank private String patientEmail;
    @Email @NotBlank private String doctorEmail;
    @NotBlank private String medication;
    private String instructions;
    private Instant createdAt = Instant.now();
    public String getId(){return id;} public void setId(String id){this.id=id;}
    public String getPatientEmail(){return patientEmail;} public void setPatientEmail(String patientEmail){this.patientEmail=patientEmail;}
    public String getDoctorEmail(){return doctorEmail;} public void setDoctorEmail(String doctorEmail){this.doctorEmail=doctorEmail;}
    public String getMedication(){return medication;} public void setMedication(String medication){this.medication=medication;}
    public String getInstructions(){return instructions;} public void setInstructions(String instructions){this.instructions=instructions;}
    public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant createdAt){this.createdAt=createdAt;}
}
