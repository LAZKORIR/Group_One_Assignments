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


//    @PostMapping("/pharmacist/dispense/{id}")
//    public String dispenseMedicine(@PathVariable Long id) {
//        PrescriptionEntity prescription = prescriptionRepository.findById(id).orElseThrow();
//        if (prescription.isRefillable() && prescription.getRefillsRemaining() > 0) {
//            prescription.setRefillsRemaining(prescription.getRefillsRemaining() - 1);
//            prescriptionRepository.save(prescription);
//        }
//        return "redirect:/pharmacist";
//    }

//    @PostMapping("/pharmacist/dispense/{id}")
//    public String dispensePrescription(@PathVariable Long id) {
//        PrescriptionEntity prescription = prescriptionRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Prescription not found"));
//        prescription.setDispensed(true);
//        prescriptionRepository.save(prescription);
//        return "redirect:/pharmacist";
//    }

    @PostMapping("/pharmacist/dispense/{id}")
    public String dispensePrescription(@PathVariable Long id) {
        PrescriptionEntity prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        // Handle refillable prescriptions
        if (prescription.isRefillable() && prescription.getRefillsRemaining() > 0) {
            prescription.setRefillsRemaining(prescription.getRefillsRemaining() - 1);

            // If at least one has been dispensed, mark as partially dispensed
            if (prescription.getRefillsRemaining() > 0) {
                prescription.setDispensed(true);  // Mark as partially dispensed
            } else {
                // If no more refills, mark as fully dispensed
                prescription.setDispensed(true);
            }
        } else if (!prescription.isRefillable()) {
            // For non-refillable prescriptions, mark as dispensed
            prescription.setDispensed(true);
        } else {
            throw new RuntimeException("No refills remaining.");
        }

        prescriptionRepository.save(prescription);
        return "redirect:/pharmacist";
    }


//    @GetMapping("/pharmacist/generic-substitutes/{id}")
//    public String viewGenericSubstitutes(@PathVariable Long id, Model model) {
//        Medication medication = medicationRepository.findById(id).orElseThrow();
//        List<Medication> substitutes = medicationRepository.findByGenericAvailable(true);
//        model.addAttribute("medication", medication);
//        model.addAttribute("substitutes", substitutes);
//        return "pharmacist/generic-substitutes";
//    }

    @GetMapping("/pharmacist/generic-substitutes/{id}")
    public String viewGenericSubstitutes(@PathVariable Long id, Model model) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medication not found"));

        List<Medication> genericSubstitutes = medication.getGenericSubstitutes();
        model.addAttribute("medication", medication);
        model.addAttribute("genericSubstitutes", genericSubstitutes);

        return "pharmacist/generic-substitutes";
    }



}

