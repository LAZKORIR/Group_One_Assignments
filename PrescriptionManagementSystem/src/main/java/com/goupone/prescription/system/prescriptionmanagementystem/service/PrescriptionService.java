package com.goupone.prescription.system.prescriptionmanagementystem.service;


import com.goupone.prescription.system.prescriptionmanagementystem.entity.PrescriptionEntity;
import com.goupone.prescription.system.prescriptionmanagementystem.model.Prescription;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PrescriptionService {

    List<Prescription> getPrescriptionsByPhysician(String physicianName);

    List<Prescription> getPrescriptionHistoryByPatient(String patientName);

    List<Prescription> getPrescriptionsForPharmacist();

    List<PrescriptionEntity> getAllPrescriptions();


}

