package com.goupone.prescription.system.prescriptionmanagementystem.repository;


import com.goupone.prescription.system.prescriptionmanagementystem.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, Long> {
    //List<PrescriptionEntity> findByPatientId(Long patientId);
    
    //List<PrescriptionEntity> findByPhysicianId(Long physicianId);

    //List<Prescription> findByPhysicianName(String physicianName);
}

