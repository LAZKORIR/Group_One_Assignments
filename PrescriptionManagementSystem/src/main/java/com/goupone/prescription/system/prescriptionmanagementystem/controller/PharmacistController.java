package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.entity.Prescription;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pharmacist")
public class PharmacistController {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @GetMapping("/prescriptions")
    public String listPrescriptions(Model model) {
        model.addAttribute("prescriptions", prescriptionRepository.findAll());
        return "pharmacist/list-prescriptions";
    }

    @PostMapping("/dispense/{id}")
    public String dispenseMedication(@PathVariable Long id) {
        Prescription prescription = prescriptionRepository.findById(id).orElse(null);
        if (prescription != null && prescription.isRefillable()) {
            // Handle dispensing logic here
        }
        return "redirect:/pharmacist/prescriptions";
    }
}

