package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.entity.Prescription;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @GetMapping("/{id}/prescriptions")
    public String viewPrescriptionHistory(@PathVariable Long id, Model model) {
        List<Prescription> prescriptions = prescriptionRepository.findByPatientId(id);
        model.addAttribute("prescriptions", prescriptions);
        return "patient/prescription-history";
    }
}



