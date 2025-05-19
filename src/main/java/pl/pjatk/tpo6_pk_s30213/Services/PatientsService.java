package pl.pjatk.tpo6_pk_s30213.Services;

import org.springframework.stereotype.Service;
import pl.pjatk.tpo6_pk_s30213.Models.Doctor;
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
        return _patientRepository.findAll().stream().sorted((a,b) -> Math.toIntExact(a.getPatientId() - b.getPatientId())).toList();
    }

    public Patient addPatient(Patient patient) {
        return _patientRepository.save(patient);
    }
}
