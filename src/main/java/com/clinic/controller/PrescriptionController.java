package com.clinic.controller;

import com.clinic.entity.Prescription;
import com.clinic.repository.mongo.PrescriptionRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {
    private final PrescriptionRepository prescriptions;
    public PrescriptionController(PrescriptionRepository prescriptions){this.prescriptions=prescriptions;}
    @PostMapping public ResponseEntity<Prescription> create(@Valid @RequestBody Prescription prescription) {
        prescription.setId(null); prescription.setCreatedAt(java.time.Instant.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(prescriptions.save(prescription));
    }
}
