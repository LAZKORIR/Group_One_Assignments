package com.goupone.prescription.system.prescriptionmanagementystem.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String unit;
    private String sideEffects;
    private boolean genericAvailable;

}



