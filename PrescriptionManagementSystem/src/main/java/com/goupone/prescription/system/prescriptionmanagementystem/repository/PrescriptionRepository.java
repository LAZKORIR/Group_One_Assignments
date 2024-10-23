package com.goupone.prescription.system.prescriptionmanagementystem.repository;


import com.goupone.prescription.system.prescriptionmanagementystem.entity.Patient;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPatient(Patient patient);

    List<Prescription> findAll();

}

