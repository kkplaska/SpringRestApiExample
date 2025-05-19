package pl.pjatk.tpo6_pk_s30213.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.tpo6_pk_s30213.Models.Doctor;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {}