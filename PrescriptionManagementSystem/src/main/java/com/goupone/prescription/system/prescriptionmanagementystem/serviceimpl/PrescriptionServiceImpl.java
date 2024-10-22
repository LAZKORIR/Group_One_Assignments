package com.goupone.prescription.system.prescriptionmanagementystem.serviceimpl;


import com.goupone.prescription.system.prescriptionmanagementystem.entity.*;
import com.goupone.prescription.system.prescriptionmanagementystem.model.Prescription;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.*;
import com.goupone.prescription.system.prescriptionmanagementystem.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
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

    @Override
    public String dispensePrescription(Long id) {
        PrescriptionEntity prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        // Handle refillable prescriptions
        if (prescription.isRefillable() && prescription.getRefillsRemaining() > 0) {
            prescription.setRefillsRemaining(prescription.getRefillsRemaining() - 1);

            // If at least one has been dispensed, mark as partially dispensed
            if (prescription.getRefillsRemaining() > 0) {
                prescription.setDispensed(true);  // Mark as partially dispensed
            } else {
                // If no more refills, mark as fully dispensed
                prescription.setDispensed(true);
            }
        } else if (!prescription.isRefillable()) {
            // For non-refillable prescriptions, mark as dispensed
            prescription.setDispensed(true);
        } else {
            throw new RuntimeException("No refills remaining.");
        }

        prescriptionRepository.save(prescription);
        return "redirect:/pharmacist";
    }

    @Override
    public String viewPrescriptions(Model model, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Patient patient = patientRepository.findByName(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        List<PrescriptionEntity> prescriptions = prescriptionRepository.findByPatient(patient);
        model.addAttribute("prescriptions", prescriptions);

        return "patient/patientHomePage";
    }

    @Override
    public String viewGenericSubstitutes(@PathVariable Long id, Model model) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medication not found"));

        List<Medication> genericSubstitutes = medication.getGenericSubstitutes();
        model.addAttribute("medication", medication);
        model.addAttribute("genericSubstitutes", genericSubstitutes);

        return "pharmacist/generic-substitutes";
    }

    @Override
    public String getPhysicianHomePage(Model model) {
        // Fetch all prescriptions to display in the view
        List<PrescriptionEntity> prescriptions = getAllPrescriptions();
        model.addAttribute("prescriptions", prescriptions);
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // Get the logged-in username

        // Add username to the model to display in the HTML
        model.addAttribute("username", "Dr. "+username);
        return "physician/physicianHomePage";  // Points to physician.html template
    }

}

