package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.entity.Patient;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.PrescriptionEntity;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.User;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PatientRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PrescriptionRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.UserRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
//@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    private final PrescriptionService prescriptionService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PatientRepository patientRepository;

    public PatientController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping("/patient")
    public String viewPrescriptions(Model model, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Patient patient = patientRepository.findByName(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        List<PrescriptionEntity> prescriptions = prescriptionRepository.findByPatient(patient);
        model.addAttribute("prescriptions", prescriptions);

        return "patient/patientHomePage";
    }


//    @GetMapping("/patient")
//    public String viewPrescriptionHistory(Model model) {
//        List<PrescriptionEntity> prescriptions = prescriptionService.getAllPrescriptions();
//        model.addAttribute("prescriptions", prescriptions);
//        return "patient/patientHomePage";  // Redirect to the home page view for patients
//    }

}



