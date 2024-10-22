package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.service.PrescriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class PatientController {

    private final PrescriptionService prescriptionService;

    public PatientController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping("/patient")
    public String viewPrescriptions(Model model, Principal principal) {
        return prescriptionService.viewPrescriptions(model, principal);
    }


}



