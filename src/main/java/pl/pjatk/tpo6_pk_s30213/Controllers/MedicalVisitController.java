package pl.pjatk.tpo6_pk_s30213.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.tpo6_pk_s30213.Models.*;
import pl.pjatk.tpo6_pk_s30213.Models.DTOs.AddVisitDto;
import pl.pjatk.tpo6_pk_s30213.Services.DoctorsService;
import pl.pjatk.tpo6_pk_s30213.Services.PatientsService;
import pl.pjatk.tpo6_pk_s30213.Services.VisitsService;

import java.util.List;

@RestController
@RequestMapping("/data")
public class MedicalVisitController {
    private final DoctorsService _doctorService;
    private final PatientsService _patientService;
    private final VisitsService _visitsService;

    public MedicalVisitController(DoctorsService _doctorService, PatientsService _patientService, VisitsService _visitsService) {
        this._doctorService = _doctorService;
        this._patientService = _patientService;
        this._visitsService = _visitsService;
    }

    // Patients endpoints
    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(_patientService.getAllPatients());
    }

    @PostMapping("/patients")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(_patientService.addPatient(patient));
    }

    // Doctors endpoints
    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(_doctorService.getAllPatients());
    }

    @PostMapping("/doctors")
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(_doctorService.addDoctor(doctor));
    }

    // Visits endpoints
    @GetMapping("/visits")
    public ResponseEntity<List<Visit>> getAllVisits() {
        return ResponseEntity.ok(_visitsService.getAllVisits());
    }

    @PostMapping("/visits")
    public ResponseEntity<Visit> createVisit(@RequestBody AddVisitDto addVisitDto) {
        Patient patient = _patientService.getPatientById(addVisitDto.getPatientId());
        Doctor doctor = _doctorService.getDoctorById(addVisitDto.getDoctorId());
        Visit visit = new Visit();
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visit.setStatus(Visit.VisitStatus.SCHEDULED);
        visit.setScheduledTime(addVisitDto.getScheduledTime());
        _visitsService.addVisit(visit);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/visits/{id}/status")
    public ResponseEntity<Visit> updateVisitStatus(
            @PathVariable Long id,
            @RequestParam Visit.VisitStatus status)
    {
        try {
            Visit visit = _visitsService.updateVisitStatus(id, status);
            return new ResponseEntity<>(visit, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/visits/{id}")
    public ResponseEntity<Visit> deleteVisit(@PathVariable Long id) {
        try {
            _visitsService.delete(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.noContent().build();
    }
}
