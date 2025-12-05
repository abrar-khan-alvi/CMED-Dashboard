package com.cmed.prescription.service;

import com.cmed.prescription.entity.Prescription;
import com.cmed.prescription.repository.IDayCount;
import com.cmed.prescription.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository repository;

    public Prescription save(Prescription prescription) {
        return repository.save(prescription);
    }

    public Page<Prescription> getPrescriptions(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return repository.findByPrescriptionDateBetween(startDate, endDate, pageable);
    }

    public Prescription getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cannot delete. ID not found: " + id);
        }
        repository.deleteById(id);
    }

    public List<IDayCount> getPrescriptionStats() {
        List<IDayCount> allStats = repository.countPrescriptionsByDay();

        return allStats.stream()
                .skip(Math.max(0, allStats.size() - 10))
                .collect(Collectors.toList());
    }
}