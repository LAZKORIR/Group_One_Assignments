package com.goupone.prescription.system.prescriptionmanagementystem.configs;


import com.goupone.prescription.system.prescriptionmanagementystem.entity.Medication;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.Patient;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.Physician;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.PrescriptionEntity;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.MedicationRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PatientRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PhysicianRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PrescriptionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader {

    @Bean
    CommandLineRunner runner(
            PatientRepository patientRepo,
            PhysicianRepository physicianRepo,
            MedicationRepository medicationRepo,
            PrescriptionRepository prescriptionRepo) {
        return args -> {

            // Create Medications
            Medication med1 = new Medication("Amoxicillin", "500mg", "Nausea, Headache", true);
            Medication med2 = new Medication("Ibuprofen", "200mg", "Dizziness, Upset Stomach", false);
            medicationRepo.saveAll(List.of(med1, med2));

            // Create a Patient
            Patient patient = new Patient();
            patient.setName("Alvin Kabwama");
            patient.setPhoneNumber("555-1234");
            patient.setDateOfBirth(LocalDate.of(1985, 5, 15));
            patient.setInsuranceProvider("Cigna");
            patient.setInsurancePolicyNumber("H12345");
            patientRepo.save(patient);

            // Create a Physician
            Physician physician = new Physician();
            physician.setName("Dr. Alyssa");
            physicianRepo.save(physician);

            // Create a Prescription
            PrescriptionEntity prescription = new PrescriptionEntity(
                    "500mg, twice a day", true, "Dr. Alyssa", "555-5678",
                    LocalDate.now(), LocalDate.now().plusMonths(6), 2, 30,
                    true, patient, physician, List.of(med1, med2)
            );

            // Save Prescription
            prescriptionRepo.save(prescription);
        };
    }
}
