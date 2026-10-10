package com.clinic.controller;

import com.clinic.entity.Doctor;
import com.clinic.repository.DoctorRepository;
import com.clinic.service.DoctorService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorRepository doctors; private final DoctorService doctorService;
    public DoctorController(DoctorRepository doctors, DoctorService doctorService){this.doctors=doctors;this.doctorService=doctorService;}
    @GetMapping public ResponseEntity<List<Doctor>> getDoctors(){return ResponseEntity.ok(doctors.findAll());}
    @GetMapping("/search")
    public ResponseEntity<List<Doctor>> search(@RequestParam(required=false) String name,
                                               @RequestParam(required=false) String specialty) {
        String n=name==null?"":name; String s=specialty==null?"":specialty;
        List<Doctor> found;
        if(!n.isBlank() && !s.isBlank()) found=doctors.findByNameContainingIgnoreCaseOrSpecializationContainingIgnoreCase(n,s).stream()
                .filter(d -> d.getName().toLowerCase().contains(n.toLowerCase()) || d.getSpecialization().toLowerCase().contains(s.toLowerCase())).toList();
        else if(!n.isBlank()) found=doctors.findByNameContainingIgnoreCaseOrSpecializationContainingIgnoreCase(n,n);
        else if(!s.isBlank()) found=doctors.findBySpecializationContainingIgnoreCase(s);
        else found=doctors.findAll();
        return ResponseEntity.ok(found);
    }
    @GetMapping("/{id}/availability")
    public ResponseEntity<?> availability(@PathVariable Long id, @RequestParam LocalDate date) {
        return ResponseEntity.ok(Map.of("doctorId",id,"date",date,"availableTimes",doctorService.getAvailableTimes(id,date)));
    }
    @PostMapping
    public ResponseEntity<?> addDoctor(@Valid @RequestBody Doctor doctor) {
        doctor.setId(null);
        try { return ResponseEntity.status(HttpStatus.CREATED).body(doctors.save(doctor)); }
        catch (org.springframework.dao.DataIntegrityViolationException ex) { return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error","A doctor with that email may already exist")); }
    }
}
