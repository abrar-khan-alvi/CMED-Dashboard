package com.cmed.prescription.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Prescription date is mandatory")
    private LocalDate prescriptionDate;

    @NotBlank(message = "Patient name is mandatory")
    private String patientName;

    @NotNull(message = "Age is mandatory")
    @Min(value = 0, message = "Age must be positive")
    @Max(value = 150, message = "Age must be realistic")
    private Integer patientAge;

    @NotNull(message = "Gender is mandatory")
    @Enumerated(EnumType.STRING)
    private Gender patientGender;

    @Column(columnDefinition = "TEXT")
    private String diagnosis;

    @Column(columnDefinition = "TEXT")
    private String medicines;

    private LocalDate nextVisitDate;
}