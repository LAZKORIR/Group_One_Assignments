package com.goupone.prescription.system.prescriptionmanagementystem.service;


import com.goupone.prescription.system.prescriptionmanagementystem.model.Prescription;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // Marks this class as a Spring-managed bean
public class PrescriptionServiceImpl implements PrescriptionService {

    // Mock Data: In a real-world scenario, these would come from a database.
    private final List<Prescription> prescriptions = new ArrayList<>();

    public PrescriptionServiceImpl() {
        // Adding some sample prescriptionEntities for testing.
        prescriptions.add(new Prescription(1, "2024-10-15", "2025-10-15", "John Doe", "Dr. Smith", "Pharmacist A"));
        prescriptions.add(new Prescription(2, "2024-09-01", "2025-09-01", "Alice", "Dr. Johnson", "Pharmacist B"));
    }

    @Override
    public List<Prescription> getPrescriptionsByPhysician(String physicianName) {
        // Filter prescriptionEntities by physician name.
        List<Prescription> result = new ArrayList<>();
        for (Prescription p : prescriptions) {
            if (p.getPrescribingPhysician().equalsIgnoreCase(physicianName)) {
                result.add(p);
            }
        }
        return result;
    }

    @Override
    public List<Prescription> getPrescriptionHistoryByPatient(String patientName) {
        // Filter prescriptionEntities by patient name.
        List<Prescription> result = new ArrayList<>();
        for (Prescription p : prescriptions) {
            if (p.getPatient().equalsIgnoreCase(patientName)) {
                result.add(p);
            }
        }
        return result;
    }

    @Override
    public List<Prescription> getPrescriptionsForPharmacist() {
        // Simply return all prescriptionEntities for now.
        return prescriptions;
    }
}

