package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.entity.PrescriptionEntity;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.MedicationRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PatientRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PhysicianRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PrescriptionRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
//@RequestMapping("/physician")
public class PhysicianController {
    @Autowired
    private PatientRepository patientRepository;


    private final PrescriptionService prescriptionService;
    @Autowired
    MedicationRepository medicationRepository;
    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    PhysicianRepository physicianRepository;

    public PhysicianController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }


    // This will load the physician's home page with the list of prescriptions
    @GetMapping("/physician")
    public String getPhysicianHomePage(Model model) {
        // Fetch all prescriptions to display in the view
        List<PrescriptionEntity> prescriptions = prescriptionService.getAllPrescriptions();
        model.addAttribute("prescriptions", prescriptions);
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // Get the logged-in username

        // Add username to the model to display in the HTML
        model.addAttribute("username", "Dr. "+username);
        return "physician/physicianHomePage";  // Points to physician.html template
    }


    @GetMapping("/physician/prescribe")
    public String showPrescribeForm(Model model) {
        model.addAttribute("prescription", new PrescriptionEntity());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("medications", medicationRepository.findAll());
        return "physician/prescribe-medicine";
    }

    @PostMapping("/physician/prescribe")
    public String prescribeMedicine(
            @RequestParam Long patientId,
            @RequestParam List<Long> medications,
            @RequestParam String dosage) {

        return prescriptionService.prescribeMedicine(patientId,medications,dosage);
    }







}




