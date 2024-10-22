package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.entity.Medication;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.Patient;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.Physician;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.PrescriptionEntity;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.MedicationRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PatientRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PhysicianRepository;
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

    @Autowired
    PhysicianRepository physicianRepository;

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
        return "redirect:/physician";  // Redirect back to the patient dashboard
    }


    @GetMapping("/physician/prescribe")
    public String showPrescribeForm(Model model) {
        model.addAttribute("prescription", new PrescriptionEntity());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("medications", medicationRepository.findAll());
        return "physician/prescribe-medicine";
    }

    @PostMapping("/physician/prescribe")
    public String prescribeMedicine(
            @ModelAttribute PrescriptionEntity prescription,
            @RequestParam Long patientId,
            @RequestParam List<Long> medications,
            Model model) {

        // Fetch the patient and medications from the database
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        List<Medication> selectedMedications = medicationRepository.findAllById(medications);

        // Set the relationships
        prescription.setPatient(patient);
        //prescription.setMedication(selectedMedications);

        // Assign the currently logged-in physician (for now, assuming Physician with ID=1)
        Physician physician = physicianRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Physician not found"));
        System.out.println("physician =="+physician);
        //prescription.setPhysician(physician);  // This is the missing piece!

        // Save the prescription
        prescriptionRepository.save(prescription);

        // Redirect to the physician dashboard
        return "redirect:/physician";
    }





}




