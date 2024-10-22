package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.dto.LoginForm;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.User;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PatientRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.RoleRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.UserRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    PrescriptionService prescriptionService;

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
            @ModelAttribute User user,
            @RequestParam String role,
            RedirectAttributes redirectAttributes) {

       return prescriptionService.registerUser(user,role,redirectAttributes);

    }


    @GetMapping("/home")
    public String home(Authentication authentication) {

        return prescriptionService.home(authentication);
    }


}
