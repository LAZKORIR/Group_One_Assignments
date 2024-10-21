package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.repository.PrescriptionRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

        private final PrescriptionService prescriptionService;

        public PatientController(PrescriptionService prescriptionService) {
            this.prescriptionService = prescriptionService;
        }

        // Default landing page: View prescription history
//        @GetMapping("/patient")
//        public String viewPrescriptionHistory(Model model) {
//            List<PrescriptionEntity> prescriptions = prescriptionService.getPrescriptionHistory();
//            model.addAttribute("prescriptions", prescriptions);
//            return "patient/home";
//        }

    @GetMapping("/patient")
    public String viewPrescriptionHistory(Model model) {
        model.addAttribute("prescriptions", prescriptionService.getPrescriptionHistoryByPatient("John Doe"));
        return "patient/home";  // Redirect to the home page view for patients
    }

}



