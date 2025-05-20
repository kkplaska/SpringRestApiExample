package pl.pjatk.tpo6_pk_s30213.Services;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.pjatk.tpo6_pk_s30213.Models.Patient;
import pl.pjatk.tpo6_pk_s30213.Repositories.PatientRepository;

import java.util.List;

@Service
public class PatientsService {
    private final PatientRepository _patientRepository;

    public PatientsService(PatientRepository _patientRepository) {
        this._patientRepository = _patientRepository;
    }

    public Patient getPatientById(long id) {
        return this._patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Patient with id: " + id + " not found"));
    }

    public List<Patient> getAllPatients() {
        return _patientRepository.findAll(Sort.by(Sort.Direction.ASC, "patientId").and(Sort.by(Sort.Direction.ASC, "lastName")));
    }

    public Patient addPatient(Patient patient) {
        return _patientRepository.save(patient);
    }
}
