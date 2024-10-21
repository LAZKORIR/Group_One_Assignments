package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.entity.Medication;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.Patient;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.PrescriptionEntity;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.MedicationRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PatientRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PrescriptionRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
//@RequestMapping("/physician")
public class PhysicianController {
    @Autowired
    private PatientRepository patientRepository;


    private final PrescriptionService prescriptionService;
    @Autowired
    MedicationRepository medicationRepository;
    @Autowired
    PrescriptionRepository prescriptionRepository;

    public PhysicianController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }


    // This will load the physician's home page with the list of prescriptions
    @GetMapping("/physician")
    public String getPhysicianHomePage(Model model) {
        // Fetch all prescriptions to display in the view
        List<PrescriptionEntity> prescriptions = prescriptionService.getAllPrescriptions();
        model.addAttribute("prescriptions", prescriptions);
        return "physician/physicianHomePage";  // Points to physician.html template
    }

    @GetMapping("/physician/add")
    public String showAddPatientForm(Model model) {
        model.addAttribute("patient", new Patient());  // Empty patient object for the form
        return "physician/add-patient";  // Render the add-patient.html view
    }

    @PostMapping("/physician/add")
    public String addPatient(@ModelAttribute Patient patient) {
        patientRepository.save(patient);  // Save patient to the database
        return "redirect:/physician/physicianHomePage";  // Redirect back to the patient dashboard
    }


    @GetMapping("/physician/prescribe")
    public String showPrescribeForm(Model model) {
        model.addAttribute("prescription", new PrescriptionEntity());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("medications", medicationRepository.findAll());
        return "physician/prescribe-medicine";
    }

    @PostMapping("/prescribe")
    public String prescribeMedicine(@ModelAttribute PrescriptionEntity prescription,
                                    @RequestParam List<Long> medications) {
        List<Medication> selectedMedications = medicationRepository.findAllById(medications);
        prescription.setMedications(selectedMedications);
        prescriptionRepository.save(prescription);
        return "redirect:/physician";
    }




}




