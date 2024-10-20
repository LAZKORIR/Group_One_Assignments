package com.goupone.prescription.system.prescriptionmanagementystem.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medication;
    private String dosage;
    private boolean refillable;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;


    // Many prescriptions can belong to one physician
    @ManyToOne
    @JoinColumn(name = "physician_id")
    private Physician physician;

    // Constructors, Getters, and Setters
    public Prescription() {}

    public Prescription(String medication, String dosage, boolean refillable, Patient patient) {
        this.medication = medication;
        this.dosage = dosage;
        this.refillable = refillable;
        this.patient = patient;
    }




    public boolean isRefillable() {
        return refillable;
    }

    public void setRefillable(boolean refillable) {
        this.refillable = refillable;
    }

}


