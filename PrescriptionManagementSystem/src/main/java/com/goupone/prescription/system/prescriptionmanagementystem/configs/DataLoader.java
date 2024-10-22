package com.goupone.prescription.system.prescriptionmanagementystem.configs;

import com.goupone.prescription.system.prescriptionmanagementystem.entity.*;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

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
        // 1. Load or Create Roles
        Role physicianRole = getOrCreateRole("ROLE_PHYSICIAN");
        Role pharmacistRole = getOrCreateRole("ROLE_PHARMACIST");
        Role patientRole = getOrCreateRole("ROLE_PATIENT");

        // 2. Create Admin Physician User
        Physician adminPhysician = new Physician();
        adminPhysician.setUsername("admin");
        adminPhysician.setPassword(passwordEncoder.encode("admin123"));
        adminPhysician.addRole(physicianRole);  // Attach role to the physician

        adminPhysician = userRepository.save(adminPhysician);  // Save physician
        System.out.println("Admin physician created: Username='admin', Password='admin123'");

        // 3. Create User for Patient
        User patientUser = new User();
        patientUser.setUsername("john_doe");
        patientUser.setPassword(passwordEncoder.encode("password"));  // Use encoded password
        patientUser.getRoles().add(patientRole);

        patientUser = userRepository.save(patientUser);  // Save user

        // 4. Create Patient and Associate with User
        Patient patient = new Patient();
        patient.setName("John Doe");
        patient.setUser(patientUser);  // Associate with the saved user
        patient.setPhoneNumber("123-456-7890");
        patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patient.setInsuranceProvider("Good Health");
        patient.setInsurancePolicyNumber("GH12345678");

        patient = patientRepository.save(patient);  // Save patient

        // 5. Create Medications and Generics
        Medication med1 = new Medication("Aspirin", "Tablet", "May cause nausea", true);
        Medication med2 = new Medication("Ibuprofen", "Capsule", "Headache, dizziness", true);
        Medication med3 = new Medication("Lisinopril", "Tablet", "May cause cough", false);
        Medication med4 = new Medication("Atorvastatin", "Tablet", "Muscle pain", false);

        // Generics for two medications
        Medication generic1 = new Medication("Generic Aspirin", "Tablet", "Same as Aspirin", true);
        Medication generic2 = new Medication("Generic Ibuprofen", "Capsule", "Same as Ibuprofen", true);

        // Set generic substitutes
        med1.getGenericSubstitutes().add(generic1);
        med2.getGenericSubstitutes().add(generic2);

        // Save medications and generics
        medicationRepository.saveAll(List.of(med1, med2, med3, med4, generic1, generic2));
        System.out.println("Medications loaded successfully!");

        // 6. Create and Save Prescription
        PrescriptionEntity prescription = new PrescriptionEntity();
        prescription.setMedication(med1);  // Use one of the saved medications
        prescription.setDosage("1 tablet twice a day");
        prescription.setRefillable(true);
        prescription.setPrescribingDoctor("Dr. Admin");
        prescription.setDoctorPhoneNumber("987-654-3210");
        prescription.setIssueDate(LocalDate.now());
        prescription.setExpirationDate(LocalDate.now().plusMonths(6));
        prescription.setRefillsRemaining(2);
        prescription.setUnitsPerRefill(30);
        prescription.setGenericAllowed(true);
        prescription.setPhysician(adminPhysician);  // Associate with physician
        prescription.setPatient(patient);  // Associate with patient

        prescriptionRepository.save(prescription);  // Save prescription

        System.out.println("Data loaded successfully!");
    }

    // Helper method to load or create a role
    private Role getOrCreateRole(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }
}
