package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.entity.PrescriptionEntity;
import com.goupone.prescription.system.prescriptionmanagementystem.model.Prescription;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PrescriptionRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pharmacist")
public class PharmacistController {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

        private final PrescriptionService prescriptionService;

        public PharmacistController(PrescriptionService prescriptionService) {
            this.prescriptionService = prescriptionService;
        }

        // Default landing page: List prescriptions for dispensing
        @GetMapping("/pharmacist")
        public String viewPrescriptions(Model model) {
            List<Prescription> prescriptions = prescriptionService.getPrescriptionsForPharmacist();
            model.addAttribute("prescriptionEntities", prescriptions);
            return "pharmacist/home";
        }

        // Check if medication is refillable
        @GetMapping("/pharmacist/check-refill")
        public String checkRefill() {
            // Logic to check refill status
            return "pharmacist/check-refill";
        }

        // Navigate to Dispense PrescriptionEntity Page
        @GetMapping("/pharmacist/dispense")
        public String dispensePrescription() {
            return "pharmacist/dispense";
        }


    @PostMapping("/dispense/{id}")
    public String dispenseMedication(@PathVariable Long id) {
        PrescriptionEntity prescriptionEntity = prescriptionRepository.findById(id).orElse(null);
        if (prescriptionEntity != null && prescriptionEntity.isRefillable()) {
            // Handle dispensing logic here
        }
        return "redirect:/pharmacist/prescriptions";
    }
}

