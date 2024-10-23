package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.service.UtilityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class PatientController {

    private final UtilityService utilityService;

    public PatientController(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

    @GetMapping("/patient")
    public String viewPrescriptions(Model model, Principal principal) {
        return utilityService.viewPrescriptions(model, principal);
    }


}



