package com.goupone.prescription.system.prescriptionmanagementystem.repository;

import com.goupone.prescription.system.prescriptionmanagementystem.entity.Physician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicianRepository extends JpaRepository<Physician, Long> {

}


