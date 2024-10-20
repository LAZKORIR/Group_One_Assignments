package com.goupone.prescription.system.prescriptionmanagementystem.service;


import com.goupone.prescription.system.prescriptionmanagementystem.entity.Patient;
import com.goupone.prescription.system.prescriptionmanagementystem.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {

    private final PatientRepository patientRepository;

    public PrescriptionService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllCustomers() {
        return patientRepository.findAll();
    }

    public Patient getCustomerById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }
}

