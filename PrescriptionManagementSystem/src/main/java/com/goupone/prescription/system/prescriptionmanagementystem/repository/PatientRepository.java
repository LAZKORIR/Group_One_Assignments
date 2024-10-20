package com.goupone.prescription.system.prescriptionmanagementystem.repository;


import com.goupone.prescription.system.prescriptionmanagementystem.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}


