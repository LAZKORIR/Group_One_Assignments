package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.entity.Medication;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medication")
public class MedicationController {
    @Autowired
    private MedicationRepository medicationRepository;

    @GetMapping
    public String listMedications(Model model) {
        model.addAttribute("medications", medicationRepository.findAll());
        return "medication/list";
    }

    @GetMapping("/{id}")
    public String viewMedication(@PathVariable Long id, Model model) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid medication Id:" + id));
        model.addAttribute("medication", medication);
        return "medication/view";
    }
}
