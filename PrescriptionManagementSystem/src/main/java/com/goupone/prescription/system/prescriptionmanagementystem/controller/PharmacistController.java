package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.repository.MedicationRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PharmacistController {

    private final PrescriptionService prescriptionService;

    @Autowired
    MedicationRepository medicationRepository;

    public PharmacistController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    // Default landing page: List prescriptions for dispensing
    @GetMapping("/pharmacist")
    public String viewPrescriptions(Model model) {
        model.addAttribute("prescriptions", prescriptionService.getAllPrescriptions());
        return "pharmacist/pharmacistHomePage";
    }


    @PostMapping("/pharmacist/dispense/{id}")
    public String dispensePrescription(@PathVariable Long id) {
       return prescriptionService.dispensePrescription(id);
    }



    @GetMapping("/pharmacist/generic-substitutes/{id}")
    public String viewGenericSubstitutes(@PathVariable Long id, Model model) {
        return prescriptionService.viewGenericSubstitutes(id, model);
    }



}

