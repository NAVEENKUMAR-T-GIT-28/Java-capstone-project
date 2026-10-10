package com.clinic;

import com.clinic.entity.Appointment;
import com.clinic.entity.Doctor;
import com.clinic.entity.Patient;
import com.clinic.entity.User;
import com.clinic.repository.AppointmentRepository;
import com.clinic.repository.DoctorRepository;
import com.clinic.repository.PatientRepository;
import com.clinic.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SmartClinicApplication {
    public static void main(String[] args) { SpringApplication.run(SmartClinicApplication.class, args); }

    @Bean
    PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    /** Development-only demo accounts; replace with a managed account workflow in production. */
    @Bean
    CommandLineRunner seedDemoData(DoctorRepository doctors, PatientRepository patients,
                                   AppointmentRepository appointments, UserRepository users, PasswordEncoder encoder) {
        return args -> {
            if (doctors.count() == 0) {
                Doctor d = new Doctor(); d.setName("Dr. Asha Raman"); d.setEmail("asha.raman@clinic.test");
                d.setPhone("9000000001"); d.setSpecialization("Cardiology");
                d.setAvailableTimes(Set.of(LocalTime.of(9, 0), LocalTime.of(10, 0), LocalTime.of(11, 0)));
                doctors.save(d);
                Doctor d2 = new Doctor(); d2.setName("Dr. Kiran Kumar"); d2.setEmail("kiran.kumar@clinic.test");
                d2.setPhone("9000000002"); d2.setSpecialization("Dermatology");
                d2.setAvailableTimes(Set.of(LocalTime.of(13, 0), LocalTime.of(14, 0), LocalTime.of(15, 0)));
                doctors.save(d2);
            }
            if (patients.count() == 0) {
                Patient p = new Patient(); p.setName("Demo Patient"); p.setEmail("patient@clinic.test"); p.setPhone("9111111111"); patients.save(p);
                Patient p2 = new Patient(); p2.setName("Sam Patient"); p2.setEmail("sam.patient@clinic.test"); p2.setPhone("9222222222"); patients.save(p2);
            }
            if (users.count() == 0) {
                users.save(makeUser("Clinic Admin", "admin@clinic.test", "Admin123!", "ADMIN", encoder));
                users.save(makeUser("Dr. Asha Raman", "doctor@clinic.test", "Doctor123!", "DOCTOR", encoder));
                users.save(makeUser("Demo Patient", "patient@clinic.test", "Patient123!", "PATIENT", encoder));
            }
            if (appointments.count() == 0) {
                Doctor doctor = doctors.findAll().get(0);
                Patient patient = patients.findAll().get(0);
                Appointment first = new Appointment(); first.setDoctor(doctor); first.setPatient(patient);
                first.setAppointmentTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0));
                first.setStatus("BOOKED"); appointments.save(first);
                if (doctors.count() > 1 && patients.count() > 1) {
                    Appointment second = new Appointment(); second.setDoctor(doctors.findAll().get(1)); second.setPatient(patients.findAll().get(1));
                    second.setAppointmentTime(LocalDateTime.now().plusDays(2).withHour(14).withMinute(0).withSecond(0).withNano(0));
                    second.setStatus("BOOKED"); appointments.save(second);
                }
            }
        };
    }
    private User makeUser(String name, String email, String password, String role, PasswordEncoder encoder) {
        User user = new User(); user.setName(name); user.setEmail(email); user.setPasswordHash(encoder.encode(password)); user.setRole(role); return user;
    }
}
