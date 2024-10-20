package com.goupone.prescription.system.prescriptionmanagementystem.controller;


import com.goupone.prescription.system.prescriptionmanagementystem.entity.Prescription;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PatientRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/prescription")
public class PrescriptionController {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/add/{patientId}")
    public String showAddPrescriptionForm(@PathVariable Long patientId, Model model) {
        Prescription prescription = new Prescription();
        prescription.setPatient(patientRepository.findById(patientId).orElse(null));
        model.addAttribute("prescription", prescription);
        return "prescription/add";
    }

    @PostMapping("/add")
    public String addPrescription(@ModelAttribute Prescription prescription) {
        prescriptionRepository.save(prescription);
        return "redirect:/patient/" + prescription.getPatient().getId();
    }
}



