package com.goupone.prescription.system.prescriptionmanagementystem.serviceimpl;


import com.goupone.prescription.system.prescriptionmanagementystem.entity.*;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.*;
import com.goupone.prescription.system.prescriptionmanagementystem.service.UtilityService;
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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UtilityServiceImpl implements UtilityService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private MedicationRepository medicationRepository;
    @Autowired
    private PhysicianRepository physicianRepository;


    @Override
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Prescription::getIssueDate))
                .collect(Collectors.toList());
    }

    @Override
    public String registerUser(User user, String role, String fullName, String phoneNumber, LocalDate dateOfBirth,
                               String insuranceProvider, String insurancePolicyNumber, RedirectAttributes redirectAttributes) {

        // Encode the password and set it on the user
        Optional.ofNullable(user)
                .ifPresent(u -> u.setPassword(passwordEncoder.encode(u.getPassword())));

        // Find the role and add it to the user
        roleRepository.findByName("ROLE_" + role)
                .ifPresentOrElse(
                        userRole -> user.getRoles().add(userRole),
                        () -> {
                            throw new IllegalArgumentException("Invalid role: " + role);
                        }
                );

        // Save the user and process the patient details if necessary
        User savedUser = userRepository.save(user);

        // If the role is 'PATIENT', use functional approach to create a Patient and save it
        Optional.of(role)
                .filter(r -> r.equalsIgnoreCase("PATIENT"))
                .ifPresent(r -> createAndSavePatient(savedUser, fullName, phoneNumber,
                        dateOfBirth, insuranceProvider,
                        insurancePolicyNumber));

        // Add success message
        redirectAttributes.addFlashAttribute("successMessage", "User registered successfully!");
        return "redirect:/physician";
    }

    // Helper method to create and save a patient
    private void createAndSavePatient(User user,
                                      String fullName,
                                      String phoneNumber,
                                      LocalDate dateOfBirth,
                                      String insuranceProvider,
                                      String insurancePolicyNumber) {

        Optional.of(new Patient())
                .ifPresent(patient -> {
                    patient.setUser(user);
                    patient.setName(fullName);
                    patient.setPhoneNumber(phoneNumber);
                    patient.setDateOfBirth(dateOfBirth);
                    patient.setInsuranceProvider(insuranceProvider);
                    patient.setInsurancePolicyNumber(insurancePolicyNumber);
                    patientRepository.save(patient);  // Save the patient entity
                });
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
    public String prescribeMedicine(Long patientId, List<Long> medications, Map<Long, String> dosages) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Physician physician = physicianRepository.findById(1L)  // Placeholder ID
                .orElseThrow(() -> new RuntimeException("Physician not found"));

        List<Prescription> prescriptions = medications.stream()
                .map(medicationId -> {
                    Medication medication = medicationRepository.findById(medicationId)
                            .orElseThrow(() -> new RuntimeException("Medication not found"));

                    String dosage = dosages.getOrDefault(medicationId, "No dosage specified");

                    return new Prescription(
                            patient,physician, medication, dosage, LocalDate.now(),
                            LocalDate.now().plusMonths(6), true, 2, true
                    );
                })
                .toList();

        prescriptionRepository.saveAll(prescriptions);

        return "redirect:/physician";
    }




    @Override
    public String dispensePrescription(Long id) {
        return prescriptionRepository.findById(id)
                .map(this::handleDispense)
                .map(prescriptionRepository::save)
                .map(p -> "redirect:/pharmacist")
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
    }

    private Prescription handleDispense(Prescription prescription) {
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
        List<Prescription> prescriptions = userRepository.findByUsername(username)
                .flatMap(user -> patientRepository.findByUserId(user.getId()))
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
        List<Prescription> prescriptions = getAllPrescriptions();
        model.addAttribute("prescriptions", prescriptions);
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // Get the logged-in username

        // Add username to the model to display in the HTML
        model.addAttribute("username", "Dr. "+username);
        return "physician/physicianHomePage";  // Points to physician.html template
    }

}

