package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.entity.User;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.MedicationRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.UserRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class PharmacistController {

    private final UtilityService utilityService;

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    UserRepository userRepository;

    public PharmacistController(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

    // Default landing page: List prescriptions for dispensing
    @GetMapping("/pharmacist")
    public String viewPrescriptions(Model model, Principal principal) {
        // Fetch the logged-in user's ID
        String username = principal.getName();
        Long userId = userRepository.findByUsername(username)
                .map(User::getId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("prescriptions", utilityService.getAllPrescriptions());
        model.addAttribute("userId", userId);
        model.addAttribute("username", username);
        return "pharmacist/pharmacistHomePage";
    }


    @PostMapping("/pharmacist/dispense/{id}")
    public String dispensePrescription(@PathVariable Long id) {
       return utilityService.dispensePrescription(id);
    }



    @GetMapping("/pharmacist/generic-substitutes/{id}")
    public String viewGenericSubstitutes(@PathVariable Long id, Model model) {
        return utilityService.viewGenericSubstitutes(id, model);
    }



}

