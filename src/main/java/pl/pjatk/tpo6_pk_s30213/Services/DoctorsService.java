package pl.pjatk.tpo6_pk_s30213.Services;

import org.springframework.stereotype.Service;
import pl.pjatk.tpo6_pk_s30213.Models.Doctor;
import pl.pjatk.tpo6_pk_s30213.Models.Visit;
import pl.pjatk.tpo6_pk_s30213.Repositories.DoctorRepository;

import java.util.List;

@Service
public class DoctorsService {
    private final DoctorRepository _doctorRepository;

    public DoctorsService(DoctorRepository doctorRepository) {
        this._doctorRepository = doctorRepository;
    }

    public Doctor getDoctorById(long id) {
        return this._doctorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Doctor with id: " + id + " not found"));
    }

    public List<Doctor> getAllPatients() {
        return this._doctorRepository.findAll().stream().sorted((a,b) -> Math.toIntExact(a.getDoctorId() - b.getDoctorId())).toList();
    }

    public Doctor addDoctor(Doctor doctor) {
        return _doctorRepository.save(doctor);
    }
}
