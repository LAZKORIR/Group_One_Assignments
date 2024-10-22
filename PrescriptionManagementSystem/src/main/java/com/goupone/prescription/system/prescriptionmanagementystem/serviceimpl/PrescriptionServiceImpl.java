package com.goupone.prescription.system.prescriptionmanagementystem.serviceimpl;


import com.goupone.prescription.system.prescriptionmanagementystem.entity.*;
import com.goupone.prescription.system.prescriptionmanagementystem.model.Prescription;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.*;
import com.goupone.prescription.system.prescriptionmanagementystem.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service // Marks this class as a Spring-managed bean
public class PrescriptionServiceImpl implements PrescriptionService {

    // Mock Data: In a real-world scenario, these would come from a database.
    private final List<Prescription> prescriptions = new ArrayList<>();

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    MedicationRepository medicationRepository;
    @Autowired
    PhysicianRepository physicianRepository;

    public PrescriptionServiceImpl() {
        // Adding some sample prescriptionEntities for testing

         }

    @Override
    public List<PrescriptionEntity> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }


    public String registerUser(User user, String role, RedirectAttributes redirectAttributes) {


        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));


        Role userRole = roleRepository.findByName("ROLE_" + role).orElseThrow(
                () -> new IllegalArgumentException("Invalid role: " + role)
        );

        user.getRoles().add(userRole);  // Ensure roles are initialized before adding
        // Save the user first to ensure it's persisted
        User savedUser = userRepository.save(user);

        // If the user is a patient, create a Patient record and associate it
        if (role.equalsIgnoreCase("PATIENT")) {
            Patient patient = new Patient();
            patient.setUser(savedUser);  // Associate user with the patient
            patient.setName(savedUser.getUsername());  // Example: Use username as patient name
            patientRepository.save(patient);  // Save the patient in the Patients table

        }

        redirectAttributes.addFlashAttribute("successMessage", "User registered successfully!");

        return "redirect:/physician";
    }

    public String home(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        // Extract the first role from the user's roles
        String role = user.getRoles().stream()
                .findFirst()
                .map(Role::getName)
                .orElseThrow(() -> new IllegalStateException("User has no roles assigned"));

        switch (role) {
            case "ROLE_PHYSICIAN":
                return "redirect:/physician";
            case "ROLE_PHARMACIST":
                return "redirect:/pharmacist";
            case "ROLE_PATIENT":
                return "redirect:/patient";
            default:
                throw new IllegalStateException("Unknown role: " + role);
        }
    }

    @Override
    public String prescribeMedicine(Long patientId,List<Long> medications,String dosage) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        List<Medication> selectedMedications = medicationRepository.findAllById(medications);
        if (selectedMedications.isEmpty()) {
            throw new RuntimeException("No medications found");
        }

        Physician physician = physicianRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Physician not found"));

        // Create a prescription for each selected medication
        for (Medication medication : selectedMedications) {
            PrescriptionEntity prescription = new PrescriptionEntity();
            prescription.setPatient(patient);
            prescription.setPhysician(physician);
            prescription.setMedication(medication);
            prescription.setDosage(dosage);
            prescription.setIssueDate(LocalDate.now());
            prescription.setExpirationDate(LocalDate.now().plusMonths(6));
            prescription.setRefillable(true);
            prescription.setRefillsRemaining(2);
            prescription.setGenericAllowed(true);

            prescriptionRepository.save(prescription);
        }

        return "redirect:/physician";
    }


}

