package com.clinic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank @Column(nullable = false) private String name;
    @Email @NotBlank @Column(nullable = false, unique = true) private String email;
    @NotBlank @Column(nullable = false) private String phone;
    @NotBlank @Column(nullable = false) private String specialization;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "doctor_available_times", joinColumns = @JoinColumn(name = "doctor_id"))
    @Column(name = "available_time", nullable = false)
    private Set<LocalTime> availableTimes = new HashSet<>();
    @OneToOne @JoinColumn(name = "user_id", unique = true) @JsonIgnore private User user;
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getEmail(){return email;} public void setEmail(String email){this.email=email;}
    public String getPhone(){return phone;} public void setPhone(String phone){this.phone=phone;}
    public String getSpecialization(){return specialization;} public void setSpecialization(String specialization){this.specialization=specialization;}
    public Set<LocalTime> getAvailableTimes(){return availableTimes;} public void setAvailableTimes(Set<LocalTime> availableTimes){this.availableTimes=availableTimes == null ? new HashSet<>() : new HashSet<>(availableTimes);}
    public User getUser(){return user;} public void setUser(User user){this.user=user;}
}
