package com.goupone.prescription.system.prescriptionmanagementystem.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String insuranceProvider;
    private String insurancePolicyNumber;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PrescriptionEntity> prescriptionEntities = new ArrayList<>();

}


