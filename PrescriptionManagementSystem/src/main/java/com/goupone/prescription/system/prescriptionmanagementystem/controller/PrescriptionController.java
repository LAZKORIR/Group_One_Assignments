package com.goupone.prescription.system.prescriptionmanagementystem.controller;


import com.goupone.prescription.system.prescriptionmanagementystem.entity.PrescriptionEntity;
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
        PrescriptionEntity prescriptionEntity = new PrescriptionEntity();
        prescriptionEntity.setPatient(patientRepository.findById(patientId).orElse(null));
        model.addAttribute("prescriptionEntity", prescriptionEntity);
        return "prescriptionEntity/add";
    }

    @PostMapping("/add")
    public String addPrescription(@ModelAttribute PrescriptionEntity prescriptionEntity) {
        prescriptionRepository.save(prescriptionEntity);
        return "redirect:/patient/" + prescriptionEntity.getPatient().getId();
    }
}



