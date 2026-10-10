package com.clinic.repository;
import com.clinic.entity.Patient;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByPhone(String phone);
    Optional<Patient> findByEmailOrPhone(String email, String phone);
}
