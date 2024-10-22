package com.goupone.prescription.system.prescriptionmanagementystem.repository;


import com.goupone.prescription.system.prescriptionmanagementystem.entity.Patient;
import com.goupone.prescription.system.prescriptionmanagementystem.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, Long> {
    List<PrescriptionEntity> findByPatient(Patient patient);

    List<PrescriptionEntity> findAll();

}

