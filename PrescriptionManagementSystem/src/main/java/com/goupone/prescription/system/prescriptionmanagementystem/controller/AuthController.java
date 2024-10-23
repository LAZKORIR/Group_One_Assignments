package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.dto.LoginForm;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.User;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PatientRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.RoleRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.UserRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

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

    @Autowired
    UtilityService utilityService;

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

    // Redirect root ("/") to /login
    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/users/login";
    }


    @GetMapping("/auth/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());  // Bind a new User object to the form
        return "users/register";
    }

    @PostMapping("/auth/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
            @RequestParam(required = false) String insuranceProvider,
            @RequestParam(required = false) String insurancePolicyNumber,
            RedirectAttributes redirectAttributes) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        // Call the service method to handle registration
        return utilityService.registerUser(
                user, role, fullName, phoneNumber, dateOfBirth,
                insuranceProvider, insurancePolicyNumber, redirectAttributes);
    }



    @GetMapping("/home")
    public String home(Authentication authentication) {

        return utilityService.home(authentication);
    }


}
