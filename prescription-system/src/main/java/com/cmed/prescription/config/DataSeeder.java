package com.cmed.prescription.config;

import com.cmed.prescription.entity.Gender;
import com.cmed.prescription.entity.Prescription;
import com.cmed.prescription.entity.User;
import com.cmed.prescription.repository.PrescriptionRepository;
import com.cmed.prescription.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepo;
    private final PrescriptionRepository prescriptionRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepo.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRole("ADMIN");
            userRepo.save(admin);
            System.out.println("--- DATA SEEDER: Admin User Created (admin/password) ---");
        }
        if (prescriptionRepo.count() == 0) {
            for (int i = 0; i < 20; i++) {
                Prescription p = new Prescription();
                p.setPatientName("Patient " + (i + 1));
                p.setPatientAge(20 + i);
                p.setPatientGender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE);
                p.setDiagnosis("Diagnosis for patient " + (i + 1));
                p.setMedicines("Medicine A, Medicine B");
                p.setPrescriptionDate(LocalDate.now().minusDays(i % 15));
                if (i % 3 == 0) {
                    p.setNextVisitDate(LocalDate.now().plusDays(7));
                }

                prescriptionRepo.save(p);
            }
            for (int i = 0; i < 20; i++) {
                Prescription p = new Prescription();
                p.setPatientName("PrevMonth Patient " + (i + 1));
                p.setPatientAge(30 + i);
                p.setPatientGender(i % 2 != 0 ? Gender.MALE : Gender.FEMALE);
                p.setDiagnosis("Old Diagnosis " + (i + 1));
                p.setMedicines("Medicine X, Medicine Y");
                LocalDate lastMonth = LocalDate.now().minusMonths(1);
                p.setPrescriptionDate(lastMonth.withDayOfMonth(1).plusDays(i % 28));
                if (i % 3 == 0) {
                    p.setNextVisitDate(p.getPrescriptionDate().plusDays(14));
                }

                prescriptionRepo.save(p);
            }
            System.out.println("--- DATA SEEDER: 20 Prescriptions Created ---");
        }
    }
}