package com.clinic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "patients")
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank @Column(nullable = false) private String name;
    @Email @NotBlank @Column(nullable = false, unique = true) private String email;
    @NotBlank @Column(nullable = false, unique = true) private String phone;
    @OneToOne @JoinColumn(name = "user_id", unique = true) @JsonIgnore private User user;
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getEmail(){return email;} public void setEmail(String email){this.email=email;}
    public String getPhone(){return phone;} public void setPhone(String phone){this.phone=phone;}
    public User getUser(){return user;} public void setUser(User user){this.user=user;}
}
