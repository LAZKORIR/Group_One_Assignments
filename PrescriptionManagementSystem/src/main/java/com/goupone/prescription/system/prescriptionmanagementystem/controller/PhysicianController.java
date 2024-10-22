package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.entity.PrescriptionEntity;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.MedicationRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PatientRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PhysicianController {
    @Autowired
    private PatientRepository patientRepository;


    private final PrescriptionService prescriptionService;
    @Autowired
    MedicationRepository medicationRepository;

    public PhysicianController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }


    // This will load the physician's home page with the list of prescriptions
    @GetMapping("/physician")
    public String getPhysicianHomePage(Model model) {
       return prescriptionService.getPhysicianHomePage(model);
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




