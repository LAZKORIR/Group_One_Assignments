package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.entity.Medication;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.PrescriptionEntity;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.MedicationRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PrescriptionRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
//@RequestMapping("/pharmacist")
public class PharmacistController {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

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
//            List<Prescription> prescriptions = prescriptionService.getPrescriptionsForPharmacist();
//            model.addAttribute("prescriptions", prescriptions);
        return "pharmacist/pharmacistHomePage";
    }

    @GetMapping("/pharmacist/check-refillable/{id}")
    public String checkIfRefillable(@PathVariable Long id, Model model) {
        PrescriptionEntity prescription = prescriptionRepository.findById(id).orElseThrow();
        boolean refillable = prescription.isRefillable() && prescription.getRefillsRemaining() > 0;
        model.addAttribute("refillable", refillable);
        return "pharmacist/check-refillable";
    }


    @PostMapping("/pharmacist/dispense/{id}")
    public String dispenseMedicine(@PathVariable Long id) {
        PrescriptionEntity prescription = prescriptionRepository.findById(id).orElseThrow();
        if (prescription.isRefillable() && prescription.getRefillsRemaining() > 0) {
            prescription.setRefillsRemaining(prescription.getRefillsRemaining() - 1);
            prescriptionRepository.save(prescription);
        }
        return "redirect:/pharmacist/pharmacistHomePage";
    }

    @GetMapping("/pharmacist/generic-substitutes/{id}")
    public String viewGenericSubstitutes(@PathVariable Long id, Model model) {
        Medication medication = medicationRepository.findById(id).orElseThrow();
        List<Medication> substitutes = medicationRepository.findByGenericAvailable(true);
        model.addAttribute("medication", medication);
        model.addAttribute("substitutes", substitutes);
        return "pharmacist/generic-substitutes";
    }


}

