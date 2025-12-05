package com.cmed.prescription.controller;

import com.cmed.prescription.entity.Prescription;
import com.cmed.prescription.repository.IDayCount;
import com.cmed.prescription.service.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RestController
@RequestMapping("/api/v1/prescription")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService service;

    @GetMapping
    public ResponseEntity<Page<Prescription>> getPrescriptions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (startDate == null)
            startDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        if (endDate == null)
            endDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

        Page<Prescription> result = service.getPrescriptions(startDate, endDate,
                PageRequest.of(page, size, Sort.by("prescriptionDate").descending()));
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Prescription> create(@Valid @RequestBody Prescription prescription) {
        return ResponseEntity.ok(service.save(prescription));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prescription> update(@PathVariable Long id, @Valid @RequestBody Prescription prescription) {
        prescription.setId(id);
        return ResponseEntity.ok(service.save(prescription));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<List<IDayCount>> getStats() {
        return ResponseEntity.ok(service.getPrescriptionStats());
    }
}