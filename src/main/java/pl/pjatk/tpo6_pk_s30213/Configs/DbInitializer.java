package pl.pjatk.tpo6_pk_s30213.Configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.pjatk.tpo6_pk_s30213.Models.*;
import pl.pjatk.tpo6_pk_s30213.Repositories.*;

import java.time.LocalDateTime;

@Configuration
public class DbInitializer {
    @Bean
    public CommandLineRunner initializeDatabase(
            PatientRepository patientRepo,
            DoctorRepository doctorRepo,
            VisitRepository visitRepo) {

        return args -> {
            // Create patients
            Patient patient1 = new Patient();
            patient1.setFirstName("John");
            patient1.setLastName("Doe");
            patient1.setEmail("john@doe.com");
            patient1.setContactNumber("123-456-7890");

            Patient patient2 = new Patient();
            patient2.setFirstName("Sarah");
            patient2.setLastName("Smith");
            patient2.setEmail("sarah@example.com");
            patient2.setContactNumber("234-567-8901");

            patient1 = patientRepo.save(patient1);
            patient2 = patientRepo.save(patient2);

            // Create doctors
            Doctor doctor1 = new Doctor();
            doctor1.setDoctorName("John Wilson");
            doctor1.setSpecialization("Cardiology");

            Doctor doctor2 = new Doctor();
            doctor2.setDoctorName("Ann Brown");
            doctor2.setSpecialization("Neurology");

            doctor1 = doctorRepo.save(doctor1);
            doctor2 = doctorRepo.save(doctor2);

            // Create visits
            Visit visit1 = new Visit();
            visit1.setPatient(patient1);
            visit1.setDoctor(doctor1);
            visit1.setScheduledTime(LocalDateTime.of(2025, 5, 20, 9, 0));
            visit1.setStatus(Visit.VisitStatus.COMPLETED);

            Visit visit2 = new Visit();
            visit2.setPatient(patient2);
            visit2.setDoctor(doctor2);
            visit2.setScheduledTime(LocalDateTime.of(2025, 5, 20, 10, 0));
            visit2.setStatus(Visit.VisitStatus.SCHEDULED);

            visitRepo.save(visit1);
            visitRepo.save(visit2);
        };
    }
}
