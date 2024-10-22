package com.goupone.prescription.system.prescriptionmanagementystem.repository;

import com.goupone.prescription.system.prescriptionmanagementystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}

