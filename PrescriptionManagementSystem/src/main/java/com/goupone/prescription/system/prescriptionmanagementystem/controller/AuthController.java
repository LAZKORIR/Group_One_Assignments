package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.dto.LoginForm;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.Patient;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.Role;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.User;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PatientRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.RoleRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/login")
    public String loginPage(Model model,
                            @RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout) {
        model.addAttribute("loginForm", new LoginForm());

        if (error != null) {
            model.addAttribute("loginError", "Invalid username or password.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out successfully.");
        }
        return "users/login";
    }

    @GetMapping("/auth/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());  // Bind a new User object to the form
        return "users/register";
    }

    @PostMapping("/auth/register")
    public String registerUser(
            @ModelAttribute User user,
            @RequestParam String role,
            RedirectAttributes redirectAttributes) {

        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Fetch the role and assign it to the user
        // Fetch the role with the correct prefix
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

        return "redirect:/physician";  // Redirect to login page after registration
    }


    @GetMapping("/home")
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


}
