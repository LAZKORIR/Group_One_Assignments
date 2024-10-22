package com.goupone.prescription.system.prescriptionmanagementystem.configs;

import com.goupone.prescription.system.prescriptionmanagementystem.entity.*;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PatientRepository patientRepository;
    private final MedicationRepository medicationRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, RoleRepository roleRepository,
                      PatientRepository patientRepository, MedicationRepository medicationRepository,
                      PrescriptionRepository prescriptionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.patientRepository = patientRepository;
        this.medicationRepository = medicationRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Load Roles
        Role physicianRole = roleRepository.findByName("ROLE_PHYSICIAN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_PHYSICIAN")));

        Role pharmacistRole = roleRepository.findByName("ROLE_PHARMACIST")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_PHARMACIST")));

        Role patientRole = roleRepository.findByName("ROLE_PATIENT")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_PATIENT")));

        // 2. Create an Admin Physician User

        // Create a physician with encoded password
        Physician adminPhysician = new Physician();
        adminPhysician.setUsername("admin");
        adminPhysician.setPassword(passwordEncoder.encode("admin123")); // Ensure BCrypt encoding
        adminPhysician.addRole(physicianRole);
        userRepository.save(adminPhysician);

        System.out.println("Admin physician created: Username='admin', Password='admin123'");

        // 3. Create a Sample Patient
        Patient patient = patientRepository.findByName("John Doe")
                .orElseGet(() -> {
                    Patient newPatient = new Patient();
                    newPatient.setName("John Doe");
                    newPatient.setPhoneNumber("123-456-7890");
                    newPatient.setDateOfBirth(LocalDate.of(1990, 1, 1));
                    newPatient.setInsuranceProvider("Good Health");
                    newPatient.setInsurancePolicyNumber("GH12345678");
                    return patientRepository.save(newPatient);
                });

        // 4. Create Sample Medication
        Medication medication = medicationRepository.findByName("Paracetamol")
                .orElseGet(() -> {
                    Medication newMedication = new Medication();
                    newMedication.setName("Paracetamol");
                    newMedication.setUnit("500 mg");
                    newMedication.setSideEffects("Nausea, Drowsiness");
                    newMedication.setGenericAvailable(true);
                    return medicationRepository.save(newMedication);
                });


       //5.  Create a Sample Prescription
        PrescriptionEntity prescription = new PrescriptionEntity();
        prescription.setMedication(medication);
        prescription.setDosage("1 tablet twice a day");
        prescription.setRefillable(true);
        prescription.setPrescribingDoctor("Dr. Admin");
        prescription.setDoctorPhoneNumber("987-654-3210");
        prescription.setIssueDate(LocalDate.now());
        prescription.setExpirationDate(LocalDate.now().plusMonths(6));
        prescription.setRefillsRemaining(2);
        prescription.setUnitsPerRefill(30);
        prescription.setGenericAllowed(true);
        prescription.setPhysician(adminPhysician);  // Assign the Physician
        prescription.setPatient(patient);

        prescriptionRepository.save(prescription);


        // Save the prescription
        prescriptionRepository.save(prescription);
    }
}
