package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.model.Prescription;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PatientRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/physician")
public class PhysicianController {
    @Autowired
    private PatientRepository patientRepository;


        private final PrescriptionService prescriptionService;

        public PhysicianController(PrescriptionService prescriptionService) {
            this.prescriptionService = prescriptionService;
        }

        // Default landing page: List prescriptions given by the physician
        @GetMapping("/physician")
        public String viewPrescriptions(Model model) {
            List<Prescription> prescriptions = prescriptionService.getPrescriptionsByPhysician("John Doe");
            model.addAttribute("prescriptionEntities", prescriptions);
            return "physician/home";
        }

        // Navigate to Add Patient Page
        @GetMapping("/physician/add-patient")
        public String addPatient() {
            return "physician/add-patient";
        }

        // Navigate to Give PrescriptionEntity Page
        @GetMapping("/physician/give-prescription")
        public String givePrescription() {
            return "physician/give-prescription";
        }
    }




