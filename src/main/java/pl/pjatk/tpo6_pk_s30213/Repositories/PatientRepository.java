package pl.pjatk.tpo6_pk_s30213.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.tpo6_pk_s30213.Models.Patient;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {}
