package pl.pjatk.tpo6_pk_s30213.Services;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.pjatk.tpo6_pk_s30213.Models.Visit;
import pl.pjatk.tpo6_pk_s30213.Repositories.VisitRepository;

import java.util.List;

@Service
public class VisitsService {
    private final VisitRepository _visitRepository;

    public VisitsService(VisitRepository _visitRepository) {
        this._visitRepository = _visitRepository;
    }

    public List<Visit> getAllVisitsSortedBy(String column){
        return _visitRepository.findAll(Sort.by(Sort.Direction.ASC, column));
    }

    public List<Visit> getAllVisits() {
        return _visitRepository.findAll(Sort.by(Sort.Direction.ASC, "visitId"));
    }

    public Visit addVisit(Visit visit) {
        return _visitRepository.save(visit);
    }

    public Visit updateVisitStatus(Long id, Visit.VisitStatus status){
        Visit visit = _visitRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Visit with id: " + id + " not found"));
        visit.setStatus(status);
        return _visitRepository.save(visit);
    }

    public void delete(Long id) {
        Visit visit = _visitRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Visit with id: " + id + " not found"));
        this._visitRepository.delete(visit);
    }
}
