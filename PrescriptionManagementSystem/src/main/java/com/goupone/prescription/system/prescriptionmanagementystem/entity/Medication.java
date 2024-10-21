package com.goupone.prescription.system.prescriptionmanagementystem.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String unit;
    private String sideEffects;
    @Column(nullable = false)
    private boolean genericAvailable = false;

    public  Medication(String name,String unit,String sideEffects,boolean genericAvailable){
        this.name=name;
        this.unit=unit;
        this.sideEffects=sideEffects;
        this.genericAvailable=genericAvailable;
    }

}



