package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.entity.Prescription;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.MedicationRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PatientRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class PhysicianController {
    @Autowired
    private PatientRepository patientRepository;


    private final UtilityService utilityService;
    @Autowired
    MedicationRepository medicationRepository;

    public PhysicianController(UtilityService utilityService) {
        this.utilityService = utilityService;
    }


    // This will load the physician's home page with the list of prescriptions
    @GetMapping("/physician")
    public String getPhysicianHomePage(Model model) {
       return utilityService.getPhysicianHomePage(model);
    }


    @GetMapping("/physician/prescribe")
    public String showPrescribeForm(Model model) {
        model.addAttribute("prescription", new Prescription());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("medications", medicationRepository.findAll());
        return "physician/prescribe-medicine";
    }

    @PostMapping("/physician/prescribe")
    public String prescribeMedicine(
            @RequestParam Long patientId,
            @RequestParam List<Long> medications,
            @RequestParam Map<Long, String> dosages) {

        return utilityService.prescribeMedicine(patientId,medications,dosages);
    }







}




