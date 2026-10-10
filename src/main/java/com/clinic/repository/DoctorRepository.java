package com.clinic.repository;
import com.clinic.entity.Doctor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);
    List<Doctor> findByNameContainingIgnoreCaseOrSpecializationContainingIgnoreCase(String name, String specialization);
}
