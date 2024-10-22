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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return prescriptionRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(PrescriptionEntity::getIssueDate))
                .collect(Collectors.toList());
    }


    public String registerUser(User user, String role, RedirectAttributes redirectAttributes) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Password encoding

        Role userRole = roleRepository.findByName("ROLE_" + role)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role: " + role));

        user.getRoles().add(userRole); // Ensure roles are initialized before adding

        User savedUser = userRepository.save(user); // Save the user

        // Functional creation of patient record if the role is PATIENT
        Optional.of(role)
                .filter(r -> r.equalsIgnoreCase("PATIENT"))
                .ifPresent(r -> patientRepository.save(new Patient(savedUser, savedUser.getUsername())));

        redirectAttributes.addFlashAttribute("successMessage", "User registered successfully!");
        return "redirect:/physician";
    }

    public String home(Authentication authentication) {
        return Optional.of(authentication)
                .map(Authentication::getPrincipal)
                .map(User.class::cast)
                .flatMap(user -> user.getRoles().stream().findFirst())
                .map(Role::getName)
                .map(role -> switch (role) {
                    case "ROLE_PHYSICIAN" -> "redirect:/physician";
                    case "ROLE_PHARMACIST" -> "redirect:/pharmacist";
                    case "ROLE_PATIENT" -> "redirect:/patient";
                    default -> throw new IllegalStateException("Unknown role: " + role);
                })
                .orElseThrow(() -> new IllegalStateException("User has no roles assigned"));
    }

    @Override
    public String prescribeMedicine(Long patientId, List<Long> medications, String dosage) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        List<Medication> selectedMedications = medicationRepository.findAllById(medications);
        if (selectedMedications.isEmpty()) {
            throw new RuntimeException("No medications found");
        }

        Physician physician = physicianRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Physician not found"));

        // Create and save a prescription for each selected medication
        selectedMedications.stream()
                .map(medication -> new PrescriptionEntity(
                        patient,
                        physician,
                        medication,
                        dosage,
                        LocalDate.now(),
                        LocalDate.now().plusMonths(6),
                        true,  // refillable
                        2,     // refillsRemaining
                        true   // genericAllowed
                ))
                .forEach(prescriptionRepository::save);

        return "redirect:/physician";
    }


    // Helper method to create a PrescriptionEntity
    private PrescriptionEntity createPrescription(Patient patient,Physician physician, Medication medication, String dosage) {
        return new PrescriptionEntity(patient, physician,medication, dosage, LocalDate.now(), LocalDate.now().plusMonths(6), true, 2, true);
    }

    @Override
    public String dispensePrescription(Long id) {
        return prescriptionRepository.findById(id)
                .map(this::handleDispense)
                .map(prescriptionRepository::save)
                .map(p -> "redirect:/pharmacist")
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
    }

    private PrescriptionEntity handleDispense(PrescriptionEntity prescription) {
        // Handle refillable prescriptions
        if (prescription.isRefillable() && prescription.getRefillsRemaining() > 0) {
            prescription.setRefillsRemaining(prescription.getRefillsRemaining() - 1);
            prescription.setDispensed(true);  // Mark as partially or fully dispensed

            if (prescription.getRefillsRemaining() == 0) {
                prescription.setDispensed(true);  // Fully dispensed if no refills left
            }
        } else if (!prescription.isRefillable()) {
            prescription.setDispensed(true);  // Mark as dispensed for non-refillable
        } else {
            throw new RuntimeException("No refills remaining.");
        }
        return prescription;
    }

    @Override
    public String viewPrescriptions(Model model, Principal principal) {
        String username = principal.getName();
        List<PrescriptionEntity> prescriptions = userRepository.findByUsername(username)
                .flatMap(user -> patientRepository.findByName(user.getUsername()))
                .map(patient -> prescriptionRepository.findByPatient(patient))
                .orElseThrow(() -> new RuntimeException("Prescriptions not found"));

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

