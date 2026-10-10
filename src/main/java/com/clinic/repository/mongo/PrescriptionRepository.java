package com.clinic.repository.mongo;
import com.clinic.entity.Prescription;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface PrescriptionRepository extends MongoRepository<Prescription, String> { }
