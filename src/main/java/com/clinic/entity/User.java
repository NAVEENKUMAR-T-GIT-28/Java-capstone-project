package com.clinic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank private String name;
    @Email @NotBlank @Column(nullable = false, unique = true) private String email;
    @NotBlank @JsonIgnore @Column(name = "password_hash", nullable = false) private String passwordHash;
    @NotBlank @Column(nullable = false) private String role;
    @OneToOne(mappedBy = "user") @JsonIgnore private Doctor doctor;
    @OneToOne(mappedBy = "user") @JsonIgnore private Patient patient;
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getEmail(){return email;} public void setEmail(String email){this.email=email;}
    public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String passwordHash){this.passwordHash=passwordHash;}
    public String getRole(){return role;} public void setRole(String role){this.role=role;}
    public Doctor getDoctor(){return doctor;} public void setDoctor(Doctor doctor){this.doctor=doctor;}
    public Patient getPatient(){return patient;} public void setPatient(Patient patient){this.patient=patient;}
}
