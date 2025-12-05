package com.cmed.prescription.repository;

import com.cmed.prescription.entity.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    Page<Prescription> findByPrescriptionDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query("SELECT p.prescriptionDate as date, COUNT(p) as count " +
            "FROM Prescription p " +
            "GROUP BY p.prescriptionDate " +
            "ORDER BY p.prescriptionDate ASC")
    List<IDayCount> countPrescriptionsByDay();
}