package com.goupone.prescription.system.prescriptionmanagementystem.controller;

import com.goupone.prescription.system.prescriptionmanagementystem.entity.Prescription;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.MedicationRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PatientRepository;
import com.goupone.prescription.system.prescriptionmanagementystem.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PhysicianController {
    @Autowired
    private PatientRepository patientRepository;


    private final UtilityService utilityService;
    @Autowired
    MedicationRepository medicationRepository;

    public PhysicianController(UtilityService utilityService) {
        this.utilityService = utilityService;
    }


    // This will load the physician's home page with the list of prescriptions
    @GetMapping("/physician")
    public String getPhysicianHomePage(Model model) {
       return utilityService.getPhysicianHomePage(model);
    }


    @GetMapping("/physician/prescribe")
    public String showPrescribeForm(Model model) {
        model.addAttribute("prescription", new Prescription());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("medications", medicationRepository.findAll());
        return "physician/prescribe-medicine";
    }


    @PostMapping("/physician/prescribe")
    public String prescribeMedicine(
            @RequestParam Long patientId,
            @RequestParam List<Long> medications,
            @RequestParam Map<String, String> dosages,
            @RequestParam(required = false) Map<String, String> requiresRefill,
            @RequestParam(required = false) Map<String, String> refillCounts) {

        // Log incoming raw data for debugging
        System.out.println("Received Dosages: " + dosages);
        System.out.println("Received Requires Refill: " + requiresRefill);
        System.out.println("Received Refill Counts: " + refillCounts);

        // Filter and parse the data from the form submission
        Map<Long, String> filteredDosages = parseMedicationData(dosages);
        Map<Long, Boolean> filteredRequiresRefill = parseBooleanData(requiresRefill);
        Map<Long, Integer> filteredRefillCounts = parseIntegerData(refillCounts);

        // Log for debugging
        System.out.println("Filtered Dosages: " + filteredDosages);
        System.out.println("Filtered Requires Refill: " + filteredRequiresRefill);
        System.out.println("Filtered Refill Counts: " + filteredRefillCounts);

        // Call the service with the parsed data
        return utilityService.prescribeMedicine(
                patientId, medications, filteredDosages, filteredRequiresRefill, filteredRefillCounts);
    }

    // Helper method to parse medication-related string data into a Long-keyed map
    private Map<Long, String> parseMedicationData(Map<String, String> input) {
        if (input == null) return Collections.emptyMap();
        return input.entrySet().stream()
                .filter(entry -> entry.getKey().matches("dosages\\[\\d+\\]"))
                .collect(Collectors.toMap(
                        entry -> Long.parseLong(entry.getKey().replaceAll("\\D", "")),
                        Map.Entry::getValue
                ));
    }

    // Helper method to parse boolean data from the form
    private Map<Long, Boolean> parseBooleanData(Map<String, String> input) {
        if (input == null) return Collections.emptyMap();
        return input.entrySet().stream()
                .filter(entry -> entry.getKey().matches("requiresRefill\\[\\d+\\]"))
                .collect(Collectors.toMap(
                        entry -> Long.parseLong(entry.getKey().replaceAll("\\D", "")),
                        entry -> Boolean.parseBoolean(entry.getValue())
                ));
    }

    // Helper method to parse integer data from the form
    private Map<Long, Integer> parseIntegerData(Map<String, String> input) {
        if (input == null) return Collections.emptyMap();
        return input.entrySet().stream()
                .filter(entry -> entry.getKey().matches("refillCounts\\[\\d+\\]") && !entry.getValue().isEmpty())
                .collect(Collectors.toMap(
                        entry -> Long.parseLong(entry.getKey().replaceAll("\\D", "")),
                        entry -> Integer.parseInt(entry.getValue())
                ));
    }

}




